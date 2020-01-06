package com.waynechu.springcloud.test.spel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.Arrays;
import java.util.Date;

/**
 * @author zhuwei
 * @date 2019/3/26 12:25
 */
@Slf4j
public class SpelTest {

    public static void main(String[] args) {
        ExpressionParser parser = new SpelExpressionParser();

        // 字符串表达式
        String helloWorld = (String) parser.parseExpression("'Hello World'").getValue();
        log.info(helloWorld);
        // 调用concat()方法进行字符串连接
        String helloWorld2 = (String) parser.parseExpression("'Hello World'.concat('!')").getValue();
        log.info(helloWorld2);
        // 调用getBytes()方法获取字节数组
        Expression exp3 = parser.parseExpression("'Hello World'.bytes");
        byte[] bytes = (byte[]) exp3.getValue();
        log.info(Arrays.toString(bytes));
        // 通过getBytes().length获取字节数组的长度
        Expression exp4 = parser.parseExpression("'Hello World'.bytes.length");
        int length = (Integer) exp4.getValue();
        log.info(String.valueOf(length));
        // 调用构造函数
        Expression exp5 = parser.parseExpression("new String('hello world').toUpperCase()");
        String msg5 = exp5.getValue(String.class);
        log.info(msg5);

        // 对象属性检索、布尔条件构建
        Inventor inventor = new Inventor("Wayne Chu", new Date(), "the Han nationality");
        Expression exp6 = parser.parseExpression("name");
        String name = (String) exp6.getValue(inventor);
        log.info(name);

        Expression exp7 = parser.parseExpression("name == 'Wayne Chu'");
        boolean result = exp7.getValue(inventor, Boolean.class);
        log.info(String.valueOf(result));

        // 构造调用 参数仅限于原始类型及String
        Inventor einstein = parser.parseExpression("new Inventor()").getValue(Inventor.class);
        log.info(einstein.toString());

        // 模板解析
        String template = (String) parser.parseExpression(
                "My name is #{name}, i'm from #{nationality}, and my birthdate is #{birthdate}",
                new TemplateParserContext()
        ).getValue(inventor);
        log.info(template);
    }
}
