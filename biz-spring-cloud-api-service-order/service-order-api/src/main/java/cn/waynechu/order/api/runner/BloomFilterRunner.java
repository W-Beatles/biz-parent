package cn.waynechu.order.api.runner;

import cn.waynechu.springcloud.apistarter.cache.BloomOperations;
import cn.waynechu.springcloud.apistarter.cache.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.Arrays;

/**
 * lettuce客户端暂不支持CommandType.BF.RESERVE
 *
 * @author zhuwei
 * @date 2020-04-20 22:28
 */
@Slf4j
//@Component
public class BloomFilterRunner implements ApplicationRunner {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String key = "TEST";

        BloomOperations<String, String> bloomOperations = redisUtil.getBloomOperations();
        // 1.创建布隆过滤器
        bloomOperations.createFilter(key, 0.01, 100);

        // 2.添加一个元素
        Boolean foo = bloomOperations.add(key, "foo");
        log.info("test add result: {}", foo);

        // 3.批量添加元素
        Boolean[] addMulti = bloomOperations.addMulti(key, "foo", "bar");
        log.info("test addMulti result: {}", Arrays.toString(addMulti));

        // 4.校验一个元素是否存在
        Boolean exists = bloomOperations.exists(key, "foo");
        log.info("test exists result: {}", exists);

        // 5.批量校验元素是否存在
        Boolean[] existsMulti = bloomOperations.existsMulti(key, "foo", "foo1");
        log.info("test existsMulti result: {}", Arrays.toString(existsMulti));

        // 6.删除布隆过滤器
        Boolean delete = bloomOperations.delete(key);
        log.info("test delete result: {}", delete);
    }
}
