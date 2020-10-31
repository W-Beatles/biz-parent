package cn.waynechu.order.common.enums;

import cn.waynechu.facade.common.enums.BizEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhuwei
 * @since 2020/10/31 20:20
 */
@Getter
@AllArgsConstructor
public enum OrderStatusEnum implements BizEnum {
    /**
     * 订单状态: 1创建 2成功 3失败
     */
    INIT(1, "INIT", "创建"),
    SUCCESS(2, "SUCCESS", "成功"),
    FAIL(3, "FAIL", "失败"),
    ;

    private Integer code;

    private String name;

    private String desc;

}
