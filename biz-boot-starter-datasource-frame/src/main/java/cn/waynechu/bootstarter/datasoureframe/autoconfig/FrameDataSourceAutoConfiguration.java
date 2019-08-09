package cn.waynechu.bootstarter.datasoureframe.autoconfig;

import cn.waynechu.bootstarter.datasoureframe.properties.FrameDataSourceProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhuwei
 * @date 2019/8/7 11:18
 */
@Slf4j
@Configuration
@AutoConfigureBefore({DataSourceAutoConfiguration.class})
@EnableConfigurationProperties(FrameDataSourceProperties.class)
public class FrameDataSourceAutoConfiguration {

}
