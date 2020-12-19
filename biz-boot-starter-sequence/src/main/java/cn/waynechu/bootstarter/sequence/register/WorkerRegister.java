package cn.waynechu.bootstarter.sequence.register;

/**
 * @author zhuwei
 * @since 2020/6/11 20:09
 */
public interface WorkerRegister {

    /**
     * 初始化
     */
    void init();

    /**
     * 注册workerId
     *
     * @return 返回workId
     */
    int register(String bizTag);

    /**
     * 退出注册
     */
    void logout(String bizTag, Integer workerId);

    /**
     * 关闭
     */
    void close();
}
