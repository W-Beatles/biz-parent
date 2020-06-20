package cn.waynechu.archetype.portal.domain.service.impl;

import cn.waynechu.archetype.portal.common.enums.StatusCodeEnum;
import cn.waynechu.archetype.portal.dal.condition.ListArchetypeCondition;
import cn.waynechu.archetype.portal.dal.dataobject.ArchetypeDO;
import cn.waynechu.archetype.portal.domain.convert.ArchetypeConvert;
import cn.waynechu.archetype.portal.domain.repository.ArchetypeRepository;
import cn.waynechu.archetype.portal.domain.service.ArchetypeService;
import cn.waynechu.archetype.portal.facade.request.CreateArchetypeRequest;
import cn.waynechu.archetype.portal.facade.request.SearchArchetypeRequest;
import cn.waynechu.archetype.portal.facade.response.SearchArchetypeResponse;
import cn.waynechu.facade.common.page.BizPageInfo;
import cn.waynechu.springcloud.common.util.BeanUtil;
import cn.waynechu.springcloud.common.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zhuwei
 * @date 2020-06-20 09:31
 */
@Service
public class ArchetypeServiceImpl implements ArchetypeService {

    @Autowired
    private ArchetypeRepository archetypeRepository;

    @Override
    public BizPageInfo<SearchArchetypeResponse> search(SearchArchetypeRequest request) {
        ListArchetypeCondition condition = BeanUtil.beanTransfer(request, ListArchetypeCondition.class);
        List<ArchetypeDO> archetypeDOList = archetypeRepository.listByCondition(condition);
        List<SearchArchetypeResponse> list = ArchetypeConvert.toSearchArchetypeResponse(archetypeDOList);
        return BizPageInfo.of(archetypeDOList).replace(list);
    }

    @Override
    public Long create(CreateArchetypeRequest request) {
        ArchetypeDO insertArchetypeDO = new ArchetypeDO();
        BeanUtil.copyProperties(request, insertArchetypeDO);
        insertArchetypeDO.setStatusCode(StatusCodeEnum.PENDING.getCode());
        insertArchetypeDO.setCreatedUser(UserUtil.getEmail());
        insertArchetypeDO.setCreatedTime(LocalDateTime.now());
        return archetypeRepository.insert(insertArchetypeDO);
    }
}



