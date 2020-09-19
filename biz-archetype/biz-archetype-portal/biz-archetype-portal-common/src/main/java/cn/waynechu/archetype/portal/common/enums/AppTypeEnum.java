package cn.waynechu.archetype.portal.common.enums;

import cn.waynechu.facade.common.enums.BizEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhuwei
 * @since 2020-06-20 23:16
 */
@Getter
@AllArgsConstructor
public enum AppTypeEnum implements BizEnum {
    /**
     * 项目类型: 0Service 1SDK
     */
    SERVICE(0, "SERVICE", "Service项目"),
    SDK(1, "SDK", "SDK项目"),
    ;

    private Integer code;
    private String name;
    private String desc;

    public static AppTypeEnum getByCode(Integer code) {
        if (code != null) {
            for (AppTypeEnum value : values()) {
                if (value.code.equals(code)) {
                    return value;
                }
            }
        }
        return null;
    }
}
