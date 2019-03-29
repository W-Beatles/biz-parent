package com.waynechu.renting.facade.exception;

import cn.waynechu.webcommon.enums.AbstractEnum;
import cn.waynechu.webcommon.exception.BizException;

/**
 * @author zhuwei
 * @date 2018/12/24 9:29
 */
public class RentingException extends BizException {

    public RentingException(AbstractEnum abstractEnum) {
        super(abstractEnum);
    }

    public RentingException(Integer errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public RentingException(Integer errorCode, String errorMessage, Throwable errorCause) {
        super(errorCode, errorMessage, errorCause);
    }
}
