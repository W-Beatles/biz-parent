package cn.waynechu.renting.web.advice;

import cn.waynechu.renting.facade.exception.RentingException;
import cn.waynechu.webcommon.enums.CommonResultEnum;
import cn.waynechu.webcommon.web.Result;
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
        return Result.error(CommonResultEnum.MISSING_REQUEST_PARAMETER.getCode(), e.getMessage());
    }

    @ExceptionHandler(RentingException.class)
    public Result rentingException(RentingException e) {
        return Result.error(e.getErrorCode(), e.getErrorMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result unknownException(Exception e) {
        log.error("[SYSTEM_ERROR] " + e.getMessage());
        return Result.error();
    }
}
