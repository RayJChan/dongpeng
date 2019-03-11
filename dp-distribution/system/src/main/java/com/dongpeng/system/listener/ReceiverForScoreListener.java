package com.dongpeng.system.listener;

import com.dongpeng.common.mapper.JsonMapper;
import com.dongpeng.entity.system.BlScoreDetail;
import com.dongpeng.entity.system.User;
import com.dongpeng.system.service.BlScoreDetailService;
import com.dongpeng.system.service.UserService;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ReceiverForScoreListener {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BlScoreDetailService blScoreDetailService ;

    @Autowired
    private UserService userService ;

    @Autowired
    private RedissonClient redisson;

    @RabbitListener(queues = "scoreMq")
    public void receiver(BlScoreDetail blScoreDetail){
       try{
           User user = userService.get(blScoreDetail.getUserId());
           if(null!=user){
               blScoreDetailService.save(blScoreDetail);
               BigDecimal total = blScoreDetailService.getScoreByUserId(blScoreDetail.getUserId());
               user.setScore(total.intValue());
               userService.save(user);
           }else{
               logger.warn("MQ积分变动：用户不存在----> :" + JsonMapper.toJsonString(blScoreDetail).toString());
           }
       }catch (Exception e){
           logger.error("MQ积分变动出现异常----> :" + JsonMapper.toJsonString(blScoreDetail).toString() ,e);
       }
    }

}
