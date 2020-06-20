package cn.waynechu.springcloud.common.util;

import cn.waynechu.springcloud.common.session.User;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Optional;

/**
 * @author zhuwei
 * @date 2020-06-21 00:10
 */
@Slf4j
@UtilityClass
public class UserUtil {

    public static final String HEADER_KEY_USER = "user";

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    public static User getUserInfo() {
        String user = WebUtil.getReqHeader(HEADER_KEY_USER);
        return parseUser(user);
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    public static User getUserInfo(HttpServletRequest request) {
        String user = WebUtil.getReqHeader(HEADER_KEY_USER, request);
        return parseUser(user);
    }

    /**
     * 解析用户信息
     *
     * @return 用户信息Json串
     */
    private static User parseUser(String user) {
        if (StringUtil.isNotBlank(user)) {
            try {
                String userDecode = URLDecoder.decode(user, "UTF-8");
                return JsonBinder.fromJson(userDecode, User.class);
            } catch (UnsupportedEncodingException var3) {
                log.error("解析用户信息失败: {}", user);
            }
        }
        return null;
    }

    public static String getUserId() {
        User user = getUserInfo();
        return Optional.ofNullable(user).map(User::getUserId).orElse("");
    }

    public static String getUserName() {
        User user = getUserInfo();
        return Optional.ofNullable(user).map(User::getUserName).orElse("");
    }

    public static String getEmail() {
        User user = getUserInfo();
        return Optional.ofNullable(user).map(User::getEmail).orElse("");
    }

    public static String getLoginSource() {
        User user = getUserInfo();
        return Optional.ofNullable(user).map(User::getLoginSource).orElse("");
    }
}
