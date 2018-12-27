package cn.waynechu.logback.appender.rabbitmq;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.ThrowableProxy;
import com.alibaba.fastjson.JSONException;
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
public class JSONLayout {

    protected SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    protected String machineName = "";

    protected String localAddress = "";

    public JSONLayout() {
        // TODO Auto-generated constructor stub
        try {
            InetAddress address = InetAddress.getLocalHost();
            machineName = address.getHostName();
            localAddress = address.getHostAddress();
        } catch (UnknownHostException e) {
            log.error("JSONLayout��ȡ������Ϣ�쳣", e);
        }
    }

    /**
     * format a given LoggingEvent to a string, in this case JSONified string
     *
     * @param iLoggingEvent
     * @return String representation of LoggingEvent
     */
    public String format(ILoggingEvent iLoggingEvent) {
        JSONObject root = new JSONObject();

        try {
            //== write MDC fields
            writeMdc(root, iLoggingEvent);

            //== write basic fields
            writeBasic(root, iLoggingEvent);

            //== write throwable fields
            writeThrowable(root, iLoggingEvent);

        } catch (JSONException e) {
            log.error("JSONLayout��ʽ���쳣", e);
        }

        return root.toString();
    }

    /**
     * Converts LoggingEvent MDCProperties to JSON object
     *
     * @param json
     * @param event
     * @throws JSONException
     */
    protected void writeMdc(JSONObject json, ILoggingEvent event) throws JSONException {
        if (event.getMDCPropertyMap() != null) {
            json.putAll(event.getMDCPropertyMap());
        }
    }

    /**
     * Converts LoggingEvent Throwable to JSON object
     *
     * @param json
     * @param event
     * @throws JSONException
     */
    protected void writeThrowable(JSONObject json, ILoggingEvent event) throws JSONException {
        IThrowableProxy iThrowableProxy = event.getThrowableProxy();
        if (iThrowableProxy != null && iThrowableProxy instanceof ThrowableProxy) {
            ThrowableProxy throwableProxy = (ThrowableProxy) iThrowableProxy;
            Throwable t = throwableProxy.getThrowable();
            JSONObject throwable = new JSONObject();

            throwable.put("message", t.getMessage());
            throwable.put("className", t.getClass().getCanonicalName());
            List<JSONObject> traceObjects = new ArrayList<JSONObject>();
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
     * Converts basic LogginEvent properties to JSON object
     *
     * @param json
     * @param event
     * @throws JSONException
     */
    protected void writeBasic(JSONObject json, ILoggingEvent event) throws JSONException {
        json.put("machineName", machineName);
        json.put("localAddress", localAddress);
        json.put("threadName", event.getThreadName());
        json.put("level", event.getLevel().toString());
        json.put("time", dateFormat.format(new Date(event.getTimeStamp())));
        json.put("message", event.getFormattedMessage());
        json.put("log", event.getLoggerName());
    }
}
