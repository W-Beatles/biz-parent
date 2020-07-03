package cn.waynechu.springcloud.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * 推荐使用turbine聚合熔断信息，参考biz-spring-cloud-dashboard-turbine项目
 */
@EnableHystrixDashboard
@SpringBootApplication
@Deprecated
public class HystrixDashboardStartMain {

    public static void main(String[] args) {
        SpringApplication.run(HystrixDashboardStartMain.class, args);
    }
}
