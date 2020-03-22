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
     * 导出状态: 1生成中 2生成成功 3数据为空 4生成失败
     */
    GENERATED(1, "GENERATED", "生成中"),
    SUCCESS(2, "SUCCESS", "生成成功"),
    NULL(3, "NULL", "数据为空"),
    FAIL(4, "FAIL", "生成失败");

    private Integer code;
    private String name;
    private String desc;

}
