
package cn.waynechu.archetype.portal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author zhuwei
 * @since 2019/4/2 10:15
 */
@EnableAsync
@EnableScheduling
@EnableHystrix
@EnableFeignClients
@EnableCircuitBreaker
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("cn.waynechu.archetype.portal.dal.mapper")
public class ArchetypePortalStartMain {

    public static void main(String[] args) {
        SpringApplication.run(ArchetypePortalStartMain.class, args);
    }
}
