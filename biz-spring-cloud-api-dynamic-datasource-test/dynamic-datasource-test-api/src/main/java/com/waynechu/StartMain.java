package com.waynechu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author zhuwei
 * @date 2019/4/2 10:15
 */
@EnableScheduling
@EnableFeignClients
@EnableCircuitBreaker
@EnableDiscoveryClient
@MapperScan("com.waynechu.dynamicdatasource.dal.mapper")
@SpringBootApplication(scanBasePackages = {"com.waynechu"})
public class StartMain {

    public static void main(String[] args) {
        SpringApplication.run(StartMain.class, args);
    }

}
