package com.waynechu.renting.core;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableMethodCache(basePackages = "com.waynechu.renting.core.repository")
@EnableCreateCacheAnnotation
@MapperScan("com.waynechu.renting.dal")
@SpringBootApplication(scanBasePackages = {"com.waynechu"})
public class RentingCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(RentingCoreApplication.class, args);
    }

}
