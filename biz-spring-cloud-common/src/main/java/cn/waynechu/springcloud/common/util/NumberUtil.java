package cn.waynechu.springcloud.common.util;

import lombok.experimental.UtilityClass;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author zhuwei
 * @since 2020/4/29 14:51
 */
@UtilityClass
public class NumberUtil {

    /**
     * 默认除法运算精度
     */
    private static final int DEFAULT_DIV_SCALE = 10;

    /**
     * 提供精确的加法运算
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 和
     */
    public static double add(float v1, float v2) {
        return add(Float.toString(v1), Float.toString(v2)).doubleValue();
    }

    /**
     * 提供精确的加法运算
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 和
     */
    public static double add(float v1, double v2) {
        return add(Float.toString(v1), Double.toString(v2)).doubleValue();
    }

    /**
     * 提供精确的加法运算
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 和
     */
    public static double add(double v1, float v2) {
        return add(Double.toString(v1), Float.toString(v2)).doubleValue();
    }

    /**
     * 提供精确的加法运算
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 和
     */
    public static double add(double v1, double v2) {
        return add(Double.toString(v1), Double.toString(v2)).doubleValue();
    }

    /**
     * 提供精确的加法运算
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 和
     */
    public static double add(Double v1, Double v2) {
        //noinspection RedundantCast
        return add((Number) v1, (Number) v2).doubleValue();
    }

    /**
     * 提供精确的加法运算
     * <br>
     * 如果传入多个值为null或者空，则返回0
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 和
     */
    public static BigDecimal add(Number v1, Number v2) {
        return add(new Number[]{v1, v2});
    }

    /**
     * 提供精确的加法运算
     * <br>
     * 如果传入多个值为null或者空，则返回0
     *
     * @param values 多个被加值
     * @return 和
     */
    public static BigDecimal add(Number... values) {
        if (ArrayUtil.isEmpty(values)) {
            return BigDecimal.ZERO;
        }

        Number value = values[0];
        BigDecimal result = new BigDecimal(null == value ? "0" : value.toString());
        for (int i = 1; i < values.length; i++) {
            value = values[i];
            if (null != value) {
                result = result.add(new BigDecimal(value.toString()));
            }
        }
        return result;
    }

    /**
     * 提供精确的加法运算
     * <br>
     * 如果传入多个值为null或者空，则返回0
     *
     * @param values 多个被加值
     * @return 和
     */
    public static BigDecimal add(String... values) {
        if (ArrayUtil.isEmpty(values)) {
            return BigDecimal.ZERO;
        }

        String value = values[0];
        BigDecimal result = new BigDecimal(null == value ? "0" : value);
        for (int i = 1; i < values.length; i++) {
            value = values[i];
            if (null != value) {
                result = result.add(new BigDecimal(value));
            }
        }
        return result;
    }

    /**
     * 提供精确的加法运算
     * <br>
     * 如果传入多个值为null或者空，则返回0
     *
     * @param values 多个被加值
     * @return 和
     */
    public static BigDecimal add(BigDecimal... values) {
        if (ArrayUtil.isEmpty(values)) {
            return BigDecimal.ZERO;
        }

        BigDecimal value = values[0];
        BigDecimal result = null == value ? BigDecimal.ZERO : value;
        for (int i = 1; i < values.length; i++) {
            value = values[i];
            if (null != value) {
                result = result.add(value);
            }
        }
        return result;
    }

    /**
     * 提供精确的减法运算
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 差
     */
    public static double sub(float v1, float v2) {
        return sub(Float.toString(v1), Float.toString(v2)).doubleValue();
    }

    /**
     * 提供精确的减法运算
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 差
     */
    public static double sub(float v1, double v2) {
        return sub(Float.toString(v1), Double.toString(v2)).doubleValue();
    }

    /**
     * 提供精确的减法运算
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 差
     */
    public static double sub(double v1, float v2) {
        return sub(Double.toString(v1), Float.toString(v2)).doubleValue();
    }

    /**
     * 提供精确的减法运算
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 差
     */
    public static double sub(double v1, double v2) {
        return sub(Double.toString(v1), Double.toString(v2)).doubleValue();
    }

