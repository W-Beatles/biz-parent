package cn.waynechu.springcloud.common.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author zhuwei
 * @since 2021/5/8 17:47
 */
@Slf4j
public class SnowFlakeGeneratorTest {

    private SnowFlakeGenerator generator;

    @Before
    public void buildGenerator() {
        generator = new SnowFlakeGenerator(1, 1);
    }

    @Test
    public void test() {
        int i = 0;
        while (i++ < 99) {
            new Thread(() -> {
                int j = 0;
                while (j++ < 9999) {
                    long id = generator.nextId();
                    log.info(String.valueOf(id));
                }
            }).start();
        }
    }

    public static void main(String[] args) {
        SnowFlakeGenerator generator = new SnowFlakeGenerator(1, 1);
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            long id = generator.nextId();
            log.info(String.valueOf(id));
        }, 0, 1, TimeUnit.SECONDS);
    }
}