package cn.waynechu.utility.dal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.waynechu.utility.dal.dataobject.RegionDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhuwei
 * @since 2019-08-16 23:22
 */
public interface RegionMapper extends BaseMapper<RegionDO> {
    int updateBatch(List<RegionDO> list);

    int batchInsert(@Param("list") List<RegionDO> list);

    int insertOrUpdate(RegionDO record);

    int insertOrUpdateSelective(RegionDO record);
}