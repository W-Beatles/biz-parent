package cn.waynechu.renting.web.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhuwei
 * @date 2019/1/18 14:34
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "系统字典返回对象")
public class ModelSysDictionary {

    @ApiModelProperty("字典ID")
    private Long id;

    @ApiModelProperty("父节点ID。0，无父节点")
    private Long parentId;

    @ApiModelProperty("类型")
    private String type;

    @ApiModelProperty("值")
    private String code;

    @ApiModelProperty("显示值")
    private String displayName;

    @ApiModelProperty("描述")
    private String description;
}
