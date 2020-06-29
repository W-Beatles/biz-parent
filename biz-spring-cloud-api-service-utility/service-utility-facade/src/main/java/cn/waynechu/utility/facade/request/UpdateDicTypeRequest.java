package cn.waynechu.utility.facade.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author zhuwei
 * @since 2020/6/29 19:58
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class UpdateDicTypeRequest extends CreateDicTypeRequest {

    @ApiModelProperty("类型id")
    @NotNull(message = "类型id不能为空")
    private Long id;
}
