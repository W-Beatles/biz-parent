package cn.waynechu.springcloud.common.http;

/**
 * @author zhuwei
 * @since 2018/11/6 18:51
 */
public interface Callback {
    /**
     * Call back method will be execute when the http request is returned.
     *
     * @param response the response of http request.
     */
    void execute(String response);

    /**
     * Callback the method when there is an exception when http connecting.
     *
     * @param exception e
     */
    void exception(Exception exception);
}
