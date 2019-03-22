package cn.waynechu.boot.starter.common.logback;

import ch.qos.logback.classic.pattern.ThrowableProxyConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.ThrowableProxy;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.LayoutBase;
import cn.waynechu.webcommon.aspect.BaseControllerLogAspect;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
            Map<String, String> mdcPropertyMap = event.getMDCPropertyMap();
            for (Map.Entry<String, String> entry : mdcPropertyMap.entrySet()) {
                if (BaseControllerLogAspect.TIME_TAKEN_KEY.equals(entry.getKey())) {
                    // timeTaken 转化为int类型
                    json.put(entry.getKey(), Integer.parseInt(entry.getValue()));
                } else {
                    json.put(entry.getKey(), entry.getValue());
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
        json.put("message", event.getFormattedMessage());
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
