package com.tuhu.typical.common.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;

/**
 *
 */
public class HttpConnectionUtil {
    public static final String CHARSET = "UTF-8";

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

            callback.execute(httpConnection.request(url, content, null, HttpConnection.MAX_TRY_TIMES,null).toString());
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
        return httpConnection.request(url, content, headerPairs, 1,method);
    }

    public static StringBuilder request(final String url, final List<NameValuePair> paramPairs, final List<NameValuePair> headerPairs, final RequestMethod method) throws IOException {
        HttpConnection httpConnection = new HttpConnection();

        return httpConnection.request(url, paramPairs, headerPairs, method, 1);
    }

    public static StringBuilder request(final String url, final List<NameValuePair> paramPairs, final List<NameValuePair> headerPairs, int retryTimes) throws IOException {
        HttpConnection httpConnection = new HttpConnection();

        return httpConnection.request(url, paramPairs, headerPairs, RequestMethod.POST, retryTimes);
    }

    //
    public static HttpUriRequest generateHttpRequest(final String url, final List<NameValuePair> paramPairs, List<NameValuePair> headerPairs, RequestMethod method) {
        try {
            //  the post method
            if (method.equals(RequestMethod.POST)) {
                HttpPost request = new HttpPost(url);

                if (paramPairs != null) {
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramPairs);
                    request.setEntity(entity);
                }

                request.setHeader("Content-Type", HttpUtil.CONTENT_TYPE_APPLICATION_JSON);

                //
                if (headerPairs != null && headerPairs.size() > 0) {
                    for (NameValuePair nameValuePair : headerPairs) {
                        if (nameValuePair != null) {
                            request.setHeader(nameValuePair.getName(), nameValuePair.getValue());
                        }
                    }
                }


                return request;
            } else {
                //the get or delete method
                StringBuilder urlBuffer = new StringBuilder(url);
                if (!url.contains("?")) {
                    urlBuffer.append("?");
                }

                if (paramPairs != null) {
                    for (NameValuePair nameValuePair : paramPairs) {
                        urlBuffer.append("&").append(nameValuePair.getName()).append("=").append(URLEncoder.encode(nameValuePair.getValue(), HttpUtil.CHARSET_UTF8));
                    }
                }

                //
                HttpGet request = new HttpGet(urlBuffer.toString());

                //
                if (headerPairs != null && headerPairs.size() > 0) {
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

    public static HttpUriRequest generateHttpRequest(final String url, final String content, String charset, List<NameValuePair> headerPairs,final RequestMethod method) {
        try {
        	if(RequestMethod.POST.equals(method)) {
        		 HttpPost request = new HttpPost(url);

                 StringEntity entity = new StringEntity(content, charset);
                 request.setEntity(entity);

                 request.setHeader("Content-Type", HttpUtil.CONTENT_TYPE_APPLICATION_JSON);

                 //
                 if (headerPairs != null && headerPairs.size() > 0) {
                     for (NameValuePair nameValuePair : headerPairs) {
                         if (nameValuePair != null) {
                             request.setHeader(nameValuePair.getName(), nameValuePair.getValue());
                         }
                     }
                 }

                 return request;
        	}else if(RequestMethod.PUT.equals(method)) {
        		HttpPut request = new HttpPut(url);

                StringEntity entity = new StringEntity(content, charset);
                request.setEntity(entity);

                request.setHeader("Content-Type", HttpUtil.CONTENT_TYPE_APPLICATION_JSON);

                //
                if (headerPairs != null && headerPairs.size() > 0) {
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

//    public static void main(String[] args) {
//
//        List<NameValuePair> headerPairs = new ArrayList<>();
//
//        NameValuePair nameValuePair = new BasicNameValuePair("RequestID", "9bdae5b24f534743b47de5b9c179e9ba");
//        headerPairs.add(nameValuePair);
//
//        NameValuePair nameValuePair2 = new BasicNameValuePair("RemoteName", "Tuhu.Service.ShopInsuranceClaimCore.Server");
//        headerPairs.add(nameValuePair2);
//
//        NameValuePair nameValuePair3 = new BasicNameValuePair("RemoteEndpoint", "DEPLOY-WCF9");
//        headerPairs.add(nameValuePair3);
//
//        String content = "{\n" +
//                "  \"RecId\": 2300,\n" +
//                "  \"InsuranceClaimId\": 0,\n" +
//                "  \"Creator\": \"panxf\",\n" +
//                "  \"ShopId\": 38\n" +
//                "}";
//        try {
//            StringBuilder builder=   HttpConnectionUtil.request("http://172.16.20.189:9237/ShopInsuranceClaimCore/ShopInsuranceClaim/CreateShopReceiveInsuranceClaimMapping", content, headerPairs);
//
//       System.out.println(builder.toString());
//        } catch (Exception e) {
//
//        }
//    }
}
