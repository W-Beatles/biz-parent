package cn.waynechu.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author zhuwei
 * @since 2019/4/2 10:15
 */
@EnableScheduling
@EnableFeignClients
@EnableCircuitBreaker
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("cn.waynechu.product.dal.mapper")
public class ProductStartMain {

    public static void main(String[] args) {
        SpringApplication.run(ProductStartMain.class, args);
    }
}
