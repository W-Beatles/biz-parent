package cn.waynechu.bootstarter.sequence.generator;

/**
 * @author zhuwei
 * @since 2020/6/11 15:16
 */
public interface IdGenerator {

    /**
     * 获取id
     *
     * @param appName 应用名
     * @param tbName  表名
     * @return id
     */
    long nextId(String appName, String tbName);

    /**
     * 批量获取id
     *
     * @param appName 应用名
     * @param tbName  表名
     * @param size    批量大小
     * @return id列表
     */
    long[] nextIds(String appName, String tbName, int size);

    /**
     * 获取id
     *
     * @param appName 应用名
     * @param tbName  表名
     * @return id
     */
    String nextStringId(String appName, String tbName);

    /**
     * 批量获取id
     *
     * @param appName 应用名
     * @param tbName  表名
     * @param size    批量大小
     * @return id列表
     */
    String[] nextStringIds(String appName, String tbName, int size);

}
