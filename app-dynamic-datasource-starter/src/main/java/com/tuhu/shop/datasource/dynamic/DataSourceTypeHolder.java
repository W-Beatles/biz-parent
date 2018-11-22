package com.tuhu.shop.datasource.dynamic;

/**
 * @author zhuwei
 * @date 2018/11/7 14:03
 */
public class DataSourceTypeHolder {
    private static ThreadLocal<String> contextHolder = new ThreadLocal<>();
    public static final String DATASOURCE_TYPE_MASTER = "master";
    public static final String DATASOURCE_TYPE_SALVE = "salve";

    private DataSourceTypeHolder() {
    }

    public static String getDataSourceType() {
        return contextHolder.get();
    }

    public static void setDataSourceType(String type) {
        contextHolder.set(type);
    }

    public static void clearDataSourceType() {
        contextHolder.remove();
    }
}
