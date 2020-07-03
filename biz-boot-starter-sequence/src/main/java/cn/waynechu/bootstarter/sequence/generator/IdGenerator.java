package cn.waynechu.bootstarter.sequence.generator;

/**
 * @author zhuwei
 * @since 2020/6/11 15:16
 */
public interface IdGenerator {

    /**
     * 获取id
     *
     * @return id
     */
    long nextId();

    /**
     * 批量获取id
     *
     * @param size 批量大小
     * @return id列表
     */
    long[] nextIds(int size);

    /**
     * 获取id
     *
     * @return id
     */
    String nextStringId();

    /**
     * 批量获取id
     *
     * @param size 批量大小
     * @return id列表
     */
    String[] nextStringIds(int size);

    /**
     * 固定长度19位的字符串id
     *
     * @return id
     */
    String nextFixedStringId();
}
