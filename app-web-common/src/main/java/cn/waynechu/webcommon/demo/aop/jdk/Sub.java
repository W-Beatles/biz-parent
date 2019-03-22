package cn.waynechu.webcommon.demo.aop.jdk;

import cn.waynechu.webcommon.annotation.MethodLogAnnotation;

/**
 * @author zhuwei
 * @date 2018/11/7 10:59
 */
public interface Sub {

    @MethodLogAnnotation
    String printOne(String str);
}
