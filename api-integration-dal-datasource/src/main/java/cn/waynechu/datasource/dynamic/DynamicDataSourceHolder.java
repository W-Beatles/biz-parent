package cn.waynechu.datasource.dynamic;

/**
 * @author zhuwei
 * @date 2018/11/7 14:03
 */
public class DynamicDataSourceHolder {

    private static ThreadLocal<String> contextHolder = new ThreadLocal<>();
    static final String DATASOURCE_TYPE_MASTER = "master";
    static final String DATASOURCE_TYPE_SALVE = "salve";

    private DynamicDataSourceHolder() {
    }

    static String getDataSourceType() {
        return contextHolder.get();
    }

    static void setDataSourceType(String type) {
        contextHolder.set(type);
    }

    static void clearDataSourceType() {
        contextHolder.remove();
    }
}
