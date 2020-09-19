package cn.waynechu.oauth.mapper.oauth;

import cn.waynechu.oauth.entity.SysUserDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhuwei
 * @since 2020/9/19 17:58
 */
public interface SysUserMapper extends BaseMapper<SysUserDO> {
    int updateBatch(List<SysUserDO> list);

    int updateBatchSelective(List<SysUserDO> list);

    int batchInsert(@Param("list") List<SysUserDO> list);

    int insertOrUpdate(SysUserDO record);

    int insertOrUpdateSelective(SysUserDO record);
}