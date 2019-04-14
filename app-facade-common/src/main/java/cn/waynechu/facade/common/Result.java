package cn.waynechu.facade.common;

import cn.waynechu.facade.common.enums.BizEnum;
import cn.waynechu.facade.common.enums.BizErrorCodeEnum;
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
public class Result<T> implements Serializable {
    private static final long serialVersionUID = -9151575407714770162L;

    @ApiModelProperty(value = "返回状态码，备注:10000为正常调用", allowableValues = "10000,....")
    private int code;

    @ApiModelProperty(value = "返回提示信息")
    private String message;

    @ApiModelProperty(value = "返回对象")
    private T data;

    private Result(BizEnum bizEnum) {
        this.code = bizEnum.getCode();
        this.message = bizEnum.getDesc();
    }

    private Result(BizEnum bizEnum, T data) {
        this.code = bizEnum.getCode();
        this.message = bizEnum.getDesc();
        this.data = data;
    }

    private Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result success() {
        return new Result<>(BizErrorCodeEnum.SUCCESS);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(BizErrorCodeEnum.SUCCESS, data);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(BizErrorCodeEnum.SUCCESS.getCode(), message, data);
    }

    public static <T> Result<T> error(BizEnum bizEnum) {
        return new Result<>(bizEnum.getCode(), bizEnum.getDesc());
    }

    public static <T> Result<T> error() {
        return new Result<>(BizErrorCodeEnum.SYSTEM_ERROR.getCode(), BizErrorCodeEnum.SYSTEM_ERROR.getDesc());
    }

    public static <T> Result<T> error(String errorMessage) {
        return new Result<>(BizErrorCodeEnum.SYSTEM_ERROR.getCode(), errorMessage);
    }

    public static <T> Result<T> error(int errorCode, String errorMessage) {
        return new Result<>(errorCode, errorMessage);
    }

    public static <T> Result<T> error(BizEnum bizEnum, T data) {
        return new Result<>(bizEnum.getCode(), bizEnum.getDesc(), data);
    }

    public static <T> Result<T> error(int errorCode, String errorMessage, T data) {
        return new Result<>(errorCode, errorMessage, data);
    }
}