    /**
     * 提供精确的减法运算
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 差
     */
    public static double sub(Double v1, Double v2) {
        //noinspection RedundantCast
        return sub((Number) v1, (Number) v2).doubleValue();
    }

    /**
     * 提供精确的减法运算
     * <br>
     * 如果传入多个值为null或者空，则返回0
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 差
     */
    public static BigDecimal sub(Number v1, Number v2) {
        return sub(new Number[]{v1, v2});
    }

    /**
     * 提供精确的减法运算
     * <br>
     * 如果传入多个值为null或者空，则返回0
     *
     * @param values 多个被减值
     * @return 差
     */
    public static BigDecimal sub(Number... values) {
        if (ArrayUtil.isEmpty(values)) {
            return BigDecimal.ZERO;
        }

        Number value = values[0];
        BigDecimal result = new BigDecimal(null == value ? "0" : value.toString());
        for (int i = 1; i < values.length; i++) {
            value = values[i];
            if (null != value) {
                result = result.subtract(new BigDecimal(value.toString()));
            }
        }
        return result;
    }

    /**
     * 提供精确的减法运算
     * <br>
     * 如果传入多个值为null或者空，则返回0
     *
     * @param values 多个被减值
     * @return 差
     */
    public static BigDecimal sub(String... values) {
        if (ArrayUtil.isEmpty(values)) {
            return BigDecimal.ZERO;
        }

        String value = values[0];
        BigDecimal result = new BigDecimal(null == value ? "0" : value);
        for (int i = 1; i < values.length; i++) {
            value = values[i];
            if (null != value) {
                result = result.subtract(new BigDecimal(value));
            }
        }
        return result;
    }

    /**
     * 提供精确的减法运算
     * <br>
     * 如果传入多个值为null或者空，则返回0
     *
     * @param values 多个被减值
     * @return 差
     */
    public static BigDecimal sub(BigDecimal... values) {
        if (ArrayUtil.isEmpty(values)) {
            return BigDecimal.ZERO;
        }

        BigDecimal value = values[0];
        BigDecimal result = null == value ? BigDecimal.ZERO : value;
        for (int i = 1; i < values.length; i++) {
            value = values[i];
            if (null != value) {
                result = result.subtract(value);
            }
        }
        return result;
    }

    /**
     * 提供精确的乘法运算
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 积
     */
    public static double mul(float v1, float v2) {
        return mul(Float.toString(v1), Float.toString(v2)).doubleValue();
    }

    /**
     * 提供精确的乘法运算
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 积
     */
    public static double mul(float v1, double v2) {
        return mul(Float.toString(v1), Double.toString(v2)).doubleValue();
    }

    /**
     * 提供精确的乘法运算
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 积
     */
    public static double mul(double v1, float v2) {
        return mul(Double.toString(v1), Float.toString(v2)).doubleValue();
    }

    /**
     * 提供精确的乘法运算
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 积
     */
    public static double mul(double v1, double v2) {
        return mul(Double.toString(v1), Double.toString(v2)).doubleValue();
    }

    /**
     * 提供精确的乘法运算
     * <br>
     * 如果传入多个值为null或者空，则返回0
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 积
     */
    public static double mul(Double v1, Double v2) {
        //noinspection RedundantCast
        return mul((Number) v1, (Number) v2).doubleValue();
    }

    /**
     * 提供精确的乘法运算
     * <br>
     * 如果传入多个值为null或者空，则返回0
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 积
     */
    public static BigDecimal mul(Number v1, Number v2) {
        return mul(new Number[]{v1, v2});
    }

    /**
     * 提供精确的乘法运算<br>
     * 如果传入多个值为null或者空，则返回0
     *
     * @param values 多个被乘值
     * @return 积
     */
    public static BigDecimal mul(Number... values) {
        if (ArrayUtil.isEmpty(values)) {
            return BigDecimal.ZERO;
        }

        Number value = values[0];
        BigDecimal result = new BigDecimal(null == value ? "0" : value.toString());
        for (int i = 1; i < values.length; i++) {
            value = values[i];
            result = result.multiply(new BigDecimal(null == value ? "0" : value.toString()));
        }
        return result;
    }

    /**
     * 提供精确的乘法运算
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 积
     */
    public static BigDecimal mul(String v1, String v2) {
        return mul(new BigDecimal(v1), new BigDecimal(v2));
    }

