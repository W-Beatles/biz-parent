package cn.waynechu.springcloud.common.util;

import lombok.experimental.UtilityClass;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.Collection;

/**
 * @author zhuwei
 * @since 2019/4/10 16:33
 */
@UtilityClass
public class PathUtil {

    /**
     * 判断url列表是否符合给定的ant风格的url
     *
     * <p>The mapping matches URLs using the following rules:<br>
     * <ul>
     * <li>{@code ?} matches one character</li>
     * <li>{@code *} matches zero or more characters</li>
     * <li>{@code **} matches zero or more <em>directories</em> in a path</li>
     * <li>{@code {spring:[a-z]+}} matches the regexp {@code [a-z]+} as a path variable named "spring"</li>
     * </ul>
     *
     * @param urls       url列表
     * @param antPattern ant风格的url
     * @return 符合返回true
     */
    public static boolean antMatch(Collection<String> urls, String antPattern) {
        PathMatcher matcher = new AntPathMatcher();
        for (String url : urls) {
            if (matcher.match(url, antPattern)) {
                return true;
            }
        }
        return false;
    }
}
