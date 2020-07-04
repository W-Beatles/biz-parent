package cn.waynechu.utility.domain.repository;

import cn.waynechu.springcloud.common.util.StringUtil;
import cn.waynechu.utility.dal.condition.DictionaryCondition;
import cn.waynechu.utility.dal.dataobject.DictionaryDO;
import cn.waynechu.utility.dal.mapper.DictionaryMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhuwei
 * @since 2020/6/29 20:10
 */
@Repository
public class DictionaryRepository {

    @Autowired
    private DictionaryMapper mapper;

    private void fillWrapper(QueryWrapper<DictionaryDO> wrapper, DictionaryCondition condition) {
        if (condition.getPid() != null) {
            wrapper.eq(DictionaryDO.COL_PID, condition.getPid());
        }
        if (StringUtil.isNotEmpty(condition.getDicTypeCode())) {
            wrapper.eq(DictionaryDO.COL_DIC_TYPE_CODE, condition.getDicTypeCode());
        }
        if (StringUtil.isNotEmpty(condition.getDicCode())) {
            wrapper.eq(DictionaryDO.COL_DIC_CODE, condition.getDicCode());
        }
        if (StringUtil.isNotEmpty(condition.getDicTypeCodeLike())) {
            wrapper.like(DictionaryDO.COL_DIC_TYPE_CODE, condition.getDicTypeCodeLike());
        }
        if (StringUtil.isNotEmpty(condition.getDicCodeLike())) {
            wrapper.like(DictionaryDO.COL_DIC_CODE, condition.getDicCodeLike());
        }
        if (StringUtil.isNotEmpty(condition.getDicDescLike())) {
            wrapper.like(DictionaryDO.COL_DIC_DESC, condition.getDicDescLike());
        }
        wrapper.eq(DictionaryDO.COL_DELETED_STATUS, false);
    }

    public List<DictionaryDO> selectByCondition(DictionaryCondition condition) {
        QueryWrapper<DictionaryDO> wrapper = new QueryWrapper<>();
        this.fillWrapper(wrapper, condition);
        return mapper.selectList(wrapper);
    }

    public int countByCondition(DictionaryCondition condition) {
        QueryWrapper<DictionaryDO> wrapper = new QueryWrapper<>();
        this.fillWrapper(wrapper, condition);
        return mapper.selectCount(wrapper);
    }

    public Long create(DictionaryDO dictionaryDO) {
        mapper.insert(dictionaryDO);
        return dictionaryDO.getId();
    }

    public void remove(Long id) {
        DictionaryDO dictionaryDO = new DictionaryDO();
        dictionaryDO.setId(id);
        dictionaryDO.setDeletedStatus(true);
        mapper.updateById(dictionaryDO);
    }

    public DictionaryDO selectMaxSortNum(String dicTypeCode) {
        QueryWrapper<DictionaryDO> wrapper = new QueryWrapper<>();
        wrapper.eq(DictionaryDO.COL_DIC_TYPE_CODE, dicTypeCode);
        wrapper.eq(DictionaryDO.COL_DELETED_STATUS, false);
        wrapper.orderByDesc(DictionaryDO.COL_SORT_NUM);
        wrapper.last("limit 1");
        return mapper.selectOne(wrapper);
    }
}
