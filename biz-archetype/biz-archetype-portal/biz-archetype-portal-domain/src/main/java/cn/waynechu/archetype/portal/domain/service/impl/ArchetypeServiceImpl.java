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
import cn.waynechu.archetype.portal.facade.response.SearchArchetypeResponse;
import cn.waynechu.facade.common.enums.BizErrorCodeEnum;
import cn.waynechu.facade.common.exception.BizException;
import cn.waynechu.facade.common.page.BizPageInfo;
import cn.waynechu.springcloud.common.util.BeanUtil;
import cn.waynechu.springcloud.common.util.CollectionUtil;
import cn.waynechu.springcloud.common.util.FileUtil;
import cn.waynechu.springcloud.common.util.UserUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author zhuwei
 * @date 2020-06-20 09:31
 */
@Slf4j
@Service
public class ArchetypeServiceImpl implements ArchetypeService {

    @Autowired
    private ArchetypeRepository archetypeRepository;

    @Autowired
    private Executor bizExecutor;

    @Setter
    @Value("${working.root.path}")
    private String workingRootPath;

    @Override
    public BizPageInfo<SearchArchetypeResponse> search(SearchArchetypeRequest request) {
        ListArchetypeCondition condition = BeanUtil.beanTransfer(request, ListArchetypeCondition.class);
        List<ArchetypeDO> archetypeDOList = archetypeRepository.listByCondition(condition);
        List<SearchArchetypeResponse> list = ArchetypeConvert.toSearchArchetypeResponse(archetypeDOList);
        return BizPageInfo.of(archetypeDOList).replace(list);
    }

    @Override
    public Long create(CreateArchetypeRequest request) {
        // 校验应用名
        this.checkAppNameExist(request);

        // 保存项目基本信息
        ArchetypeDO insertArchetypeDO = new ArchetypeDO();
        BeanUtil.copyProperties(request, insertArchetypeDO);
        insertArchetypeDO.setStatusCode(StatusCodeEnum.PENDING.getCode());
        insertArchetypeDO.setCreatedUser(UserUtil.getEmail());
        insertArchetypeDO.setCreatedTime(LocalDateTime.now());
        Long archetypeId = archetypeRepository.insert(insertArchetypeDO);

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
                ZipUtil.zip(zipFileName, zipFileName + ".zip");

                // 同步原型生成状态
                this.syncArchetypeStatus(archetypeId, StatusCodeEnum.SUCCEED);
            } catch (Exception e) {
                log.warn("create project archetype failed", e);
                this.syncArchetypeStatus(archetypeId, StatusCodeEnum.FAILED);
            }
        });
        return archetypeId;
    }

    private void checkAppNameExist(CreateArchetypeRequest request) {
        ListArchetypeCondition condition = new ListArchetypeCondition();
        condition.setAppName(request.getAppName());
        List<ArchetypeDO> archetypeDOList = archetypeRepository.listByCondition(condition);
        if (CollectionUtil.isNotNullOrEmpty(archetypeDOList)) {
            throw new BizException(BizErrorCodeEnum.DATA_ALREADY_EXIST, "该应用已存在");
        }
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
        //noinspection ResultOfMethodCallIgnored
        projectDir.mkdir();

        Process process = Runtime.getRuntime().exec(cmd, null, projectDir);
        StringBuilder result = new StringBuilder();
        InputStream is = process.getInputStream();
        Reader reader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(reader);
        for (String res; (res = bufferedReader.readLine()) != null; ) {
            result.append(res).append("\n");
        }
        log.info("create project archetype info: {}", result.toString());

        bufferedReader.close();
        reader.close();
        is.close();
        process.getOutputStream().close();
    }
}



