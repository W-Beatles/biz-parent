package cn.waynechu.webcommon.session;

/**
 * @author zhuwei
 * @date 2018/12/24 14:10
 */
public class SessionHolder {

    private SessionHolder() {
        throw new IllegalStateException("Utility class");
    }

    private static final ThreadLocal<AccountSession> ACCOUNT_SESSION = new ThreadLocal<>();

    public static AccountSession getAccountSession() {
        return ACCOUNT_SESSION.get();
    }

    public static void setAccountSession(AccountSession session) {
        ACCOUNT_SESSION.set(session);
    }

    public static void removeAccountSession() {
        ACCOUNT_SESSION.remove();
    }
}
