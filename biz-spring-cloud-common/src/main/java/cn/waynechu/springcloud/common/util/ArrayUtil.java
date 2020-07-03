package cn.waynechu.springcloud.common.util;

import lombok.experimental.UtilityClass;

/**
 * @author zhuwei
 * @date 2020/4/29 14:51
 */
@UtilityClass
public class ArrayUtil {

    /**
     * 数组是否为空
     *
     * @param <T> 数组元素类型
     * @param array 数组
     * @return 是否为空
     */
    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }
}
