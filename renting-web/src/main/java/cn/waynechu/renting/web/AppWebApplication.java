package cn.waynechu.renting.web;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.InetAddress;
import java.net.UnknownHostException;

@ComponentScan(basePackages = {"cn.waynechu"})
@MapperScan("cn.waynechu.renting.dal.mapper")
@EnableSwagger2
@EnableAspectJAutoProxy
@SpringBootApplication
public class AppWebApplication {

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication.run(AppWebApplication.class, args);

        // TODO: 2018/12/28 MDC Filter/Interceptor
        MDC.put("user", "waynechu");
        MDC.put("ip", InetAddress.getLocalHost().getHostAddress());
    }
}
