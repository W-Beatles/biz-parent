package com.waynechu.renting.facade.exception;

import cn.waynechu.facade.common.enums.BizEnum;
import cn.waynechu.facade.common.exception.BizException;

/**
 * @author zhuwei
 * @date 2018/12/24 9:29
 */
public class RentingException extends BizException {

    public RentingException(BizEnum bizEnum) {
        super(bizEnum);
    }

    public RentingException(Integer errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public RentingException(Integer errorCode, String errorMessage, Throwable errorCause) {
        super(errorCode, errorMessage, errorCause);
    }
}
