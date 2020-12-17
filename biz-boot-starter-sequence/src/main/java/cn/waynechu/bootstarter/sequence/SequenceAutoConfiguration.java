package cn.waynechu.bootstarter.sequence;

import cn.waynechu.bootstarter.sequence.generator.SnowFlakeIdGenerator;
import cn.waynechu.bootstarter.sequence.property.SequenceProperty;
import cn.waynechu.bootstarter.sequence.register.WorkerRegister;
import cn.waynechu.bootstarter.sequence.register.zookeeper.ZookeeperWorkerRegister;
import cn.waynechu.bootstarter.sequence.registry.ZookeeperRegistryCenter;
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
public class SequenceAutoConfiguration {

    @Autowired
    private SequenceProperty sequenceProperty;

    @Bean(initMethod = "init")
    @ConditionalOnProperty(value = SequenceProperty.SEQUENCE_PREFIX + ".enable", havingValue = "true")
    public ZookeeperRegistryCenter zookeeperRegistryCenter() {
        return new ZookeeperRegistryCenter(sequenceProperty.getZookeeper());
    }

    @Bean
    @ConditionalOnProperty(value = SequenceProperty.SEQUENCE_PREFIX + ".enable", havingValue = "true")
    public ZookeeperWorkerRegister workerRegister() {
        return new ZookeeperWorkerRegister(zookeeperRegistryCenter(), sequenceProperty);
    }

    @Bean(destroyMethod = "close")
    @ConditionalOnProperty(value = SequenceProperty.SEQUENCE_PREFIX + ".enable", havingValue = "true")
    public SnowFlakeIdGenerator generator(WorkerRegister workerRegister) {
        return new SnowFlakeIdGenerator(workerRegister);
    }
}
