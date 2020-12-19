package cn.waynechu.bootstarter.sequence;

import cn.waynechu.bootstarter.sequence.generator.SnowFlakeIdGenerator;
import cn.waynechu.bootstarter.sequence.property.SequenceProperty;
import cn.waynechu.bootstarter.sequence.property.ZookeeperProperty;
import cn.waynechu.bootstarter.sequence.register.zookeeper.ZookeeperWorkerRegister;
import cn.waynechu.bootstarter.sequence.registry.ZookeeperRegistryCenter;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @author zhuwei
 * @since 2020/6/12 17:07
 */
public class ProducerTest {

    @Test
    public void test() {
        try {
            ZookeeperProperty configuration = new ZookeeperProperty();
            configuration.setServerLists("127.0.0.1:2181");
            configuration.setNamespace("sequence");

            ZookeeperRegistryCenter zookeeperRegistryCenter = new ZookeeperRegistryCenter(configuration);
            zookeeperRegistryCenter.init();

            SequenceProperty sequenceProperty = new SequenceProperty();
            ZookeeperWorkerRegister zookeeperWorkerRegister = new ZookeeperWorkerRegister(zookeeperRegistryCenter, sequenceProperty);
            SnowFlakeIdGenerator generator = new SnowFlakeIdGenerator(zookeeperWorkerRegister);

            System.out.println(generator.nextId("default"));
            System.out.println(generator.nextId("default"));
            generator.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
