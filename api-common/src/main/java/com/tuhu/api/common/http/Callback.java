/**
 * CopyRight ShellColr.com 2013.
 */

package com.tuhu.typical.common.http;

/**
 * Description:
 * Author: <a mailto="yinpengyi@gmail.com">Yin Pengyi</a>
 * DateTime: 10/30/13 4:43 PM
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
     * @param exception
     */
    void exception(Exception exception);
}
