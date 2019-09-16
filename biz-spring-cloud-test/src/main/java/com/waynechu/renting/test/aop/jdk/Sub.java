package com.waynechu.renting.test.aop.jdk;

import cn.waynechu.springcloud.common.annotation.MethodLog;

/**
 * @author zhuwei
 * @date 2018/11/7 10:59
 */
public interface Sub {

    @MethodLog
    String printOne(String str);
}
