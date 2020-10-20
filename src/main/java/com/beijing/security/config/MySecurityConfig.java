package com.beijing.security.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 登录授权配置
 * @Author zc217
 * @Date 2020/10/20
 */
@EnableWebSecurity //内部添加了@Configuration注解
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 定义认证规则
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //遇到的问题 There is no PasswordEncoder mapped for the id "null"
        // s参考https://www.cnblogs.com/bjlhx/p/9878450.html
//        auth.inMemoryAuthentication().withUser("zhangsan").password("123").roles("VIP1","VIP2").and()//报错
//        auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance()).withUser("zhangsan").password("123").roles("VIP1","VIP2").and() // 无加密方式(或者注入bean  如CustomePasswordEncoder类）
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("zhangsan").password(new BCryptPasswordEncoder().encode("123")).roles("VIP1","VIP2").and()//指定加密
                .passwordEncoder(new BCryptPasswordEncoder()).withUser("lisi").password("123").password(new BCryptPasswordEncoder().encode("123")).roles("VIP2","VIP3").and()
                .passwordEncoder(new BCryptPasswordEncoder()).withUser("wangwu").password("123").password(new BCryptPasswordEncoder().encode("123")).roles("VIP1","VIP3");
    }

    /**
     * 授权规则
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 定制请求规则
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("VIP1")
                .antMatchers("/level2/**").hasRole("VIP2")
                .antMatchers("/level3/**").hasRole("VIP3");

        //开启自动配置 登录功能 (如果没有登录权限，就会来到spring security登录页面http://localhost:8080/login）
        //1. The most basic configuration defaults to automatically generating a login page at the URL "/login"
        //2. redirecting to "/login?error" for authentication failure.
//        http.formLogin();
        //3. 跳转到自己的登录也  防止直接访问资源 http://localhost:8080/level1/1
        http.formLogin()
                .usernameParameter("user")
                .passwordParameter("pwd")
                .loginPage("/userlogin");
//                .loginProcessingUrl("/login");

        //开启自动配置 注销功能
        // The default is that accessing the URL "/logout" will log the user out by invalidating the HTTP Session
        // 默认注销成功后会返回login?logout页面
        // 定制规则logoutSuccessUrl("/")
        http.logout().logoutSuccessUrl("/");

        //开启记住我功能cookie14天,点击注销会删除cookie
        http.rememberMe()
                .rememberMeParameter("remember");//自定义提交页面中给记住我添加和定义参数
    }


}
