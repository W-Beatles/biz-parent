package cn.waynechu.bootstarter.sequence.stratagy;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author zhuwei
 * @since 2020/6/12 16:44
 */
@Slf4j
class SnowFlakeTest {

    @Test
    public void nextId() {
        long id = new SnowFlake(1).nextId();
        System.out.println(id);
    }

    @Test
    public void nextIds() {
        long[] ids = new SnowFlake(1).nextIds(10);
        for (long id : ids) {
            System.out.println(id);
        }
    }
}