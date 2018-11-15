package cn.waynechu.app.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"cn.waynechu"})
@MapperScan("cn.waynechu.app.dal.mapper")
@SpringBootApplication
public class AppWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppWebApplication.class, args);
    }
}
