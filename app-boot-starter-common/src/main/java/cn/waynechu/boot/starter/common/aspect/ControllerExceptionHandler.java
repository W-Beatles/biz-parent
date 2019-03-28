package cn.waynechu.boot.starter.common.aspect;

import cn.waynechu.webcommon.enums.CommonResultEnum;
import cn.waynechu.webcommon.exception.BizException;
import cn.waynechu.webcommon.web.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理切面
 *
 * @author zhuwei
 * @date 2018/11/15 10:45
 */
@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result httpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.info("请求参数格式不正确: {}", e.getMessage(), e);
        return Result.error(CommonResultEnum.ARGUMENT_IS_INCORRECT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.info("请求参数校验不合法: {}", e.getMessage(), e);
        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult == null || bindingResult.getFieldError() == null) {
            return Result.error(CommonResultEnum.ARGUMENT_NOT_VALID);
        }
        return Result.error(CommonResultEnum.ARGUMENT_NOT_VALID.getCode(), bindingResult.getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result missingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.info("缺少请求参数: ({}) {}", e.getParameterType(), e.getParameterName(), e);
        return Result.error(CommonResultEnum.MISSING_REQUEST_PARAM.getCode(),
                CommonResultEnum.MISSING_REQUEST_PARAM.getDesc()
                        + ": (" + e.getParameterType() + ") " + e.getParameterName());
    }

    @ExceptionHandler(BizException.class)
    public Result bizException(BizException e) {
        log.info("[业务异常] {}", e.getErrorMessage(), e);
        return Result.error(e.getErrorCode(), e.getErrorMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result unknownException(Exception e) {
        log.error("[系统异常] ", e);
        return Result.error();
    }
}
