package cn.waynechu.springcloud.apicommon.logback;

import ch.qos.logback.classic.pattern.ThrowableProxyConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.ThrowableProxy;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.LayoutBase;
import cn.waynechu.springcloud.common.aspect.AbstractControllerLogAspect;
import cn.waynechu.springcloud.common.util.DesensitizeUtils;
import cn.waynechu.springcloud.common.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author zhuwei
 * @date 2018/12/27 18:52
 */
@Slf4j
public class RabbitmqLayout extends LayoutBase<ILoggingEvent> {

    private static List<String> optionList = Collections.singletonList("full");

    private static ThrowableProxyConverter converter = new ThrowableProxyConverter();

    protected String machineName;

    protected String localAddress;

    static {
        converter.setOptionList(optionList);
        converter.start();
    }

    public RabbitmqLayout() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            machineName = address.getHostName();
            localAddress = address.getHostAddress();
        } catch (UnknownHostException e) {
            this.addError("RabbitmqLayout无法获取machineName和localAddress", e);
        }
    }

    @Override
    public String doLayout(ILoggingEvent iLoggingEvent) {
        return buildELKFormat(iLoggingEvent);
    }

    private String buildELKFormat(ILoggingEvent iLoggingEvent) {
        JSONObject root = new JSONObject();

        // write mdc fields
        writeMdc(root, iLoggingEvent);
        // write basic fields
        writeBasic(root, iLoggingEvent);
        // write throwable fields
        writeThrowable(root, iLoggingEvent);

        return root.toString();
    }

    private void writeMdc(JSONObject json, ILoggingEvent event) {
        if (event.getMDCPropertyMap() != null) {
            json.putAll(event.getMDCPropertyMap());
            // 转化timeTaken为Integer类型
            String timeTaken = event.getMDCPropertyMap().get(AbstractControllerLogAspect.MDC_TIME_TAKEN_KEY);
            if (StringUtil.isNotBlank(timeTaken)) {
                try {
                    json.put(AbstractControllerLogAspect.MDC_TIME_TAKEN_KEY, Integer.valueOf(timeTaken));
                } catch (Exception e) {
                    this.addError("timeTaken转换成Integer失败", e);
                }
            }
        }
    }

    private void writeBasic(JSONObject json, ILoggingEvent event) {
        json.put("machineName", machineName);
        json.put("localAddress", localAddress);
        json.put("threadName", event.getThreadName());
        json.put("level", event.getLevel().toString());
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(event.getTimeStamp()),
                ZoneId.systemDefault());
        json.put("time", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(localDateTime));
        // 日志脱敏
        String[] keyArray = {DesensitizeUtils.PASSWORD, DesensitizeUtils.PWD};
        json.put("message", DesensitizeUtils.desensitize(event.getFormattedMessage(), keyArray));
        json.put("logger", event.getLoggerName());
    }

    private void writeThrowable(JSONObject json, ILoggingEvent event) {
        IThrowableProxy iThrowableProxy = event.getThrowableProxy();
        if (iThrowableProxy instanceof ThrowableProxy) {
            ThrowableProxy throwableProxy = (ThrowableProxy) iThrowableProxy;
            Throwable t = throwableProxy.getThrowable();
            JSONObject throwable = new JSONObject();

            throwable.put("message", t.getMessage());
            throwable.put("className", t.getClass().getCanonicalName());
            throwable.put("stackTrace", writeStackTrace(event));
            json.put("throwable", throwable);

            List<JSONObject> traceObjects = new ArrayList<>();
            for (StackTraceElement ste : t.getStackTrace()) {
                JSONObject element = new JSONObject();
                element.put("class", ste.getClassName());
                element.put("method", ste.getMethodName());
                element.put("line", ste.getLineNumber());
                element.put("file", ste.getFileName());
                traceObjects.add(element);
            }
            json.put("stackTrace", traceObjects);
        }
    }

    private String writeStackTrace(ILoggingEvent event) {
        StringBuilder stringBuilder = new StringBuilder(2048);
        IThrowableProxy proxy = event.getThrowableProxy();
        if (proxy != null) {
            stringBuilder.append(converter.convert(event));
            stringBuilder.append(CoreConstants.LINE_SEPARATOR);
        }
        return stringBuilder.toString();
    }
}
