package cn.waynechu.order.api.runner;

import cn.hutool.bloomfilter.BitMapBloomFilter;
import cn.waynechu.springcloud.apistarter.cache.BloomOperations;
import cn.waynechu.springcloud.apistarter.cache.RedisHelper;
import com.alibaba.fastjson.JSONObject;
import com.carrotsearch.sizeof.RamUsageEstimator;
import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.Arrays;

/**
 * lettuce客户端暂不支持CommandType.BF.RESERVE
 *
 * @author zhuwei
 * @since 2020-04-20 22:28
 */
@Slf4j
//@Component
public class BloomFilterRunner implements ApplicationRunner {

    @Autowired
    private RedisHelper redisHelper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String key = "TEST";

        BloomOperations<String, String> bloomOperations = redisHelper.getBloomOperations();
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

    public void testHuToolBloomFilter() {
        // 初始化
        BitMapBloomFilter filter = new BitMapBloomFilter(10);
        filter.add("123");
        filter.add("abc");
        filter.add("ddd");

        // 查找
        boolean flag = filter.contains("abc");
        System.out.println(flag);
    }

    public void testGuavaBloomFilter() {
        int size = 3000000;
        BloomFilter<String> filter = BloomFilter.create(
                Funnels.stringFunnel(Charsets.UTF_8), size, 0.1);

        for (int i = 0; i < size; i++) {
            TaskCreateBO taskCreateBO = TaskCreateBO.builder()
                    .shopId(i).processor("10000" + i).role(i)
                    .build();
            filter.put(JSONObject.toJSONString(taskCreateBO));
        }

        log.info(String.valueOf(RamUsageEstimator.sizeOf(filter)));
        log.info(String.valueOf(getMb(filter)));

        TaskCreateBO taskCreateBO = TaskCreateBO.builder()
                .shopId(1).processor("10000" + 1).role(1)
                .build();
        log.info(String.valueOf(filter.mightContain(JSONObject.toJSONString(taskCreateBO))));
    }

    public static void main(String[] args) {
        BloomFilterRunner bloomFilterRunner = new BloomFilterRunner();
        bloomFilterRunner.testHuToolBloomFilter();

        bloomFilterRunner.testGuavaBloomFilter();
    }

    private int getMb(Object obj) {

        if (obj == null) {
            return 0;
        }
        //计算指定对象本身在堆空间的大小，单位字节
        long byteCount = RamUsageEstimator.shallowSizeOf(obj);
        if (byteCount == 0) {
            return 0;
        }
        double oneMb = 1 * 1024 * 1024;

        if (byteCount < oneMb) {
            return 1;
        }

        Double v = Double.valueOf(byteCount) / oneMb;
        return v.intValue();
    }
}
