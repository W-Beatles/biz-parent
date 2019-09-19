package com.waynechu.dynamicdatasource.dal.mapper;

import com.waynechu.dynamicdatasource.dal.dataobject.ProductDO;
import com.waynechu.dynamicdatasource.dal.dataobject.ProductDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhuwei
 * @date 2019/9/19 19:50
 */
@Mapper
public interface ProductMapper {
    long countByExample(ProductDOExample example);

    int deleteByExample(ProductDOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProductDO record);

    int insertOrUpdate(ProductDO record);

    int insertOrUpdateSelective(ProductDO record);

    int insertSelective(ProductDO record);

    List<ProductDO> selectByExample(ProductDOExample example);

    ProductDO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProductDO record, @Param("example") ProductDOExample example);

    int updateByExample(@Param("record") ProductDO record, @Param("example") ProductDOExample example);

    int updateByPrimaryKeySelective(ProductDO record);

    int updateByPrimaryKey(ProductDO record);

    int updateBatch(List<ProductDO> list);

    int batchInsert(@Param("list") List<ProductDO> list);
}