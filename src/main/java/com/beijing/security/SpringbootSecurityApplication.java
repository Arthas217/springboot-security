package com.beijing.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * welcome页面认证和授权
 * 1. 引入spring security
 * 2. 编写配置、配置类MySecurityConfig 继承WebSecurityConfigurerAdapter
 * 3. 控制请求的访问权限 authorizeRequests、antMatchers、hasRole及登录页面formLogin
 * 4. 定义认证规则
 */
@SpringBootApplication
public class SpringbootSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootSecurityApplication.class, args);
    }

}
