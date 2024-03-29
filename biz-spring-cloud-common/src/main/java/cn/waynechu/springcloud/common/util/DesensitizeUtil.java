package cn.waynechu.springcloud.common.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 脱敏工具
 *
 * @author zhuwei
 * @since 2019/4/9 17:13
 */
@UtilityClass
public class DesensitizeUtil {
    public static final String IDCARD = "idcard";
    public static final String MOBILE = "mobile";
    public static final String REALNAME = "realname";
    public static final String BANKCARD = "bankcard";
    public static final String PWD = "pwd";
    public static final String PASSWORD = "password";

    private static Pattern pattern = Pattern.compile("[0-9a-zA-Z]");

    public static final String PHONE_11_REGEX = "(\\d{3})(\\d{4})(\\d{4})";
    public static final String PHONE_11_REPLACE_REGEX = "$1****$3";

    /**
     * [姓名] 只显示第一个汉字，其他隐藏为星号
     *
     * @param fullName 姓名
     * @return 示例: 李**
     */
    public static String chineseName(String fullName) {
        if (StringUtils.isBlank(fullName)) {
            return "";
        }
        String name = StringUtils.left(fullName, 1);
        return StringUtils.rightPad(name, StringUtils.length(fullName), "*");
    }

    /**
     * [身份证号] 显示最后四位，其他隐藏。共计18位或者15位
     *
     * @param idCardNum 身份证号
     * @return 示例: *************5762
     */
    public static String idCardNum(String idCardNum) {
        if (StringUtils.isBlank(idCardNum)) {
            return "";
        }
        String num = StringUtils.right(idCardNum, 4);
        return StringUtils.leftPad(num, StringUtils.length(idCardNum), "*");
    }

