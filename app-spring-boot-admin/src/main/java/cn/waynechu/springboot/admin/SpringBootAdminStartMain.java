package cn.waynechu.springboot.admin;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableApolloConfig
@EnableAdminServer
@SpringBootApplication
public class SpringBootAdminStartMain {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootAdminStartMain.class, args);
    }

}
