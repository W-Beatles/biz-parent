package cn.waynechu.utility.domain.repository;

import com.alicp.jetcache.anno.CachePenetrationProtect;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import cn.waynechu.utility.common.consts.RedisPrefix;
import cn.waynechu.utility.dal.dataobject.RegionDO;
import cn.waynechu.utility.dal.mapper.RegionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhuwei
 * @since 2019-08-11 16:43
 */
@Repository
public class RegionRepository {

    @Autowired
    private RegionMapper regionMapper;

    @Cached(expire = 3600, name = RedisPrefix.Regions.LIST_BY_PID_PREFIX, key = "#pid", cacheType = CacheType.REMOTE)
    @CacheRefresh(refresh = 1800, stopRefreshAfterLastAccess = 3600)
    @CachePenetrationProtect
    public List<RegionDO> listByPid(Integer pid) {
        QueryWrapper<RegionDO> wrapper = new QueryWrapper<>();
        wrapper.eq(RegionDO.COL_PID, pid);
        wrapper.eq(RegionDO.COL_DELETED_STATUS, false);
        return regionMapper.selectList(wrapper);
    }
}