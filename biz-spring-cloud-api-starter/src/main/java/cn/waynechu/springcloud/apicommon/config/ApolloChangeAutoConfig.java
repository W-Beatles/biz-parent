package cn.waynechu.springcloud.apicommon.config;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * @author zhuwei
 * @date 2019/7/11 19:55
 */
@Slf4j
@Configuration
public class ApolloChangeAutoConfig {

    @Value("${spring.application.name}")
    private String springApplicationName;

    @Value("${apollo.bootstrap.namespaces:notApollo}")
    private String[] namespaces;

    /**
     * 配置apollo监听事件
     */
    @PostConstruct
    private void init() {
        if (null == namespaces || namespaces.length == 0 || "notApollo".equals(namespaces[0])) {
            return;
        }

        for (String namespace : namespaces) {
            Config config = ConfigService.getConfig(namespace);
            config.addChangeListener(
                    changeEvent -> {
                        Set<String> changedKeys = changeEvent.changedKeys();
                        changedKeys.forEach(key -> log.info("Apollo配置中心 key : {} 的值修改为: {}", key, changeEvent.getChange(key)));
                        // TODO 2019/7/11 20:24 解决apollo不能自动刷新@ConfigurationProperties的问题
                    });
        }
    }
}
