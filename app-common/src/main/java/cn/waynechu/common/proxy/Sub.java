package cn.waynechu.common.proxy;

import cn.waynechu.common.annotation.MethodPrintAnnotation;

/**
 * @author zhuwei
 * @date 2018/11/7 10:59
 */
public interface Sub {

    /**
     * 打印方法
     *
     * @param str str
     * @return Result
     */
    @MethodPrintAnnotation(isClassFullName = true, isFormat = true)
    String print(String str);
}
