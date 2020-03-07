package cn.waynechu.springcloud.gateway.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhuwei
 * @date 2020-03-07 17:54
 */
@Getter
@AllArgsConstructor
public enum AuthTypeEnum {

    /**
     * 鉴权渠道枚举
     */
    OAUTH2("oauth2", "oauth2AuthTypeFilter", "oauth2鉴权"),
    ;

    private String name;
    private String beanName;
    private String desc;

    public static AuthTypeEnum getByName(String authType) {
        for (AuthTypeEnum item : AuthTypeEnum.values()) {
            if (item.getName().equalsIgnoreCase(authType)) {
                return item;
            }
        }
        return null;
    }
}
