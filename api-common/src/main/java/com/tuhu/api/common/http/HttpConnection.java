package com.tuhu.typical.common.http;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.List;

/**
 *
 */
public class HttpConnection {
    //
    private static final Logger logger = LoggerFactory.getLogger(HttpConnection.class);

    //the connection max try times.
    public static final int MAX_TRY_TIMES = 3;
    public static final int RETRY_WAIT_MSECS = 5000;
    public static final int MAX_REDIRECT_TIMES = 10;

    //
    private int redirectTimes = 0;

    //
    public HttpConnection() {
    }

    public StringBuilder request(final String url, final List<NameValuePair> paramPairs, final List<NameValuePair> headerPairs, final RequestMethod method, int retryTimes) throws IOException {
        StringBuilder returnBuffer = new StringBuilder();

        if (retryTimes < 1) {
            retryTimes = 1;
        }

        if (retryTimes > MAX_TRY_TIMES) {
            retryTimes = MAX_TRY_TIMES;
        }

        //
        int triedTimes = 0;
        boolean success = false;

        do {
            BufferedReader reader = null;

            try {
                //new the client
                HttpClient client = HttpClients.createMinimal();

                //generate the request.
                HttpUriRequest request = HttpConnectionUtil.generateHttpRequest(url, paramPairs, headerPairs, method);

                HttpResponse response = client.execute(request);

                //
                int statusCode = response.getStatusLine().getStatusCode();

                //
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

                            //
                            return request(redirectUrl, paramPairs, headerPairs, method, triedTimes);
                        }
                    } else {
                        logger.error("HttpConnection", "loop redirect");
                    }
                }

                //
                success = true;
            } catch (ClientProtocolException e) {
                success = false;

                logger.error("HttpConnection", e.getMessage(), e);

                if (triedTimes >= (retryTimes - 1)) {
                    throw e;
                }
            } catch (UnknownHostException e) {
                success = false;

                logger.error("HttpConnection", e.getMessage(), e);

                if (triedTimes >= (retryTimes - 1)) {
                    throw e;
                }
            } catch (IOException e) {
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
                    //ignore me
                }
            }

            //
            triedTimes++;

            //
            if (!success && triedTimes < retryTimes) {
                try {
                    Thread.sleep(RETRY_WAIT_MSECS);
                } catch (InterruptedException e) {
                    //
                }
            }
        } while (!success && triedTimes < retryTimes);

        //
        return returnBuffer;
    }

    //
    public StringBuilder request(final String url, final String content, final List<NameValuePair> headerPairs, int retryTimes,final RequestMethod method) throws IOException {
        StringBuilder returnBuffer = new StringBuilder();

        if (retryTimes < 1) {
            retryTimes = 1;
        }

        if (retryTimes > MAX_TRY_TIMES) {
            retryTimes = MAX_TRY_TIMES;
        }

        //
        int triedTimes = 0;
        boolean success = false;

        do {
            BufferedReader reader = null;

            try {
                //new the client
                HttpClient client = HttpClients.createMinimal();

                //generate the request.
                HttpUriRequest request = HttpConnectionUtil.generateHttpRequest(url, content, HttpUtil.CHARSET_UTF8, headerPairs, method);
                HttpResponse response = client.execute(request);

                //
                int statusCode = response.getStatusLine().getStatusCode();

                //
                if (HttpStatus.SC_OK == statusCode) {
                    reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), HttpUtil.CHARSET_UTF8));
                    returnBuffer = new StringBuilder();

                    //
                    for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                        returnBuffer.append(line);
                    }
                } else if (HttpStatus.SC_MOVED_TEMPORARILY == statusCode ||
                        HttpStatus.SC_MOVED_PERMANENTLY == statusCode ||
                        HttpStatus.SC_TEMPORARY_REDIRECT == statusCode) {
                    if (redirectTimes < MAX_REDIRECT_TIMES) {
                        String redirectUrl = null;

                        //
                        Header[] headers = response.getHeaders("location");
                        for (Header header : headers) {
                            redirectUrl = header.getValue();
                        }

                        //
                        if (redirectUrl != null) {
                            redirectTimes++;
                            //
                            return request(redirectUrl, content, headerPairs, triedTimes,method);
                        }
                    } else {
                        logger.error("HttpConnection", "loop redirect");
                    }
                }

                //
                success = true;
            } catch (ClientProtocolException e) {
                success = false;

                logger.error("HttpConnection", e.getMessage(), e);

                if (triedTimes >= (retryTimes - 1)) {
                    throw e;
                }
            } catch (UnknownHostException e) {
                success = false;

                logger.error("HttpConnection", e.getMessage(), e);

                if (triedTimes >= (retryTimes - 1)) {
                    throw e;
                }
            } catch (IOException e) {
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
                    //ignore me
                }
            }

            //
            triedTimes++;

            //
            if (!success && triedTimes < retryTimes) {
                try {
                    Thread.sleep(RETRY_WAIT_MSECS);
                } catch (InterruptedException e) {
                    //
                }
            }
        } while (!success && triedTimes < retryTimes);

        //
        return returnBuffer;
    }
}
