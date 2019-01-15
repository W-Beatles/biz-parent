package cn.waynechu.webcommon.aop.jdk;

import cn.waynechu.webcommon.annotation.MethodPrintAnnotation;

/**
 * @author zhuwei
 * @date 2018/11/7 10:59
 */
public interface Sub {

    @MethodPrintAnnotation
    String printOne(String str);
}