    /**
     * [手机号码] 前三位，后四位，其他隐藏
     *
     * @param num 手机号码
     * @return 示例: 138****1234
     */
    public static String mobilePhone(String num) {
        if (StringUtils.isBlank(num)) {
            return "";
        }
        return StringUtils.left(num, 3).concat(
                StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(num, 4), StringUtils.length(num), "*"),
                        "***"));
    }

    /**
     * 手机号脱敏
     *
     * @param phone 手机号
     * @return 脱敏后的手机号
     */
    public static String phone11(String phone) {
        if (StringUtils.isEmpty(phone)) {
            return phone;
        }

        // 11位手机号
        phone = phone.replaceAll(PHONE_11_REGEX, PHONE_11_REPLACE_REGEX);
        return phone;
    }

    /**
     * [银行卡号] 前六位，后四位，其他用星号隐藏每位1个星号
     *
     * @param cardNum 银行卡号
     * @return 示例: 6222600**********1234
     */
    public static String bankCard(String cardNum) {
        if (StringUtils.isBlank(cardNum)) {
            return "";
        }
        return StringUtils
                .left(cardNum, 6)
                .concat(StringUtils.removeStart(
                        StringUtils.leftPad(StringUtils.right(cardNum, 4), StringUtils.length(cardNum), "*"), "******"));
    }

    /**
     * [密码] 用六位星号隐藏
     *
     * @param password 密码
     * @return 示例: ******
     */
    public static String password(String password) {
        if (StringUtils.isBlank(password)) {
            return "";
        }
        return "******";
    }

    /**
     * 同时处理多种脱敏类型后返回的字符串
     *
     * @param oriMsg    原始字符串
     * @param keysArray DesensitizeUtils中定义的key类型
     * @return 脱敏后的字符串
     */
    public static String desensitize(String oriMsg, String[] keysArray) {
        String tempMsg = oriMsg;
        // 处理字符串
        if (keysArray != null && keysArray.length > 0) {
            for (String key : keysArray) {
                int index = -1;
                do {
                    index = tempMsg.indexOf(key, index + 1);
                    if (index != -1) {
                        // 判断key是否为单词字符
                        if (isWordChar(tempMsg, key, index)) {
                            continue;
                        }
                        // 寻找值的开始位置
                        int valueStart = getValueStartIndex(tempMsg, index + key.length());
                        if (valueStart == -1) {
                            continue;
                        }

                        // 查找值的结束位置（逗号，分号）
                        int valueEnd = getValueEndIndex(tempMsg, valueStart);

                        // 对获取的值进行脱敏
                        String subStr = tempMsg.substring(valueStart, valueEnd);
                        subStr = invokeMsg(subStr, key);
                        tempMsg = tempMsg.substring(0, valueStart) + subStr + tempMsg.substring(valueEnd);
                    }
                } while (index != -1);
            }
        }
        return tempMsg;
    }

    /**
     * 判断从字符串msg获取的key值是否为单词，index为key在msg中的索引值
     *
     * @return true if success
     */
    private static boolean isWordChar(String msg, String key, int index) {
        // 必须确定key是一个单词
        // 判断key前面一个字符
        if (index != 0) {
            char preCh = msg.charAt(index - 1);
            Matcher match = pattern.matcher(preCh + "");
            if (match.matches()) {
                return true;
            }
        }
        // 判断key后面一个字符
        char nextCh = msg.charAt(index + key.length());
        Matcher match = pattern.matcher(nextCh + "");
        return match.matches();
    }

    /**
     * 获取value值的开始位置
     *
     * @param msg        要查找的字符串
     * @param valueStart 查找的开始位置
     * @return value值的开始位置
     */
    private static int getValueStartIndex(String msg, int valueStart) {
        // 寻找值的开始位置
        do {
            char ch = msg.charAt(valueStart);
            // key与 value的分隔符
            if (ch == ':' || ch == '=') {
                valueStart++;
                ch = msg.charAt(valueStart);
                if (ch == '"') {
                    valueStart++;
                }
                // 找到值的开始位置
                break;
            } else {
                valueStart++;
                if (valueStart >= msg.length()) {
                    return -1;
                }
            }
        } while (true);
        return valueStart;
    }

    /**
     * 获取value值的结束位置
     *
     * @return value值的结束位置
     */
    private static int getValueEndIndex(String msg, int valueEnd) {
        do {
            if (valueEnd == msg.length()) {
                break;
            }
            char ch = msg.charAt(valueEnd);

            // 引号时，判断下一个值是结束，分号还是逗号决定是否为值的结束
            if (ch == '"') {
                if (valueEnd + 1 == msg.length()) {
                    break;
                }
                char nextCh = msg.charAt(valueEnd + 1);
                if (nextCh == ';' || nextCh == ',') {
                    // 去掉前面的 \  处理这种形式的数据
                    while (valueEnd > 0) {
                        char preCh = msg.charAt(valueEnd - 1);
                        if (preCh != '\\') {
                            break;
                        }
                        valueEnd--;
                    }
                    break;
                } else {
                    valueEnd++;
                }
            } else if (ch == ';' || ch == ',' || ch == '}') {
                break;
            } else {
                valueEnd++;
            }
        } while (true);
        return valueEnd;
    }

    private static String invokeMsg(String submsg, String key) {
        if (DesensitizeUtil.IDCARD.equalsIgnoreCase(key)) {
            return DesensitizeUtil.idCardNum(submsg);
        }
        if (DesensitizeUtil.REALNAME.equalsIgnoreCase(key)) {
            return DesensitizeUtil.chineseName(submsg);
        }
        if (DesensitizeUtil.BANKCARD.equalsIgnoreCase(key)) {
            return DesensitizeUtil.bankCard(submsg);
        }
        if (DesensitizeUtil.MOBILE.equalsIgnoreCase(key)) {
            return DesensitizeUtil.mobilePhone(submsg);
        }
        if (DesensitizeUtil.PASSWORD.equalsIgnoreCase(key) || DesensitizeUtil.PWD.equalsIgnoreCase(key)) {
            return DesensitizeUtil.password(submsg);
        }
        return "";
    }
}
