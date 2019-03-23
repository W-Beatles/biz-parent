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

    @ApiModelProperty("字典类型Code")
    @NotNull(message = "字典类型Code不能为空")
    private Integer typeCode;

    @ApiModelProperty("字典类型Name")
    @NotNull(message = "字典类型Name不能为空")
    private String typeName;

    @ApiModelProperty("字典Code")
    @NotNull(message = "字典Code不能为空")
    private Integer code;

    @ApiModelProperty("字典Name")
    @NotNull(message = "字典Name不能为空")
    private String name;

    @ApiModelProperty("父节点ID。默认0，无父节点")
    @NotNull(message = "父节点ID不能为空")
    private Long parentId;

    @ApiModelProperty("创建人")
    @NotNull(message = "创建人不能为空")
    private String createUser;

    @ApiModelProperty("创建时间")
    @NotNull(message = "创建时间不能为空")
    private Date createTime;
}
