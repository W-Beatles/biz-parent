package cn.waynechu.utility.dal.mapper;

import cn.waynechu.utility.dal.dataobject.DictionaryTypeDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhuwei
 * @date 2020-06-28 23:22
 */
public interface DictionaryTypeMapper extends BaseMapper<DictionaryTypeDO> {
    int updateBatch(List<DictionaryTypeDO> list);

    int batchInsert(@Param("list") List<DictionaryTypeDO> list);

    int insertOrUpdate(DictionaryTypeDO record);

    int insertOrUpdateSelective(DictionaryTypeDO record);
}