package cn.waynechu.springcloud.common.util;

import lombok.experimental.UtilityClass;

import java.security.MessageDigest;

/**
 * 摘要算法
 *
 * @author zhuwei
 * @date 2019/1/2 17:53
 * @see org.springframework.util.DigestUtils
 */
@UtilityClass
public class EncryptUtil {

    public static final String MD5 = "MD5";
    public static final String SHA_1 = "SHA-1";
    public static final String SHA_224 = "SHA-224";
    public static final String SHA_256 = "SHA-256";
    public static final String SHA_384 = "SHA-384";
    public static final String SHA_512 = "SHA-512";

    public static final String UTF_8 = "UTF-8";

    /**
     * 获取加盐的MD5摘要
     *
     * @param origin 原始字符串
     * @param salt   盐值
     * @return MD5摘要
     */
    public static String encodeMD5WithSalt(String origin, String salt) {
        return encodeWithSalt(origin, salt, UTF_8, MD5, 0);
    }

    /**
     * 获取加盐的SHA1摘要
     *
     * @param origin 原始字符串
     * @param salt   盐值
     * @return SHA1摘要
     */
    public static String encodeSHA1WithSalt(String origin, String salt) {
        return encodeWithSalt(origin, salt, UTF_8, SHA_1, 0);
    }

    /**
     * 获取加盐后的摘要
     *
     * @param origin      原始字符串
     * @param salt        盐值
     * @param charsetName 字符集
     * @param algorithm   摘要算法
     * @param showCase    摘要大小写 0: 小写 1：大写
     * @return 加盐后的摘要
     */
    public static String encodeWithSalt(String origin, String salt, String charsetName, String algorithm, int showCase) {
        return encode(origin + salt, charsetName, algorithm, showCase);
    }

    /**
     * 获取MD5摘要
     *
     * @param origin 原始字符串
     * @return MD5摘要
     */
    public static String encodeMD5(String origin) {
        return encode(origin, UTF_8, MD5, 0);
    }

    /**
     * 获取 SHA_1 摘要
     *
     * @param origin 原始字符串
     * @return SHA_1 摘要
     */
    public static String encodeSHA1(String origin) {
        return encode(origin, UTF_8, SHA_1, 0);
    }

    /**
     * 获取 SHA_224 摘要
     *
     * @param origin 原始字符串
     * @return SHA_224 摘要
     */
    public static String encodeSHA224(String origin) {
        return encode(origin, UTF_8, SHA_224, 0);
    }

    /**
     * 获取 SHA_256 摘要
     *
     * @param origin 原始字符串
     * @return SHA_256 摘要
     */
    public static String encodeSHA256(String origin) {
        return encode(origin, UTF_8, SHA_256, 0);
    }

    /**
     * 获取 SHA_384 摘要
     *
     * @param origin 原始字符串
     * @return SHA_384 摘要
     */
    public static String encodeSHA384(String origin) {
        return encode(origin, UTF_8, SHA_384, 0);
    }

    /**
     * 获取 SHA_512 摘要
     *
     * @param origin 原始字符串
     * @return SHA_512 摘要
     */
    public static String encodeSHA512(String origin) {
        return encode(origin, UTF_8, SHA_512, 0);
    }

    /**
     * 获取摘要
     *
     * @param origin      原始字符串
     * @param charsetName 字符集
     * @param algorithm   摘要算法
     * @param showCase    摘要大小写 0: 小写 1：大写
     * @return 大写摘要
     */
    private static String encode(String origin, String charsetName, String algorithm, int showCase) {
        String resultString = null;
        try {
            resultString = origin;
            MessageDigest md = MessageDigest.getInstance(algorithm);
            if (StringUtil.isEmpty(charsetName)) {
                resultString = ByteUtil.byteArrayToHexString(md.digest(resultString.getBytes()));
            } else {
                resultString = ByteUtil.byteArrayToHexString(md.digest(resultString.getBytes(charsetName)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return showCase == 0 ? resultString : resultString.toUpperCase();
    }
}