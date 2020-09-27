package cn.waynechu.oauth.config;

import cn.waynechu.oauth.provider.MobileAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author zhuwei
 * @since 2020-02-27 21:50
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService sysUserService;

    @Autowired
    private UserDetailsService mobileUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors()
                .and().authorizeRequests().anyRequest().permitAll()
                .and().formLogin(formLogin -> formLogin
                .loginPage("/login").permitAll())
                .logout(logout -> logout.logoutRequestMatcher(
                        new AntPathRequestMatcher("/logout") /* supports GET /logout */).permitAll());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/static/**");
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(sysUserService).passwordEncoder(passwordEncoder());
        authenticationManagerBuilder.authenticationProvider(mobileAuthenticationProvider());
    }

    @Bean
    public MobileAuthenticationProvider mobileAuthenticationProvider() {
        MobileAuthenticationProvider mobileAuthenticationProvider = new MobileAuthenticationProvider(this.mobileUserDetailsService);
        mobileAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return mobileAuthenticationProvider;
    }
}
