package cn.waynechu.springcloud.apistarter.advice;

import cn.waynechu.facade.common.enums.BizErrorCodeEnum;
import cn.waynechu.facade.common.exception.BizException;
import cn.waynechu.facade.common.response.BizResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

import static cn.waynechu.bootstarter.logger.constant.TraceKeyConstant.MDC_KEY_REQUEST_ID;

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
    public BizResponse<String> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.info("请求参数格式不正确: {}", e.getMessage());
        return BizResponse.error(BizErrorCodeEnum.REQUEST_PARAM_INCORRECT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BizResponse<String> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.info("请求参数校验不合法: {}", e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult.getFieldError() == null) {
            return BizResponse.error(BizErrorCodeEnum.REQUEST_PARAM_INVALID);
        }
        return BizResponse.error(BizErrorCodeEnum.REQUEST_PARAM_INVALID.getCode(), bindingResult.getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public BizResponse<String> missingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.info("缺少请求参数: ({}) {}", e.getParameterType(), e.getParameterName());
        return BizResponse.error(BizErrorCodeEnum.MISSING_REQUEST_PARAM.getCode(),
                BizErrorCodeEnum.MISSING_REQUEST_PARAM.getDesc()
                        + ": (" + e.getParameterType() + ") " + e.getParameterName());
    }

    @ExceptionHandler(BizException.class)
    public BizResponse<String> bizException(BizException e) {
        if (Objects.equals(BizErrorCodeEnum.SYSTEM_ERROR.getCode(), e.getErrorCode())
                || Objects.equals(BizErrorCodeEnum.CALL_SERVICE_ERROR.getCode(), e.getErrorCode())) {
            log.error("[BizError] {}", e.getErrorMessage(), e);
        }
        log.info("[BizError] {}", e.getErrorMessage());
        return BizResponse.error(e.getErrorCode(), e.getErrorMessage(), MDC.get(MDC_KEY_REQUEST_ID).substring(0, 6));
    }

    @ExceptionHandler(Exception.class)
    public BizResponse<String> unknownException(Exception e) {
        log.error("[SystemError] ", e);
        return BizResponse.error(BizErrorCodeEnum.SYSTEM_ERROR.getCode(),
                BizErrorCodeEnum.SYSTEM_ERROR.getDesc() + ": " + MDC.get(MDC_KEY_REQUEST_ID).substring(0, 6));
    }
}
