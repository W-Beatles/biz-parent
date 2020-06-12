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
     * 断开连接
     */
    void disconnect();

    /**
     * 是否正在连接
     *
     * @return true if connecting
     */
    boolean isConnecting();

}