    /**
     * 提供精确的乘法运算
     * <br>
     * 如果传入多个值为null或者空，则返回0
     *
     * @param values 多个被乘值
     * @return 积
     */
    public static BigDecimal mul(String... values) {
        if (ArrayUtil.isEmpty(values)) {
            return BigDecimal.ZERO;
        }

        String value = values[0];
        BigDecimal result = new BigDecimal(null == value ? "0" : value);
        for (int i = 1; i < values.length; i++) {
            value = values[i];
            if (null != value) {
                result = result.multiply(new BigDecimal(value));
            }
        }
        return result;
    }

    /**
     * 提供精确的乘法运算
     * <br>
     * 如果传入多个值为null或者空，则返回0
     *
     * @param values 多个被乘值
     * @return 积
     */
    public static BigDecimal mul(BigDecimal... values) {
        if (ArrayUtil.isEmpty(values)) {
            return BigDecimal.ZERO;
        }

        BigDecimal value = values[0];
        BigDecimal result = null == value ? BigDecimal.ZERO : value;
        for (int i = 1; i < values.length; i++) {
            value = values[i];
            if (null != value) {
                result = result.multiply(value);
            }
        }
        return result;
    }

    /**
     * 提供(相对)精确的除法运算,当发生除不尽的情况的时候,精确到小数点后10位,后面的四舍五入
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(float v1, float v2) {
        return div(v1, v2, DEFAULT_DIV_SCALE);
    }

    /**
     * 提供(相对)精确的除法运算,当发生除不尽的情况的时候,精确到小数点后10位,后面的四舍五入
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(float v1, double v2) {
        return div(v1, v2, DEFAULT_DIV_SCALE);
    }

    /**
     * 提供(相对)精确的除法运算,当发生除不尽的情况的时候,精确到小数点后10位,后面的四舍五入
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1, float v2) {
        return div(v1, v2, DEFAULT_DIV_SCALE);
    }

    /**
     * 提供(相对)精确的除法运算,当发生除不尽的情况的时候,精确到小数点后10位,后面的四舍五入
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEFAULT_DIV_SCALE);
    }

    /**
     * 提供(相对)精确的除法运算,当发生除不尽的情况的时候,精确到小数点后10位,后面的四舍五入
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(Double v1, Double v2) {
        return div(v1, v2, DEFAULT_DIV_SCALE);
    }

    /**
     * 提供(相对)精确的除法运算,当发生除不尽的情况的时候,精确到小数点后10位,后面的四舍五入
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static BigDecimal div(Number v1, Number v2) {
        return div(v1, v2, DEFAULT_DIV_SCALE);
    }

    /**
     * 提供(相对)精确的除法运算,当发生除不尽的情况的时候,精确到小数点后10位,后面的四舍五入
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static BigDecimal div(String v1, String v2) {
        return div(v1, v2, DEFAULT_DIV_SCALE);
    }

    /**
     * 提供(相对)精确的除法运算,当发生除不尽的情况时,由scale指定精确度,后面的四舍五入
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 精确度，如果为负值，取绝对值
     * @return 两个参数的商
     */
    public static double div(float v1, float v2, int scale) {
        return div(v1, v2, scale, RoundingMode.HALF_UP);
    }

    /**
     * 提供(相对)精确的除法运算,当发生除不尽的情况时,由scale指定精确度,后面的四舍五入
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 精确度，如果为负值，取绝对值
     * @return 两个参数的商
     */
    public static double div(float v1, double v2, int scale) {
        return div(v1, v2, scale, RoundingMode.HALF_UP);
    }

    /**
     * 提供(相对)精确的除法运算,当发生除不尽的情况时,由scale指定精确度,后面的四舍五入
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 精确度，如果为负值，取绝对值
     * @return 两个参数的商
     */
    public static double div(double v1, float v2, int scale) {
        return div(v1, v2, scale, RoundingMode.HALF_UP);
    }

