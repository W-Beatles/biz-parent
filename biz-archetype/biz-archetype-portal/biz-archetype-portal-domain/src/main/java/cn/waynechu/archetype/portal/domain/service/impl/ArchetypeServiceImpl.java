package cn.waynechu.archetype.portal.domain.service.impl;

import cn.hutool.core.util.ZipUtil;
import cn.waynechu.archetype.portal.common.enums.StatusCodeEnum;
import cn.waynechu.archetype.portal.common.util.SystemUtil;
import cn.waynechu.archetype.portal.dal.condition.ListArchetypeCondition;
import cn.waynechu.archetype.portal.dal.dataobject.ArchetypeDO;
import cn.waynechu.archetype.portal.domain.convert.ArchetypeConvert;
import cn.waynechu.archetype.portal.domain.repository.ArchetypeRepository;
import cn.waynechu.archetype.portal.domain.service.ArchetypeService;
import cn.waynechu.archetype.portal.facade.request.CreateArchetypeRequest;
import cn.waynechu.archetype.portal.facade.request.SearchArchetypeRequest;
import cn.waynechu.archetype.portal.facade.request.UpdateArchetypeRequest;
import cn.waynechu.archetype.portal.facade.response.ArchetypeResponse;
import cn.waynechu.archetype.portal.facade.response.SearchArchetypeResponse;
import cn.waynechu.facade.common.enums.BizErrorCodeEnum;
import cn.waynechu.facade.common.exception.BizException;
import cn.waynechu.facade.common.page.BizPageInfo;
import cn.waynechu.springcloud.common.excel.ExcelHelper;
import cn.waynechu.springcloud.common.util.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

/**
 * @author zhuwei
 * @since 2020-06-20 09:31
 */
@Slf4j
@Service
public class ArchetypeServiceImpl implements ArchetypeService, InitializingBean {

    @Value("${working.root.path}")
    private String workingRootPath;

    @Value("${git.user-name}")
    private String gitUserName;

    @Value("${git.password}")
    private String gitPassword;

    @Autowired
    private ArchetypeRepository archetypeRepository;

    @Autowired
    private Executor bizExecutor;

    @Autowired
    private ExcelHelper excelHelper;

    @Autowired
    private PageLoopHelper pageLoopHelper;

    @Override
    public void afterPropertiesSet() {
        this.initWorkingPath(workingRootPath);
    }

    @Override
    public BizPageInfo<SearchArchetypeResponse> search(SearchArchetypeRequest request) {
        ListArchetypeCondition condition = new ListArchetypeCondition();
        BeanUtils.copyProperties(request, condition);
        condition.setOrderBy(ArchetypeDO.COL_UPDATED_TIME);

        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<ArchetypeDO> archetypeDOList = archetypeRepository.listByCondition(condition);
        List<SearchArchetypeResponse> list = ArchetypeConvert.toSearchArchetypeResponse(archetypeDOList);
        return BizPageInfo.of(archetypeDOList).replace(list);
    }

    @Override
    public List<SearchArchetypeResponse> listAllPage(SearchArchetypeRequest request) {
        return pageLoopHelper.listAllPage(request, () -> search(request));
    }

    @Override
    public List<SearchArchetypeResponse> listAllPageConcurrency(SearchArchetypeRequest request) {
        return pageLoopHelper.listAllPage(request, 2, this::search);
    }

    @Override
    public List<SearchArchetypeResponse> listAllPageConcurrency2(SearchArchetypeRequest request) {
        List<SearchArchetypeResponse> returnValue = Collections.synchronizedList(new ArrayList<>());
        pageLoopHelper.listAllPage(request, 2, this::search, returnValue::addAll);
        return returnValue;
    }

    @Override
    public String export(SearchArchetypeRequest request) {
        String fileName = "原型列表 " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return excelHelper.exportForSid(fileName, request, () -> search(request), SearchArchetypeResponse.class);
    }

    @Override
    public Long create(CreateArchetypeRequest request) {
        // 校验AppID是否重复
        this.checkAppIdNotExist(request.getAppId());

        // 保存项目基本信息
        ArchetypeDO insertArchetypeDO = new ArchetypeDO();
        BeanUtil.copyProperties(request, insertArchetypeDO);
        insertArchetypeDO.setStatusCode(StatusCodeEnum.PENDING.getCode());
        insertArchetypeDO.setCreatedUser(UserUtil.getEmail());
        insertArchetypeDO.setCreatedTime(LocalDateTime.now());
        Long archetypeId = archetypeRepository.insert(insertArchetypeDO);

        this.asyncCreateArchetype(request, archetypeId);
        return archetypeId;
    }

