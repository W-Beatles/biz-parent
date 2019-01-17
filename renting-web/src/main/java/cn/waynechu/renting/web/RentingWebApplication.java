package cn.waynechu.renting.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@ComponentScan(basePackages = {"cn.waynechu"})
@MapperScan("cn.waynechu.renting.dal")
@EnableAspectJAutoProxy
@SpringBootApplication
public class RentingWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(RentingWebApplication.class, args);
    }
}
