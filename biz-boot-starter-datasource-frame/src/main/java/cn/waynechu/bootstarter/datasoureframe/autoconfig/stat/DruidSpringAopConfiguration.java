package cn.waynechu.bootstarter.datasoureframe.autoconfig.stat;

import cn.waynechu.bootstarter.datasoureframe.properties.DruidStatProperties;
import cn.waynechu.bootstarter.datasoureframe.properties.FrameDataSourceProperties;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.RegexpMethodPointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * Code from com.alibaba:druid-spring-boot-starter:1.1.10
 *
 * @author lihengming [89921218@qq.com]
 */
@ConditionalOnProperty(FrameDataSourceProperties.FRAME_DATA_SOURCE_PREFIX + ".druid.aop-patterns")
public class DruidSpringAopConfiguration {

    @Bean
    public Advice advice() {
        return new DruidStatInterceptor();
    }

    @Bean
    public Advisor advisor(DruidStatProperties properties) {
        return new RegexpMethodPointcutAdvisor(properties.getAopPatterns(), advice());
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
}
