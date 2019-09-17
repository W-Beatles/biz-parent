package cn.waynechu.springcloud.common.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * SPEL表达式工具类
 *
 * @author zhuwei
 * @date 2019/9/17 14:57
 */
@Slf4j
@UtilityClass
public class SpelUtil {

    private static final String POUND_KEY = "#";

    private final static ExpressionParser SPEL_EXPRESSION_PARSER = new SpelExpressionParser();

    /**
     * 判断表达式是否是SPEL表达式
     *
     * @param spelStr 字符串表达式
     * @return 是SPEL表达式返回true
     */
    public static boolean isSpelEx(String spelStr) {
        return spelStr.startsWith(POUND_KEY);
    }

    /**
     * 解析SPEL表达式
     *
     * @param spelStr    spel表达式
     * @param paramNames 参数名称
     * @param arguments  参数值
     * @return SPEL表达式解析值
     */
    public static String getKey(String spelStr, String[] paramNames, Object[] arguments) {
        Expression expression = SPEL_EXPRESSION_PARSER.parseExpression(spelStr);
        EvaluationContext context = new StandardEvaluationContext();
        int length = paramNames.length;
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                context.setVariable(paramNames[i], arguments[i]);
            }
        }
        return expression.getValue(context, String.class);
    }
}
