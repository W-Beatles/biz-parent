package cn.waynechu.utility;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
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
@MapperScan("cn.waynechu.utility.dal.mapper")
@EnableMethodCache(basePackages = "cn.waynechu.utility.domain.repository")
@EnableCreateCacheAnnotation
@SpringBootApplication
public class UtilityStartMain {

    public static void main(String[] args) {
        SpringApplication.run(UtilityStartMain.class, args);
    }
}
