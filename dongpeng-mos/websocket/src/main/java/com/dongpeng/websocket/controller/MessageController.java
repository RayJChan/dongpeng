package com.dongpeng.websocket.controller;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.entity.WsServerMessage;
import com.dongpeng.common.utils.J2CacheUtils;
import com.dongpeng.common.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * websocket 提供给第三方服务的 消息接口
 */
@RestController
@RequestMapping("/message")
public class MessageController extends BaseController {

    /** 通过simpMessagingTemplate向浏览器发送消息 **/
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 广播消息
     * @param wsServerMessage
     */
    @RequestMapping("/sendPublicMsg")
    public void sendPublicMsg(@RequestBody WsServerMessage wsServerMessage){
        simpMessagingTemplate.convertAndSend(Global.WEBSOCKET_URL_TOPIC_PUBLIC, wsServerMessage);
    }

    /**
     * 发送消息到指定用户
     * @param wsServerMessage
     */
    @RequestMapping("/sendMsgToUser")
    public void sendMsgToUser(@RequestBody WsServerMessage wsServerMessage){
        //若缓存已不存在user，证明是连接断开，不推送消息
        Object rs= J2CacheUtils.get(Global.WEBSOCKET_REGION, wsServerMessage.getToUser().toString());
        if(null==rs){
            return;
        }
        //向用户发送消息,第一个参数:接收消息的用户,第二个参数:浏览器订阅地址,第三个参数:消息
        simpMessagingTemplate.convertAndSendToUser(wsServerMessage.getToUser().toString(), Global.WEBSOCKET_URL_QUEUE_MESSAGE, wsServerMessage);
    }
}
