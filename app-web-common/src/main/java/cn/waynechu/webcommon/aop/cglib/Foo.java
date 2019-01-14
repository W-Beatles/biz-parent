package cn.waynechu.webcommon.aop.cglib;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhuwei
 * @date 2019/1/14 20:48
 */
@Slf4j
public class Foo {

    public String printOne(String str) {
        log.info("call printOne method, arg is {}", str);
        return "print";
    }

    public String printTwo(String str) {
        log.info("call printTwo method, arg is {}", str);
        return "print";
    }

    public String threePrint(String str) {
        log.info("call printThree method, arg is {}", str);
        return "print";
    }
}
