package cn.waynechu.bootstarter.datasoureframe.autoconfig.stat;

import cn.waynechu.bootstarter.datasoureframe.properties.DruidStatProperties;
import cn.waynechu.bootstarter.datasoureframe.properties.FrameDataSourceProperties;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * Code from com.alibaba:druid-spring-boot-starter:1.1.10
 *
 * @author lihengming [89921218@qq.com]
 */
@ConditionalOnWebApplication
@ConditionalOnProperty(name = FrameDataSourceProperties.FRAME_DATA_SOURCE_PREFIX + ".druid.web-stat-filter.enabled", havingValue = "true")
public class DruidWebStatFilterConfiguration {

    @Bean
    @SuppressWarnings("unchecked")
    public FilterRegistrationBean webStatFilterRegistrationBean(DruidStatProperties properties) {
        DruidStatProperties.WebStatFilter config = properties.getWebStatFilter();
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        WebStatFilter filter = new WebStatFilter();
        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns(config.getUrlPattern() != null ? config.getUrlPattern() : "/*");
        registrationBean.addInitParameter("exclusions", config.getExclusions() != null ? config.getExclusions() : "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        if (config.getSessionStatEnable() != null) {
            registrationBean.addInitParameter("sessionStatEnable", config.getSessionStatEnable());
        }
        if (config.getSessionStatMaxCount() != null) {
            registrationBean.addInitParameter("sessionStatMaxCount", config.getSessionStatMaxCount());
        }
        if (config.getPrincipalSessionName() != null) {
            registrationBean.addInitParameter("principalSessionName", config.getPrincipalSessionName());
        }
        if (config.getPrincipalCookieName() != null) {
            registrationBean.addInitParameter("principalCookieName", config.getPrincipalCookieName());
        }
        if (config.getProfileEnable() != null) {
            registrationBean.addInitParameter("profileEnable", config.getProfileEnable());
        }
        return registrationBean;
    }
}
