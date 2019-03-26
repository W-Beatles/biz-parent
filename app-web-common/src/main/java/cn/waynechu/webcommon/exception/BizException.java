package cn.waynechu.webcommon.exception;

import cn.waynechu.webcommon.enums.AbstractEnum;

import java.io.Serializable;

/**
 * 业务异常
 * <p>
 * 继承该类的异常都会以info级别日志打印
 *
 * @author zhuwei
 * @date 2018/11/6 19:27
 */
public class BizException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 771762126749223759L;

    private final Integer errorCode;

    private final String errorMessage;

    public BizException(AbstractEnum abstractEnum) {
        super(abstractEnum.getDesc());
        this.errorCode = abstractEnum.getCode();
        this.errorMessage = abstractEnum.getDesc();
    }

    public BizException(Integer errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public BizException(Integer errorCode, String errorMessage, Throwable errorCause) {
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
