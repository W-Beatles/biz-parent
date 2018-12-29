package cn.waynechu.webcommon.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhuwei
 * @date 2018/11/6 16:12
 */
public class BeanStructure {
    private Map<String, BeanProperty> properties = new HashMap<>();

    public Map<String, BeanProperty> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, BeanProperty> properties) {
        this.properties = properties;
    }
}
