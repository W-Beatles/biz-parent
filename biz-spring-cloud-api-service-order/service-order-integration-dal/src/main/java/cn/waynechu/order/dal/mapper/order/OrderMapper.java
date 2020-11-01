package cn.waynechu.order.dal.mapper.order;

import cn.waynechu.order.dal.dataobject.order.OrderDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhuwei
 * @date 2020-11-01 17:18
 */
public interface OrderMapper extends BaseMapper<OrderDO> {
    int updateBatch(List<OrderDO> list);

    int updateBatchSelective(List<OrderDO> list);

    int batchInsert(@Param("list") List<OrderDO> list);

    int insertOrUpdate(OrderDO record);

    int insertOrUpdateSelective(OrderDO record);
}