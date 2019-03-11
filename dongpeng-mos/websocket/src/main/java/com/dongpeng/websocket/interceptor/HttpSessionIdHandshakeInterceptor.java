package com.dongpeng.websocket.interceptor;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.entity.JWTResult;
import com.dongpeng.common.entity.ResponseCode;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.mapper.JsonMapper;
import com.dongpeng.common.utils.J2CacheUtils;
import com.dongpeng.common.utils.JWTUtils;
import com.dongpeng.common.utils.UserAgentUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * websocket握手（handshake）接口
 * <p>点对对式发送消息到某一个用户，需要把某一个用户的id存放websock session中（项目使用userId表示用户的唯一性</p>
 */
@Component
public class HttpSessionIdHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response
            , WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        //解决The extension [x-webkit-deflate-frame] is not supported问题
        if(request.getHeaders().containsKey("Sec-WebSocket-Extensions")) {
            request.getHeaders().set("Sec-WebSocket-Extensions", "permessage-deflate");
        }
        //检查session的值是否存在
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;

            Object cacheToken=J2CacheUtils.get(Global.TOKEN_REGION, servletRequest.getRemoteAddress().getHostString());
            String token=cacheToken==null?null:cacheToken.toString();

            //开始验证token有效性
            if (StringUtils.isNotBlank(token)) {
                JWTUtils jwtUtils = JWTUtils.getInstance();
                JWTResult jwtResult = jwtUtils.checkToken(token);
                if (jwtResult.isStatus()) {
                    String[] userInfo=jwtResult.getUid().split(",");
                    //把userId存放起来
                    attributes.put(Global.SECURITY_TOKEN_USERID, userInfo[0]);
                }
            }
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
