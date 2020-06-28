package cn.waynechu.utility.dal.mapper;

import cn.waynechu.utility.dal.dataobject.DictionaryDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhuwei
 * @date 2020-06-28 23:22
 */
public interface DictionaryMapper extends BaseMapper<DictionaryDO> {
    int updateBatch(List<DictionaryDO> list);

    int batchInsert(@Param("list") List<DictionaryDO> list);

    int insertOrUpdate(DictionaryDO record);

    int insertOrUpdateSelective(DictionaryDO record);
}