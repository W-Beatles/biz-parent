package cn.waynechu.bootstarter.sequence;

import cn.waynechu.bootstarter.sequence.generator.SnowFlakeIdGenerator;
import cn.waynechu.bootstarter.sequence.property.ZookeeperConfiguration;
import cn.waynechu.bootstarter.sequence.register.zookeeper.ZookeeperWorkerRegister;
import cn.waynechu.bootstarter.sequence.registry.ZookeeperRegistryCenter;
import cn.waynechu.bootstarter.sequence.stratagy.SnowFlake;
import org.junit.jupiter.api.Test;

/**
 * @author zhuwei
 * @since 2020/6/12 17:07
 */
public class ProducerTest {

    @Test
    public void test() {
        ZookeeperConfiguration configuration = new ZookeeperConfiguration();
        configuration.setServerLists("127.0.0.1:2181");
         configuration.setNamespace("sequence");

        ZookeeperRegistryCenter zookeeperRegistryCenter = new ZookeeperRegistryCenter(configuration);
        zookeeperRegistryCenter.init();

        ZookeeperWorkerRegister zookeeperWorkerRegister = new ZookeeperWorkerRegister(zookeeperRegistryCenter);
        SnowFlakeIdGenerator snowFlakeIdGenerator = new SnowFlakeIdGenerator(zookeeperWorkerRegister);
        snowFlakeIdGenerator.init();

        long workerId = zookeeperWorkerRegister.register();

        SnowFlake snowFlake = new SnowFlake(workerId);
        long id = snowFlake.nextId();
        System.out.println(id);
    }
}