    /**
     * 提供(相对)精确的除法运算,当发生除不尽的情况时,由scale指定精确度,后面的四舍五入
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 精确度，如果为负值，取绝对值
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        return div(v1, v2, scale, RoundingMode.HALF_UP);
    }

    /**
     * 提供(相对)精确的除法运算,当发生除不尽的情况时,由scale指定精确度,后面的四舍五入
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 精确度，如果为负值，取绝对值
     * @return 两个参数的商
     */
    public static double div(Double v1, Double v2, int scale) {
        return div(v1, v2, scale, RoundingMode.HALF_UP);
    }

    /**
     * 提供(相对)精确的除法运算,当发生除不尽的情况时,由scale指定精确度,后面的四舍五入
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 精确度，如果为负值，取绝对值
     * @return 两个参数的商
     */
    public static BigDecimal div(Number v1, Number v2, int scale) {
        return div(v1, v2, scale, RoundingMode.HALF_UP);
    }

    /**
     * 提供(相对)精确的除法运算,当发生除不尽的情况时,由scale指定精确度,后面的四舍五入
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 精确度，如果为负值，取绝对值
     * @return 两个参数的商
     */
    public static BigDecimal div(String v1, String v2, int scale) {
        return div(v1, v2, scale, RoundingMode.HALF_UP);
    }

    /**
     * 提供(相对)精确的除法运算,当发生除不尽的情况时,由scale指定精确度
     *
     * @param v1           被除数
     * @param v2           除数
     * @param scale        精确度，如果为负值，取绝对值
     * @param roundingMode 保留小数的模式 {@link RoundingMode}
     * @return 两个参数的商
     */
    public static double div(float v1, float v2, int scale, RoundingMode roundingMode) {
        return div(Float.toString(v1), Float.toString(v2), scale, roundingMode).doubleValue();
    }

    /**
     * 提供(相对)精确的除法运算,当发生除不尽的情况时,由scale指定精确度
     *
     * @param v1           被除数
     * @param v2           除数
     * @param scale        精确度，如果为负值，取绝对值
     * @param roundingMode 保留小数的模式 {@link RoundingMode}
     * @return 两个参数的商
     */
    public static double div(float v1, double v2, int scale, RoundingMode roundingMode) {
        return div(Float.toString(v1), Double.toString(v2), scale, roundingMode).doubleValue();
    }

    /**
     * 提供(相对)精确的除法运算,当发生除不尽的情况时,由scale指定精确度
     *
     * @param v1           被除数
     * @param v2           除数
     * @param scale        精确度，如果为负值，取绝对值
     * @param roundingMode 保留小数的模式 {@link RoundingMode}
     * @return 两个参数的商
     */
    public static double div(double v1, float v2, int scale, RoundingMode roundingMode) {
        return div(Double.toString(v1), Float.toString(v2), scale, roundingMode).doubleValue();
    }

    /**
     * 提供(相对)精确的除法运算,当发生除不尽的情况时,由scale指定精确度
     *
     * @param v1           被除数
     * @param v2           除数
     * @param scale        精确度，如果为负值，取绝对值
     * @param roundingMode 保留小数的模式 {@link RoundingMode}
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale, RoundingMode roundingMode) {
        return div(Double.toString(v1), Double.toString(v2), scale, roundingMode).doubleValue();
    }

    /**
     * 提供(相对)精确的除法运算,当发生除不尽的情况时,由scale指定精确度
     *
     * @param v1           被除数
     * @param v2           除数
     * @param scale        精确度，如果为负值，取绝对值
     * @param roundingMode 保留小数的模式 {@link RoundingMode}
     * @return 两个参数的商
     */
    public static double div(Double v1, Double v2, int scale, RoundingMode roundingMode) {
        //noinspection RedundantCast
        return div((Number) v1, (Number) v2, scale, roundingMode).doubleValue();
    }

    /**
     * 提供(相对)精确的除法运算,当发生除不尽的情况时,由scale指定精确度
     *
     * @param v1           被除数
     * @param v2           除数
     * @param scale        精确度，如果为负值，取绝对值
     * @param roundingMode 保留小数的模式 {@link RoundingMode}
     * @return 两个参数的商
     */
    public static BigDecimal div(Number v1, Number v2, int scale, RoundingMode roundingMode) {
        return div(v1.toString(), v2.toString(), scale, roundingMode);
    }

