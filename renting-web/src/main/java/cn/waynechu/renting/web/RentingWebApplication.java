package cn.waynechu.renting.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = {"cn.waynechu"},exclude= {DataSourceAutoConfiguration.class})
public class RentingWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(RentingWebApplication.class, args);
    }
}
