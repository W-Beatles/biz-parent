package cn.waynechu.utility.domain.repository;

import cn.waynechu.springcloud.common.util.StringUtil;
import cn.waynechu.utility.dal.condition.DictionaryTypeCondition;
import cn.waynechu.utility.dal.dataobject.DictionaryTypeDO;
import cn.waynechu.utility.dal.mapper.DictionaryTypeMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhuwei
 * @since 2020/6/29 20:10
 */
@Repository
public class DictionaryTypeRepository {

    @Autowired
    private DictionaryTypeMapper mapper;

    private void fillWrapper(QueryWrapper<DictionaryTypeDO> wrapper, DictionaryTypeCondition condition) {
        if (StringUtil.isNotEmpty(condition.getTypeCode())) {
            wrapper.eq(DictionaryTypeDO.COL_TYPE_CODE, condition.getTypeCode());
        }
        if (StringUtil.isNotEmpty(condition.getTypeCodeLike())) {
            wrapper.like(DictionaryTypeDO.COL_TYPE_CODE, condition.getTypeCode());
        }
        if (StringUtil.isNotEmpty(condition.getAppIdLike())) {
            wrapper.like(DictionaryTypeDO.COL_TYPE_CODE, condition.getAppIdLike());
        }
        if (condition.getDeletedStatus() != null) {
            wrapper.eq(DictionaryTypeDO.COL_DELETED_STATUS, condition.getDeletedStatus());
        }
    }

    public List<DictionaryTypeDO> selectByCondition(DictionaryTypeCondition condition) {
        QueryWrapper<DictionaryTypeDO> wrapper = new QueryWrapper<>();
        this.fillWrapper(wrapper, condition);
        return mapper.selectList(wrapper);
    }

    public int countByCondition(DictionaryTypeCondition condition) {
        QueryWrapper<DictionaryTypeDO> wrapper = new QueryWrapper<>();
        this.fillWrapper(wrapper, condition);
        return mapper.selectCount(wrapper);
    }

    public Long create(DictionaryTypeDO typeDO) {
        mapper.insert(typeDO);
        return typeDO.getId();
    }

    public void remove(Long id) {
        DictionaryTypeDO typeDO = new DictionaryTypeDO();
        typeDO.setId(id);
        typeDO.setDeletedStatus(true);
        mapper.updateById(typeDO);
    }
}
