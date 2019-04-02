package cn.waynechu.spirngcloud.common.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author zhuwei
 * @date 2018/11/6 16:13
 */
@Slf4j
@UtilityClass
public class BeanUtil {
    private static final String NEW_INSTANCE_INSTANTIATION_ERROR = "New instance error. Is it an abstract class?";
    private static final String NEW_INSTANCE_ILLEGAL_ACCESS_ERROR = "New instance error. Is the constructor accessible?";

    private static Map<String, BeanStructure> beanStructureCache = new HashMap<>();

    /**
     * 转换<X>源类型对象为<Y>目标类型对象
     *
     * @param srcObject   源对象
     * @param targetClass 目标类
     * @param <X>         <X>源对象类型
     * @param <Y>         <Y>目标对象类型
     * @return T2类型对象
     */
    public static <X, Y> Y beanTransfer(X srcObject, Class<Y> targetClass) {
        Y returnValue = null;

        if (srcObject != null && targetClass != null) {
            try {
                returnValue = targetClass.newInstance();
                copyProperties(srcObject, returnValue);
            } catch (InstantiationException e) {
                log.error(NEW_INSTANCE_INSTANTIATION_ERROR, e);
            } catch (IllegalAccessException e) {
                log.error(NEW_INSTANCE_ILLEGAL_ACCESS_ERROR, e);
            }
        }
        return returnValue;
    }

    /**
     * 将map转化为指定类型对象
     *
     * @param srcMapData  源map数据
     * @param targetClass 目标类
     * @param <T>         <T>目标类型
     * @return <T>类型对象
     */
    public static <T> T beanTransfer(Map<String, Object> srcMapData, Class<T> targetClass) {
        T returnValue = null;

        if (CollectionUtil.isNotNullOrEmpty(srcMapData) && targetClass != null) {
            try {
                returnValue = targetClass.newInstance();

                copyMapToObject(srcMapData, returnValue);
            } catch (InstantiationException e) {
                log.error(NEW_INSTANCE_INSTANTIATION_ERROR, e);
            } catch (IllegalAccessException e) {
                log.error(NEW_INSTANCE_ILLEGAL_ACCESS_ERROR, e);
            }
        }
        return returnValue;
    }

    /**
     * 转换<X>源类型列表对象为<Y>目标类型列表对象
     *
     * @param srcObjects  转换列表对象
     * @param targetClass 目标类
     * @param <X>         <X>源类型
     * @param <Y>         <Y>目标类型
     * @return 目标类型列表对象
     */
    public static <X, Y> List<Y> beanListTransfer(List<X> srcObjects, Class<Y> targetClass) {
        List<Y> returnValue = null;
        try {
            if (CollectionUtil.isNotNullOrEmpty(srcObjects)) {
                returnValue = new ArrayList<>();
                for (X srcObject : srcObjects) {
                    Y targetObject = targetClass.newInstance();
                    copyProperties(srcObject, targetObject);
                    returnValue.add(targetObject);
                }
            }
        } catch (InstantiationException e) {
            log.error(NEW_INSTANCE_INSTANTIATION_ERROR, e);
        } catch (IllegalAccessException e) {
            log.error(NEW_INSTANCE_ILLEGAL_ACCESS_ERROR, e);
        }
        return returnValue;
    }

    /**
     * 拷贝对象属性
     *
     * @param srcObject    源对象
     * @param targetObject 目标对象
     */
    public static void copyProperties(Object srcObject, Object targetObject) {
        if (srcObject == null || targetObject == null) {
            throw new IllegalArgumentException("The coping source or target objects is null.");
        }

        BeanStructure srcBeanStructure = getBeanStructure(srcObject.getClass());
        BeanStructure targetBeanStructure = getBeanStructure(targetObject.getClass());

        for (BeanProperty targetBeanProperty : targetBeanStructure.getProperties().values()) {
            BeanProperty srcBeanProperty = srcBeanStructure.getProperties().get(targetBeanProperty.getName());
            if (srcBeanProperty != null && srcBeanProperty.getGetterMethod() != null
                    && targetBeanProperty.getSetterMethod() != null
                    && srcBeanProperty.getType().equals(targetBeanProperty.getType())) {
                try {
                    targetBeanProperty.getSetterMethod().invoke(targetObject,
                            srcBeanProperty.getGetterMethod().invoke(srcObject));
                } catch (Exception e) {
                    log.error("copyProperties error.", e);
                }
            }
        }
    }

