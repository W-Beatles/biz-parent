package cn.waynechu.springcloud.test.agent.bytebuddy;

/**
 * 测试基于bytebuddy的java agent
 * <pre>
 * 1. 添加启动参数:
 *   -javaagent:D:\workspace\biz-parent\biz-spring-cloud-test\target\biz-spring-cloud-test-1.0.0-RELEASE.jar
 * 2. 修改 pom Premain-Class
 * </pre>
 *
 * @author zhuwei
 * @since 2021/1/28 13:51
 */
public class AgentTest {

    private void fun1() throws Exception {
        System.out.println("this is fun 1.");
        Thread.sleep(500);
    }

    private void fun2() throws Exception {
        System.out.println("this is fun 2.");
        Thread.sleep(500);
    }

    public static void main(String[] args) throws Exception {
        AgentTest test = new AgentTest();
        test.fun1();
        test.fun2();
    }
}
