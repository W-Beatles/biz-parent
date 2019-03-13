package cn.waynechu.renting.facade.exception;

import cn.waynechu.webcommon.enums.BaseEnum;
import cn.waynechu.webcommon.exception.BaseException;

/**
 * @author zhuwei
 * @date 2018/12/24 9:29
 */
public class RentingException extends BaseException {
    public RentingException(BaseEnum baseEnum) {
        super(baseEnum);
    }

    public RentingException(Integer errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public RentingException(Integer errorCode, String errorMessage, Throwable errorCause) {
        super(errorCode, errorMessage, errorCause);
    }
}
