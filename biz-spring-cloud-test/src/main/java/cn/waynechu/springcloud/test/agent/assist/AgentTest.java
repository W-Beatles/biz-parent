package cn.waynechu.springcloud.test.agent.assist;

import cn.hutool.core.thread.ThreadUtil;

/**
 * 测试基于javassist的java agent
 * <p>
 * 添加启动参数:
 * -javaagent:D:\workspace\biz-parent\biz-spring-cloud-test\target\biz-spring-cloud-test-1.0.0-RELEASE.jar
 *
 * @author zhuwei
 * @since 2021/1/23 14:46
 */
public class AgentTest {

    public static void main(String[] args) {
        testAgent();
    }

    public static void testAgent() {
        System.out.println("testAgent执行中");
        ThreadUtil.sleep(1000);
    }
}
