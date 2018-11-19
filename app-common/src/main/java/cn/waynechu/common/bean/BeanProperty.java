package cn.waynechu.common.bean;

import java.lang.reflect.Method;

/**
 * @author zhuwei
 * @date 2018/11/6 16:10
 */

public class BeanProperty {
    private String name;
    private Class<?> type;

    private Method setterMethod;
    private Method getterMethod;

    public BeanProperty() {
    }

    public BeanProperty(String name, Class<?> type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public Method getSetterMethod() {
        return setterMethod;
    }

    public void setSetterMethod(Method setterMethod) {
        this.setterMethod = setterMethod;
    }

    public Method getGetterMethod() {
        return getterMethod;
    }

    public void setGetterMethod(Method getterMethod) {
        this.getterMethod = getterMethod;
    }
}
