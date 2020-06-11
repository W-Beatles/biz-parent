package cn.waynechu.bootstarter.sequence.register;

/**
 * @author zhuwei
 * @since 2020/6/11 20:09
 */
public interface WorkerRegister {

    /**
     * 注册workerId
     *
     * @return 返回workId
     */
    long register();

    /**
     * 退出注册
     */
    void logout();

}
