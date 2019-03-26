package cn.waynechu.renting.test.spi;

import lombok.extern.slf4j.Slf4j;

import java.util.ServiceLoader;

/**
 * Java SPI演示案例
 * <p>
 * 1、服务提供者提供接口及具体实现
 * 2、在jar包的META-INF/services目录下创建一个以“接口全限定名”为命名的文件，内容为实现类的全限定名；
 * 3、接口实现类所在的jar包放在主程序的classpath中；
 * 4、主程序通过java.util.ServiceLoader动态装载实现模块，它通过扫描META-INF/services目录下的配置文件找到实现类的全限定名，把类加载到JVM；
 * 5、SPI的实现类必须携带一个不带参数的构造方法；
 *
 * @author zhuwei
 * @date 2019/3/26 14:56
 */
@Slf4j
public class JavaSPITest {

    public static void main(String[] args) {
        ServiceLoader<Robot> serviceLoader = ServiceLoader.load(Robot.class);
        log.info("Java SPI");
        serviceLoader.forEach(Robot::sayHello);
    }
}
