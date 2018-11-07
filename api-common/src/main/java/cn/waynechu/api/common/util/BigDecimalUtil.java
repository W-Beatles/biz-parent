package cn.waynechu.api.common.util;

import java.math.BigDecimal;

/**
 * @author zhuwei
 * @date 2018/11/7 11:18
 */
public class BigDecimalUtil {
    public static BigDecimal add(BigDecimal value1, BigDecimal value2, int scale, int roundingMode) {
        if (value1 == null) {
            value1 = new BigDecimal(0);
        }
        if (value2 == null) {
            value2 = new BigDecimal(0);
        }

        return value1.add(value2).setScale(scale, roundingMode);
    }

    public static BigDecimal add(BigDecimal value1, BigDecimal value2, int scale) {
        return add(value1, value2, scale, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal add(BigDecimal value1, BigDecimal value2) {
        if (value1 == null) {
            value1 = new BigDecimal(0);
        }
        if (value2 == null) {
            value2 = new BigDecimal(0);
        }

        return value1.add(value2);
    }

    /**
     * 提供精确的减法运算。
     *
     * @param value1 被减数
     * @param value2 减数
     * @return 两个参数的差
     */
    public static BigDecimal subtract(BigDecimal value1, BigDecimal value2, int scale, int roundingMode) {
        if (value1 == null) {
            value1 = new BigDecimal(0);
        }
        if (value2 == null) {
            value2 = new BigDecimal(0);
        }

        return value1.subtract(value2).setScale(scale, roundingMode);
    }

    public static BigDecimal subtract(BigDecimal value1, BigDecimal value2, int scale) {
        return subtract(value1, value2, scale, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal subtract(BigDecimal value1, BigDecimal value2) {
        if (value1 == null) {
            value1 = new BigDecimal(0);
        }
        if (value2 == null) {
            value2 = new BigDecimal(0);
        }

        return value1.subtract(value2);
    }

    /**
     * 相乘
     *
     * @param value1
     * @param value2
     * @param scale
     * @param roundingMode
     * @return
     */
    public static BigDecimal multiply(BigDecimal value1, BigDecimal value2, int scale, int roundingMode) {
        if (value1 == null) {
            value1 = new BigDecimal(0);
        }
        if (value2 == null) {
            value2 = new BigDecimal(0);
        }

        return value1.multiply(value2).setScale(scale, roundingMode);
    }

    public static BigDecimal multiply(BigDecimal value1, BigDecimal value2, int scale) {
        return multiply(value1, value2, scale, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal multiply(BigDecimal value1, BigDecimal value2) {
        if (value1 == null) {
            value1 = new BigDecimal(0);
        }
        if (value2 == null) {
            value2 = new BigDecimal(0);
        }
        return value1.multiply(value2);
    }

    /**
     * 相除
     *
     * @param value1
     * @param value2
     * @param scale
     * @param roundingMode
     * @return
     */
    public static BigDecimal divide(BigDecimal value1, BigDecimal value2, int scale, int roundingMode) {
        if (value1 == null) {
            value1 = new BigDecimal(0);
        }
        if (value2 == null) {
            value2 = new BigDecimal(0);
        }

        return value1.divide(value2, scale, roundingMode);
    }

    public static BigDecimal divide(BigDecimal value1, BigDecimal value2, int scale) {
        return divide(value1, value2, scale, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal divide(BigDecimal value1, BigDecimal value2) {
        if (value1 == null) {
            value1 = new BigDecimal(0);
        }
        if (value2 == null) {
            value2 = new BigDecimal(0);
        }

        return value1.divide(value2, BigDecimal.ROUND_HALF_UP);
    }

    public static int compareTo(BigDecimal value1, BigDecimal value2) {
        if (value1 == null) {
            value1 = BigDecimal.ZERO;
        }

        if (value2 == null) {
            value2 = BigDecimal.ZERO;
        }

        return value1.compareTo(value2);
    }

    public static BigDecimal toBigDecimal(int value) {
        return new BigDecimal(value);
    }

    public static BigDecimal toBigDecimal(float value) {
        return BigDecimal.valueOf(value);
    }

    public static BigDecimal toBigDecimal(float value, int scale) {
        return BigDecimal.valueOf(value).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal toBigDecimal(double value) {
        return BigDecimal.valueOf(value);
    }

    public static BigDecimal toBigDecimal(double value, int scale) {
        return BigDecimal.valueOf(value).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal toBigDecimal(long value, int scale) {
        return new BigDecimal(value).setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

    public static float toFloat(BigDecimal value, int scale, int roundingMode) {
        float returnValue = 0f;
        if (value != null) {
            returnValue = value.setScale(scale, roundingMode).floatValue();
        }

        return returnValue;
    }

    public static double toDouble(BigDecimal value, int scale, int roundingMode) {
        double returnValue = 0d;
        if (value != null) {
            returnValue = value.setScale(scale, roundingMode).doubleValue();
        }
        return returnValue;
    }

    public static long toLong(BigDecimal value, int roundingMode) {
        long returnValue = 0L;
        if (value != null) {
            returnValue = value.setScale(0, roundingMode).longValue();
        }
        return returnValue;
    }
}
