package cn.waynechu.bootstarter.datasoureframe.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Code from com.alibaba:druid-spring-boot-starter:1.1.10
 *
 * @author lihengming [89921218@qq.com]
 */
@ConfigurationProperties("spring.datasource.dynamic.druid")
public class DruidStatProperties {
    private String[] aopPatterns;
    private StatViewServlet statViewServlet = new StatViewServlet();
    private WebStatFilter webStatFilter = new WebStatFilter();

    public String[] getAopPatterns() {
        return aopPatterns;
    }

    public void setAopPatterns(String[] aopPatterns) {
        this.aopPatterns = aopPatterns;
    }

    public StatViewServlet getStatViewServlet() {
        return statViewServlet;
    }

    public void setStatViewServlet(StatViewServlet statViewServlet) {
        this.statViewServlet = statViewServlet;
    }

    public WebStatFilter getWebStatFilter() {
        return webStatFilter;
    }

    public void setWebStatFilter(WebStatFilter webStatFilter) {
        this.webStatFilter = webStatFilter;
    }

    public static class StatViewServlet {
        /**
         * Enable StatViewServlet.
         */
        private boolean enabled = true;
        private String urlPattern;
        private String allow;
        private String deny;
        private String loginUsername;
        private String loginPassword;
        private String resetEnable;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getUrlPattern() {
            return urlPattern;
        }

        public void setUrlPattern(String urlPattern) {
            this.urlPattern = urlPattern;
        }

        public String getAllow() {
            return allow;
        }

        public void setAllow(String allow) {
            this.allow = allow;
        }

        public String getDeny() {
            return deny;
        }

        public void setDeny(String deny) {
            this.deny = deny;
        }

        public String getLoginUsername() {
            return loginUsername;
        }

        public void setLoginUsername(String loginUsername) {
            this.loginUsername = loginUsername;
        }

        public String getLoginPassword() {
            return loginPassword;
        }

        public void setLoginPassword(String loginPassword) {
            this.loginPassword = loginPassword;
        }

        public String getResetEnable() {
            return resetEnable;
        }

        public void setResetEnable(String resetEnable) {
            this.resetEnable = resetEnable;
        }
    }

    public static class WebStatFilter {
        /**
         * Enable WebStatFilter.
         */
        private boolean enabled = true;
        private String urlPattern;
        private String exclusions;
        private String sessionStatMaxCount;
        private String sessionStatEnable;
        private String principalSessionName;
        private String principalCookieName;
        private String profileEnable;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getUrlPattern() {
            return urlPattern;
        }

        public void setUrlPattern(String urlPattern) {
            this.urlPattern = urlPattern;
        }

        public String getExclusions() {
            return exclusions;
        }

        public void setExclusions(String exclusions) {
            this.exclusions = exclusions;
        }

        public String getSessionStatMaxCount() {
            return sessionStatMaxCount;
        }

        public void setSessionStatMaxCount(String sessionStatMaxCount) {
            this.sessionStatMaxCount = sessionStatMaxCount;
        }

        public String getSessionStatEnable() {
            return sessionStatEnable;
        }

        public void setSessionStatEnable(String sessionStatEnable) {
            this.sessionStatEnable = sessionStatEnable;
        }

        public String getPrincipalSessionName() {
            return principalSessionName;
        }

        public void setPrincipalSessionName(String principalSessionName) {
            this.principalSessionName = principalSessionName;
        }

        public String getPrincipalCookieName() {
            return principalCookieName;
        }

        public void setPrincipalCookieName(String principalCookieName) {
            this.principalCookieName = principalCookieName;
        }

        public String getProfileEnable() {
            return profileEnable;
        }

        public void setProfileEnable(String profileEnable) {
            this.profileEnable = profileEnable;
        }
    }
}
