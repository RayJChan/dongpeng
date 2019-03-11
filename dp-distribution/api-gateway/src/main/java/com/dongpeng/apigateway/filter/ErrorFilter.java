package com.dongpeng.apigateway.filter;

import com.dongpeng.common.entity.ResponseCode;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.mapper.JsonMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.ZuulServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.cloud.netflix.zuul.filters.post.SendResponseFilter;
import org.springframework.cloud.netflix.zuul.filters.pre.PreDecorationFilter;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonRoutingFilter;
import org.springframework.cloud.netflix.zuul.filters.route.SendForwardFilter;
import org.springframework.cloud.netflix.zuul.filters.route.SimpleHostRoutingFilter;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 * zuul 调用微服务出错 过滤器
 * <p>主要功能为统一错误的返回格式</p>
 * <p>暂时deprecated，因为未调通</p>
 */
@Deprecated
//@Component
public class ErrorFilter extends ZuulFilter {
    private final Logger logger = LoggerFactory.getLogger(ErrorFilter.class);

    /**
     * 过滤器的类型，它决定过滤器在请求的哪个生命周期中执行。(pre ->routing->POST)  error 自定义
     * 这里定义为error，代表会在请求出现异常后执行。
     *
     * @return
     */
    @Override
    public String filterType() {
        return FilterConstants.ERROR_TYPE;
    }

    /**
     * filter执行顺序，通过数字指定。
     * 数字越大，优先级越低。
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 1; // 大于ErrorFilter的值
    }

    /**
     * 判断该过滤器是否需要被执行。这里直接返回了true，因此该过滤器对所有请求都会生效。
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        /*// 判断：仅处理来自post过滤器引起的异常
        RequestContext ctx = RequestContext.getCurrentContext();
        ZuulFilter failedFilter = (ZuulFilter) ctx.get("failed.filter");
        if (failedFilter != null && failedFilter.filterType().equals("post")) {
            return true;
        }
        return false;*/
        RequestContext ctx = RequestContext.getCurrentContext();
        // only forward to errorPath if it hasn't been forwarded to already
        return ctx.getThrowable() != null
                && !ctx.getBoolean("sendErrorFilter.ran", false);
        //return true;
    }

    /**
     * 过滤器的具体逻辑
     *
     * @return
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        /*Throwable throwable = ctx.getThrowable();
        logger.error("this is a ErrorFilter :" + throwable.getCause().getMessage(), throwable);

        HttpServletResponse response=ctx.getResponse();
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        ctx.setResponseStatusCode(ResponseCode.SERVER_ERROR.getCode());//返回的错误码
        ctx.setResponseBody(JsonMapper.toJsonString(
                ResponseResult.fail(
                        ResponseCode.SERVER_ERROR.getCode()
                        , throwable.getCause().getMessage())));*/

        int statusCode = (Integer) ctx.get("error.status_code");
        String message = (String) ctx.get("error.message");
        if (ctx.containsKey("error.exception")) {
            Throwable throwable = (Exception) ctx.get("error.exception");
            logger.error(throwable.getCause().getMessage());
            HttpServletResponse response=ctx.getResponse();
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json");
            ctx.setResponseStatusCode(ResponseCode.SERVER_ERROR.getCode());//返回的错误码
            ctx.setResponseBody(JsonMapper.toJsonString(
                    ResponseResult.fail(
                            ResponseCode.SERVER_ERROR.getCode()
                            , throwable.getCause().getMessage())));
        }
        return null;
    }
}
