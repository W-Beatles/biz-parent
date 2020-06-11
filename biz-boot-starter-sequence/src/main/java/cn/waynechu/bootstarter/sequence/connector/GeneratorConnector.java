package cn.waynechu.bootstarter.sequence.connector;

/**
 * @author zhuwei
 * @since 2020/6/11 15:32
 */
public interface GeneratorConnector {

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
