package cn.waynechu.springcloud.common.util;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 本地线程工具类。使用LIFO队列，用于嵌套ThreadLocal嵌套设值的情况
 *
 * @author zhuwei
 * @date 2018/1/21 14:03
 */
public class DequeThreadLocal<T> {

    private final ThreadLocal<Deque<T>> dequeThreadLocal;

    public DequeThreadLocal() {
        this.dequeThreadLocal = ThreadLocal.withInitial(ArrayDeque::new);
    }

    /**
     * 设置本地线程变量
     *
     * @param obj 本地线程变量
     */
    public void offerFirst(T obj) {
        dequeThreadLocal.get().offerFirst(obj);
    }

    /**
     * 获取并删除本地线程变量
     */
    public T pollFirst() {
        Deque<T> deque = dequeThreadLocal.get();
        T obj = deque.pollFirst();
        if (deque.isEmpty()) {
            dequeThreadLocal.remove();
        }
        return obj;
    }

    /**
     * 清空本地线程变量
     */
    public void clear() {
        dequeThreadLocal.remove();
    }
}
