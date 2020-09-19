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
public enum StatusCodeEnum implements BizEnum {
    /**
     * 状态: 0生成中 1成功 2失败
     */
    PENDING(0, "PENDING", "生成中"),
    SUCCEED(1, "SUCCEED", "成功"),
    FAILED(2, "FAILED", "失败"),
    ;

    private Integer code;
    private String name;
    private String desc;

    public static StatusCodeEnum getByCode(Integer code) {
        if (code != null) {
            for (StatusCodeEnum value : values()) {
                if (value.code.equals(code)) {
                    return value;
                }
            }
        }
        return null;
    }
}
