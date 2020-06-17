package cn.waynechu.order.dal.mapper.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.waynechu.order.dal.dataobject.order.OrderDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhuwei
 * @date 2019/9/20 15:34
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderDO> {
    int updateBatch(List<OrderDO> list);

    int batchInsert(@Param("list") List<OrderDO> list);

    int insertOrUpdate(OrderDO record);

    int insertOrUpdateSelective(OrderDO record);
}