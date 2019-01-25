package cn.waynechu.webcommon.demo.aop.jdk;

/**
 * @author zhuwei
 * @date 2019/1/15 10:35
 */
public class TestJkdProxy {

    public static void main(String[] args) {
        Sub sub = (Sub) MethodPrintProxy.getInstance().bind(new SubImpl());
        sub.printOne("sub");
    }
}
