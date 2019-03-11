package com.dongpeng.websocket.config;

import com.dongpeng.websocket.interceptor.HttpSessionIdHandshakeInterceptor;
import com.dongpeng.websocket.interceptor.PresenceChannelInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * 注解开启使用STOMP协议来传输基于代理(message broker)的消息,这时控制器支持使用@MessageMapping,就像使用@RequestMapping一样
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 注册STOMP协议的节点(endpoint),并映射指定的url
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 添加服务端点，可以理解为某一服务的唯一key值
        registry.addEndpoint("/message");

        //添加服务端点，可以理解为某一服务的唯一key值。当浏览器支持sockjs时执行该配置
        registry.addEndpoint("/message") //添加服务端点
                .setAllowedOrigins("*") //允许跨域访问
                .withSockJS()//使用sockJS
                .setInterceptors(httpSessionIdHandshakeInterceptor());

    }

    /**
     * 配置消息代理(Message Broker)
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        // 订阅Broker名称.广播式应配置一个/topic消息代理
        //registry.enableSimpleBroker("/topic");

        // 订阅Broker名称.点对点式应配置/queue和/topic消息代理
        // /topic 代表发布广播，即群发
        // /user 代表点对点，即发指定用户
        registry.enableSimpleBroker("/topic","/user");

        // 全局使用的消息前缀（客户端订阅路径上会体现出来）
        //例如客户端发送消息的目的地为/app/sendTest，则对应控制层@MessageMapping(“/sendTest”)
        //客户端订阅主题的目的地为/app/subscribeTest，则对应控制层@SubscribeMapping(“/subscribeTest”)
        registry.setApplicationDestinationPrefixes("/dongpeng");

        // 点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user/,需要跟broker中存在对应
         registry.setUserDestinationPrefix("/user/");

    }

    /**
     * 输入通道参数设置
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(presenceChannelInterceptor());
    }

    /**
     * 输出通道参数设置
     */
    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.interceptors(presenceChannelInterceptor());
    }

    @Bean
    public HttpSessionIdHandshakeInterceptor httpSessionIdHandshakeInterceptor(){
        return new HttpSessionIdHandshakeInterceptor();
    }

    @Bean
    public PresenceChannelInterceptor presenceChannelInterceptor(){
        return new PresenceChannelInterceptor();
    }
}
