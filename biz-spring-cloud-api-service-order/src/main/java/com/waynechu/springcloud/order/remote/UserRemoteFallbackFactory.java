package com.waynechu.springcloud.order.remote;

import cn.waynechu.facade.common.enums.BizErrorCodeEnum;
import cn.waynechu.facade.common.response.BizResponse;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author zhuwei
 * @date 2019/4/25 20:54
 */
@Component
public class UserRemoteFallbackFactory implements FallbackFactory<UserRemote> {

    @Override
    public UserRemote create(Throwable throwable) {
        return id -> new BizResponse<>(BizErrorCodeEnum.CALL_SERVICE_ERROR);
    }
}
