package cn.waynechu.springcloud.common.util;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zhuwei
 * @since 2019/8/13 15:07
 */
@UtilityClass
public class BinaryUtil {

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
     * 列表数值相加转化为二进制形式
     *
     * @param binaryList 列表数值
     * @return 二进制值
     */
    public static Integer toBinary(List<Integer> binaryList) {
        if (CollectionUtil.isNullOrEmpty(binaryList)) {
            return null;
        }
        return binaryList.stream().mapToInt(item -> item).sum();
    }

    /**
     * 二进制形式转化数值列表
     * <p>
     * 如：
     * 7  -> [1,1,1]
     * 8  -> [1,0,0,0]
     * 13 -> [1,1,0,1]
     *
     * @param binary 二进制数值
     * @return 数值列表
     */
    public static List<Integer> toList(Integer binary) {
        return toList(binary, false);
    }

    /**
     * 二进制形式转化数值列表
     * <p>
     * 如：
     * 7  -> [1,1,1]
     * 8  -> [1,0,0,0]
     * 13 -> [1,1,0,1]
     *
     * @param binary  二进制数值
     * @param reverse 是否反转
     * @return 数值列表
     */
    public static List<Integer> toList(Integer binary, boolean reverse) {
        LinkedList<Integer> binaryList = new LinkedList<>();
        while (binary > 0) {
            if (reverse) {
                binaryList.addLast(binary % 2);
            } else {
                binaryList.addFirst(binary % 2);
            }
            binary /= 2;
        }
        return new ArrayList<>(binaryList);
    }

    /**
     * 二进制形式转化二进制数值列表
     * <p>
     * 如：
     * 7  -> [4,2,1]
     * 8  -> [8]
     * 13 -> [8,4,1]
     *
     * @param binary 二进制数值
     * @return 数值列表
     */
    public static List<Integer> toBinaryList(Integer binary) {
        return toBinaryList(binary, false);
    }

    /**
     * 二进制形式转化二进制数值列表
     * <p>
     * 如：
     * 7  -> [4,2,1]
     * 8  -> [8]
     * 13 -> [8,4,1]
     *
     * @param binary  二进制数值
     * @param reverse 是否反转
     * @return 数值列表
     */
    public static List<Integer> toBinaryList(Integer binary, boolean reverse) {
        LinkedList<Integer> binaryList = new LinkedList<>();

        if (binary == null) {
            return binaryList;
        }

        int i = 0;
        while (binary >= (Math.pow(2, i))) {
            int v = (int) Math.pow(2, i++);
            if ((binary & v) == v) {
                if (reverse) {
                    binaryList.addLast(v);
                } else {
                    binaryList.addFirst(v);
                }
            }
        }
        return binaryList;
    }

    /**
     * 获取指定索引位置的二进制数
     *
     * @param binary 要获取二进制值的数
     * @param index  索引位置
     * @return 二进制数
     */
    public static Integer get(int binary, int index) {
        return (binary & (0x1 << index)) >> index;
    }

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
        char c;
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

        System.out.println("7 -> " + toList(7));
        System.out.println("7 -> " + toBinaryList(7));
        System.out.println("8 -> " + toList(8));
        System.out.println("8 -> " + toBinaryList(8));
        System.out.println("13 -> " + toList(13));
        System.out.println("13 -> " + toBinaryList(13));
    }
}
