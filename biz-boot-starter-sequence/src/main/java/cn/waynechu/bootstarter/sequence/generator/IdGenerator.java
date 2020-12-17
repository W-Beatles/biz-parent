package cn.waynechu.bootstarter.sequence.generator;

/**
 * @author zhuwei
 * @since 2020/6/11 15:16
 */
public interface IdGenerator {

    /**
     * 获取id
     *
     * @param bizTag 业务标识
     * @return id
     */
    long nextId(String bizTag);

    /**
     * 批量获取id
     *
     * @param bizTag 业务标识
     * @param size   批量大小
     * @return id列表
     */
    long[] nextIds(String bizTag, int size);

    /**
     * 获取id
     *
     * @param bizTag 业务标识
     * @return id
     */
    String nextStringId(String bizTag);

    /**
     * 批量获取id
     *
     * @param bizTag 业务标识
     * @param size   批量大小
     * @return id列表
     */
    String[] nextStringIds(String bizTag, int size);

    /**
     * 固定长度19位的字符串id
     *
     * @param bizTag 业务标识
     * @return id
     */
    String nextFixedStringId(String bizTag);
}
