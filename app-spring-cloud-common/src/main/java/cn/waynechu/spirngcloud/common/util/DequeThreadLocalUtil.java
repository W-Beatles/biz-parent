package cn.waynechu.spirngcloud.common.util;

import lombok.experimental.UtilityClass;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 *  本地线程工具类。使用LIFO队列，用于嵌套ThreadLocal嵌套设值的情况
 *
 * @author zhuwei
 * @date 2018/1/21 14:03
 */
@UtilityClass
public class DequeThreadLocalUtil {
    private static final ThreadLocal<Deque<Object>> DEQUE_THREAD_LOCAL = ThreadLocal.withInitial(ArrayDeque::new);

    /**
     * 设置本地线程变量
     *
     * @param obj 本地线程变量
     */
    public static void offerFirst(Object obj) {
        DEQUE_THREAD_LOCAL.get().offerFirst(obj);
    }

    /**
     * 获取并删除本地线程变量
     */
    public static Object pollFirst() {
        Deque<Object> deque = DEQUE_THREAD_LOCAL.get();
        Object obj = deque.pollFirst();
        if (deque.isEmpty()) {
            DEQUE_THREAD_LOCAL.remove();
        }
        return obj;
    }

    /**
     * 清空本地线程变量
     */
    public static void clear() {
        DEQUE_THREAD_LOCAL.remove();
    }
}
