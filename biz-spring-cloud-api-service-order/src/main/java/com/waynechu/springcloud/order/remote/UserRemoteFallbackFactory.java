package com.waynechu.springcloud.order.remote;

import cn.waynechu.facade.common.enums.BizErrorCodeEnum;
import cn.waynechu.facade.common.exception.BizException;
import cn.waynechu.facade.common.response.BizResponse;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zhuwei
 * @date 2019/4/25 20:54
 */
@Component
public class UserRemoteFallbackFactory implements FallbackFactory<UserRemote> {

    @Override
    public UserRemote create(Throwable throwable) {
        return new UserRemote() {
            @Override
            public BizResponse<Map<String, Object>> getById(Integer id) {
                throw new BizException(BizErrorCodeEnum.CALL_SERVICE_ERROR, throwable.getMessage());
            }
        };
    }
}
