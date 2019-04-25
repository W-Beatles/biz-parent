package cn.waynechu.springcloud.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class AppSpringCloudServiceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppSpringCloudServiceProviderApplication.class, args);
    }

}
