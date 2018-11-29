package cn.waynechu.renting.web;

import cn.waynechu.common.proxy.Sub;
import cn.waynechu.common.proxy.SubImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"cn.waynechu"})
@MapperScan("cn.waynechu.renting.dal.mapper")
@SpringBootApplication
public class AppWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppWebApplication.class, args);
    }

    @Bean
    public ProxyFactoryBean subSingleton() throws ClassNotFoundException {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setProxyInterfaces(new Class[]{Sub.class});
        proxyFactoryBean.setTarget(new SubImpl());
//        proxyFactoryBean.setInterceptorNames();
        return proxyFactoryBean;
    }
}
