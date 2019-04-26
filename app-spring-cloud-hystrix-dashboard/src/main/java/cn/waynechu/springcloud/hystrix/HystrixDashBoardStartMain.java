package cn.waynechu.springcloud.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@EnableHystrixDashboard
@SpringBootApplication
public class HystrixDashBoardStartMain {

    public static void main(String[] args) {
        SpringApplication.run(HystrixDashBoardStartMain.class, args);
    }

}
