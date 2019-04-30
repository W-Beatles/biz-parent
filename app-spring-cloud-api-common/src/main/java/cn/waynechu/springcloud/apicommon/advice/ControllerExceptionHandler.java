package cn.waynechu.springcloud.apicommon.advice;

import cn.waynechu.facade.common.enums.BizErrorCodeEnum;
import cn.waynechu.facade.common.exception.BizException;
import cn.waynechu.facade.common.response.BizResponse;
import cn.waynechu.springcloud.apicommon.mdc.MDCFilter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
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
    public BizResponse httpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.info("请求参数格式不正确: {}", e.getMessage(), e);
        return new BizResponse<>(BizErrorCodeEnum.ARGUMENT_IS_INCORRECT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BizResponse methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.info("请求参数校验不合法: {}", e.getMessage(), e);
        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult == null || bindingResult.getFieldError() == null) {
            return new BizResponse<>(BizErrorCodeEnum.ARGUMENT_NOT_VALID);
        }
        return new BizResponse<>(BizErrorCodeEnum.ARGUMENT_NOT_VALID.getCode(), bindingResult.getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public BizResponse missingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.info("缺少请求参数: ({}) {}", e.getParameterType(), e.getParameterName(), e);
        return new BizResponse<>(BizErrorCodeEnum.MISSING_REQUEST_PARAM.getCode(),
                BizErrorCodeEnum.MISSING_REQUEST_PARAM.getDesc()
                        + ": (" + e.getParameterType() + ") " + e.getParameterName());
    }

    @ExceptionHandler(BizException.class)
    public BizResponse bizException(BizException e) {
        log.info("[业务异常] {}", e.getErrorMessage(), e);
        return new BizResponse<>(e.getErrorCode(), e.getErrorMessage(), MDC.get(MDCFilter.REQ_KEY));
    }

    @ExceptionHandler(Exception.class)
    public BizResponse unknownException(Exception e) {
        log.error("[系统异常] ", e);
        return new BizResponse<>(BizErrorCodeEnum.SYSTEM_ERROR.getCode(),
                BizErrorCodeEnum.SYSTEM_ERROR.getDesc() + ": " + MDC.get(MDCFilter.REQ_UUID));
    }
}
