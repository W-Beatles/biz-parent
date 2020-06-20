package cn.waynechu.archetype.portal.domain.service;

import cn.waynechu.archetype.portal.facade.request.SearchArchetypeRequest;
import cn.waynechu.archetype.portal.facade.response.SearchArchetypeResponse;
import cn.waynechu.facade.common.page.BizPageInfo;

/**
 * @author zhuwei
 * @date 2020-06-20 09:31
 */
public interface ArchetypeService {

    /**
     * 查询原型列表
     *
     * @param request req
     * @return 原型列表
     */
    BizPageInfo<SearchArchetypeResponse> search(SearchArchetypeRequest request);
}




