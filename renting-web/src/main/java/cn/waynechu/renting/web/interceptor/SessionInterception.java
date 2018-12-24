package cn.waynechu.renting.web.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhuwei
 * @date 2018/12/24 13:49
 */
public class SessionInterception implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AccountSession accountSession = SessionHolder.getAccountSession();

        if (accountSession == null) {
            // TODO: 2018/12/24 get from db
            accountSession = new AccountSession();
            accountSession.setUserName("waynechu");

            SessionHolder.setAccountSession(accountSession);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // do nothing here.
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        SessionHolder.removeAccountSession();
    }
}
