package cn.waynechu.springcloud.apistarter.cache;

/**
 * @author zhuwei
 * @date 2020/4/20 16:48
 */
public interface BloomOperations<K, V> {
    /**
     * 创建布隆过滤器
     *
     * @param key          键
     * @param errorRate    错误率
     * @param initCapacity 初始化大小
     */
    void createFilter(K key, double errorRate, long initCapacity);

    /**
     * 添加元素
     *
     * @param key   键
     * @param value 值
     * @return true if success
     */
    Boolean add(K key, V value);

    /**
     * 批量添加元素
     *
     * @param key    键
     * @param values 值列表
     * @return true if success
     */
    @SuppressWarnings("unchecked")
    Boolean[] addMulti(K key, V... values);

    /**
     * 判断键值是否存在
     *
     * @param key   键
     * @param value 值
     * @return true if exist
     */
    Boolean exists(K key, V value);

    /**
     * 批量判断键值是否存在
     *
     * @param key    键
     * @param values 值列表
     * @return true if exist
     */
    Boolean[] existsMulti(K key, V... values);

    /**
     * 删除布隆过滤器
     *
     * @param key 键
     * @return true if success
     */
    Boolean delete(K key);

}
