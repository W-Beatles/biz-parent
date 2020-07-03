package cn.waynechu.utility.domain.service;

import cn.waynechu.facade.common.enums.BizErrorCodeEnum;
import cn.waynechu.facade.common.exception.BizException;
import cn.waynechu.facade.common.page.BizPageInfo;
import cn.waynechu.springcloud.common.util.UserUtil;
import cn.waynechu.utility.dal.condition.DictionaryTypeCondition;
import cn.waynechu.utility.dal.dataobject.DictionaryTypeDO;
import cn.waynechu.utility.domain.convert.DictionaryTypeConvert;
import cn.waynechu.utility.domain.repository.DictionaryTypeRepository;
import cn.waynechu.utility.facade.request.CreateDicTypeRequest;
import cn.waynechu.utility.facade.request.SearchDicTypeRequest;
import cn.waynechu.utility.facade.response.DicTypeResponse;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhuwei
 * @since 2020/7/3 17:34
 */
@Service
public class DictionaryTypeService {

    @Autowired
    private DictionaryTypeRepository dictionaryTypeRepository;

    /**
     * 搜索字典类型
     *
     * @param request req
     * @return 分页信息
     */
    public BizPageInfo<DicTypeResponse> search(SearchDicTypeRequest request) {
        DictionaryTypeCondition condition = new DictionaryTypeCondition();
        condition.setTypeCodeLike(request.getTypeCodeLike());
        condition.setAppIdLike(request.getAppIdLike());

        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<DictionaryTypeDO> dictionaryTypeDOList = dictionaryTypeRepository.selectByCondition(condition);
        List<DicTypeResponse> dicTypeResponseList = DictionaryTypeConvert.toDicTypeResponseList(dictionaryTypeDOList);
        return BizPageInfo.of(dictionaryTypeDOList).replace(dicTypeResponseList);
    }

    /**
     * 添加字典类型
     *
     * @param request req
     * @return 字典类型id
     */
    public Long create(CreateDicTypeRequest request) {
        this.checkTypeCodeExist(request.getTypeCode());

        DictionaryTypeDO typeDO = new DictionaryTypeDO();
        typeDO.setTypeCode(request.getTypeCode());
        typeDO.setAppId(request.getAppId());
        typeDO.setDescription(request.getDescription());
        typeDO.setCreatedUser(UserUtil.getEmail());
        return dictionaryTypeRepository.create(typeDO);
    }

    /**
     * 移除字典类型
     *
     * @param id 字典类型id
     */
    public void remove(Long id) {
        dictionaryTypeRepository.remove(id);
    }


    /**
     * 校验字典类型编码是否存在
     *
     * @param typeCode 类型编码
     */
    private void checkTypeCodeExist(String typeCode) {
        DictionaryTypeCondition condition = new DictionaryTypeCondition();
        condition.setTypeCode(typeCode);
        condition.setDeletedStatus(false);
        int count = dictionaryTypeRepository.countByCondition(condition);
        if (count > 0) {
            throw new BizException(BizErrorCodeEnum.DATA_ALREADY_EXIST, "类型编码已存在");
        }
    }
}
