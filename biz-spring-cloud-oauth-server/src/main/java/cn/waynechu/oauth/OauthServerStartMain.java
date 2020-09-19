package cn.waynechu.oauth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("cn.waynechu.oauth.mapper")
@SpringBootApplication
public class OauthServerStartMain {

    public static void main(String[] args) {
        SpringApplication.run(OauthServerStartMain.class, args);
    }
}
