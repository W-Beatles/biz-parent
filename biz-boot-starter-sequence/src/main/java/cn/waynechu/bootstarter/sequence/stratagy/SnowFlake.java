package cn.waynechu.bootstarter.sequence.stratagy;

import lombok.Getter;

import java.util.Random;

/**
 * Twitter Snowflake ID 生成器
 *
 * <pre>
 * SnowFlake的结构如下(每部分用-分开):
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000
 *
 * 1位符号标识，由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0
 *
 * 41位时间戳(毫秒级)，注意，41位时间戳不是存储当前时间的时间戳，而是存储时间戳的差值（当前时间戳 - 开始时间戳 得到的差值)
 * 这里的开始时间戳，一般是我们的id生成器开始使用的时间，由我们程序来指定的（如下面的epoch属性）
 * 可使用69年，年T = (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69
 *
 * 10位的数据机器位，可以部署在1024个节点，包括5位dataCenterId数据中心Id 和 5位workerId机器Id
 *
 * 12位序列，毫秒内的计数，12位的计数顺序号支持每个节点每毫秒(同一机器，同一时间戳)产生4096个ID序号，加起来刚好64位，为一个Long型
 *
 * SnowFlake的优点:
 * 1.整体上按照时间自增排序
 * 2.整个分布式系统内不会产生ID碰撞
 * 3.效率较高，测试SnowFlake每秒能够产生26万ID左右
 *
 * 说明:
 * 1.SnowFlake算法包括5位dataCenterId数据中心Id 和 5位workerId机器Id。这里使用10位的机器id代替
 * 2.对系统时间的依赖性非常强，需关闭ntp的时间同步功能。当检测到ntp时间调整后，将会拒绝分配id
 * </pre>
 *
 * @author zhuwei
 * @since 2019/5/5 16:46
 */
public class SnowFlake {
    /**
     * 起始时间戳(2020-01-01 00:00:00)
     */
    private static final long EPOCH = 1577808000000L;
    private static final long WORKER_ID_BITS = 10L;
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
    private static final long SEQUENCE_BITS = 12L;
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;
    private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);
    private static final int MAX_ID_SIZE = 100_000;
    private static final Random RANDOM = new Random();

    @Getter
    private int workerId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    /**
     * @param workerId 机器ID
     */
    public SnowFlake(int workerId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format("Worker Id can't be greater than %d or less than 0", MAX_WORKER_ID));
        }
        this.workerId = workerId;
    }

    /**
     * 批量获取ID
     *
     * @param size 获取大小，最多10万个
     * @return SnowflakeId
     */
    public long[] nextIds(int size) {
        if (size <= 0 || size > MAX_ID_SIZE) {
            String message = String.format("Size can't be greater than %d or less than 0", MAX_ID_SIZE);
            throw new IllegalArgumentException(message);
        }

        long[] ids = new long[size];
        for (int i = 0; i < size; i++) {
            ids[i] = nextId();
        }
        return ids;
    }

    public synchronized long nextId() {
        long timestamp = timeGen();
        // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        // 如果是同一时间生成的，则进行毫秒内序列(0-4095循环)
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            // 毫秒内序列溢出
            if (sequence == 0) {
                // 阻塞到下一个毫秒，获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            // 时间戳改变，毫秒内序列重置。避免低并发的情况下id都为偶数
            sequence = RANDOM.nextInt(10);
        }
        lastTimestamp = timestamp;
        return ((timestamp - EPOCH) << TIMESTAMP_LEFT_SHIFT) | (workerId << WORKER_ID_SHIFT) | sequence;
    }

    private static long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private static long timeGen() {
        return System.currentTimeMillis();
    }
}
