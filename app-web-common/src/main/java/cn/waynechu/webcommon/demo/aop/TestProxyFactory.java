package cn.waynechu.webcommon.demo.aop;

import cn.waynechu.webcommon.demo.aop.cglib.Foo;
import cn.waynechu.webcommon.demo.aop.cglib.MethodPrintAdvice;
import cn.waynechu.webcommon.demo.aop.jdk.Sub;
import cn.waynechu.webcommon.demo.aop.jdk.SubImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;

/**
 * @author zhuwei
 * @date 2019/1/14 20:50
 */
@Slf4j
public class TestProxyFactory {
    public static void main(String[] args) {
        JdkRegexpMethodPointcut jdkRegexpMethodPointcut = new JdkRegexpMethodPointcut();
        // 匹配方法名称中含有print的方法
        jdkRegexpMethodPointcut.setPattern(".*printOne.*");

        // 创建advisor
        MethodPrintAdvice methodPrintAdvice = new MethodPrintAdvice();
        Advisor advisor = new DefaultPointcutAdvisor(jdkRegexpMethodPointcut, methodPrintAdvice);

        log.info("----- CGLIB -----");
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.addAdvisor(advisor);
        proxyFactory.setTarget(new Foo());
        Foo proxyFoo = (Foo) proxyFactory.getProxy();

        proxyFoo.printOne("1");
        proxyFoo.printTwo("2");
        proxyFoo.threePrint("3");
        log.info(proxyFoo.getClass().getName());

        log.info("----- GDK -----");
        ProxyFactory proxyFactory1 = new ProxyFactory();
        proxyFactory1.addAdvisor(advisor);
        proxyFactory1.setInterfaces(Sub.class);
        proxyFactory1.setTarget(new SubImpl());
        Sub proxySub = (Sub) proxyFactory1.getProxy();
        proxySub.printOne("1");
        log.info(proxySub.getClass().getName());
    }
}
