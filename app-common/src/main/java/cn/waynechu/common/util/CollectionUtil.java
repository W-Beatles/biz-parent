package cn.waynechu.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author zhuwei
 * @date 2018/11/6 16:18
 */
public class CollectionUtil {
    public static boolean isNotNullOrEmpty(Collection<? extends Object> collection) {
        return !isNullOrEmpty(collection);
    }

    public static boolean isNotNullOrEmpty(Map<? extends Object, ? extends Object> map) {
        return !isNullOrEmpty(map);
    }

    public static boolean isNullOrEmpty(Collection<? extends Object> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNullOrEmpty(Map<? extends Object, ? extends Object> map) {
        return map == null || map.isEmpty();
    }

    public static <T> List<List<T>> splitList(List<T> list, int len) {
        List<List<T>> result = new ArrayList<>();

        if (list == null || list.isEmpty() || len < 1) {
            return result;
        }

        int size = list.size();
        int count = (size + len - 1) / len;

        for (int i = 0; i < count; i++) {
            List<T> subList = list.subList(i * len, ((i + 1) * len > size ? size : (i + 1) * len));
            result.add(subList);
        }
        return result;
    }
}
