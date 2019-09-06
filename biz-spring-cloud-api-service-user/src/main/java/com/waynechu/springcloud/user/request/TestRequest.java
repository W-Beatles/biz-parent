package com.waynechu.springcloud.user.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author zhuwei
 * @date 2019/9/4 15:08
 */
@Data
@ApiModel
public class TestRequest {

    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Integer id;

    @ApiModelProperty("名称")
    @Length(max = 3, message = "名称不能超过3个字")
    private String name;

    @ApiModelProperty("年龄")
    @Min(1)
    @Max(150)
    private Integer age;
}
