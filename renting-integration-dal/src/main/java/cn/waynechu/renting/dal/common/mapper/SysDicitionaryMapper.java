package cn.waynechu.renting.dal.common.mapper;

import cn.waynechu.renting.dal.common.entity.SysDicitionary;
import cn.waynechu.renting.dal.common.entity.SysDicitionaryExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDicitionaryMapper {
    int countByExample(SysDicitionaryExample example);

    int deleteByExample(SysDicitionaryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysDicitionary record);

    int insertSelective(SysDicitionary record);

    List<SysDicitionary> selectByExample(SysDicitionaryExample example);

    SysDicitionary selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysDicitionary record, @Param("example") SysDicitionaryExample example);

    int updateByExample(@Param("record") SysDicitionary record, @Param("example") SysDicitionaryExample example);

    int updateByPrimaryKeySelective(SysDicitionary record);

    int updateByPrimaryKey(SysDicitionary record);
}