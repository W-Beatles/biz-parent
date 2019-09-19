package com.waynechu.dynamicdatasource.dal.mapper;

import com.waynechu.dynamicdatasource.dal.dataobject.OrderDO;
import com.waynechu.dynamicdatasource.dal.dataobject.OrderDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhuwei
 * @date 2019/9/19 19:50
 */
@Mapper
public interface OrderMapper {
    long countByExample(OrderDOExample example);

    int deleteByExample(OrderDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(OrderDO record);

    int insertOrUpdate(OrderDO record);

    int insertOrUpdateSelective(OrderDO record);

    int insertSelective(OrderDO record);

    List<OrderDO> selectByExample(OrderDOExample example);

    OrderDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") OrderDO record, @Param("example") OrderDOExample example);

    int updateByExample(@Param("record") OrderDO record, @Param("example") OrderDOExample example);

    int updateByPrimaryKeySelective(OrderDO record);

    int updateByPrimaryKey(OrderDO record);

    int updateBatch(List<OrderDO> list);

    int batchInsert(@Param("list") List<OrderDO> list);
}