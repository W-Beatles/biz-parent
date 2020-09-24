package cn.waynechu.oauth.service;

import org.springframework.stereotype.Service;

/**
 * @author zhuwei
 * @since 2020/9/24 20:27
 */
@Service
public class SmsCodeService {

    /**
     * 获取验证码
     *
     * @param mobile       手机号
     * @param businessType 业务类型
     * @return 验证码
     */
    public String getSmsCode(String mobile, String businessType) {
        return "123456";
    }
}
