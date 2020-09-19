#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

package ${package}.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhuwei
 * @since 2019/4/2 10:15
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartMain.class)
public class StartMain {

    @Test
    public void test() {
    }
}
