package cn.waynechu.springcloud.common.util;

import com.github.promeg.pinyinhelper.Pinyin;
import org.apache.commons.lang3.StringUtils;

/**
 * 汉字转拼音工具类
 *
 * @author zhuwei
 * @since 2019/4/9 17:31
 * @see "https://github.com/promeG/TinyPinyin"
 */
public class PinyinUtil {

    /**
     * 中文转拼音
     *
     * @param chineseWord 中文
     * @return String 拼音
     */
    public static String toPinyin(String chineseWord) {

        if (StringUtils.isBlank(chineseWord)) {
            return "";
        } else {
            StringBuilder s = new StringBuilder();
            for (char c : chineseWord.toCharArray()) {
                s.append(Pinyin.toPinyin(c));
            }
            return s.toString().toLowerCase();
        }
    }
}
