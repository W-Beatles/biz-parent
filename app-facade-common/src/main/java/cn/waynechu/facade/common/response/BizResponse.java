package cn.waynechu.facade.common.response;

import cn.waynechu.facade.common.enums.BizEnum;
import cn.waynechu.facade.common.enums.BizErrorCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zhuwei
 * @date 2018/11/15 13:38
 */
@Data
@NoArgsConstructor
@ApiModel(description = "请求返回对象")
public class BizResponse<T> implements Serializable {
    private static final long serialVersionUID = 9111238562185215154L;

    @ApiModelProperty(value = "返回状态码，备注:10000为正常调用", allowableValues = "10000,....")
    private int code;

    @ApiModelProperty(value = "返回提示信息")
    private String message;

    @ApiModelProperty(value = "返回对象")
    private T data;

    public BizResponse(T data) {
        this(BizErrorCodeEnum.SUCCESS, data);
    }

    public BizResponse(BizEnum bizEnum) {
        this.code = bizEnum.getCode();
        this.message = bizEnum.getDesc();
    }

    public BizResponse(BizEnum bizEnum, T data) {
        this.code = bizEnum.getCode();
        this.message = bizEnum.getDesc();
        this.data = data;
    }

    public BizResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public BizResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static BizResponse success() {
        return new BizResponse<>(BizErrorCodeEnum.SUCCESS);
    }

    public static <T> BizResponse<T> success(T data) {
        return new BizResponse<>(BizErrorCodeEnum.SUCCESS, data);
    }

    public static <T> BizResponse<T> success(String message, T data) {
        return new BizResponse<>(BizErrorCodeEnum.SUCCESS.getCode(), message, data);
    }

    public static <T> BizResponse<T> error(BizEnum bizEnum) {
        return new BizResponse<>(bizEnum.getCode(), bizEnum.getDesc());
    }

    public static <T> BizResponse<T> error() {
        return new BizResponse<>(BizErrorCodeEnum.SYSTEM_ERROR.getCode(), BizErrorCodeEnum.SYSTEM_ERROR.getDesc());
    }

    public static <T> BizResponse<T> error(String errorMessage) {
        return new BizResponse<>(BizErrorCodeEnum.SYSTEM_ERROR.getCode(), errorMessage);
    }

    public static <T> BizResponse<T> error(int errorCode, String errorMessage) {
        return new BizResponse<>(errorCode, errorMessage);
    }

    public static <T> BizResponse<T> error(BizEnum bizEnum, T data) {
        return new BizResponse<>(bizEnum.getCode(), bizEnum.getDesc(), data);
    }

    public static <T> BizResponse<T> error(int errorCode, String errorMessage, T data) {
        return new BizResponse<>(errorCode, errorMessage, data);
    }
}
