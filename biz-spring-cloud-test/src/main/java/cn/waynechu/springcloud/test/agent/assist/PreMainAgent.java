package cn.waynechu.springcloud.test.agent.assist;

import lombok.extern.slf4j.Slf4j;

import java.lang.instrument.Instrumentation;

/**
 * Java Agent代理 - premain在主程序运行前执行
 *
 * <pre>
 *     在 JDK 1.5 中，Java 引入了 java.lang.Instrument 包，该包提供了一些工具帮助开发人员在 Java 程序运行时，
 *     动态修改系统中的 Class 类型。其中，使用该软件包的一个关键组件就是 Java agent
 *
 *     1. 添加 META-INF/MANIFEST.MF 文件并指定 Premain-Class。或者使用maven插件来自动生成该文件
 *     2. Premain-Class 类实现 premain() 方法。两种方法签名：
 *          - public static void premain(String args, Instrumentation instrumentation) （优先加载）
 *          - public static void premain(String args)
 *     3. 启动时添加参数，-javaagent:path-to-agent.jar
 *
 * </pre>
 *
 * @author zhuwei
 * @since 2021/1/23 14:15
 */
@Slf4j
public class PreMainAgent {

    public static void premain(String args, Instrumentation instrumentation) {
        log.info("------------------ assist PreMainAgent ------------------");
        instrumentation.addTransformer(new SimpleClassFileTransformer());
    }
}
