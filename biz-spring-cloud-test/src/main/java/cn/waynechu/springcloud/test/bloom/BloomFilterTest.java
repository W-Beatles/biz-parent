package cn.waynechu.springcloud.test.bloom;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhuwei
 * @since 2020-04-20 22:56
 */
@Slf4j
public class BloomFilterTest {

    public static void main(String[] args) {
        // 使用guava库提供的布隆过滤器
        int size = 10000000;
        BloomFilter<Integer> filter = BloomFilter.create(
                Funnels.integerFunnel(), size, 0.01);

        for (int i = 0; i < size; i++) {
            filter.put(i);
        }
        log.info(String.valueOf(filter.mightContain(10)));
        log.info(String.valueOf(filter.mightContain(20)));
    }
}
