package cn.waynechu.appender.rabbitmq.logback;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.ThrowableProxy;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhuwei
 * @date 2018/12/27 18:52
 */
@Slf4j
public class JsonLayout {

    protected SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    protected String machineName = "";

    protected String localAddress = "";

    public JsonLayout() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            machineName = address.getHostName();
            localAddress = address.getHostAddress();
        } catch (UnknownHostException e) {
            log.error("JsonLayout UnknownHost", e);
        }
    }

    /**
     * format a given LoggingEvent to a string, in this case JSONified string
     *
     * @param iLoggingEvent iLoggingEvent
     * @return String representation of LoggingEvent
     */
    public String format(ILoggingEvent iLoggingEvent) {
        JSONObject root = new JSONObject();

        //== write MDC fields
        writeMdc(root, iLoggingEvent);
        //== write basic fields
        writeBasic(root, iLoggingEvent);
        //== write throwable fields
        writeThrowable(root, iLoggingEvent);

        return root.toString();
    }

    /**
     * Converts LoggingEvent MDCProperties to JSON object
     *
     * @param json  json
     * @param event event
     */
    protected void writeMdc(JSONObject json, ILoggingEvent event) {
        if (event.getMDCPropertyMap() != null) {
            json.putAll(event.getMDCPropertyMap());
        }
    }

    /**
     * Converts LoggingEvent Throwable to JSON object
     *
     * @param json  json
     * @param event event
     */
    protected void writeThrowable(JSONObject json, ILoggingEvent event) {
        IThrowableProxy iThrowableProxy = event.getThrowableProxy();
        if (iThrowableProxy instanceof ThrowableProxy) {
            ThrowableProxy throwableProxy = (ThrowableProxy) iThrowableProxy;
            Throwable t = throwableProxy.getThrowable();
            JSONObject throwable = new JSONObject();

            throwable.put("message", t.getMessage());
            throwable.put("className", t.getClass().getCanonicalName());

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
            json.put("throwable", throwable);
        }
    }

    /**
     * Converts basic LoggingEvent properties to JSON object
     *
     * @param json  json
     * @param event event
     */
    protected void writeBasic(JSONObject json, ILoggingEvent event) {
        json.put("machineName", machineName);
        json.put("localAddress", localAddress);
        json.put("threadName", event.getThreadName());
        json.put("level", event.getLevel().toString());
        json.put("time", dateFormat.format(new Date(event.getTimeStamp())));
        json.put("message", event.getFormattedMessage());
        json.put("logger", event.getLoggerName());
    }
}
