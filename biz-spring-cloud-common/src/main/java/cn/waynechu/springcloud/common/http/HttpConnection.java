package cn.waynechu.springcloud.common.http;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author zhuwei
 * @date 2018/11/6 18:51
 */
public class HttpConnection {
    private static final Logger logger = LoggerFactory.getLogger(HttpConnection.class);

    public static final int MAX_TRY_TIMES = 3;
    public static final int RETRY_WAIT_MSECS = 5000;
    public static final int MAX_REDIRECT_TIMES = 10;

    private int redirectTimes = 0;

    public StringBuilder request(final String url, final List<NameValuePair> paramPairs, final List<NameValuePair> headerPairs, final RequestMethod method, int retryTimes) throws IOException {
        StringBuilder returnBuffer = new StringBuilder();

        if (retryTimes < 1) {
            retryTimes = 1;
        }

        if (retryTimes > MAX_TRY_TIMES) {
            retryTimes = MAX_TRY_TIMES;
        }

        int triedTimes = 0;
        boolean success = false;

        do {
            BufferedReader reader = null;

            try {
                HttpClient client = HttpClients.createMinimal();

                HttpUriRequest request = generateHttpRequest(url, paramPairs, headerPairs, method);

                HttpResponse response = client.execute(request);

                int statusCode = response.getStatusLine().getStatusCode();

                if (HttpStatus.SC_OK == statusCode) {
                    reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), HttpUtil.CHARSET_UTF8));
                    returnBuffer = new StringBuilder();

                    for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                        returnBuffer.append(line);
                    }
                } else if (HttpStatus.SC_MOVED_TEMPORARILY == statusCode ||
                        HttpStatus.SC_MOVED_PERMANENTLY == statusCode ||
                        HttpStatus.SC_TEMPORARY_REDIRECT == statusCode) {
                    if (redirectTimes < MAX_REDIRECT_TIMES) {
                        String redirectUrl = null;
                        Header[] headers = response.getHeaders("location");
                        for (Header header : headers) {
                            redirectUrl = header.getValue();
                        }

                        if (redirectUrl != null) {
                            redirectTimes++;
                            return request(redirectUrl, paramPairs, headerPairs, method, triedTimes);
                        }
                    } else {
                        logger.error("HttpConnection loop redirect.");
                    }
                }
                success = true;
            } catch (Exception e) {
                success = false;

                logger.error("HttpConnection", e.getMessage(), e);

                if (triedTimes >= (retryTimes - 1)) {
                    throw e;
                }
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    //
                }
            }
            triedTimes++;

            if (!success && triedTimes < retryTimes) {
                try {
                    Thread.sleep(RETRY_WAIT_MSECS);
                } catch (InterruptedException e) {
                    //
                }
            }
        } while (!success && triedTimes < retryTimes);
        return returnBuffer;
    }

    public StringBuilder request(final String url, final String content, final List<NameValuePair> headerPairs, int retryTimes, final RequestMethod method) throws IOException {
        StringBuilder returnBuffer = new StringBuilder();

        if (retryTimes < 1) {
            retryTimes = 1;
        }

        if (retryTimes > MAX_TRY_TIMES) {
            retryTimes = MAX_TRY_TIMES;
        }

        int triedTimes = 0;
        boolean success = false;

        do {
            BufferedReader reader = null;

            try {
                HttpClient client = HttpClients.createMinimal();

                HttpUriRequest request = generateHttpRequest(url, content, HttpUtil.CHARSET_UTF8, headerPairs, method);
                HttpResponse response = client.execute(request);

                int statusCode = response.getStatusLine().getStatusCode();

                if (HttpStatus.SC_OK == statusCode) {
                    reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), HttpUtil.CHARSET_UTF8));
                    returnBuffer = new StringBuilder();

                    for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                        returnBuffer.append(line);
                    }
                } else if (HttpStatus.SC_MOVED_TEMPORARILY == statusCode ||
                        HttpStatus.SC_MOVED_PERMANENTLY == statusCode ||
                        HttpStatus.SC_TEMPORARY_REDIRECT == statusCode) {
                    if (redirectTimes < MAX_REDIRECT_TIMES) {
                        String redirectUrl = null;

                        Header[] headers = response.getHeaders("location");
                        for (Header header : headers) {
                            redirectUrl = header.getValue();
                        }

                        if (redirectUrl != null) {
                            redirectTimes++;
                            return request(redirectUrl, content, headerPairs, triedTimes, method);
                        }
                    } else {
                        logger.error("HttpConnection loop redirect.");
                    }
                }
                success = true;
            } catch (Exception e) {
                success = false;
                logger.error("HttpConnection", e.getMessage(), e);

                if (triedTimes >= (retryTimes - 1)) {
                    throw e;
                }
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    //
                }
            }
            triedTimes++;

            if (!success && triedTimes < retryTimes) {
                try {
                    Thread.sleep(RETRY_WAIT_MSECS);
                } catch (InterruptedException e) {
                    //
                }
            }
        } while (!success && triedTimes < retryTimes);

        return returnBuffer;
    }


    public static HttpUriRequest generateHttpRequest(final String url, final List<HttpEntity> entities) {
        //  the post method
        HttpPost request = new HttpPost(url);

        if (entities != null) {
            for (HttpEntity entity : entities) {
                request.setEntity(entity);
            }
        }
        return request;
    }

    public static HttpUriRequest generateHttpRequest(final String url, final List<NameValuePair> paramPairs, List<NameValuePair> headerPairs, RequestMethod method) {
        try {
            //  the post method
            if (method.equals(RequestMethod.POST)) {
                HttpPost request = new HttpPost(url);

                if (paramPairs != null) {
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramPairs);
                    request.setEntity(entity);
                }

                request.setHeader(HttpUtil.HEADER_KEY_CONTENT_TYPE, HttpUtil.CONTENT_TYPE_APPLICATION_JSON);

                if (!headerPairs.isEmpty()) {
                    for (NameValuePair nameValuePair : headerPairs) {
                        if (nameValuePair != null) {
                            request.setHeader(nameValuePair.getName(), nameValuePair.getValue());
                        }
                    }
                }
                return request;
            }
            // the get or delete method
            else {
                StringBuilder urlBuffer = new StringBuilder(url);
                if (!url.contains("?")) {
                    urlBuffer.append("?");
                }

                if (paramPairs != null) {
                    for (NameValuePair nameValuePair : paramPairs) {
                        urlBuffer.append("&").append(nameValuePair.getName()).append("=").append(URLEncoder.encode(nameValuePair.getValue(), HttpUtil.CHARSET_UTF8));
                    }
                }

                HttpGet request = new HttpGet(urlBuffer.toString());
                if (!headerPairs.isEmpty()) {
                    for (NameValuePair nameValuePair : headerPairs) {
                        if (nameValuePair != null) {
                            request.setHeader(nameValuePair.getName(), nameValuePair.getValue());
                        }
                    }
                }
                return request;
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static HttpUriRequest generateHttpRequest(final String url, final String content, String charset, List<NameValuePair> headerPairs, final RequestMethod method) {
        try {
            if (RequestMethod.POST.equals(method)) {
                HttpPost request = new HttpPost(url);

                StringEntity entity = new StringEntity(content, charset);
                request.setEntity(entity);

                request.setHeader(HttpUtil.HEADER_KEY_CONTENT_TYPE, HttpUtil.CONTENT_TYPE_APPLICATION_JSON);

                if (!headerPairs.isEmpty()) {
                    for (NameValuePair nameValuePair : headerPairs) {
                        if (nameValuePair != null) {
                            request.setHeader(nameValuePair.getName(), nameValuePair.getValue());
                        }
                    }
                }
                return request;
            } else if (RequestMethod.PUT.equals(method)) {
                HttpPut request = new HttpPut(url);

                StringEntity entity = new StringEntity(content, charset);
                request.setEntity(entity);
                request.setHeader(HttpUtil.HEADER_KEY_CONTENT_TYPE, HttpUtil.CONTENT_TYPE_APPLICATION_JSON);

                if (!headerPairs.isEmpty()) {
                    for (NameValuePair nameValuePair : headerPairs) {
                        if (nameValuePair != null) {
                            request.setHeader(nameValuePair.getName(), nameValuePair.getValue());
                        }
                    }
                }
                return request;
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return null;
    }
}
