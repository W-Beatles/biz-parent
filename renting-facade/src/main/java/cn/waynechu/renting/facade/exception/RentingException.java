package cn.waynechu.renting.facade.exception;

import cn.waynechu.renting.facade.enums.ErrorCodeEnum;
import cn.waynechu.webcommon.exception.BaseBizException;

/**
 * @author zhuwei
 * @date 2018/12/24 9:29
 */
public class RentingException extends BaseBizException {

    public RentingException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum);
    }

    public RentingException(Integer errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public RentingException(Integer errorCode, String errorMessage, Throwable errorCause) {
        super(errorCode, errorMessage, errorCause);
    }
}
