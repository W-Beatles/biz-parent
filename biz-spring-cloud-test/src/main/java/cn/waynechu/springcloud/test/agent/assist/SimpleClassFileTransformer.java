package cn.waynechu.springcloud.test.agent.assist;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * 使用Javassist作为字节码修改工具
 * 示例： 拦截testAgent方法并打印方法的耗时
 *
 * @author zhuwei
 * @since 2021/1/23 14:38
 */
public class SimpleClassFileTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        // 获取Javassist Class池
        ClassPool cp = ClassPool.getDefault();
        try {
            // 获取到Class池中的PreMainAgent CtClass对象 (与PreMainAgent的Class对象一对一的关系)
            CtClass ctClass = cp.getCtClass(className.replace("/", "."));
            // 找到testAgent方法
            CtMethod method = ctClass.getDeclaredMethod("testAgent");
            // 增加本地变量
            method.addLocalVariable("beginTime", CtClass.longType);
            // 在main方法之前增加代码
            method.insertBefore("long beginTime = System.currentTimeMillis();");
            method.insertBefore("log.info(\"开始调用\");");
            // 在main方法之后打印出耗时长短
            method.insertAfter("log.info(\"调用结束\");");
            method.insertAfter("log.info(\"总共耗时：\" + (System.currentTimeMillis() - beginTime) + \"ms\");");
            // 返回修改过后的字节码数据
            return ctClass.toBytecode();
        } catch (NotFoundException e) {
            return null;
        } catch (CannotCompileException | IOException e) {
            e.printStackTrace();
        }
        // 返回null, 代表没有修改此字节码
        return null;
    }
}
