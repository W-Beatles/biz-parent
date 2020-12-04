package cn.waynechu.bootstarter.sequence.connector;

import java.io.Closeable;

/**
 * @author zhuwei
 * @since 2020/6/11 15:32
 */
public interface GeneratorConnector extends Closeable {

    /**
     * 初始化数据
     */
    void init();

    /**
     * 发起连接
     */
    void connect();

    /**
     * 是否连接成功
     *
     * @return true if connected
     */
    boolean isConnected();
}
