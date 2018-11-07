package cn.waynechu.api.common.proxy;

import cn.waynechu.api.common.annotation.MethodPrintAnnotation;
import cn.waynechu.api.common.exception.BaseException;

/**
 * @author zhuwei
 * @date 2018/11/7 10:59
 */
public interface Sub {

    @MethodPrintAnnotation(isParamFormat = true)
    BaseException print(String arg);
}
