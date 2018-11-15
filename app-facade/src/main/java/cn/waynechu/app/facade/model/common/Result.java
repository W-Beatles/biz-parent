package cn.waynechu.app.facade.model.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhuwei
 * @date 2018/11/15 13:35
 */
@Data
@ApiModel(description = "请求返回对象")
public class Result<T extends AbstractModel> implements Serializable {
    private static final long serialVersionUID = -9151575407714770162L;

    @ApiModelProperty(value = "返回状态码，备注:10000为正常调用", allowableValues = "10000,....")
    private int code;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "返回提示信息")
    private String message;

    @ApiModelProperty(value = "返回对象")
    private T data;
}
