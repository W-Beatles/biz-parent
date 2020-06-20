package cn.waynechu.archetype.portal.domain.repository;

import cn.waynechu.archetype.portal.dal.condition.ListArchetypeCondition;
import cn.waynechu.archetype.portal.dal.dataobject.ArchetypeDO;
import cn.waynechu.archetype.portal.dal.mapper.project.ArchetypeMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhuwei
 * @date 2020-06-20 21:37
 */
@Repository
public class ArchetypeRepository {

    @Autowired
    private ArchetypeMapper mapper;

    public List<ArchetypeDO> listByCondition(ListArchetypeCondition condition) {
        QueryWrapper<ArchetypeDO> wrapper = new QueryWrapper<>();
        if (condition.getId() != null) {
            wrapper.eq(ArchetypeDO.COL_ID, condition.getAppId());
        }
        if (condition.getAppId() != null) {
            wrapper.eq(ArchetypeDO.COL_APP_ID, condition.getAppId());
        }
        return mapper.selectList(wrapper);
    }
}
