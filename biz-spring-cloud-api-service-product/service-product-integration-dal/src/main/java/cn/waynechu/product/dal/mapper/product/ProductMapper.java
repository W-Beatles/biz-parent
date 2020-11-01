package cn.waynechu.product.dal.mapper.product;

import cn.waynechu.product.dal.dataobject.product.ProductDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhuwei
 * @date 2020-11-01 17:21
 */
public interface ProductMapper extends BaseMapper<ProductDO> {
    int updateBatch(List<ProductDO> list);

    int updateBatchSelective(List<ProductDO> list);

    int batchInsert(@Param("list") List<ProductDO> list);

    int insertOrUpdate(ProductDO record);

    int insertOrUpdateSelective(ProductDO record);

    int reduceStock(@Param("productId") Long productId, @Param("amount") Integer amount);
}