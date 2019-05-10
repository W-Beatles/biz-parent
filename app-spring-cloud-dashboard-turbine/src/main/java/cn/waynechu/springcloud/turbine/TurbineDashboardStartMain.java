package cn.waynechu.springcloud.turbine;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@EnableTurbine
@EnableHystrixDashboard
@EnableDiscoveryClient
@EnableApolloConfig
@SpringBootApplication
public class TurbineDashboardStartMain {

    public static void main(String[] args) {
        SpringApplication.run(TurbineDashboardStartMain.class, args);
    }

}
