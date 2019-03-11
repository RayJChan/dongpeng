package com.dongpeng.common.config;

import com.dongpeng.common.filter.LogFilter;
import com.dongpeng.common.interceptor.LogInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.google.common.base.Strings;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RefreshScope
public class CommonConfig implements WebMvcConfigurer {

    @Value("${spring.redis.host}")
    private String redissonAddress;

    @Value("${spring.redis.password}")
    private String redissonPassword;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //配置日志拦截器，拦截所有url
        registry.addInterceptor(new LogInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public FilterRegistrationBean logFilterRegistration() {
        //新建过滤器注册类
        FilterRegistrationBean registration = new FilterRegistrationBean();
        // 添加日志记录过滤器
        registration.setFilter(new LogFilter());
        // 设置过滤器的URL模式
        registration.addUrlPatterns("/*");
        return registration;
    }

    @Bean
    public RedissonClient redisson() {
        Config config = new Config();
        if (Strings.isNullOrEmpty(redissonPassword)) {
            config.useSingleServer().setAddress("redis://" + redissonAddress + ":6379");
        } else {
            config.useSingleServer().setAddress("redis://" + redissonAddress + ":6379").setPassword(redissonPassword);
        }
        return Redisson.create(config);
    }


    @Bean("jackson2ObjectMapperBuilderCustomizer")
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        Jackson2ObjectMapperBuilderCustomizer customizer = new Jackson2ObjectMapperBuilderCustomizer() {
            @Override
            public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
                jacksonObjectMapperBuilder.serializerByType(Long.class, ToStringSerializer.instance)
                        .serializerByType(Long.TYPE, ToStringSerializer.instance);
            }
        };
        return customizer;
    }
}