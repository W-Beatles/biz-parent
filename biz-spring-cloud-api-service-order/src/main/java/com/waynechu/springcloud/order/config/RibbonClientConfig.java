package com.waynechu.springcloud.order.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Ribbon自定义配置类
 * 更多配置参见默认配置类：org.springframework.cloud.netflix.ribbon.RibbonClientConfiguration
 * <p>
 * **推荐在配置文件添加自定义Ribbon属性的方式来配置，这样就可以针对不同的服务使用不同的规则，如负载均衡规则等**
 *
 * @author zhuwei
 * @date 2018/8/27 10:49
 */
@Configuration
public class RibbonClientConfig {

    @Bean
    public IRule ribbonRule() {
        // 负载均衡规则改为随机
        return new RandomRule();
    }
}