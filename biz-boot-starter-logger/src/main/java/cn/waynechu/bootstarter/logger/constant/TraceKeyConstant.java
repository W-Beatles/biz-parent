package cn.waynechu.bootstarter.logger.constant;

import lombok.experimental.UtilityClass;

/**
 * @author zhuwei
 * @since 2020/3/26 11:39
 */
@UtilityClass
public class TraceKeyConstant {

    /**
     * 请求头追踪key
     */
    public static final String HEADER_KEY_API_VERSION = "api-version";
    public static final String HEADER_KEY_CHANNEL = "channel";
    public static final String HEADER_KEY_DEVICE_ID = "device-id";
    public static final String HEADER_KEY_REQUEST_ID = "request-id";
    public static final String HEADER_KEY_SC_CLIENT_IP = "sc-client-ip";
    public static final String HEADER_KEY_ORIGIN_URL = "origin-url";
    public static final String HEADER_KEY_TRACE_APP_IDS = "trace-app-ids";
    public static final String HEADER_KEY_TRACE_APP_NAMES = "trace-app-names";
    public static final String HEADER_KEY_TRACE_HOST_NAMES = "trace-host-names";
    public static final String HEADER_KEY_TRACE_HOST_ADDRESSES = "trace-host-addresses";

    /**
     * MDC追踪key
     */
    public static final String MDC_KEY_API_VERSION = "apiVersion";
    public static final String MDC_KEY_CHANNEL = "channel";
    public static final String MDC_KEY_DEVICE_ID = "deviceId";
    public static final String MDC_KEY_REQUEST_ID = "requestId";
    public static final String MDC_KEY_SC_CLIENT_IP = "scClientIp";
    public static final String MDC_KEY_ORIGIN_URL = "originUrl";
    public static final String MDC_KEY_TRACE_APP_IDS = "traceAppIds";
    public static final String MDC_KEY_TRACE_APP_NAMES = "traceAppNames";
    public static final String MDC_KEY_TRACE_HOST_NAMES = "traceHostNames";
    public static final String MDC_KEY_TRACE_HOST_ADDRESSES = "traceHostAddresses";
}
