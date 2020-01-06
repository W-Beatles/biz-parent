package com.waynechu.springcloud.test.aop.jdk;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhuwei
 * @date 2018/11/7 11:00
 */
@Slf4j
public class SubImpl implements Sub {

    @Override
    public String printOne(String str) {
        log.info("call printOne method, arg is {}", str);
        return "printOne";
    }
}
