package cn.waynechu.utility.dal.mapper;

import cn.waynechu.utility.dal.dataobject.DictionaryDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhuwei
 * @date 2020-07-04 15:28
 */
public interface DictionaryMapper extends BaseMapper<DictionaryDO> {
    int updateBatch(List<DictionaryDO> list);

    int batchInsert(@Param("list") List<DictionaryDO> list);

    int insertOrUpdate(DictionaryDO record);

    int insertOrUpdateSelective(DictionaryDO record);
}