package cn.waynechu.renting.dal.renting.mapper;

import cn.waynechu.renting.dal.renting.entity.HouseDetail;
import cn.waynechu.renting.dal.renting.entity.HouseDetailExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HouseDetailMapper {
    int countByExample(HouseDetailExample example);

    int deleteByExample(HouseDetailExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HouseDetail record);

    int insertSelective(HouseDetail record);

    List<HouseDetail> selectByExample(HouseDetailExample example);

    HouseDetail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") HouseDetail record, @Param("example") HouseDetailExample example);

    int updateByExample(@Param("record") HouseDetail record, @Param("example") HouseDetailExample example);

    int updateByPrimaryKeySelective(HouseDetail record);

    int updateByPrimaryKey(HouseDetail record);
}