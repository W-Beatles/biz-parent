package cn.waynechu.utility.facade.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zhuwei
 * @since 2020/7/3 18:14
 */
@Data
@ApiModel
public class CreateDictionaryRequest {

    @ApiModelProperty("父节点id")
    private Long pid;

    @ApiModelProperty(value = "字典类型id", required = true)
    @NotNull(message = "字典类型id不能为空")
    private Long dicTypeId;

    @ApiModelProperty(value = "字典编码", required = true)
    @NotBlank(message = "字典编码不能为空")
    @Length(max = 100, message = "字典编码不能超过100个字符")
    private String dicCode;

    @ApiModelProperty(value = "字典值", required = true)
    @NotBlank(message = "字典值不能为空")
    @Length(max = 200, message = "字典值不能超过200个字符")
    private String dicValue;

    @ApiModelProperty("字典描述")
    @Length(max = 400, message = "字典描述不能超过400个字符")
    private String dicDesc;

    @ApiModelProperty("是否固定: 0否 1是")
    private Integer fixedStatus;
}
