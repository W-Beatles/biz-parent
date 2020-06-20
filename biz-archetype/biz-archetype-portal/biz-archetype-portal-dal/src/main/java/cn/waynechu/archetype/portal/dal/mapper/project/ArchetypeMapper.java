package cn.waynechu.archetype.portal.dal.mapper.project;

import cn.waynechu.archetype.portal.dal.dataobject.ArchetypeDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhuwei
 * @date 2020-06-20 22:14
 */
public interface ArchetypeMapper extends BaseMapper<ArchetypeDO> {
    int updateBatch(List<ArchetypeDO> list);

    int batchInsert(@Param("list") List<ArchetypeDO> list);

    int insertOrUpdate(ArchetypeDO record);

    int insertOrUpdateSelective(ArchetypeDO record);
}