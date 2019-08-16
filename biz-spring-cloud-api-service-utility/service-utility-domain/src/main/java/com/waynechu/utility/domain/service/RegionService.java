package com.waynechu.utility.domain.service;

import com.waynechu.utility.dal.dataobject.RegionDO;
import com.waynechu.utility.domain.convert.RegionConvert;
import com.waynechu.utility.domain.repository.RegionRepository;
import com.waynechu.utility.facade.response.RegionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhuwei
 * @date 2019-08-11 16:41
 */
@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    /**
     * 根据父级id获取子级行政区列表
     *
     * @param pid 父级id
     * @return 子级行政区列表
     */
    public List<RegionResponse> listByPid(Integer pid) {
        List<RegionDO> regionDoList = regionRepository.listByPid(pid);
        return RegionConvert.toRegionResponseList(regionDoList);
    }
}
