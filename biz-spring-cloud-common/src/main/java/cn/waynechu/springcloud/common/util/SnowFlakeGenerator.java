package cn.waynechu.springcloud.common.util;

import java.util.Random;

/**
 * Twitter_Snowflake ID生成器
 *
 * <pre>
 * SnowFlake的结构如下(每部分用-分开):
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000
 *
 * 1位符号标识，由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0
 * 41位时间戳(毫秒级)，注意，41位时间戳不是存储当前时间的时间戳，而是存储时间戳的差值（当前时间戳 - 开始时间戳 得到的差值)
 * 这里的开始时间戳，一般是我们的id生成器开始使用的时间，由我们程序来指定的（如下面的twepoch属性）。41位的时间戳，可以使用69年，年T = (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69
 * 10位的数据机器位，可以部署在1024个节点，包括5位 dataCenterId数据中心Id 和5位 workerId机器Id
 * 12位序列，毫秒内的计数，12位的计数顺序号支持每个节点每毫秒(同一机器，同一时间戳)产生4096个ID序号
 * 加起来刚好64位，为一个Long型
 *
 * SnowFlake的优点:
 * 1.整体上按照时间自增排序
 * 2.整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分)
 * 3.效率较高，测试SnowFlake每秒能够产生26万ID左右
 * </pre>
 *
 * @author zhuwei
 * @since 2019/5/5 16:46
 */
public class SnowFlakeGenerator {
    /**
     * 起始时间戳(2019-01-01 00:00:00)
     */
    private static final long TWEPOCH = 1546272000000L;
    private static final long WORKER_ID_BITS = 5L;
    private static final long DATA_CENTER_ID_BITS = 5L;
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
    private static final long MAX_DATA_CENTER_ID = ~(-1L << DATA_CENTER_ID_BITS);
    private static final long SEQUENCE_BITS = 12L;
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;
    private static final long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);
    private static final Random RANDOM = new Random();

    private final long workerId;
    private final long dataCenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    private long lastShiftTimestamp;
    private int lastShiftValue;

    /**
     * @param dataCenterId 数据中心ID
     * @param workerId     机器ID
     */
    public SnowFlakeGenerator(long dataCenterId, long workerId) {
        if (dataCenterId > MAX_DATA_CENTER_ID || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("dataCenter Id can't be greater than %d or less than 0", MAX_DATA_CENTER_ID));
        }
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", MAX_WORKER_ID));
        }
        this.dataCenterId = dataCenterId;
        this.workerId = workerId;
    }

    public synchronized long nextId() {
        long currentTimeMillis = timeGen();
        // 处理系统时钟回退
        if (currentTimeMillis < lastTimestamp) {
            if (lastShiftTimestamp != currentTimeMillis) {
                lastShiftValue++;
                lastShiftTimestamp = currentTimeMillis;
            }
            currentTimeMillis = lastShiftValue;
        }
        // 如果是同一时间生成的，则进行毫秒内序列
        else if (currentTimeMillis == lastTimestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            // 毫秒内序列溢出
            if (sequence == 0) {
                // 阻塞到下一个毫秒，获得新的时间戳
                currentTimeMillis = tilNextMillis(lastTimestamp);
            }
        } else {
            // 时间戳改变，毫秒内序列重置。避免低并发的情况下id都为偶数
            sequence = RANDOM.nextInt(10);
        }
        lastTimestamp = currentTimeMillis;
        return ((currentTimeMillis - TWEPOCH) << TIMESTAMP_LEFT_SHIFT) | (dataCenterId << DATA_CENTER_ID_SHIFT) | (workerId << WORKER_ID_SHIFT) | sequence;
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
