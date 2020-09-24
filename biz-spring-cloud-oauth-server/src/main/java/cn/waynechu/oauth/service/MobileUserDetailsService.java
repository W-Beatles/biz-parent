package cn.waynechu.oauth.service;

import cn.waynechu.oauth.entity.SysUserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuwei
 * @since 2020/9/24 20:21
 */
@Service
public class MobileUserDetailsService implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SmsCodeService smsCodeService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String role = "ROLE_ADMIN";
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        SysUserDO sysUserDO = sysUserService.getByMobile(username);
        if (sysUserDO == null) {
            throw new UsernameNotFoundException(username);
        }

        // 如果为mobile模式，从短信服务中获取验证码（动态密码）
        String credentials = smsCodeService.getSmsCode(username, "LOGIN");

        return User.withUsername(username)
                .password(credentials)
                .disabled(Integer.valueOf(1).equals(sysUserDO.getUserStatus()))
                .authorities(authorities).build();
    }
}
