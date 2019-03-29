package com.waynechu.renting.dal.common.mapper;

import com.waynechu.renting.dal.common.entity.ShopConfigLog;
import com.waynechu.renting.dal.common.entity.ShopConfigLogExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopConfigLogMapper {
    int countByExample(ShopConfigLogExample example);

    int deleteByExample(ShopConfigLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ShopConfigLog record);

    int insertSelective(ShopConfigLog record);

    List<ShopConfigLog> selectByExample(ShopConfigLogExample example);

    ShopConfigLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ShopConfigLog record, @Param("example") ShopConfigLogExample example);

    int updateByExample(@Param("record") ShopConfigLog record, @Param("example") ShopConfigLogExample example);

    int updateByPrimaryKeySelective(ShopConfigLog record);

    int updateByPrimaryKey(ShopConfigLog record);
}