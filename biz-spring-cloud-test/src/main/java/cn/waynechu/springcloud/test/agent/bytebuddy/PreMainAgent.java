package cn.waynechu.springcloud.test.agent.bytebuddy;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

/**
 * @author zhuwei
 * @since 2021/1/28 13:48
 */
public class PreMainAgent {

    public static void premain(String args, Instrumentation instrumentation) {
        System.out.println("------------------ bytebuddy PreMainAgent ------------------");

        AgentBuilder.Transformer transformer = new AgentBuilder.Transformer() {
            @Override
            public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule) {
                return builder
                        // 拦截任意方法
                        .method(ElementMatchers.any())
                        // 委托
                        .intercept(MethodDelegation.to(TimeInterceptor.class));
            }
        };

        AgentBuilder.Listener listener = new AgentBuilder.Listener() {
            @Override
            public void onDiscovery(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {
            }

            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b, DynamicType dynamicType) {
            }

            @Override
            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule javaModule, boolean b) {
            }

            @Override
            public void onError(String s, ClassLoader classLoader, JavaModule javaModule, boolean b, Throwable throwable) {
            }

            @Override
            public void onComplete(String s, ClassLoader classLoader, JavaModule javaModule, boolean b) {
            }
        };

        new AgentBuilder
                .Default()
                // 指定需要拦截的类
                .type(ElementMatchers.nameStartsWith("cn.waynechu.springcloud.test.agent.bytebuddy"))
                .transform(transformer)
                .with(listener)
                .installOn(instrumentation);
    }
}
