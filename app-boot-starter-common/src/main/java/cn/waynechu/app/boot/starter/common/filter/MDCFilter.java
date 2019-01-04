package cn.waynechu.app.boot.starter.common.filter;

import cn.waynechu.app.boot.starter.common.util.MDCUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

/**
 * @author zhuwei
 * @date 2019/1/4 15:10
 */
public class MDCFilter implements Filter {
    private String mdcPrefix;
    private static final String TRACE_NO = "traceNo";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            HttpServletRequest httpReq = (HttpServletRequest) request;
            String traceNo = httpReq.getHeader(TRACE_NO);

            // reqKey格式：traceNo_mdcPrefix-shortJavaUUID-localHostName
            this.initMDC(traceNo);

            chain.doFilter(request, response);
        } finally {
            MDCUtil.clear();
        }
    }

    private void initMDC(String traceNo) {
        StringBuilder builder = new StringBuilder();
        // append traceNo
        if (StringUtils.isNotBlank(traceNo)) {
            builder.append(traceNo).append("-");
        }
        // append mdcPrefix
        if (StringUtils.isNotBlank(this.mdcPrefix)) {
            builder.append(this.mdcPrefix).append("-");
        }
        // append shortJavaUUID
        builder.append(generateShortJavaUUID());

        MDCUtil.addObjects(builder.toString());
        MDCUtil.addLocalHostName();
    }

    public void setMdcPrefix(String mdcPrefix) {
        if (StringUtils.isNotBlank(mdcPrefix)) {
            this.mdcPrefix = mdcPrefix;
        }
    }

    /**
     * 生成随机UUID的前6位
     * <p>
     * 10000次内约有五次左右重复
     *
     * @return uuid
     */
    private static String generateShortJavaUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);
    }
}
