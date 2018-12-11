package cn.waynechu.renting.web.advice;

import cn.waynechu.common.enums.ResultEnum;
import cn.waynechu.common.facade.Result;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author zhuwei
 * @date 2018/11/15 10:45
 */
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public Result missingServletRequestParameterException(MissingServletRequestParameterException e) {
        // 缺少请求参数
        return Result.error(ResultEnum.MISSING_REQUEST_PARAMETER.getCode(), e.getMessage());
    }
}
