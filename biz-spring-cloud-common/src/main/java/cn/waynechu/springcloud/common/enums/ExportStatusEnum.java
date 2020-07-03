package cn.waynechu.springcloud.common.enums;

import cn.waynechu.facade.common.enums.BizEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * excel导出状态枚举
 *
 * @author zhuwei
 * @date 2020-03-22 17:55
 */
@Getter
@AllArgsConstructor
public enum ExportStatusEnum implements BizEnum {

    /**
     * 导出状态: -1失败 0生成中 1生成成功
     */
    FAIL(-1, "FAIL", "生成失败"),
    GENERATED(0, "GENERATED", "生成中"),
    SUCCESS(1, "SUCCESS", "生成成功"),
    ;

    private Integer code;
    private String name;
    private String desc;
}
