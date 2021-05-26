package cn.waynechu.springcloud.test.hash;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author zhuwei
 * @since 2021/5/26 14:21
 */
public class ConsistentHash<T> {

    // 哈希方法
    private final IHashService iHashService;
    // 每个机器节点关联的虚拟节点数量
    private final int numberOfReplicas;
    // 环形虚拟节点
    private final SortedMap<Long, T> circle = new TreeMap<>();

    public ConsistentHash(IHashService iHashService, int numberOfReplicas, Collection<T> nodes) {
        this.iHashService = iHashService;
        this.numberOfReplicas = numberOfReplicas;
        for (T node : nodes) {
            add(node);
        }
    }

    /**
     * 增加真实机器节点
     *
     * @param node T
     */
    public void add(T node) {
        for (int i = 0; i < this.numberOfReplicas; i++) {
            circle.put(this.iHashService.hash(node.toString() + i), node);
        }
    }

    /**
     * 删除真实机器节点
     *
     * @param node T
     */
    public void remove(T node) {
        for (int i = 0; i < this.numberOfReplicas; i++) {
            circle.remove(this.iHashService.hash(node.toString() + i));
        }
    }

    /**
     * 获取key所在的虚拟节点
     *
     * @param key key
     * @return 虚拟节点
     */
    public T get(String key) {
        if (circle.isEmpty()) return null;

        long hash = iHashService.hash(key);

        // 沿环的顺时针找到一个虚拟节点
        if (!circle.containsKey(hash)) {
            SortedMap<Long, T> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(hash);
    }
}
