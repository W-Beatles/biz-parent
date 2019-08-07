package cn.waynechu.bootstarter.datasoureframe.properties;

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

}
