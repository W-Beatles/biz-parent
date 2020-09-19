package cn.waynechu.springcloud.common.http;

import org.apache.http.NameValuePair;

import java.io.IOException;
import java.util.List;

/**
 * @author zhuwei
 * @since 2018/11/6 18:51
 */
public class HttpConnectionUtil {

    ////////////////////////////////////////
    // sync APIs
    ////////////////////////////////////////
    public static void syncRequest(final String url, final RequestMethod method, final Callback callback) {
        syncRequest(url, null, null, method, callback);
    }

    public static void syncRequest(final String url, final List<NameValuePair> paramPairs, final List<NameValuePair> headerPairs, final RequestMethod method, final Callback callback) {
        try {
            HttpConnection httpConnection = new HttpConnection();

            callback.execute(httpConnection.request(url, paramPairs, headerPairs, method, HttpConnection.MAX_TRY_TIMES).toString());
        } catch (IOException e) {
            callback.exception(e);
        }
    }

    public static void syncRequest(final String url, final String content, final Callback callback) {
        try {
            HttpConnection httpConnection = new HttpConnection();

            callback.execute(httpConnection.request(url, content, null, HttpConnection.MAX_TRY_TIMES, null).toString());
        } catch (IOException e) {
            callback.exception(e);
        }
    }

    ////////////////////////////////////////
    // request APIs
    ////////////////////////////////////////
    public static StringBuilder request(final String url) throws IOException {
        HttpConnection httpConnection = new HttpConnection();

        return httpConnection.request(url, null, null, RequestMethod.GET, 1);
    }

    public static StringBuilder request(final String url, final List<NameValuePair> paramPairs, final List<NameValuePair> headerPairs) throws IOException {
        HttpConnection httpConnection = new HttpConnection();

        return httpConnection.request(url, paramPairs, headerPairs, RequestMethod.POST, 1);
    }

    public static StringBuilder request(final RequestMethod method, final String url, final String content, List<NameValuePair> headerPairs) throws IOException {
        HttpConnection httpConnection = new HttpConnection();
        return httpConnection.request(url, content, headerPairs, 1, method);
    }

    public static StringBuilder request(final String url, final List<NameValuePair> paramPairs, final List<NameValuePair> headerPairs, final RequestMethod method) throws IOException {
        HttpConnection httpConnection = new HttpConnection();

        return httpConnection.request(url, paramPairs, headerPairs, method, 1);
    }

    public static StringBuilder request(final String url, final List<NameValuePair> paramPairs, final List<NameValuePair> headerPairs, int retryTimes) throws IOException {
        HttpConnection httpConnection = new HttpConnection();

        return httpConnection.request(url, paramPairs, headerPairs, RequestMethod.POST, retryTimes);
    }
}
