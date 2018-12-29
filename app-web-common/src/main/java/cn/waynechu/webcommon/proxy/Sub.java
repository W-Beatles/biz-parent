package cn.waynechu.webcommon.proxy;

import cn.waynechu.webcommon.annotation.MethodPrintAnnotation;

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