    @Override
    public Long update(UpdateArchetypeRequest request) {
        Long archetypeId = request.getId();
        ArchetypeDO archetypeDO = this.checkArchetypeExist(archetypeId);
        if (Objects.equals(StatusCodeEnum.PENDING.getCode(), archetypeDO.getStatusCode())) {
            throw new BizException(BizErrorCodeEnum.ILLEGAL_STATE_ERROR, "原型正在生成，请稍候...");
        }

        // 更新项目基本信息
        ArchetypeDO updateArchetypeDO = new ArchetypeDO();
        BeanUtil.copyProperties(request, updateArchetypeDO);
        updateArchetypeDO.setStatusCode(StatusCodeEnum.PENDING.getCode());
        updateArchetypeDO.setUpdatedUser(UserUtil.getEmail());
        updateArchetypeDO.setUpdatedTime(LocalDateTime.now());
        archetypeRepository.updateById(updateArchetypeDO);

        this.asyncCreateArchetype(request, archetypeId);
        return archetypeId;
    }

    @Override
    public void download(Long id, HttpServletResponse response) {
        ArchetypeDO archetypeDO = this.checkArchetypeExist(id);
        if (!StatusCodeEnum.SUCCEED.getCode().equals(archetypeDO.getStatusCode())) {
            throw new BizException(BizErrorCodeEnum.ILLEGAL_STATE_ERROR, "项目骨架尚未生成");
        }
        String projectPath = workingRootPath + "project" + File.separator + id + File.separator;
        String fileName = archetypeDO.getAppName() + ".zip";
        FileUploadUtil.download(response, projectPath, fileName);
    }

    @Override
    public ArchetypeDO checkArchetypeExist(Long id) {
        ArchetypeDO archetypeDO = archetypeRepository.selectById(id);
        if (archetypeDO == null || Boolean.TRUE.equals(archetypeDO.getDeletedStatus())) {
            throw new BizException(BizErrorCodeEnum.DATA_NOT_EXIST, "原型不存在");
        }
        return archetypeDO;
    }

    @Override
    public ArchetypeResponse getById(Long id) {
        ArchetypeDO archetypeDO = this.checkArchetypeExist(id);
        return ArchetypeConvert.toArchetypeResponse(archetypeDO);
    }

    @Override
    public void remove(Long id) {
        this.checkArchetypeExist(id);
        archetypeRepository.removeById(id);

        String projectPath = workingRootPath + "project" + File.separator;
        File projectDir = new File(projectPath + id + File.separator);
        FileUtil.delDir(projectDir);
    }

    /**
     * 校验appId是否存在
     *
     * @param appId AppId
     */
    private void checkAppIdNotExist(String appId) {
        ListArchetypeCondition condition = new ListArchetypeCondition();
        condition.setAppId(appId);
        List<ArchetypeDO> archetypeDOList = archetypeRepository.listByCondition(condition);
        if (CollectionUtil.isNotNullOrEmpty(archetypeDOList)) {
            throw new BizException(BizErrorCodeEnum.DATA_ALREADY_EXIST, "该AppId已存在");
        }
    }

    /**
     * 异步创建项目原型
     *
     * @param request     req
     * @param archetypeId 原型id
     */
    private void asyncCreateArchetype(CreateArchetypeRequest request, final Long archetypeId) {
        bizExecutor.execute(() -> {
            StatusCodeEnum statusCode = StatusCodeEnum.FAILED;
            try {
                // TODO 2020/8/24 19:52 先默认一个archetype，等字典服务提供多级缓存懒加载工具后支持切换骨架模型
                String archetypeArtifactId = "biz-archetype-template-service";
                // 生成项目原型文件
                this.createProjectArchetype(archetypeId, archetypeArtifactId, request.getAppName(), request.getPackageName());

                // 打包压缩为ZIP
                String workPath = workingRootPath + "project" + File.separator + archetypeId + File.separator;
                String projectPath = workPath + request.getAppName();
                ZipUtil.zip(projectPath, projectPath + ".zip", true);

                // 上传git仓库
                if (Boolean.TRUE.equals(request.getGitUploadType())) {
                    UsernamePasswordCredentialsProvider provider = new UsernamePasswordCredentialsProvider(gitUserName, gitPassword);
                    String gitProjectPath = projectPath + "-repo";
                    Git git = Git.cloneRepository()
                            .setCredentialsProvider(provider)
                            .setURI(request.getGitUploadUrl()).setDirectory(new File(gitProjectPath))
                            .setBranch("master")
                            .call();
                    cn.hutool.core.io.FileUtil.copy(projectPath, gitProjectPath, true);
                    git.add().addFilepattern(".").call();
                    git.commit().setAuthor("developer", "developer@waynechu.cn").setMessage("initial project").call();
                    git.push().setRemote("origin").setRefSpecs(new RefSpec("master")).call();
                }
                statusCode = StatusCodeEnum.SUCCEED;
            } catch (Exception e) {
                log.warn("Create project archetype failed, archetypeId: {}, appId: {}", archetypeId, request.getAppId(), e);
            } finally {
                // 同步原型生成状态
                this.syncArchetypeStatus(archetypeId, statusCode, null, null);
            }
        });
    }

