package cn.waynechu.order.domain.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhuwei
 * @date 2020/5/15 15:32
 */
@Data
@Component
@ConfigurationProperties(prefix = "apollo")
public class ApolloRefreshProperty {

    private String refreshProperty;

}
