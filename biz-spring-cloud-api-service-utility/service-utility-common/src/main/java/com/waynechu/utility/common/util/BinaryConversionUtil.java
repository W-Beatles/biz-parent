package com.waynechu.utility.common.util;

import lombok.experimental.UtilityClass;

/**
 * @author zhuwei
 * @date 2019/8/13 15:07
 */
@UtilityClass
public class BinaryConversionUtil {

    /**
     * 62进制 [0-9][a-z][A-Z]
     */
    private static final char[] BINARY_TEMPLATE = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    /**
     * 10进制转化为62进制
     *
     * @param number 10进制数
     * @return 62进制数
     */
    public static String convert10to62(long number) {
        int scale = 62;
        StringBuilder result = new StringBuilder();
        while (number != 0) {
            result.append(BINARY_TEMPLATE[Math.toIntExact(number % scale)]);
            number = number / 62;
        }
        return result.reverse().toString();
    }

    /**
     * 62进制转化为10进制
     *
     * @param str 62进制字符串
     * @return 10进制
     */
    public static long convert62to10(String str) {
        int multiple = 1;
        long result = 0;
        Character c;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(str.length() - i - 1);
            result += get62Value(c) * multiple;
            multiple = multiple * 62;
        }
        return result;
    }

    private static int get62Value(Character c) {
        for (int i = 0; i < BINARY_TEMPLATE.length; i++) {
            if (c == BINARY_TEMPLATE[i]) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        String s = convert10to62(Integer.MAX_VALUE);
        System.out.println(s);

        long ud2 = convert62to10("2lkCB1");
        System.out.println(ud2);
    }
}
