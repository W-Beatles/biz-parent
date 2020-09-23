package cn.waynechu.oauth.service;

import cn.waynechu.oauth.entity.SysUserDO;
import cn.waynechu.oauth.mapper.oauth.SysUserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuwei
 * @since 2020/9/19 17:50
 */
@Service
public class SysUserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    public SysUserDO getByUserName(String username) {
        QueryWrapper<SysUserDO> wrapper = new QueryWrapper<>();
        wrapper.eq(SysUserDO.COL_USERNAME, username);
        return sysUserMapper.selectOne(wrapper);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String role = "ROLE_ADMIN";
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        SysUserDO sysUserDO = this.getByUserName(username);
        if (sysUserDO == null) {
            throw new UsernameNotFoundException(username);
        }

        return User.withUsername(username)
                .password(sysUserDO.getPassword())
                .authorities(authorities).build();
    }
}
