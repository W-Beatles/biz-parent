package cn.waynechu.renting.web.advice;

import cn.waynechu.common.enums.ResultEnum;
import cn.waynechu.common.facade.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author zhuwei
 * @date 2018/11/15 10:45
 */
@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public Result missingServletRequestParameterException(MissingServletRequestParameterException e) {
        // 缺少请求参数
        return Result.error(ResultEnum.MISSING_REQUEST_PARAMETER.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public Result unknownException(Exception e) {
        log.error("[系统异常]", e);
        return Result.error("[系统异常] " + e.toString());
    }
}
