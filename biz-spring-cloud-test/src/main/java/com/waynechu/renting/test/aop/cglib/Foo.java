package com.waynechu.renting.test.aop.cglib;

import cn.waynechu.springcloud.common.annotation.MethodLog;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhuwei
 * @date 2019/1/14 20:48
 */
@Slf4j
public class Foo {

    @MethodLog
    public String printOne(String str) {
        log.info("call printOne method, arg is {}", str);
        return "printOne";
    }

    @MethodLog
    public String printTwo(String str) {
        log.info("call printTwo method, arg is {}", str);
        return "printTwo";
    }

    @MethodLog
    public String threePrint(String str) {
        log.info("call printThree method, arg is {}", str);
        return "threePrint";
    }
}
