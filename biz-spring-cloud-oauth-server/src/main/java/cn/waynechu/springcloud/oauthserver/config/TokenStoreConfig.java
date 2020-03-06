package cn.waynechu.springcloud.oauthserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * @author zhuwei
 * @date 2020-02-27 21:57
 */
@Configuration
public class TokenStoreConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private DataSource dataSource;

    @Bean
    public TokenStore tokenStore() {
        // return new RedisTokenStore(redisConnectionFactory);
        return new JdbcTokenStore(dataSource);
    }
}
