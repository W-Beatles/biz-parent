package cn.waynechu.logback.appender.rabbitmq;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

/**
 * @author zhuwei
 * @date 2018/12/27 18:57
 */
public class RabbitMQAppenderLayout extends JSONLayout {
    private String index = "json-index";
    private String type = "json";

    /**
     * format a given LoggingEvent to a string, in this case JSONified string
     * @param iLoggingEvent
     * @return String representation of LoggingEvent
     */
    @Override
    public String format(ILoggingEvent iLoggingEvent) {

        StringBuilder sb = new StringBuilder();

        JSONObject action = new JSONObject();
        JSONObject source = new JSONObject();

        try {
            JSONObject actionContent = new JSONObject();
            actionContent.put("_index", this.getIndex());
            actionContent.put("_type", this.getType());
            action.put("index", actionContent);

            JSONObject sourceContent = new JSONObject();

            //== write basic fields
            writeBasic(sourceContent, iLoggingEvent);

            //== write throwable fields
            writeThrowable(sourceContent, iLoggingEvent);

            source.put(this.getType(), sourceContent);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        sb.append(action.toString());
        sb.append("\n");
        sb.append(source.toString());
        sb.append("\n");

        return sb.toString();
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
