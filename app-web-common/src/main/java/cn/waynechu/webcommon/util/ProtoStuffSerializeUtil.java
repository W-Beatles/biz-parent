package cn.waynechu.webcommon.util;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import lombok.experimental.UtilityClass;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhuwei
 * @date 2019/1/30 15:49
 */
@UtilityClass
public class ProtoStuffSerializeUtil {

    private static Map<Class<?>, RuntimeSchema<?>> cachedRuntimeSchema = new ConcurrentHashMap<>();

    /**
     * 获取相应类型的runtimeSchema
     *
     * @param clazz 类类型
     * @return runtimeSchema
     */
    @SuppressWarnings({"unchecked"})
    private <T> RuntimeSchema<T> getSchema(Class<T> clazz) {
        return (RuntimeSchema<T>) cachedRuntimeSchema.computeIfAbsent(clazz, RuntimeSchema::createFrom);
    }

    /**
     * 序列化对象
     *
     * @param obj 对象
     * @return byte[]
     */
    @SuppressWarnings("unchecked")
    public static <T> byte[] serialize(T obj) {
        if (obj == null) {
            throw new IllegalArgumentException("序列化对象不能为空!");
        }

        Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(obj.getClass());
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        byte[] protoStuff;
        try {
            protoStuff = ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } finally {
            buffer.clear();
        }
        return protoStuff;
    }

    /**
     * 反序列化对象
     *
     * @param bytes       byte[]
     * @param targetClass 目标类
     * @return 对象
     */
    public static <T> T deserialize(byte[] bytes, Class<T> targetClass) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }

        RuntimeSchema<T> schema = getSchema(targetClass);
        T instance = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(bytes, instance, schema);
        return instance;
    }

    /**
     * 序列化列表
     *
     * @param objList 对象列表
     * @return byte[]
     */
    @SuppressWarnings("unchecked")
    public static <T> byte[] serializeList(List<T> objList) {
        if (objList == null || objList.isEmpty()) {
            throw new IllegalArgumentException("序列化对象列表不能为空!");
        }
        Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(objList.get(0).getClass());
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        byte[] protoStuff;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            ProtostuffIOUtil.writeListTo(baos, objList, schema, buffer);
            protoStuff = baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("序列化对象列表发生异常!", e);
        } finally {
            buffer.clear();
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return protoStuff;
    }

    /**
     * 反序列化列表
     *
     * @param paramArrayOfByte byte[]
     * @param targetClass      目标类
     * @return 对象列表
     */
    public static <T> List<T> deserializeList(byte[] paramArrayOfByte, Class<T> targetClass) {
        if (paramArrayOfByte == null || paramArrayOfByte.length == 0) {
            throw new IllegalArgumentException("反序列化对象发生异常,byte序列为空!");
        }
        List<T> result;
        Schema<T> schema = RuntimeSchema.getSchema(targetClass);
        try {
            result = ProtostuffIOUtil.parseListFrom(new ByteArrayInputStream(paramArrayOfByte), schema);
        } catch (IOException e) {
            throw new RuntimeException("反序列化对象列表发生异常!", e);
        }
        return result;
    }
}
