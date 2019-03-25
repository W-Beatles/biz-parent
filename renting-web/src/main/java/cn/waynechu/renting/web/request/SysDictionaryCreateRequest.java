package cn.waynechu.renting.web.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author zhuwei
 * @date 2019/1/18 15:07
 */
@Data
@ApiModel(description = "添加字典信息请求对象")
public class SysDictionaryCreateRequest {

    @ApiModelProperty("父节点ID。0，无父节点")
    @NotNull(message = "父节点ID不能为空")
    private Long parentId;

    @ApiModelProperty("类型")
    @NotNull(message = "类型不能为空")
    private String type;

    @ApiModelProperty("值")
    @NotNull(message = "值不能为空")
    private String code;

    @ApiModelProperty("显示值")
    @NotNull(message = "显示值不能为空")
    private String displayName;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("创建人")
    @NotNull(message = "创建人不能为空")
    private String createUser;

    @ApiModelProperty("创建时间")
    @NotNull(message = "创建时间不能为空")
    private Date createTime;
}
