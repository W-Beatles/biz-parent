package cn.waynechu.renting.core;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableMethodCache(basePackages = "cn.waynechu.renting.core.repository")
@EnableCreateCacheAnnotation
@MapperScan("cn.waynechu.renting.dal")
@SpringBootApplication(scanBasePackages = {"cn.waynechu"})
public class RentingCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(RentingCoreApplication.class, args);
    }

}
