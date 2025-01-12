package cn.bugstack.xfg.dev.tech.config;

import cn.bugstack.xfg.dev.tech.config.oauth2.OauthAccountUserDetailsService;
import cn.bugstack.xfg.dev.tech.config.valobj.AuthVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsUtils;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Lazy
    @Resource
    private OauthAccountUserDetailsService oauthAccountUserDetailsService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and().csrf().disable()
                .authorizeRequests()
                //处理跨域请求中的Preflight请求
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/oauth/**", "/user/**", "/actuator/**", "/error", "/open/api")
                .permitAll()
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(authenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler())
                .and().logout()
                .logoutSuccessHandler(logoutSuccessHandler())
                .deleteCookies("JSESSIONID")
                .and().httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 登录成功处理器
     */
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (httpServletRequest, httpServletResponse, authentication) -> {
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            AuthVO authVO = new AuthVO(HttpStatus.OK.value(), "hi login success!");
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(authVO));
        };
    }

    /**
     * 登出成功处理器
     */
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return (httpServletRequest, httpServletResponse, authentication) -> {
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            AuthVO authVO = new AuthVO(HttpStatus.OK.value(), "hi logout success!");
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(authVO));
        };
    }

    /**
     * 常规登录失败处理器
     */
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (httpServletRequest, httpServletResponse, e) -> {
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            AuthVO authVO = new AuthVO(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(authVO));
        };
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(oauthAccountUserDetailsService).passwordEncoder(passwordEncoder());
        auth.eraseCredentials(true);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
}
