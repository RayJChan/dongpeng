package com.dongpeng.apigateway.filter;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.entity.JWTResult;
import com.dongpeng.common.entity.ResponseCode;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.mapper.JsonMapper;
import com.dongpeng.common.utils.Encodes;
import com.dongpeng.common.utils.J2CacheUtils;
import com.dongpeng.common.utils.JWTUtils;
import com.dongpeng.common.utils.UrlPatternUtils;
import com.dongpeng.common.utils.UserAgentUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonRoutingFilter;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * zuul 接口安全性验证过滤器
 */
@Component
public class TokenFilter extends ZuulFilter {
    private final Logger logger = LoggerFactory.getLogger(TokenFilter.class);
    private UrlPatternUtils urlPatternUtils=null;


    /**
     * 过滤器的类型，它决定过滤器在请求的哪个生命周期中执行。(pre ->routing->POST)  error 自定义
     * 这里定义为pre，代表会在请求被路由之前执行。
     *
     * @return
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * filter执行顺序，通过数字指定。
     * 数字越大，优先级越低。
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * 判断该过滤器是否需要被执行。这里直接返回了true，因此该过滤器对所有请求都会生效。
     * 实际运用中我们可以利用该函数来指定过滤器的有效范围。
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        if(urlPatternUtils==null){
            urlPatternUtils=new UrlPatternUtils(null, Global.getConfig(Global.SECURITY_TOKEN_EXCLUDE));
        }
        if(urlPatternUtils.checkExclude(request.getServletPath())){
            logger.info("请求来源 ip: "+UserAgentUtils.getIpAddr(request));
            logger.info("token 例外 url: "+request.getServletPath());
            return false;//表示不需要执行run方法，直接路由
        }

        return true;
    }

    /**
     * 过滤器的具体逻辑
     *
     * @return
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response=ctx.getResponse();
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");

        //token先从请求头查找,再从缓存中查找
        String token = request.getHeader(Global.HEADER_TOKEN);
        if(StringUtils.isBlank(token)){
            //logger.info(Global.TOKEN_REGION);
            logger.info(UserAgentUtils.getIpAddr(request));
            Object cacheToken=J2CacheUtils.get(Global.TOKEN_REGION, UserAgentUtils.getIpAddr(request));
            token=cacheToken==null?null:cacheToken.toString();
        }

        //开始验证token有效性
        if (StringUtils.isBlank(token)) {
            ctx.setSendZuulResponse(false);//过滤该请求，不对其进行路由
            ctx.setResponseStatusCode(ResponseCode.NO_AUTH.getCode());//返回的错误码
            ctx.setResponseBody(JsonMapper.toJsonString(ResponseResult.fail(ResponseCode.NO_AUTH.getCode(), ResponseCode.NO_AUTH.getMsg()+",token is empty")));//返回 body 内容
        }else{
            JWTUtils jwtUtils=JWTUtils.getInstance();
            JWTResult jwtResult=jwtUtils.checkToken(token);
            if(!jwtResult.isStatus()){
                ctx.setSendZuulResponse(false);//过滤该请求，不对其进行路由
                ctx.setResponse(response);
                ctx.setResponseStatusCode(jwtResult.getCode());//返回的错误码
                ctx.setResponseBody(JsonMapper.toJsonString(ResponseResult.fail(jwtResult.getCode(), jwtResult.getMsg())));//返回 body 内容
            }else{
                //token合法，把当前userid和username保存进requestHeader，方便之后微服务获取使用
                String[] userInfo=jwtResult.getUid().split(",");
                ctx.addZuulRequestHeader(Global.SECURITY_TOKEN_USERID, userInfo[0]);
                ctx.addZuulRequestHeader(Global.SECURITY_TOKEN_USERNAME, Encodes.encodeBase64(userInfo[1]));//base64编码，解决中文乱码
            }
        }
        return null;
    }
}
