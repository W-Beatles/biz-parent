package cn.waynechu.bootstarter.datasoureframe.properties;

import cn.waynechu.bootstarter.datasoureframe.strategy.DynamicDataSourceStrategy;
import cn.waynechu.bootstarter.datasoureframe.strategy.RoundRobinDynamicDataSourceStrategy;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhuwei
 * @date 2019/8/7 11:20
 */
@Data
@ConfigurationProperties(prefix = FrameDataSourceProperties.FRAME_DATA_SOURCE_PREFIX)
public class FrameDataSourceProperties {
    public static final String FRAME_DATA_SOURCE_PREFIX = "spring.datasource.frame";


    /**
     * 动态数据源选择策略，默认轮询策略
     */
    private Class<? extends DynamicDataSourceStrategy> strategy = RoundRobinDynamicDataSourceStrategy.class;

}
