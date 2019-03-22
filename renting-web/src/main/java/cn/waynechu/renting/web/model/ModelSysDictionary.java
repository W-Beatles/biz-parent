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

    @ApiModelProperty("字典类型Code")
    private Integer typeCode;

    @ApiModelProperty("字典类型Name")
    private String typeName;

    @ApiModelProperty("字典Code")
    private Integer code;

    @ApiModelProperty("字典Name")
    private String name;

    @ApiModelProperty("父节点ID。0，无父节点")
    private Long parentId;
}
