package cn.waynechu.product.dal.mapper.product;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.waynechu.product.dal.dataobject.product.ProductDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhuwei
 * @since 2019/9/20 15:35
 */
@Mapper
public interface ProductMapper extends BaseMapper<ProductDO> {
    int updateBatch(List<ProductDO> list);

    int batchInsert(@Param("list") List<ProductDO> list);

    int insertOrUpdate(ProductDO record);

    int insertOrUpdateSelective(ProductDO record);
}