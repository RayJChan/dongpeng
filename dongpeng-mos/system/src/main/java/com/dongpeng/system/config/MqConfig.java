package com.dongpeng.system.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitmq 配置
 */
@Configuration
public class MqConfig {

   /*
    //配置队列
    @Bean
    public Queue couponCalculate(){
        return new Queue("couponCalculate");
    }


    @Bean
    public Queue scoreMqQueue() {
        return new Queue("scoreMq");
    }*/
}
