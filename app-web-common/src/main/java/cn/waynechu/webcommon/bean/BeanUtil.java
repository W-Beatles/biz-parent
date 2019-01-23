package cn.waynechu.webcommon.bean;

import cn.waynechu.webcommon.util.CollectionUtil;
import cn.waynechu.webcommon.util.StringUtil;
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

    public static <T> T beanTransfer(Map<String, String> srcData, Class<T> targetClass) {
        T returnValue = null;

        if (CollectionUtil.isNotNullOrEmpty(srcData) && targetClass != null) {
            try {
                returnValue = targetClass.newInstance();

                for (Map.Entry<String, String> entry : srcData.entrySet()) {
                    setPropertyValueWithCast(returnValue, entry.getKey(), entry.getValue());
                }
            } catch (InstantiationException e) {
                log.error(NEW_INSTANCE_INSTANTIATION_ERROR, e);
            } catch (IllegalAccessException e) {
                log.error(NEW_INSTANCE_ILLEGAL_ACCESS_ERROR, e);
            }
        }
        return returnValue;
    }

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

    public static void copyProperties(Object srcObject, Object targetObject) {
        if (srcObject == null || targetObject == null) {
            log.error("The coping source or target objects is null.");
            return;
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

    public static void copyMapToObject(Map<String, Object> srcData, Object targetObject) {
        if (CollectionUtil.isNullOrEmpty(srcData) || targetObject == null) {
            log.error("The coping src data or target objects is null.");
        } else {
            for (Map.Entry<String, Object> entry : srcData.entrySet()) {
                setPropertyValue(targetObject, entry.getKey(), entry.getValue());
            }
        }
    }

    public static void setPropertyValue(Object targetObject, String propertyName, Object value) {
        if (targetObject == null || StringUtil.isEmpty(propertyName)) {
            log.error("The target object or property is null.");
        } else {
            BeanStructure targetBeanStructure = getBeanStructure(targetObject.getClass());

            BeanProperty targetBeanProperty = targetBeanStructure.getProperties().get(propertyName);
            if (targetBeanProperty.getSetterMethod() != null && targetBeanProperty.getType() != null) {
                try {
                    Object castValue = targetBeanProperty.getType().cast(value);

                    if (castValue != null) {
                        targetBeanProperty.getSetterMethod().invoke(targetObject, castValue);
                    }
                } catch (Exception e) {
                    log.error("setProperty error.", e);
                }
            }
        }
    }

    public static void setPropertyValueWithCast(Object targetObject, String propertyName, String value) {
        if (targetObject == null || StringUtil.isEmpty(propertyName)) {
            log.error("The target object or property is null.");
        } else {
            BeanStructure targetBeanStructure = getBeanStructure(targetObject.getClass());

            BeanProperty targetBeanProperty = targetBeanStructure.getProperties().get(propertyName);
            if (targetBeanProperty.getSetterMethod() != null) {
                Object castValue = StringUtil.castToBaseObject(value, targetBeanProperty.getType());

                if (castValue != null) {
                    try {
                        targetBeanProperty.getSetterMethod().invoke(targetObject, castValue);
                    } catch (Exception e) {
                        log.error("setProperty error.", e);
                    }
                }
            }
        }
    }

    public static void setPropertyValues(Collection<?> targetObjects, String propertyName, Object value) {
        for (Object obj : targetObjects) {
            setPropertyValue(obj, propertyName, value);
        }
    }

    public static <T> T getPropertyValue(Object object, String propertyName, Class<T> clazz) {
        return getPropertyValue(object, propertyName);
    }

    public static <T> T getPropertyValue(Object object, String propertyName) {
        T returnValue = null;

        if (object == null || propertyName == null) {
            log.error("The object or property name is null.");
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

            boolean hasSetter = false;
            Method setterMethod = null;
            for (String setterMethodName : setterMethodNames) {
                setterMethod = getMethod(clazz, setterMethodName, entry.getValue());

                hasSetter = hasSetter || setterMethod != null;

                if (hasSetter) {
                    beanProperty.setSetterMethod(setterMethod);
                    break;
                }
            }

            boolean hasGetter = false;
            Method getterMethod = null;
            for (String getterMethodName : getterMethodNames) {
                getterMethod = getMethod(clazz, getterMethodName);

                hasGetter = hasGetter || (getterMethod != null && getterMethod.getReturnType().equals(entry.getValue()));

                if (hasGetter) {
                    beanProperty.setGetterMethod(getterMethod);
                    break;
                }
            }
            returnValue.getProperties().put(entry.getKey(), beanProperty);
        }
        beanStructureCache.put(clazz.getName(), returnValue);
        return returnValue;
    }

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
}
