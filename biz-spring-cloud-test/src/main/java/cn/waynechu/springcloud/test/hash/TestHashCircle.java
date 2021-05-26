package cn.waynechu.springcloud.test.hash;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author zhuwei
 * @since 2021/5/26 14:28
 */
@Slf4j
public class TestHashCircle {

    // 机器节点IP前缀
    private static final String IP_PREFIX = "192.168.0.";

    public static void main(String[] args) {
        // 每台真实机器节点上保存的记录条数
        Map<String, Integer> map = new HashMap<>();

        // 真实机器节点, 模拟10台
        List<Node> nodes = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            // 初始化记录
            map.put(IP_PREFIX + i, 0);
            Node node = new Node(IP_PREFIX + i, "node" + i);
            nodes.add(node);
        }

        IHashService iHashService = new HashService();
        // 每台真实机器引入500个虚拟节点
        ConsistentHash<Node> consistentHash = new ConsistentHash<>(iHashService, 500, nodes);

        // 将5000条记录尽可能均匀的存储到10台机器节点上
        for (int i = 0; i < 5000; i++) {
            // 产生随机一个字符串当做一条记录，可以是其它更复杂的业务对象，比如随机字符串相当于对象的业务唯一标识
            String data = UUID.randomUUID().toString() + i;
            // 通过记录找到真实机器节点
            Node node = consistentHash.get(data);
            // ...
            map.put(node.getIp(), map.get(node.getIp()) + 1);
        }

        // 打印每台真实机器节点保存的记录条数
        for (int i = 1; i <= 10; i++) {
            log.info(IP_PREFIX + i + "节点记录条数：" + map.get(IP_PREFIX + i));
        }
    }
}
