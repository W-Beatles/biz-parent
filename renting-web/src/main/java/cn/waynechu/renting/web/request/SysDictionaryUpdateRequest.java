package cn.waynechu.renting.web.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author zhuwei
 * @date 2019/1/18 15:09
 */
@Data
@ApiModel(description = "更新字典信息请求对象")
public class SysDictionaryUpdateRequest {

    @ApiModelProperty("字典ID")
    @NotNull(message = "字典ID不能为空")
    private Long id;

    @ApiModelProperty("字典类型Code")
    private Integer typeCode;

    @ApiModelProperty("字典类型Name")
    private String typeName;

    @ApiModelProperty("字典Code")
    private Integer code;

    @ApiModelProperty("字典Name")
    private String name;

    @ApiModelProperty("父节点ID。默认0，无父节点")
    private Long parentId;

    @ApiModelProperty("更新人")
    @NotNull(message = "更新人不能为空")
    private String updateUser;

    @ApiModelProperty("更新时间")
    @NotNull(message = "更新时间不能为空")
    private Date updateTime;
}
