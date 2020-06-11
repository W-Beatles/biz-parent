package cn.waynechu.bootstarter.sequence;

import cn.waynechu.bootstarter.sequence.generator.IdGenerator;
import cn.waynechu.bootstarter.sequence.property.SequenceProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhuwei
 * @since 2020/6/11 16:39
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(SequenceProperty.class)
@ConditionalOnProperty(value = SequenceProperty.SEQUENCE_PREFIX + ".enable", havingValue = "true")
public class SequenceAutoConfiguration {

    @Autowired
    private SequenceProperty sequenceProperty;

    @Bean
    public IdGenerator idGenerator() {
        return null;
    }
}
