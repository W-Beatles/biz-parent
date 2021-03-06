package cn.waynechu.springcloud.common.http;

import cn.waynechu.springcloud.common.util.JsonBinder;
import cn.waynechu.springcloud.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zhuwei
 * @since 2018/11/6 18:51
 */
public class HttpUrl {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUrl.class);

    private String raw;
    private URL url;

    //HOST:www.xxx.com
    //FILE:/aaa/bbb.xml?q=l&ZH_PATTERN=o
    //PATH:/aaa/bbb.xml
    //AUTH:www.zhaolr.com:8000
    //PORT:8000
    //PROTOCOL:http
    //QUERY:q=l&ZH_PATTERN=o
    //REF:ref001
    //USERINFO:null

    private String protocol;
    private String host;
    private int port;
    private String path;
    private String query;
    private String ref;

    private Map<String, String> queryParams = new LinkedHashMap<>();

    public HttpUrl(String raw) {
        this.raw = raw;
        parseRaw();
    }

    public HttpUrl(URL url) {
        this.url = url;
        parseUrl();
    }

    public void setFile(String file) {
        if (file.startsWith("/")) {
            raw = urlOnlyHostPort() + file;
            parseRaw();
        } else {
            raw = urlOnlyHostPort() + path.substring(0, path.lastIndexOf("/")) + "/" + file;
            parseRaw();
        }
    }

    private void parseRaw() {
        try {
            url = new URL(raw);

            protocol = url.getProtocol();
            host = url.getHost();
            port = url.getPort();
            path = url.getPath();
            query = url.getQuery();
            ref = url.getRef();

            queryParams = HttpUtil.getQueryParameters(query);
        } catch (Exception e) {
            LOGGER.error("Parse url raw error.", e);
        }
    }

    private void parseUrl() {
        try {
            raw = url.toString();

            protocol = url.getProtocol();
            host = url.getHost();
            port = url.getPort();
            path = url.getPath();
            query = url.getQuery();
            ref = url.getRef();

            path = StringUtil.isEmpty(path) ? "/" : path;
            queryParams = HttpUtil.getQueryParameters(query);
        } catch (Exception e) {
            LOGGER.error("Parse url error.", e);
        }
    }

    public String fullUrl() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(protocol).append("://").append(host);
        if (port != 80 && port != -1 && "http".equals(protocol)) {
            stringBuilder.append(":").append(port);
        } else if (port != 443 && port != -1 && "https".equals(protocol)) {
            stringBuilder.append(":").append(port);
        }

        stringBuilder.append(path);

        if (queryParams.size() > 0) {
            stringBuilder.append("?").append(HttpUtil.generateQueryString(queryParams));
        }

        if (!StringUtil.isEmpty(ref)) {
            stringBuilder.append("#").append(ref);
        }

        return stringBuilder.toString();
    }

    public String urlOnlyHostPort() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(protocol).append("://").append(host);
        if (port != 80 && port != -1 && "http".equals(protocol)) {
            stringBuilder.append(":").append(port);
        } else if (port != 443 && port != -1 && "https".equals(protocol)) {
            stringBuilder.append(":").append(port);
        }

        return stringBuilder.toString();
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(Map<String, String> queryParams) {
        this.queryParams = queryParams;
    }

    @Override
    public String toString() {
        return JsonBinder.buildNonDefaultBinder().toJsonString(this);
    }
}
