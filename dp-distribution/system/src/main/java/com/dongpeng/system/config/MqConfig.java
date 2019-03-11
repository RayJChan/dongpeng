package com.dongpeng.system.config;

import com.google.common.collect.Maps;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class MqConfig {

    @Bean
    public Queue CouponCalculateQueue(){
        Map<String,Object> propertites = Maps.newHashMapWithExpectedSize(2);
        propertites.put("x-dead-letter-exchange","CouponCalculateDeadExchange");
        propertites.put("x-dead-letter-routing-key","couponQueue-dead");
        propertites.put("x-message-ttl", 15000);
        return QueueBuilder.durable("CouponCalculateQueue").withArguments(propertites).build();
    }

    @Bean
    public Exchange CouponCalculateExchange(){
        return ExchangeBuilder.topicExchange("CouponCalculateExchange").durable(true).build();
    }

    @Bean
    public Binding bind (){
        return  BindingBuilder.bind(CouponCalculateQueue()).to(CouponCalculateExchange()).with("CouponCalculateQueue").noargs();
    }

    @Bean
    public Queue CouponCalculateDeadQueue(){
        return QueueBuilder.durable("CouponCalculateDeadQueue").build();
    }

    @Bean
    public Exchange CouponCalculateDeadExchange(){
        return ExchangeBuilder.topicExchange("CouponCalculateDeadExchange").build();
    }

    @Bean
    public Binding deadBind(){
        return BindingBuilder.bind(CouponCalculateDeadQueue()).to(CouponCalculateDeadExchange()).with("couponQueue-dead").noargs();
    }


    @Bean
    public Queue scoreMqQueue() {
        return new Queue("scoreMq");
    }
}
