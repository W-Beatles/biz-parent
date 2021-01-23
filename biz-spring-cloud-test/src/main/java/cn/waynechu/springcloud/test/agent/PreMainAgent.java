package cn.waynechu.springcloud.test.agent;

import java.lang.instrument.Instrumentation;

/**
 * Java Agent代理 - premain在主程序运行前执行
 *
 * @author zhuwei
 * @since 2021/1/23 14:15
 */
public class PreMainAgent {

    public static void premain(String args, Instrumentation instrumentation) {
        System.out.println("------------------ PreMainAgent ------------------");
        instrumentation.addTransformer(new MyClassFileTransformer());
    }
}
