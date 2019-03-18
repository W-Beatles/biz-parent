package cn.waynechu.webcommon.exception;

import cn.waynechu.webcommon.enums.BaseEnum;

import java.io.Serializable;

/**
 * @author zhuwei
 * @date 2018/11/6 19:27
 */
public class BaseBizException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 771762126749223759L;

    private final Integer errorCode;

    private final String errorMessage;

    public BaseBizException(BaseEnum baseEnum) {
        super(baseEnum.getDesc());
        this.errorCode = baseEnum.getCode();
        this.errorMessage = baseEnum.getDesc();
    }

    public BaseBizException(Integer errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public BaseBizException(Integer errorCode, String errorMessage, Throwable errorCause) {
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
