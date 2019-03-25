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

    @ApiModelProperty("更新人")
    @NotNull(message = "更新人不能为空")
    private String updateUser;

    @ApiModelProperty("更新时间")
    @NotNull(message = "更新时间不能为空")
    private Date updateTime;
}
