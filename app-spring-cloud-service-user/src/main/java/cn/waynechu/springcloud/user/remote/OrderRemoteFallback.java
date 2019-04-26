package cn.waynechu.springcloud.user.remote;

import cn.waynechu.facade.common.response.BizResponse;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author zhuwei
 * @date 2019/4/26 15:15
 */
@Service
public class OrderRemoteFallback implements OrderRemote {

    @Override
    public BizResponse<Map<String, Object>> getById(Integer id) {
        return BizResponse.error();
    }
}
