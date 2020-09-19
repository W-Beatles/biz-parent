package cn.waynechu.utility.domain.service;

import cn.waynechu.facade.common.enums.BizErrorCodeEnum;
import cn.waynechu.facade.common.exception.BizException;
import cn.waynechu.facade.common.page.BizPageInfo;
import cn.waynechu.springcloud.common.util.UserUtil;
import cn.waynechu.utility.dal.condition.DictionaryCondition;
import cn.waynechu.utility.dal.dataobject.DictionaryDO;
import cn.waynechu.utility.domain.convert.DictionaryConvert;
import cn.waynechu.utility.domain.repository.DictionaryRepository;
import cn.waynechu.utility.facade.request.CreateDictionaryRequest;
import cn.waynechu.utility.facade.request.SearchDictionaryRequest;
import cn.waynechu.utility.facade.request.UpdateDictionaryRequest;
import cn.waynechu.utility.facade.response.DictionaryMiniResponse;
import cn.waynechu.utility.facade.response.DictionaryResponse;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zhuwei
 * @since 2020-06-29 00:07
 */
@Service
public class DictionaryService {

    @Autowired
    private DictionaryRepository dictionaryRepository;

    @Autowired
    private DictionaryTypeService dictionaryTypeService;

    /**
     * 搜索字典列表
     *
     * @param request req
     * @return 分页信息
     */
    public BizPageInfo<DictionaryResponse> search(SearchDictionaryRequest request) {
        DictionaryCondition condition = new DictionaryCondition();
        condition.setPid(request.getPid());
        condition.setDicTypeCodeLike(request.getDicTypeCodeLike());
        condition.setDicCodeLike(request.getDicCodeLike());
        condition.setDicDescLike(request.getDicDescLike());
        condition.setOrderBy(DictionaryDO.COL_SORT_NUM);

        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<DictionaryDO> dictionaryDOList = dictionaryRepository.selectByCondition(condition);
        List<DictionaryResponse> dictionaryResponseList = DictionaryConvert.toDictionaryResponseList(dictionaryDOList);
        return BizPageInfo.of(dictionaryDOList).replace(dictionaryResponseList);
    }

    /**
     * 根据字典类型编码查询字典列表
     *
     * @param dicTypeCode 字典类型编码
     * @return 字典列表
     */
    public List<DictionaryMiniResponse> listByType(String dicTypeCode) {
        DictionaryCondition condition = new DictionaryCondition();
        condition.setDicTypeCode(dicTypeCode);
        List<DictionaryDO> dictionaryDOList = dictionaryRepository.selectByCondition(condition);
        return DictionaryConvert.toDictionaryMinResponseList(dictionaryDOList);
    }

    /**
     * 添加字典
     *
     * @param request req
     * @return 字典id
     */
    public Long create(CreateDictionaryRequest request) {
        // 校验类型编码
        String dicTypeCode = request.getDicTypeCode();
        dictionaryTypeService.checkTypeCodeExist(dicTypeCode);

        // 校验字典编码
        String dicCode = request.getDicCode();
        this.checkDicCodeNotExist(dicTypeCode, dicCode);

        // 查找最大排序号
        DictionaryDO maxSortDictionary = dictionaryRepository.selectMaxSortNum(dicTypeCode);
        int maxSort = maxSortDictionary == null ? 0 : maxSortDictionary.getSortNum();

        DictionaryDO dictionaryDO = new DictionaryDO();
        dictionaryDO.setPid(request.getPid());
        dictionaryDO.setDicTypeCode(dicTypeCode);
        dictionaryDO.setDicCode(request.getDicCode());
        dictionaryDO.setDicValue(request.getDicValue());
        dictionaryDO.setDicDesc(request.getDicDesc());
        dictionaryDO.setSortNum(maxSort + 1);
        dictionaryDO.setFixedStatus(request.getFixedStatus());
        dictionaryDO.setCreatedUser(UserUtil.getEmail());
        dictionaryDO.setCreatedTime(LocalDateTime.now());
        return dictionaryRepository.create(dictionaryDO);
    }

    /**
     * 编辑字典
     *
     * @param request req
     * @return 字典id
     */
    public Long update(UpdateDictionaryRequest request) {
        // 校验字典编码
        String dicCode = request.getDicCode();
        this.checkDicCodeNotExist(request.getDicTypeCode(), dicCode);

        DictionaryDO dictionaryDO = new DictionaryDO();
        dictionaryDO.setId(request.getId());
        dictionaryDO.setPid(request.getPid());
        dictionaryDO.setDicCode(request.getDicCode());
        dictionaryDO.setDicValue(request.getDicValue());
        dictionaryDO.setDicDesc(request.getDicDesc());
        dictionaryDO.setFixedStatus(request.getFixedStatus());
        dictionaryDO.setUpdatedUser(UserUtil.getEmail());
        dictionaryDO.setUpdatedTime(LocalDateTime.now());
        return dictionaryRepository.update(dictionaryDO);
    }

    /**
     * 删除字典
     *
     * @param id 字典id
     */
    public void remove(Long id) {
        dictionaryRepository.remove(id);
    }

    /**
     * 校验字典编码不存在
     *
     * @param dicTypeCode 类型编码
     * @param dicCode     字典编码
     */
    public void checkDicCodeNotExist(String dicTypeCode, String dicCode) {
        DictionaryCondition condition = new DictionaryCondition();
        condition.setDicTypeCode(dicTypeCode);
        condition.setDicCode(dicCode);
        condition.setDeletedStatus(false);
        int count = dictionaryRepository.countByCondition(condition);
        if (count > 0) {
            throw new BizException(BizErrorCodeEnum.DATA_ALREADY_EXIST, "字典编码已存在");
        }
    }
}