    /**
     * 拷贝map值到目标对象
     *
     * @param srcMapData   源map值
     * @param targetObject 目标对象
     */
    public static void copyMapToObject(Map<String, Object> srcMapData, Object targetObject) {
        if (CollectionUtil.isNullOrEmpty(srcMapData) || targetObject == null) {
            throw new IllegalArgumentException("The coping src data or target objects is null.");
        } else {
            for (Map.Entry<String, Object> entry : srcMapData.entrySet()) {
                setPropertyValue(targetObject, entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * 设置目标对象的属性值
     *
     * @param targetObject  目标对象
     * @param propertyName  属性名称
     * @param propertyValue 属性值
     */
    public static void setPropertyValue(Object targetObject, String propertyName, Object propertyValue) {
        if (targetObject == null || StringUtil.isEmpty(propertyName)) {
            log.error("The target object or property is null.");
        } else {
            BeanStructure targetBeanStructure = getBeanStructure(targetObject.getClass());
            BeanProperty targetBeanProperty = targetBeanStructure.getProperties().get(propertyName);
            if (targetBeanProperty.getSetterMethod() != null && targetBeanProperty.getType() != null) {
                try {
                    Object castValue = targetBeanProperty.getType().cast(propertyValue);
                    if (castValue != null) {
                        targetBeanProperty.getSetterMethod().invoke(targetObject, castValue);
                    }
                } catch (Exception e) {
                    log.error("setProperty error.", e);
                }
            }
        }
    }

    /**
     * 批量设置目标对象属性值
     *
     * @param targetObjects 目标对象列表
     * @param propertyName  属性名
     * @param propertyValue 属性值
     */
    public static void setPropertyValues(Collection<?> targetObjects, String propertyName, Object propertyValue) {
        for (Object obj : targetObjects) {
            setPropertyValue(obj, propertyName, propertyValue);
        }
    }

    /**
     * 获取对象属性值并转化成<T>目标类型
     *
     * @param object       源对象
     * @param propertyName 属性名称
     * @param clazz        目标类
     * @param <T>          目标类型
     * @return 对象属性值
     */
    public static <T> T getPropertyValue(Object object, String propertyName, Class<T> clazz) {
        return getPropertyValue(object, propertyName);
    }

    /**
     * 获取对象属性值
     *
     * @param object       源对象
     * @param propertyName 属性名称
     * @param <T>          目标类型
     * @return 对象属性值
     */
    @SuppressWarnings("unchecked")
    public static <T> T getPropertyValue(Object object, String propertyName) {
        T returnValue = null;

        if (object == null || propertyName == null) {
            throw new IllegalArgumentException("The object or property name is null.");
        } else {
            BeanStructure srcBeanStructure = getBeanStructure(object.getClass());

            BeanProperty beanProperty = srcBeanStructure.getProperties().get(propertyName);
            if (beanProperty != null) {
                Method getterMethod = beanProperty.getGetterMethod();
                if (getterMethod != null) {
                    try {
                        returnValue = (T) getterMethod.invoke(object);
                    } catch (Exception e) {
                        log.error("getPropertyValue error.", e);
                    }
                }
            }
        }
        return returnValue;
    }

    /**
     * 获取目标类属性结构
     *
     * @param clazz 目标类
     * @return 属性结构
     */
    public static BeanStructure getBeanStructure(Class<?> clazz) {
        BeanStructure returnValue = beanStructureCache.get(clazz.getName());
        if (returnValue != null) {
            return returnValue;
        }
        returnValue = new BeanStructure();

        Map<String, Class<?>> allProperties = getProperties(clazz);
        for (Map.Entry<String, Class<?>> entry : allProperties.entrySet()) {
            Set<String> getterMethodNames = getGetterMethodNames(entry.getKey());
            Set<String> setterMethodNames = getSetterMethodNames(entry.getKey());

            BeanProperty beanProperty = new BeanProperty(entry.getKey(), entry.getValue());

            Method setterMethod;
            for (String setterMethodName : setterMethodNames) {
                setterMethod = getMethod(clazz, setterMethodName, entry.getValue());

                if (setterMethod != null) {
                    beanProperty.setSetterMethod(setterMethod);
                    break;
                }
            }

            Method getterMethod;
            for (String getterMethodName : getterMethodNames) {
                getterMethod = getMethod(clazz, getterMethodName);

                if (getterMethod != null && getterMethod.getReturnType().equals(entry.getValue())) {
                    beanProperty.setGetterMethod(getterMethod);
                    break;
                }
            }
            returnValue.getProperties().put(entry.getKey(), beanProperty);
        }
        beanStructureCache.put(clazz.getName(), returnValue);
        return returnValue;
    }

    /**
     * 递归获取类的所有属性名及类型
     *
     * @param clazz 类类型
     * @return key: 属性名 value:属性类型
     */
    public static Map<String, Class<?>> getProperties(Class<?> clazz) {
        Map<String, Class<?>> returnValue = new HashMap<>();

        // the current class
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            returnValue.put(field.getName(), field.getType());
        }

        // loop to super classes.
        if (!clazz.getSuperclass().equals(Object.class)) {
            returnValue.putAll(getProperties(clazz.getSuperclass()));
        }
        return returnValue;
    }

    public static <X, Y> Set<X> getPropertySetFromList(final Collection<Y> list, final String propertyName, Class<X> xClass) {
        return getPropertySetFromList(list, propertyName);
    }

    public static <X, Y> Set<X> getPropertySetFromList(final Collection<Y> list, final String propertyName) {
        Set<X> returnValue = new LinkedHashSet<>();

        try {
            if (CollectionUtil.isNotNullOrEmpty(list) && StringUtil.isNotEmpty(propertyName)) {
                for (Y item : list) {
                    X attributeValue = getPropertyValue(item, propertyName);
                    if (attributeValue != null) {
                        returnValue.add(attributeValue);
                    }
                }
            }
        } catch (Exception e) {
            log.error("getPropertySetFromList error", e);
        }
        return returnValue;
    }

    public static <X, Y> List<X> getPropertyListFromList(final Collection<Y> list, final String propertyName, Class<X> xClass) {
        return getPropertyListFromList(list, propertyName);
    }

    public static <X, Y> List<X> getPropertyListFromList(final Collection<Y> list, final String propertyName) {
        List<X> returnValue = new ArrayList<>();

        try {
            if (CollectionUtil.isNotNullOrEmpty(list) && StringUtil.isNotEmpty(propertyName)) {
                for (Y item : list) {
                    X attributeValue = getPropertyValue(item, propertyName);
                    if (attributeValue != null) {
                        returnValue.add(attributeValue);
                    }
                }
            }
        } catch (Exception e) {
            log.error("getPropertyListFromList error", e);
        }
        return returnValue;
    }

    public static <K, V> Map<K, V> listToMapTransfer(final Collection<V> list, final String mapKeyPropertyName) {
        Map<K, V> returnValue = new HashMap<>();

        if (!CollectionUtil.isNullOrEmpty(list) || StringUtil.isEmpty(mapKeyPropertyName)) {
            try {
                for (V item : list) {
                    K propertyValue = getPropertyValue(item, mapKeyPropertyName);
                    if (propertyValue != null) {
                        returnValue.put(propertyValue, item);
                    }
                }
            } catch (Exception e) {
                log.error("listToMapTransfer error", e);
            }
        }
        return returnValue;
    }

    public static <V, F> List<V> filterObjectListByObjectValues(final Collection<V> srcObjects, final String srcPropertyName, final Collection<F> filterObjects, final String filterPropertyName) {
        List<V> returnValue = new ArrayList<>();

        if (!CollectionUtil.isNullOrEmpty(srcObjects) && !StringUtil.isEmpty(srcPropertyName)
                && !CollectionUtil.isNullOrEmpty(filterObjects) && !StringUtil.isEmpty(filterPropertyName)) {
            Set<V> filterValues = getPropertySetFromList(filterObjects, filterPropertyName);

            returnValue.addAll(filterObjectsByPropertyValues(srcObjects, srcPropertyName, filterValues));
        }
        return returnValue;
    }

    public static <V, F> List<V> filterObjectsByPropertyValues(final Collection<V> srcObjects, final String srcPropertyName, final Set<F> filterPropertyValues) {
        List<V> returnValue = new LinkedList<>();

        if (!CollectionUtil.isNullOrEmpty(srcObjects) && !CollectionUtil.isNullOrEmpty(filterPropertyValues) && !StringUtil.isEmpty(srcPropertyName)) {
            try {
                for (V srcObject : srcObjects) {
                    F propertyValue = getPropertyValue(srcObject, srcPropertyName);

                    if (propertyValue != null && filterPropertyValues.contains(propertyValue)) {
                        returnValue.add(srcObject);
                    }
                }
            } catch (Exception e) {
                log.error("filterObjectsByPropertyValues error", e);
            }
        }
        return returnValue;
    }

    private static Method getMethod(Class<?> clazz, String name, Class<?>... params) {
        try {
            return clazz.getMethod(name, params);
        } catch (Exception e) {
            return null;
        }
    }

    private static Set<String> getSetterMethodNames(String propertyName) {
        Set<String> returnValue = new HashSet<>();
        returnValue.add("set" + StringUtil.capitalize(propertyName));
        return returnValue;
    }

    private static Set<String> getGetterMethodNames(String propertyName) {
        Set<String> returnValue = new HashSet<>();
        returnValue.add("get" + StringUtil.capitalize(propertyName));
        returnValue.add("is" + StringUtil.capitalize(propertyName));
        returnValue.add("has" + StringUtil.capitalize(propertyName));
        return returnValue;
    }

    public class BeanStructure {
        /**
         * key: 属性名 value: 属性详情
         */
        private Map<String, BeanProperty> properties = new HashMap<>();

        public Map<String, BeanProperty> getProperties() {
            return properties;
        }

        public void setProperties(Map<String, BeanProperty> properties) {
            this.properties = properties;
        }
    }

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
}
