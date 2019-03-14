package cn.waynechu.webcommon.web;

import cn.waynechu.webcommon.enums.BaseEnum;
import cn.waynechu.webcommon.enums.CommonResultEnum;
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

    private Result(BaseEnum baseEnum) {
        this.code = baseEnum.getCode();
        this.message = baseEnum.getDesc();
    }

    private Result(BaseEnum baseEnum, T data) {
        this.code = baseEnum.getCode();
        this.message = baseEnum.getDesc();
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
        return new Result<>(CommonResultEnum.SUCCESS);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(CommonResultEnum.SUCCESS, data);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(CommonResultEnum.SUCCESS.getCode(), message, data);
    }

    public static <T> Result<T> error(BaseEnum baseEnum) {
        return new Result<>(baseEnum.getCode(), baseEnum.getDesc());
    }

    public static <T> Result<T> error() {
        return new Result<>(CommonResultEnum.SYSTEM_ERROR.getCode(), CommonResultEnum.SYSTEM_ERROR.getDesc());
    }

    public static <T> Result<T> error(String errorMessage) {
        return new Result<>(CommonResultEnum.SYSTEM_ERROR.getCode(), errorMessage);
    }

    public static <T> Result<T> error(int errorCode, String errorMessage) {
        return new Result<>(errorCode, errorMessage);
    }

    public static <T> Result<T> error(int errorCode, String errorMessage, T data) {
        return new Result<>(errorCode, errorMessage, data);
    }
}
