package cn.waynechu.springcloud.test.aop.jdk;

/**
 * @author zhuwei
 * @since 2019/1/15 10:35
 */
public class TestJkdProxy {

    public static void main(String[] args) {
        Sub sub = (Sub) MethodPrintProxy.getInstance().bind(new SubImpl());
        sub.printOne("sub");
    }
}
