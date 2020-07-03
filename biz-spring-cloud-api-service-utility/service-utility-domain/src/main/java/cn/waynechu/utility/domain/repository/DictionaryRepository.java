package cn.waynechu.utility.domain.repository;

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

    public List<DictionaryDO> selectByCondition(DictionaryCondition condition) {
        QueryWrapper<DictionaryDO> wrapper = this.buildWrapper(condition);
        return mapper.selectList(wrapper);
    }

    private QueryWrapper<DictionaryDO> buildWrapper(DictionaryCondition condition) {
        // TODO 2020/7/3 18:53
        return null;
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
}
