package cn.waynechu.utility.facade.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author zhuwei
 * @since 2020/7/3 18:27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class UpdateDictionaryRequest extends CreateDictionaryRequest {

    @ApiModelProperty(value = "字典id", required = true)
    @NotNull(message = "字典id不能为空")
    private Long id;
}
