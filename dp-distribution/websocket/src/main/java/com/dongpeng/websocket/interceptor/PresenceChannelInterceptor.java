package com.dongpeng.websocket.interceptor;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.utils.J2CacheUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;

/**
 * stomp连接处理类
 * <p>说明：监听用户连接情况</p>
 */
@Component
public class PresenceChannelInterceptor extends ChannelInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(PresenceChannelInterceptor.class);

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);
        // ignore non-STOMP messages like heartbeat messages
        if(sha.getCommand() == null) {
            return;
        }
        //这里的userId对应HttpSessionIdHandshakeInterceptor拦截器的存放key
        Object userId = sha.getSessionAttributes().get(Global.SECURITY_TOKEN_USERID);
        userId=null==userId?"":userId;
        //判断客户端的连接状态
        switch(sha.getCommand()) {
            case CONNECT:
                connect(userId.toString());
                break;
            case CONNECTED:
                break;
            case DISCONNECT:
                disconnect(userId.toString(),sha);
                break;
            default:
                break;
        }
    }

    /**
     * 连接成功
     * @param userId
     */
    private void connect(String userId){
        logger.debug(" STOMP Connect [userId: " + userId + "]");
        //若在多个浏览器登录，直接覆盖保存
        J2CacheUtils.put(Global.WEBSOCKET_REGION, userId, userId);
    }

    /**
     * 断开连接
     * @param userId
     * @param sha
     */
    private void disconnect(String userId, StompHeaderAccessor sha){
        logger.debug(" STOMP disconnect [userId: " + userId + "]");
        sha.getSessionAttributes().remove(Global.SECURITY_TOKEN_USERID);
        //从缓存移除
        J2CacheUtils.remove(Global.WEBSOCKET_REGION ,userId);

    }
}
