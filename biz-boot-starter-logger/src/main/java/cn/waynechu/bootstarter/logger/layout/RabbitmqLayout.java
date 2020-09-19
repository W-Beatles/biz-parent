package cn.waynechu.bootstarter.logger.layout;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.ThrowableProxy;
import ch.qos.logback.core.LayoutBase;
import cn.waynechu.bootstarter.logger.provider.ApplicationProvider;
import cn.waynechu.springcloud.common.aspect.AbstractControllerLogAspect;
import cn.waynechu.springcloud.common.util.DesensitizeUtil;
import cn.waynechu.springcloud.common.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuwei
 * @since 2018/12/27 18:52
 */
@Slf4j
public class RabbitmqLayout extends LayoutBase<ILoggingEvent> {

    @Override
    public String doLayout(ILoggingEvent iLoggingEvent) {
        return buildElkFormat(iLoggingEvent);
    }

    private String buildElkFormat(ILoggingEvent iLoggingEvent) {
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
            String timeTaken = event.getMDCPropertyMap().get(AbstractControllerLogAspect.MDC_KEY_TIME_TAKEN);
            if (StringUtil.isNotBlank(timeTaken)) {
                try {
                    json.put(AbstractControllerLogAspect.MDC_KEY_TIME_TAKEN, Integer.valueOf(timeTaken));
                } catch (Exception e) {
                    this.addError("timeTaken转换成Integer失败", e);
                }
            }
        }
    }

    private void writeBasic(JSONObject json, ILoggingEvent event) {
        json.put("parentProjectVersion", ApplicationProvider.getParentProjectVersion());
        json.put("appId", ApplicationProvider.getAppId());
        json.put("appName", ApplicationProvider.getAppName());
        json.put("hostName", ApplicationProvider.getHostName());
        json.put("hostAddress", ApplicationProvider.getHostAddress());

        json.put("logger", event.getLoggerName());
        json.put("threadName", event.getThreadName());
        json.put("level", event.getLevel().toString());
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(event.getTimeStamp()),
                ZoneId.systemDefault());
        json.put("time", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").format(localDateTime));
        // 日志脱敏 & 超长截取
        String[] keyArray = {DesensitizeUtil.PASSWORD, DesensitizeUtil.PWD};
        String message = event.getFormattedMessage();
        if (StringUtil.isNotBlank(message)) {
            message = message.length() <= 4096 ? message : message.substring(0, 4096) + "...总长度为: " + message.length();
        }
        json.put("message", DesensitizeUtil.desensitize(message, keyArray));
    }

    private void writeThrowable(JSONObject json, ILoggingEvent event) {
        IThrowableProxy iThrowableProxy = event.getThrowableProxy();
        if (iThrowableProxy instanceof ThrowableProxy) {
            ThrowableProxy throwableProxy = (ThrowableProxy) iThrowableProxy;
            Throwable t = throwableProxy.getThrowable();
            Throwable ec = t.getCause();
            JSONObject throwable = new JSONObject();

            throwable.put("message", t.getMessage());
            throwable.put("className", t.getClass().getCanonicalName());

            if (ec == null) {
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
            } else {
                throwable.put("cause", ec);
            }
            json.put("throwable", throwable);
        }
    }
}