    /**
     * 提供(相对)精确的除法运算,当发生除不尽的情况时,由scale指定精确度
     *
     * @param v1           被除数
     * @param v2           除数
     * @param scale        精确度，如果为负值，取绝对值
     * @param roundingMode 保留小数的模式 {@link RoundingMode}
     * @return 两个参数的商
     */
    public static BigDecimal div(String v1, String v2, int scale, RoundingMode roundingMode) {
        return div(new BigDecimal(v1), new BigDecimal(v2), scale, roundingMode);
    }

    /**
     * 提供(相对)精确的除法运算,当发生除不尽的情况时,由scale指定精确度
     *
     * @param v1           被除数
     * @param v2           除数
     * @param scale        精确度，如果为负值，取绝对值
     * @param roundingMode 保留小数的模式 {@link RoundingMode}
     * @return 两个参数的商
     */
    public static BigDecimal div(BigDecimal v1, BigDecimal v2, int scale, RoundingMode roundingMode) {
        Assert.notNull(v2, "Divisor must be not null !");
        if (null == v1) {
            return BigDecimal.ZERO;
        }
        if (scale < 0) {
            scale = -scale;
        }
        return v1.divide(v2, scale, roundingMode);
    }

    // ------------------------------------------------------------------------------------------- round

    /**
     * 保留固定位数小数
     * <br>
     * 采用四舍五入策略 {@link RoundingMode#HALF_UP}<br>
     * 例如保留2位小数：123.456789 =》 123.46
     *
     * @param v     值
     * @param scale 保留小数位数
     * @return 新值
     */
    public static BigDecimal round(double v, int scale) {
        return round(v, scale, RoundingMode.HALF_UP);
    }

    /**
     * 保留固定位数小数<br>
     * 采用四舍五入策略 {@link RoundingMode#HALF_UP}<br>
     * 例如保留2位小数：123.456789 =》 123.46
     *
     * @param v     值
     * @param scale 保留小数位数
     * @return 新值
     */
    public static String roundStr(double v, int scale) {
        return round(v, scale).toString();
    }

    /**
     * 保留固定位数小数<br>
     * 采用四舍五入策略 {@link RoundingMode#HALF_UP}<br>
     * 例如保留2位小数：123.456789 =》 123.46
     *
     * @param numberStr 数字值的字符串表现形式
     * @param scale     保留小数位数
     * @return 新值
     */
    public static BigDecimal round(String numberStr, int scale) {
        return round(numberStr, scale, RoundingMode.HALF_UP);
    }

    /**
     * 保留固定位数小数<br>
     * 采用四舍五入策略 {@link RoundingMode#HALF_UP}<br>
     * 例如保留2位小数：123.456789 =》 123.46
     *
     * @param number 数字值
     * @param scale  保留小数位数
     * @return 新值
     */
    public static BigDecimal round(BigDecimal number, int scale) {
        return round(number, scale, RoundingMode.HALF_UP);
    }

    /**
     * 保留固定位数小数<br>
     * 采用四舍五入策略 {@link RoundingMode#HALF_UP}<br>
     * 例如保留2位小数：123.456789 =》 123.46
     *
     * @param numberStr 数字值的字符串表现形式
     * @param scale     保留小数位数
     * @return 新值
     */
    public static String roundStr(String numberStr, int scale) {
        return round(numberStr, scale).toString();
    }

    /**
     * 保留固定位数小数<br>
     * 例如保留四位小数：123.456789 =》 123.4567
     *
     * @param v            值
     * @param scale        保留小数位数
     * @param roundingMode 保留小数的模式 {@link RoundingMode}
     * @return 新值
     */
    public static BigDecimal round(double v, int scale, RoundingMode roundingMode) {
        return round(Double.toString(v), scale, roundingMode);
    }

    /**
     * 保留固定位数小数<br>
     * 例如保留四位小数：123.456789 =》 123.4567
     *
     * @param v            值
     * @param scale        保留小数位数
     * @param roundingMode 保留小数的模式 {@link RoundingMode}
     * @return 新值
     */
    public static String roundStr(double v, int scale, RoundingMode roundingMode) {
        return round(v, scale, roundingMode).toString();
    }

