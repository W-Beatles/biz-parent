package com.waynechu.springcloud.user.remote;

import cn.waynechu.facade.common.enums.BizErrorCodeEnum;
import cn.waynechu.facade.common.exception.BizException;
import cn.waynechu.facade.common.response.BizResponse;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author zhuwei
 * @date 2019/4/26 15:15
 */
@Service
public class OrderRemoteFallbackFactory implements FallbackFactory<OrderRemote> {

    @Override
    public OrderRemote create(Throwable throwable) {
        return new OrderRemote() {
            @Override
            public BizResponse<Map<String, Object>> getById(Integer id) {
                throw new BizException(BizErrorCodeEnum.CALL_SERVICE_ERROR, throwable.getMessage());
            }
        };
    }
}
