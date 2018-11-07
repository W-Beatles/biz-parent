package com.tuhu.api.common.proxy;

import com.tuhu.api.common.annotation.MethodPrintAnnotation;
import com.tuhu.api.common.exception.BaseException;

/**
 * @author zhuwei
 * @date 2018/11/7 10:59
 */
public interface Sub {

    @MethodPrintAnnotation(isParamFormat = true)
    BaseException print(String arg);
}
