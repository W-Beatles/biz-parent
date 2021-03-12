package cn.waynechu.springcloud.common.http;

import cn.waynechu.springcloud.common.util.CollectionUtil;
import cn.waynechu.springcloud.common.util.StringUtil;
import org.springframework.util.StreamUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author zhuwei
 * @since 2018/11/6 18:51
 */
public class HttpUtil {
    public static final String HEADER_KEY_USER_AGENT = "User-Agent";
    public static final String HEADER_KEY_REFERER = "referer";
    public static final String HEADER_KEY_CONTENT_TYPE = "Content-Type";

    public static final String CHARSET_UTF8 = "utf-8";
    public static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";

    private HttpUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static Object getSession(HttpServletRequest request, String key) {
        Object returnValue = null;

        if (request.getSession() != null) {
            returnValue = request.getSession(true).getAttribute(key);
        }
        return returnValue;
    }

    public static void setSession(HttpServletRequest request, String key, Object value) {
        request.getSession(true).setAttribute(key, value);
    }

    public static String getCookieValue(HttpServletRequest request, String key) {
        String value = null;

        if (request != null && request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if (c.getName().equals(key)) {
                    value = c.getValue();
                    break;
                }
            }
        }
        return value;
    }

    public static void setCookie(HttpServletResponse response, Cookie sessionCookie) {
        response.addCookie(sessionCookie);
    }

    public static void setCookie(HttpServletResponse response, String name, String value) {
        response.addCookie(new Cookie(name, value));
    }

    public static void removeCookie(HttpServletResponse response, String cookieName) {
        Cookie removeCookie = new Cookie(cookieName, null);
        removeCookie.setMaxAge(0);
        response.addCookie(removeCookie);
    }

    public static void removeCookie(HttpServletResponse response, Cookie removeCookie) {
        removeCookie.setMaxAge(0);
        response.addCookie(removeCookie);
    }

    public static String getHost(HttpServletRequest request) {
        return "http://" + request.getHeader("Host");
    }

    public static String getReferer(HttpServletRequest request) {
        return request.getHeader(HEADER_KEY_REFERER);
    }

    public static String getRedr(HttpServletRequest request) {
        String redirectUrl = null;

        if ("get".equalsIgnoreCase(request.getMethod())) {
            redirectUrl = getRequestedUrl(request);
        } else {
            redirectUrl = request.getHeader(HEADER_KEY_REFERER);
        }
        return redirectUrl == null ? "" : redirectUrl;
    }

    public static String getValueFromRequest(HttpServletRequest request, String key) {
        String returnValue = null;

        returnValue = request.getParameter(key);
        if (StringUtil.isEmpty(returnValue)) {
            returnValue = request.getHeader(key);
        }
        return returnValue;
    }

    public static String urlEncode(String url, String charset) {
        if (url != null) {
            try {
                return URLEncoder.encode(url, charset);
            } catch (UnsupportedEncodingException e) {
                //
            }
        }
        return "";
    }

    public static String urlEncode(String url) {
        return urlEncode(url, CHARSET_UTF8);
    }

    public static String urlDecode(String url, String charset) {
        if (url != null) {
            try {
                return URLDecoder.decode(url, charset);
            } catch (UnsupportedEncodingException e) {
                //
            }
        }
        return "";
    }

    public static String urlDecode(String url) {
        return urlDecode(url, CHARSET_UTF8);
    }

    public static String getRequestedUrl(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder(16);

        stringBuilder.append(request.getRequestURL());

        if (!StringUtil.isEmpty(request.getQueryString())) {
            stringBuilder.append("?").append(request.getQueryString());
        }
        return stringBuilder.toString();
    }

    public static String getRequestedUriQueryString(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(request.getRequestURI());
        if (!StringUtil.isEmpty(request.getQueryString())) {
            stringBuilder.append("?").append(request.getQueryString());
        }
        return stringBuilder.toString();
    }

    public static String getUriQueryString(String url) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            URL urlObj = new URL(url);
            stringBuilder.append(urlObj.getFile());
        } catch (Exception e) {
            //
        }
        return stringBuilder.toString();
    }

    public static String getRequestedHostname(HttpServletRequest request, boolean includeProtocol) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            URL urlObj = new URL(request.getRequestURL().toString());

            if (includeProtocol) {
                stringBuilder.append(urlObj.getProtocol()).append("://");
            }

            stringBuilder.append(urlObj.getHost());
            if (urlObj.getPort() > 0) {
                stringBuilder.append(":").append(urlObj.getPort());
            }
        } catch (Exception e) {
            //
        }
        return stringBuilder.toString();
    }

    public static String getServerBaseUrl(HttpServletRequest request, String url) {
        return request.getServletContext().getRealPath(url);
    }

    public static Map<String, String> getQueryParameters(URL url) {
        return getQueryParameters(url.getQuery());
    }

    public static Map<String, String> getQueryParameters(String queryString) {
        Map<String, String> returnValue = new LinkedHashMap<>();

        if (queryString != null && queryString.length() > 0) {
            String[] queries = queryString.split("&");
            for (String q : queries) {
                int posit = q.indexOf("=");

                if (posit > 0) {
                    String key = q.substring(0, posit);
                    String value = null;

                    if (posit + 1 < q.length()) {
                        value = q.substring(posit + 1);
                    } else {
                        value = "";
                    }

                    returnValue.put(key, urlDecode(value));
                }
            }
        }
        return returnValue;
    }

    public static String generateQueryString(Map<String, String> queryParams) {
        StringBuilder returnValue = new StringBuilder();

        int i = 0;
        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            if (i > 0) {
                returnValue.append("&");
            }
            returnValue.append(entry.getKey()).append("=");

            if (entry.getValue() != null) {
                returnValue.append(urlEncode(entry.getValue()));
            }
            i++;
        }
        return returnValue.toString();
    }

    public static void writeJson(HttpServletResponse response, String jsonStr, String contentType) throws IOException {
        response.setContentType(contentType);

        try (PrintWriter pw = response.getWriter()) {
            pw.write(jsonStr);
        }
    }

    public static String buildAbsoluteUrl(HttpServletRequest request, String url) {
        return addParamsToUrl(request, url, null);
    }

    public static String addParamsToUrl(HttpServletRequest request, String url, Map<String, String> params) {
        String returnValue = null;

        if (!StringUtil.isEmpty(url)) {
            HttpUrl httpUrl = null;

            String formattedUrl = url.toLowerCase();

            if (formattedUrl.startsWith("http://") || formattedUrl.startsWith("https://")) {
                httpUrl = new HttpUrl(url);
            } else {
                httpUrl = new HttpUrl(getRequestedUrl(request));
                httpUrl.setFile(url);
            }
            if (!CollectionUtil.isNullOrEmpty(params)) {
                httpUrl.getQueryParams().putAll(params);
            }

            returnValue = httpUrl.fullUrl();
        } else {
            returnValue = url;
        }
        return returnValue;
    }

    public static String removeParamsFromUrl(HttpServletRequest request, String url, Map<String, String> params) {
        String returnValue = null;

        if (!StringUtil.isEmpty(url)) {
            HttpUrl httpUrl = null;

            String formattedUrl = url.toLowerCase();

            if (formattedUrl.startsWith("http://") || formattedUrl.startsWith("https://")) {
                httpUrl = new HttpUrl(url);
            } else {
                httpUrl = new HttpUrl(getRequestedUrl(request));
                httpUrl.setFile(url);
            }

            if (!CollectionUtil.isNullOrEmpty(params)) {
                for (String key : params.keySet()) {
                    httpUrl.getQueryParams().remove(key);
                }
            }
            returnValue = httpUrl.fullUrl();
        } else {
            returnValue = url;
        }
        return returnValue;
    }

    public static String readRequestRawContent(HttpServletRequest request) {
        return readRequestRawContent(request, headerContains(request, "Content-Encoding", "gzip"), CHARSET_UTF8);
    }

    public static String readRequestRawContent(HttpServletRequest request, boolean gzip, String charset) {
        String returnValue = null;
        try {
            if (gzip) {
                returnValue = StreamUtils.copyToString(new GZIPInputStream(request.getInputStream()),
                        Charset.forName(charset));
            } else {
                StringBuilder stringBuilder = new StringBuilder();

                BufferedReader reader = request.getReader();
                for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                    stringBuilder.append(line);
                }
                returnValue = stringBuilder.toString();
            }
        } catch (IOException e) {
            returnValue = null;
        }
        return returnValue;
    }

    public static boolean headerContains(HttpServletRequest request, String key, String value) {
        boolean gzip = false;

        if ((request.getHeader(key) != null && request.getHeader(key).contains(value))
                || (request.getHeader(key.toUpperCase()) != null
                && request.getHeader(key.toUpperCase()).contains(value))
                || (request.getHeader(key.toLowerCase()) != null
                && request.getHeader(key.toLowerCase()).contains(value))) {
            gzip = true;
        }
        return gzip;
    }

    public static byte[] compressContent(String content, Charset charset) throws IOException {
        if (content == null || content.length() == 0) {
            return new byte[0];
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream);

        gzipOutputStream.write(content.getBytes(charset.name()));

        gzipOutputStream.flush();
        gzipOutputStream.close();

        return outputStream.toByteArray();
    }

    public static boolean isSpecialUserAgent(HttpServletRequest request, Collection<Pattern> specialUserAgentPatterns,
                                             boolean checkNullOrEmpty) {
        boolean returnValue = false;

        String userAgent = HttpUtil.getValueFromRequest(request, HEADER_KEY_USER_AGENT);
        if (StringUtil.isEmpty(userAgent)) {
            userAgent = HttpUtil.getValueFromRequest(request, HEADER_KEY_USER_AGENT.toLowerCase());
        }

        if (!StringUtil.isEmpty(userAgent)) {
            userAgent = userAgent.toLowerCase();

            for (Pattern userAgentPattern : specialUserAgentPatterns) {
                returnValue = userAgentPattern.matcher(userAgent).find();

                if (returnValue) {
                    break;
                }
            }
        } else if (checkNullOrEmpty) {
            returnValue = true;
        }
        return returnValue;
    }
}
