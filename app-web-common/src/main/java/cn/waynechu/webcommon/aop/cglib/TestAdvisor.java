package cn.waynechu.webcommon.aop.cglib;

import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;

/**
 * @author zhuwei
 * @date 2019/1/14 20:50
 */
public class TestAdvisor {
    public static void main(String[] args) {
        Foo foo = new Foo();

        JdkRegexpMethodPointcut jdkRegexpMethodPointcut = new JdkRegexpMethodPointcut();
        // 匹配方法名称中含有print的方法
        jdkRegexpMethodPointcut.setPattern(".*print.*");

        SimpleAdvice simpleAdvice = new SimpleAdvice();
        Advisor advisor = new DefaultPointcutAdvisor(jdkRegexpMethodPointcut, simpleAdvice);

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(foo);
        proxyFactory.addAdvisor(advisor);
        Foo proxyFoo = (Foo) proxyFactory.getProxy();

        proxyFoo.printOne("1");
        proxyFoo.printTwo("2");
        proxyFoo.threePrint("3");
    }
}
