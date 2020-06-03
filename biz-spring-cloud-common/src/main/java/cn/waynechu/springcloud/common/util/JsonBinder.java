package cn.waynechu.springcloud.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Jackson的简单封装
 *
 * @author zhuwei
 * @date 2019/1/2 17:53
 */
@Slf4j
public class JsonBinder {
    private static Map<JsonInclude.Include, JsonBinder> mappers = new EnumMap<>(JsonInclude.Include.class);

    private ObjectMapper mapper;

    private JsonBinder(JsonInclude.Include inclusion) {
        mapper = new ObjectMapper();

        // 设置序列化策略
        mapper.setSerializationInclusion(inclusion);

        // ---------- SerializationFeature ----------
        mapper.disable(SerializationFeature.FLUSH_AFTER_WRITE_VALUE);
        // 允许反序列化空对象
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // ---------- DeserializationFeature ----------
        // 设置反序列化时存在未知属性是否抛出异常
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        // ---------- JsonParser ----------
        // 支持解析注释
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        // 设置解析器支持解析单引号
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        // 设置ObjectMapper对大小写不敏感
        //mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    }

    private static synchronized JsonBinder createBinder(JsonInclude.Include inclusion) {
        return mappers.computeIfAbsent(inclusion, JsonBinder::new);
    }

    /**
     * 创建输出全部属性到Json字符串的Binder.
     */
    public static JsonBinder buildAlwaysBinder() {
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
    public <T> T parseObject(String jsonString, Class<T> clazz) {
        if (StringUtil.isEmpty(jsonString)) {
            return null;
        }

        try {
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            log.error("Json字符串转换Java对象失败，源[" + jsonString + "]", e);
            return null;
        }
    }

    public <T> T parseObject(String jsonString, TypeReference<T> typeReference) {
        if (StringUtil.isEmpty(jsonString)) {
            return null;
        }

        try {
            return mapper.readValue(jsonString, typeReference);
        } catch (IOException e) {
            log.error("Json字符串转换Java对象失败，源[" + jsonString + "]", e);
            return null;
        }
    }

    public <T> List<T> parseArray(String jsonString, Class<T> clazz) {
        if (StringUtil.isEmpty(jsonString)) {
            return Collections.emptyList();
        }

        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, clazz);
            return mapper.readValue(mapper.getFactory().createParser(jsonString), javaType);
        } catch (Exception e) {
            log.error("Json字符串转换Java列表失败，源[" + jsonString + "]", e);
            return Collections.emptyList();
        }
    }

    /**
     * Java对象转化为jsonStr
     * <p>
     * 如果对象为null，返回null
     * 如果集合为空集合，返回[]
     *
     * @param object 源对象
     * @return jsonStr
     */
    public String toJsonString(Object object) {
        return toJsonString(object, false);
    }

    /**
     * Java对象转化为格式化的jsonStr
     * <p>
     * 如果对象为null，返回null
     * 如果集合为空集合，返回[]
     *
     * @param object 源对象
     * @return 格式化的jsonStr
     */
    public String toPrettyJsonString(Object object) {
        return toJsonString(object, true);
    }

    /**
     * Java对象转化为JsonStr
     * <p>
     * 如果对象为null，返回null
     * 如果集合为空集合，返回[]
     */
    private String toJsonString(Object object, boolean isFormat) {
        if (object != null) {
            try {
                if (!isFormat) {
                    return mapper.writeValueAsString(object);
                } else {
                    return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
                }
            } catch (IOException e) {
                log.error("Java对象转换JsonStr失败", e);
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
        if (!StringUtil.isEmpty(pattern)) {
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
