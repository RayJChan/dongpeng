package com.dongpeng.websocket.controller;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.entity.WsClientMessage;
import com.dongpeng.common.entity.WsServerMessage;
import com.dongpeng.common.utils.J2CacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.util.Date;

/**
 * websocket 控制器
 */
@Controller
public class WebSocketController {

    /** 通过simpMessagingTemplate向浏览器发送消息 **/
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 发送公共消息到公共订阅频道
     * @param clientMessage
     * @return
     */
    @MessageMapping(Global.WEBSOCKET_URL_SENDPUBLIC) //当浏览器向服务端发送请求时,通过@MessageMapping映射/sendPublicMsg
    @SendTo(Global.WEBSOCKET_URL_TOPIC_PUBLIC) //当服务器有消息时,会对订阅了@SendTo中的路径的浏览器发送消息 (Broker 下的指定订阅路径)
    public WsServerMessage receiveAndSend(WsClientMessage clientMessage){
        System.out.println("get message (" + clientMessage + ") from client!");
        System.out.println("send this messages to all subscriber '"+Global.WEBSOCKET_URL_TOPIC_PUBLIC+"' user!");

        return new WsServerMessage(clientMessage);
    }

    /**
     * 发送消息到指定用户频道
     * @param clientMessage
     */
    @MessageMapping(Global.WEBSOCKET_URL_SEND)
    public void sendToSpecifiedUser(WsClientMessage clientMessage){
        //若缓存已不存在user，证明是连接断开，不推送消息
        Object rs= J2CacheUtils.get(Global.WEBSOCKET_REGION, clientMessage.getToUser().toString());
        if(null==rs){
            return;
        }
        WsServerMessage wsServerMessage=new WsServerMessage(clientMessage);
        //向用户发送消息,第一个参数:接收消息的用户,第二个参数:浏览器订阅地址,第三个参数:消息
        simpMessagingTemplate.convertAndSendToUser(wsServerMessage.getToUser().toString(), Global.WEBSOCKET_URL_QUEUE_MESSAGE, wsServerMessage);
    }

    /*@SubscribeMapping("/sub")
    public WsServerMessage sub(){
        return new WsServerMessage(1, 1232L, "感谢订阅");
    }*/

    @MessageExceptionHandler
    public void handleExceptions(Exception e,WsClientMessage clientMessage){
        //把错误信息发回给发送人
        simpMessagingTemplate.convertAndSendToUser( clientMessage.getFromUser().toString(),Global.WEBSOCKET_URL_QUEUE_ERROR,e.getMessage());
    }

}
