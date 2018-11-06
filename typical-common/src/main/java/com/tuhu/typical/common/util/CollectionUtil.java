package com.tuhu.typical.common.util;

import java.util.Collection;
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
}
