package com.dongpeng.common.utils;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.entity.WsServerMessage;
import net.oschina.j2cache.cache.support.util.SpringUtil;
import org.springframework.messaging.simp.SimpMessagingTemplate;

/**
 * websocket 消息发送工具类
 */
@Deprecated
public class WebSocketUtils {

    private static SimpMessagingTemplate simpMessagingTemplate=SpringUtil.getBean(SimpMessagingTemplate.class);

    /**
     * 广播消息
     * @param wsServerMessage
     */
    public static void sendPublicMsg(WsServerMessage wsServerMessage){
        simpMessagingTemplate.convertAndSend(Global.WEBSOCKET_URL_TOPIC_PUBLIC, wsServerMessage);
    }

    /**
     * 发送消息到指定用户
     * @param wsServerMessage
     */
    public static void sendMsgToUser(WsServerMessage wsServerMessage){
        //若缓存已不存在user，证明是连接断开，不推送消息
        Object rs= J2CacheUtils.get(Global.WEBSOCKET_REGION, wsServerMessage.getToUser().toString());
        if(null==rs){
            return;
        }
        //向用户发送消息,第一个参数:接收消息的用户,第二个参数:浏览器订阅地址,第三个参数:消息
        simpMessagingTemplate.convertAndSendToUser(wsServerMessage.getToUser().toString(), Global.WEBSOCKET_URL_QUEUE_MESSAGE, wsServerMessage);
    }
}
