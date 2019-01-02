package cn.waynechu.webcommon.util;

import lombok.experimental.UtilityClass;

import java.security.MessageDigest;

/**
 * MD5摘要
 *
 * @author zhuwei
 * @date 2019/1/2 17:53
 */
@UtilityClass
public class MD5Util {
    private static final String[] HEX_DIGITS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 获取大写MD5摘要
     *
     * @param origin      原始字符串
     * @param charsetName 字符集
     * @return 大写MD5摘要
     */
    private static String md5Encode(String origin, String charsetName) {
        String resultString = null;
        try {
            resultString = origin;
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetName == null || "".equals(charsetName)) {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            } else {
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetName)));
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return resultString.toUpperCase();
    }

    /**
     * 获取加盐后的大写MD5摘要
     *
     * @param origin 原始字符串
     * @param salt   盐值
     * @return 大写MD5摘要
     */
    public static String md5EncodeWithSalt(String origin, String salt) {
        origin = origin + salt;
        return md5Encode(origin, "utf-8");
    }

    private static String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(byteToHexString(b));
        }
        return sb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }
}
