package com.sun.snow.module.system.config;


import com.sun.snow.module.system.filter.JWTLoginFilter;
import com.sun.snow.module.system.filter.JwtAuthenticationFilter;
import com.sun.snow.module.system.filter.AuthEntryPoint;
import com.sun.snow.module.system.filter.RestAuthenticationAccessDeniedHandler;
import com.sun.snow.module.system.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author: lish
 * @date: 2019/10/29 15:22
 * @description:security配置
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers("/user/hello").permitAll()
                .antMatchers("/user/logout").permitAll()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .anyRequest()
                .access("@rbacauthorityservice.hasPermission(request,authentication)")
                .and()
                .addFilter(new JWTLoginFilter(authenticationManager()))
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .exceptionHandling().accessDeniedHandler(new RestAuthenticationAccessDeniedHandler())
                .authenticationEntryPoint(new AuthEntryPoint());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
