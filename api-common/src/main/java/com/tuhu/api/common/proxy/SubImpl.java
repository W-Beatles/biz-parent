package com.tuhu.api.common.proxy;

import com.tuhu.api.common.exception.BaseException;
import com.tuhu.api.common.exception.ErrorCodeEnum;

/**
 * @author zhuwei
 * @date 2018/11/7 11:00
 */
public class SubImpl implements Sub {

    @Override
    public BaseException print(String arg) {
        System.out.println("call print method, arg is " + arg);
        return new BaseException(ErrorCodeEnum.SYSTEM_ERROR);
    }

    public static void main(String[] args) {
        Sub sub = (Sub) MethodPrintAnnotationProxy.getInstance().bind(new SubImpl());
        sub.print("sub");
    }
}
