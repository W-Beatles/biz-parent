package cn.waynechu.springcloud.common.util;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;

import static cn.waynechu.springcloud.common.constants.CommonConstants.DEFAULT_NAMESPACE;

/**
 * @author zhuwei
 * @since 2020/6/2 10:46
 */
public enum ApolloUtil {

    /**
     * default application property
     */
    APPLICATION_PROPERTY(DEFAULT_NAMESPACE);

    private volatile Config config;
    private String apolloNamespace;

    ApolloUtil(String apolloNamespace) {
        this.apolloNamespace = apolloNamespace;
        config = ConfigService.getConfig(apolloNamespace);
    }

    public Config getConfig() {
        return config;
    }

    public String getApolloNamespace() {
        return apolloNamespace;
    }

    /**
     * 获取配置属性,指定命名空间和默认值
     *
     * @param namespace    命名空间
     * @param key          键
     * @param defaultValue 默认值
     * @return 配置属性
     */
    public static String getProperty(String namespace, String key, String defaultValue) {
        Config config = ConfigService.getConfig(namespace);
        return config.getProperty(key, defaultValue);
    }

    /**
     * 获取配置属性,指定命名空间和默认值
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 配置属性
     */
    public  String getProperty(String key, String defaultValue) {
        return config.getProperty(key, defaultValue);
    }

    public void registryChangeListener(ConfigChangeListener listener) {
        config.addChangeListener(listener);
    }

    public void unRegistryChangeListener(ConfigChangeListener listener) {
        config.removeChangeListener(listener);
    }

}
