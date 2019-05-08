package cn.waynechu.springcloud.gateway;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableApolloConfig
@SpringBootApplication
public class GatewayStartMain {

    public static void main(String[] args) {
        SpringApplication.run(GatewayStartMain.class, args);
    }
}
