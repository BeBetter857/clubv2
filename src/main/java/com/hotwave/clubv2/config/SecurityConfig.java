package com.hotwave.clubv2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Kyrie
 * @version 1.0.0
 * @Description
 * @date 2022-10-12 10:17:00
 */
@Configuration
public class SecurityConfig {

    @Autowired
    private AuthenticationConfiguration auth;

    // 创建BCryptPasswordEncoder并注入容器（密码加密的）
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // 用户表单提交过来的用户名和密码会被封装成对象委托类
    // 由AuthenticationManager 的验证方法 authenticate() 进行身份验证。
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return auth.getAuthenticationManager();
    }

}
