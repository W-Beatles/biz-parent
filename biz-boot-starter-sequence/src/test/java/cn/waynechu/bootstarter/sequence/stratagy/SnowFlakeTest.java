package cn.waynechu.bootstarter.sequence.stratagy;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author zhuwei
 * @since 2020/6/12 16:44
 */
@Slf4j
class SnowFlakeTest {

    @Test
    public void nextId() {
        long id = new SnowFlake(1).nextId();
        assertThat(id).isPositive();
    }

    @Test
    public void nextIds() {
        long[] ids = new SnowFlake(1).nextIds(1000);
        assertThat(ids).doesNotHaveDuplicates();
    }
}