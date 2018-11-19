package cn.waynechu.common.proxy;

import cn.waynechu.common.annotation.MethodPrintAnnotation;
import cn.waynechu.common.exception.BaseException;

/**
 * @author zhuwei
 * @date 2018/11/7 10:59
 */
public interface Sub {

    @MethodPrintAnnotation(isParamFormat = true)
    BaseException print(String arg);
}
