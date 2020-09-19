package cn.waynechu.archetype.portal.facade.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @author zhuwei
 * @since 2020-06-26 12:30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class UpdateArchetypeRequest extends CreateArchetypeRequest {

    @ApiModelProperty(value = "原型id", required = true)
    @NotNull(message = "原型id不能为空")
    private Long id;
}
