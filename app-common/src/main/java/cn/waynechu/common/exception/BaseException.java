package cn.waynechu.common.exception;

import cn.waynechu.common.enums.BaseEnum;

/**
 * @author zhuwei
 * @date 2018/11/6 19:27
 */
public class BaseException extends RuntimeException {

    private final Integer errorCode;

    private final String errorMessage;

    public BaseException(BaseEnum baseEnum) {
        this.errorCode = baseEnum.getCode();
        this.errorMessage = baseEnum.getDesc();
    }

    public BaseException(Integer errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public BaseException(Integer errorCode, String errorMessage, Throwable errorCause) {
        super(errorMessage, errorCause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        super.initCause(errorCause);
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
