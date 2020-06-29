package cn.waynechu.utility.facade.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * @author zhuwei
 * @since 2020/6/29 19:54
 */
@Data
@ApiModel
public class CreateDicTypeRequest {

    @ApiModelProperty("类型编码")
    @NotEmpty(message = "类型编码不能为空")
    private String typeCode;

    @ApiModelProperty("所属AppID")
    @Length(max = 50, message = "所属AppID不能超过50个字符")
    private String appId;

    @ApiModelProperty("类型描述")
    @Length(max = 400, message = "类型描述不能超过400个字符")
    private String description;
}
