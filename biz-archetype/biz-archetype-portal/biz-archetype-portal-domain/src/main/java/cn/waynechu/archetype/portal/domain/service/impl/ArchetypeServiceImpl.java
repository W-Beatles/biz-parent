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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

/**
 * @author zhuwei
 * @date 2020-06-20 09:31
 */
@Slf4j
@Service
public class ArchetypeServiceImpl implements ArchetypeService, InitializingBean {

    @Value("${working.root.path}")
    private String workingRootPath;

    @Autowired
    private ArchetypeRepository archetypeRepository;

    @Autowired
    private Executor bizExecutor;

    @Autowired
    private ExcelHelper excelHelper;

    @Override
    public void afterPropertiesSet() {
        this.initWorkingPath(workingRootPath);
    }

    @Override
    public BizPageInfo<SearchArchetypeResponse> search(SearchArchetypeRequest request) {
        ListArchetypeCondition condition = new ListArchetypeCondition();
        BeanUtils.copyProperties(request, condition);
        condition.setOrderBy(ArchetypeDO.COL_UPDATED_TIME).setIsAsc(false);

        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<ArchetypeDO> archetypeDOList = archetypeRepository.listByCondition(condition);
        List<SearchArchetypeResponse> list = ArchetypeConvert.toSearchArchetypeResponse(archetypeDOList);
        return BizPageInfo.of(archetypeDOList).replace(list);
    }

    @Override
    public String export(SearchArchetypeRequest request) {
        String fileName = "原型列表 " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return excelHelper.exportForSid(fileName, SearchArchetypeResponse.class, request, () -> search(request));
    }

    @Override
    public Long create(CreateArchetypeRequest request) {
        // 校验AppID是否重复
        this.checkAppIdExist(request.getAppId());

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
    private void checkAppIdExist(String appId) {
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
    private void asyncCreateArchetype(CreateArchetypeRequest request, Long archetypeId) {
        bizExecutor.execute(() -> {
            try {
                // 生成项目原型文件
                this.createProjectArchetype(archetypeId, request.getAppName(), request.getPackageName());

                // 上传git仓库
                if (Boolean.TRUE.equals(request.getGitUploadType())) {
                    // TODO 2020-06-22 01:17
                }

                // 压缩ZIP
                String projectPath = workingRootPath + "project" + File.separator + archetypeId + File.separator;
                String zipFileName = projectPath + request.getAppName();
                ZipUtil.zip(zipFileName, zipFileName + ".zip", true);

                // 同步原型生成状态
                this.syncArchetypeStatus(archetypeId, StatusCodeEnum.SUCCEED);
            } catch (Exception e) {
                log.warn("Create project archetype failed", e);
                this.syncArchetypeStatus(archetypeId, StatusCodeEnum.FAILED);
            }
        });
    }

    /**
     * 同步原型生成状态
     *
     * @param archetypeId    原型id
     * @param statusCodeEnum 状态
     */
    private void syncArchetypeStatus(Long archetypeId, StatusCodeEnum statusCodeEnum) {
        ArchetypeDO updateArchetypeDO = new ArchetypeDO();
        updateArchetypeDO.setId(archetypeId);
        updateArchetypeDO.setStatusCode(statusCodeEnum.getCode());
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
    private void createProjectArchetype(Long archetypeId, String appName, String packageName) throws IOException {
        String scriptName = SystemUtil.isWindows() ? "CreateProject.bat" : "CreateProject.sh";
        // d:\archetype-generator\script\CreateProject.bat appName packageName
        String cmd = workingRootPath + "script" + File.separator + scriptName + " " + appName + " " + packageName;
        // d:\archetype-generator\project\1\
        String projectPath = workingRootPath + "project" + File.separator + archetypeId + File.separator;
        File projectDir = new File(projectPath);
        if (projectDir.exists()) {
            FileUtil.delDir(projectDir);
        }
        // noinspection ResultOfMethodCallIgnored
        projectDir.mkdir();

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
        File scriptFilePath = new File(scriptPath);
        if (!scriptFilePath.exists()) {
            String[] scriptNames = {"CreateProject.bat", "CreateProject.sh"};
            for (String scriptName : scriptNames) {
                this.copyScript(scriptName, scriptPath);
            }
        }

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



