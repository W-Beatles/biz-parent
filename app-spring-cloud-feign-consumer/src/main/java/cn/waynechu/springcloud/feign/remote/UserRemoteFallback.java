package cn.waynechu.springcloud.feign.remote;

import cn.waynechu.facade.common.response.BizResponse;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zhuwei
 * @date 2019/4/25 20:54
 */
@Component
public class UserRemoteFallback implements UserRemote {

    @Override
    public BizResponse<Map<String, Object>> getById(Integer id) {
        return BizResponse.error();
    }
}
