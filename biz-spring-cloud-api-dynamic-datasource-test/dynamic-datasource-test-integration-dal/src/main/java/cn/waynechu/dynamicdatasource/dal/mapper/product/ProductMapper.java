package cn.waynechu.dynamicdatasource.dal.mapper.product;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.waynechu.dynamicdatasource.dal.dataobject.product.ProductDO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhuwei
 * @date 2019/9/20 15:35
 */
@Mapper
public interface ProductMapper extends BaseMapper<ProductDO> {
    int updateBatch(List<ProductDO> list);

    int batchInsert(@Param("list") List<ProductDO> list);

    int insertOrUpdate(ProductDO record);

    int insertOrUpdateSelective(ProductDO record);
}