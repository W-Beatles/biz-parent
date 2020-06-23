package cn.waynechu.springcloud.common.session;

import cn.waynechu.springcloud.common.util.JsonBinder;
import lombok.Data;

/**
 * @author zhuwei
 * @date 2018/12/24 13:54
 */
@Data
public class User {

    private String userId;

    private String userName;

    private String email;

    private String userType;

    private String loginSource;

    @Override
    public String toString() {
        return JsonBinder.toJson(this);
    }
}
