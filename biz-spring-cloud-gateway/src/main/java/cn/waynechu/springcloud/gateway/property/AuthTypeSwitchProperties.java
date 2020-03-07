package cn.waynechu.springcloud.gateway.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhuwei
 * @date 2020-03-07 16:58
 */
@Data
@Component
@ConfigurationProperties(prefix = "gateway.auth-type.switch")
public class AuthTypeSwitchProperties {

    /**
     * 全部authType都可用
     */
    private Boolean openAll = false;

    /**
     * 可用的authType列表
     */
    private List<String> opens;
}