    /**
     * 同步原型生成状态
     *
     * @param archetypeId    原型id
     * @param statusCodeEnum 状态
     * @param downloadUrl    项目下载地址
     * @param gitUrl         项目git地址
     */
    private void syncArchetypeStatus(Long archetypeId, StatusCodeEnum statusCodeEnum, String downloadUrl, String gitUrl) {
        ArchetypeDO updateArchetypeDO = new ArchetypeDO();
        updateArchetypeDO.setId(archetypeId);
        updateArchetypeDO.setStatusCode(statusCodeEnum.getCode());
        updateArchetypeDO.setGitUrl(gitUrl);
        updateArchetypeDO.setDownloadUrl(downloadUrl);
        updateArchetypeDO.setUpdatedUser(UserUtil.getEmail());
        updateArchetypeDO.setUpdatedTime(LocalDateTime.now());
        archetypeRepository.updateById(updateArchetypeDO);
    }

    /**
     * 执行脚本创建项目骨架原型文件
     *
     * @param archetypeId 原型id
     * @param appName     项目名称
     * @param packageName 包名
     * @throws IOException e
     */
    private void createProjectArchetype(Long archetypeId, String archetypeArtifactId, String appName, String packageName) throws IOException {
        String cmd;
        if (SystemUtil.isWindows()) {
            // d:\archetype-generator\script\CreateProject.bat archetypeArtifactId appName packageName
            cmd = workingRootPath + "script" + File.separator + "CreateProject.bat "
                    + archetypeArtifactId + " " + appName + " " + packageName;
        } else {
            // /script/CreateProject.sh -a archetypeArtifactId -n appName -p packageName
            cmd = "sh " + workingRootPath + "script" + File.separator + "CreateProject.sh"
                    + " -a " + archetypeArtifactId + " -n " + appName + " -p " + packageName;
        }

        // 存放项目的目录
        String projectPath = workingRootPath + "project" + File.separator + archetypeId + File.separator;
        File projectDir = new File(projectPath);
        if (projectDir.exists()) {
            FileUtil.delDir(projectDir);
        }
        // noinspection ResultOfMethodCallIgnored
        projectDir.mkdir();

        // 生成项目骨架文件
        log.info("Cmd: {}", cmd);
        Process process = Runtime.getRuntime().exec(cmd, null, projectDir);
        StringBuilder result = new StringBuilder();
        InputStream is = process.getInputStream();
        Reader reader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(reader);
        for (String res; (res = bufferedReader.readLine()) != null; ) {
            result.append(res).append("\n");
        }
        log.info("Create project archetype info: {}", result.toString());

        bufferedReader.close();
        reader.close();
        is.close();
        process.getOutputStream().close();
    }

    /**
     * 初始化工作路径
     * <p>
     * 包括 script脚本 和 project文件夹
     *
     * @param workingRootPath 根路径
     */
    private void initWorkingPath(String workingRootPath) {
        // 复制script脚本
        String scriptPath = workingRootPath + "script" + File.separator;
        String[] scriptNames = {"CreateProject.bat", "CreateProject.sh"};
        for (String scriptName : scriptNames) {
            this.copyScript(scriptName, scriptPath);
        }
        // 脚本授权
        SystemUtil.setShellPermission(scriptPath + scriptNames[1]);


        // 创建project文件夹
        String projectPath = workingRootPath + "project" + File.separator;
        File projectFilePath = new File(projectPath);
        if (!projectFilePath.exists()) {
            // noinspection ResultOfMethodCallIgnored
            projectFilePath.mkdir();
        }
    }

    /**
     * 拷贝 /resources/script/ 目录下文件到指定路径
     *
     * @param scriptName 文件名字
     * @param scriptPath 指定路径
     */
    private void copyScript(String scriptName, String scriptPath) {
        InputStream is = this.getClass().getResourceAsStream("/script/" + scriptName);
        cn.hutool.core.io.FileUtil.writeFromStream(is, new File(scriptPath + scriptName));
    }
}



