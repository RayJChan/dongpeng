package com.dongpeng.eurekaserver;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 当Spring Security位于类路径中时，默认情况下会添加@enablewebsecurity。
 * 这会默认启用CSRF保护。如果添加@EnableWebSeurity，您将在1.5.10中遇到同样的问题。
 * 一种解决方法是，如果浏览器使用Eureka仪表板，这不是最安全的解决方法，那就是禁用CSRF保护。这可以通过将以下配置添加到您的应用程序来完成。
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.csrf().disable();
    }
}
