package cn.waynechu.springcloud.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * @author zhuwei
 * @since 2019/5/9 10:08
 */
public class UUIDUtil {
    /**
     * 带-的uuid
     */
    private static final String LONG_UUID = "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";
    /**
     * 不带-的uuid
     */
    private static final String SHORT_UUID = "^[0-9a-f]{8}[0-9a-f]{4}[0-9a-f]{4}[0-9a-f]{4}[0-9a-f]{12}$";

    /**
     * UUID校验
     *
     * @param uuid uuid
     * @return 是uuid返回<code>true</code>,否则返回<code>false</code>
     */
    public static boolean isValidUUID(String uuid) {
        if (StringUtils.isBlank(uuid)) {
            return false;
        }
        return uuid.matches(LONG_UUID) || uuid.matches(SHORT_UUID);
    }

    /**
     * 获取去-的uuid
     *
     * @param uuid uuid
     * @return short uuid
     */
    public static String getShortUUID(String uuid) {
        if (!isValidUUID(uuid)) {
            return UUID.randomUUID().toString().replace("-", "");
        }
        return uuid.replace("-", "");
    }

    /**
     * 获取去-的uuid
     *
     * @return short uuid
     */
    public static String getShortUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取不去-的uuid
     *
     * @return uuid
     */
    public static String getRandomUUID() {
        return getRandomUUID(null);
    }

    /**
     * 获取uuid
     *
     * @param str str
     * @return uuid
     */
    public static String getRandomUUID(String str) {
        if (StringUtils.isBlank(str)) {
            return UUID.randomUUID().toString();
        } else {
            return UUID.nameUUIDFromBytes(str.getBytes()).toString();
        }
    }

    public static void main(String[] args) {
        String uuid1 = "e65deb0c-a110-49c8-a4ef-6e69447968d9";
        String uuid2 = "ca4a8a92-d4ed-4fc4-8a4f-345c587fbdcb";
        String uuid3 = "e1f15f1d-6edb-4f70-8a05465se273eaf95a";
        String uuid4 = "ca4a8a92d4ed4fcf8a4f345c587fbdcb";
        System.out.println("check > " + uuid1 + " > " + isValidUUID(uuid1));
        System.out.println("check > " + uuid2 + " > " + isValidUUID(uuid2));
        System.out.println("check > " + uuid3 + " > " + isValidUUID(uuid3));
        System.out.println("check > " + uuid4 + " > " + isValidUUID(uuid4));
        System.out.println("build a uuid > " + getRandomUUID(null));
        System.out.println("build a uuid > " + getRandomUUID(null));
        System.out.println("build a uuid > " + getRandomUUID("waynechu"));
        System.out.println("build a uuid > " + getRandomUUID("waynechu"));
        System.out.println("build a short uuid > " + getShortUUID(uuid1) + " > " + isValidUUID(getShortUUID(uuid1)));
        System.out.println("build a short uuid > " + getShortUUID(uuid4) + " > " + isValidUUID(getShortUUID(uuid4)));
        System.out.println("build a short uuid > " + getShortUUID(uuid3) + " > " + isValidUUID(getShortUUID(uuid3)));
        String shortUUID = getShortUUID();
        System.out.println("build a short uuid > " + shortUUID + " > " + isValidUUID(shortUUID));
    }
}
