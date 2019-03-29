package com.waynechu.renting.dal.renting.mapper;

import com.waynechu.renting.dal.renting.entity.House;
import com.waynechu.renting.dal.renting.entity.HouseExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HouseMapper {
    int countByExample(HouseExample example);

    int deleteByExample(HouseExample example);

    int deleteByPrimaryKey(Long id);

    int insert(House record);

    int insertSelective(House record);

    List<House> selectByExample(HouseExample example);

    House selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") House record, @Param("example") HouseExample example);

    int updateByExample(@Param("record") House record, @Param("example") HouseExample example);

    int updateByPrimaryKeySelective(House record);

    int updateByPrimaryKey(House record);
}