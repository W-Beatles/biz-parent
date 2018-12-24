package cn.waynechu.renting.facade.exception;

import cn.waynechu.common.enums.IBaseEnum;
import cn.waynechu.common.exception.BaseException;

/**
 * @author zhuwei
 * @date 2018/12/24 9:29
 */
public class RentingException extends BaseException {
    public RentingException(IBaseEnum iBaseEnum) {
        super(iBaseEnum);
    }

    public RentingException(Integer errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public RentingException(Integer errorCode, String errorMessage, Throwable errorCause) {
        super(errorCode, errorMessage, errorCause);
    }
}
