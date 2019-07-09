package cn.waynechu.springcloud.common.util;

import lombok.experimental.UtilityClass;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**
 * @author zhuwei
 * @date 2019/4/10 16:33
 */
@UtilityClass
public class PathUtil {

    /**
     * 判断url列表是否符合给定ant风格的url
     *
     * @param urls       url列表
     * @param antPattern ant风格的url
     * @return 符合返回true
     */
    public static boolean urlContain(String[] urls, String antPattern) {
        PathMatcher matcher = new AntPathMatcher();
        for (String url : urls) {
            if (matcher.match(url, antPattern)) {
                return true;
            }
        }
        return false;
    }
}