    /**
     * 保留固定位数小数<br>
     * 例如保留四位小数：123.456789 =》 123.4567
     *
     * @param numberStr    数字值的字符串表现形式
     * @param scale        保留小数位数，如果传入小于0，则默认0
     * @param roundingMode 保留小数的模式 {@link RoundingMode}，如果传入null则默认四舍五入
     * @return 新值
     */
    public static BigDecimal round(String numberStr, int scale, RoundingMode roundingMode) {
        Assert.hasLength(numberStr, "NumberStr must be not null !");
        if (scale < 0) {
            scale = 0;
        }
        return round(toBigDecimal(numberStr), scale, roundingMode);
    }

    /**
     * 保留固定位数小数<br>
     * 例如保留四位小数：123.456789 =》 123.4567
     *
     * @param number       数字值
     * @param scale        保留小数位数，如果传入小于0，则默认0
     * @param roundingMode 保留小数的模式 {@link RoundingMode}，如果传入null则默认四舍五入
     * @return 新值
     */
    public static BigDecimal round(BigDecimal number, int scale, RoundingMode roundingMode) {
        if (null == number) {
            number = BigDecimal.ZERO;
        }
        if (scale < 0) {
            scale = 0;
        }
        if (null == roundingMode) {
            roundingMode = RoundingMode.HALF_UP;
        }

        return number.setScale(scale, roundingMode);
    }

    /**
     * 保留固定位数小数<br>
     * 例如保留四位小数：123.456789 =》 123.4567
     *
     * @param numberStr    数字值的字符串表现形式
     * @param scale        保留小数位数
     * @param roundingMode 保留小数的模式 {@link RoundingMode}
     * @return 新值
     */
    public static String roundStr(String numberStr, int scale, RoundingMode roundingMode) {
        return round(numberStr, scale, roundingMode).toString();
    }

    /**
     * 数字转{@link BigDecimal}
     *
     * @param number 数字
     * @return {@link BigDecimal}
     */
    public static BigDecimal toBigDecimal(Number number) {
        if (null == number) {
            return BigDecimal.ZERO;
        }
        return toBigDecimal(number.toString());
    }

    /**
     * 数字转{@link BigDecimal}
     *
     * @param number 数字
     * @return {@link BigDecimal}
     */
    public static BigDecimal toBigDecimal(String number) {
        return (null == number) ? BigDecimal.ZERO : new BigDecimal(number);
    }

    /**
     * 四舍六入五成双计算法
     * <p>
     * 四舍六入五成双是一种比较精确比较科学的计数保留法，是一种数字修约规则。
     * </p>
     *
     * <pre>
     * 算法规则:
     * 四舍六入五考虑，
     * 五后非零就进一，
     * 五后皆零看奇偶，
     * 五前为偶应舍去，
     * 五前为奇要进一。
     * </pre>
     *
     * @param number 需要科学计算的数据
     * @param scale  保留的小数位
     * @return 结果
     */
    public static BigDecimal roundHalfEven(Number number, int scale) {
        return roundHalfEven(toBigDecimal(number), scale);
    }

    /**
     * 四舍六入五成双计算法
     * <p>
     * 四舍六入五成双是一种比较精确比较科学的计数保留法，是一种数字修约规则。
     * </p>
     *
     * <pre>
     * 算法规则:
     * 四舍六入五考虑，
     * 五后非零就进一，
     * 五后皆零看奇偶，
     * 五前为偶应舍去，
     * 五前为奇要进一。
     * </pre>
     *
     * @param value 需要科学计算的数据
     * @param scale 保留的小数位
     * @return 结果
     */
    public static BigDecimal roundHalfEven(BigDecimal value, int scale) {
        return round(value, scale, RoundingMode.HALF_EVEN);
    }

    /**
     * 保留固定小数位数，舍去多余位数
     *
     * @param number 需要科学计算的数据
     * @param scale  保留的小数位
     * @return 结果
     */
    public static BigDecimal roundDown(Number number, int scale) {
        return roundDown(toBigDecimal(number), scale);
    }

    /**
     * 保留固定小数位数，舍去多余位数
     *
     * @param value 需要科学计算的数据
     * @param scale 保留的小数位
     * @return 结果
     */
    public static BigDecimal roundDown(BigDecimal value, int scale) {
        return round(value, scale, RoundingMode.DOWN);
    }
}
