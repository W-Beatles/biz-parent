package com.tuhu.typical.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Jackson的简单封装.
 */
public class JsonBinder {
    private static Map<JsonInclude.Include, JsonBinder> mappers = new HashMap<>();

    private ObjectMapper mapper;

    public JsonBinder(JsonInclude.Include inclusion) {
        mapper = new ObjectMapper();

        // 设置输出包含的属性
        SerializationConfig config = mapper.getSerializationConfig();
        config.withPropertyInclusion(config.getDefaultPropertyInclusion().withValueInclusion(inclusion));

        // 设置输入时忽略JSON字符串中存在而Java对象实际没有的属性
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
    }

    private static synchronized JsonBinder createBinder(JsonInclude.Include inclusion) {
        JsonBinder returnValue = mappers.get(inclusion);
        if (returnValue == null) {
            returnValue = new JsonBinder(inclusion);
            mappers.put(inclusion, returnValue);
        }

        return returnValue;
    }

    /**
     * 创建输出全部属性到Json字符串的Binder.
     */
    public static JsonBinder buildNormalBinder() {
        return createBinder(JsonInclude.Include.ALWAYS);
    }

    /**
     * 创建只输出非空属性到Json字符串的Binder.
     */
    public static JsonBinder buildNonNullBinder() {
        return createBinder(JsonInclude.Include.NON_NULL);
    }

    /**
     * 创建只输出非空属性到Json字符串的Binder.
     */
    public static JsonBinder buildNonEmptyBinder() {
        return createBinder(JsonInclude.Include.NON_EMPTY);
    }

    /**
     * 创建只输出初始值被改变的属性到Json字符串的Binder.
     */
    public static JsonBinder buildNonDefaultBinder() {
        return createBinder(JsonInclude.Include.NON_DEFAULT);
    }

    /**
     * 如果JSON字符串为Null或"null"字符串,返回Null.
     * 如果JSON字符串为"[]",返回空集合.
     * <p>
     * 如需读取集合如List/Map,且不是List<String>这种简单类型时使用如下语句:
     * List<MyBean> beanList = binder.getMapper().readValue(listString, new TypeReference<List<MyBean>>() {});
     */
    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if (StringUtil.isNullOrEmpty(jsonString)) {
            return null;
        }

        try {
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            return null;
        }
    }

    public <T> T fromJson(String jsonString, TypeReference<T> typeReference) {
        if (StringUtil.isNullOrEmpty(jsonString)) {
            return null;
        }

        try {
            return mapper.readValue(jsonString, typeReference);
        } catch (IOException e) {

            return null;
        }
    }

    public <T> List<T> fromJsonList(String jsonString, Class<T> clazz) {
        if (StringUtil.isNullOrEmpty(jsonString)) {
            return null;
        }

        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, clazz);
            return mapper.readValue(jsonString, javaType);
        } catch (Exception e) {
            //
        }
        return null;
    }

    /**
     * 如果对象为Null,返回"null".
     * 如果集合为空集合,返回"[]".
     */
    public String toJson(Object object) {
        if (object != null) {
            try {
                return mapper.writeValueAsString(object);
            } catch (IOException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 设置转换日期类型的format pattern,如果不设置默认打印Timestamp毫秒数.
     */
    public void setDateFormat(String pattern) {
        if (!StringUtil.isNullOrEmpty(pattern)) {
            DateFormat dateFormat = new SimpleDateFormat(pattern);

            mapper.getSerializationConfig().with(dateFormat);
            mapper.getDeserializationConfig().with(dateFormat);
        }
    }

    /**
     * 取出Mapper做进一步的设置或使用其他序列化API.
     */
    public ObjectMapper getMapper() {
        return mapper;
    }
}
