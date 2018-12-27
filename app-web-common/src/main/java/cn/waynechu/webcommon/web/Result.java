package cn.waynechu.webcommon.web;

import cn.waynechu.common.enums.CommonResultEnum;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> implements Serializable {
    private static final long serialVersionUID = -9151575407714770162L;

    @ApiModelProperty(value = "返回状态码，备注:10000为正常调用", allowableValues = "10000,....")
    private int code;

    @ApiModelProperty(value = "返回提示信息")
    private String message;

    @ApiModelProperty(value = "返回对象")
    private T data;

    private Result(int code) {
        this.code = code;
    }

    private Result(int code, T data) {
        this.code = code;
        this.data = data;
    }

    private Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Result success() {
        return new Result<>(CommonResultEnum.SUCCESS.getCode());
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(CommonResultEnum.SUCCESS.getCode(), data);
    }

    public static Result successWithMessage(String message) {
        return new Result<>(CommonResultEnum.SUCCESS.getCode(), message);
    }

    public static <T> Result<T> successWithData(String message, T data) {
        return new Result<>(CommonResultEnum.SUCCESS.getCode(), message, data);
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
