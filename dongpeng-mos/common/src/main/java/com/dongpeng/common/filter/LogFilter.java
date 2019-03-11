package com.dongpeng.common.filter;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.entity.BodyReaderHttpServletRequestWrapper;
import com.dongpeng.common.mapper.JsonMapper;
import com.dongpeng.common.utils.Encodes;
import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 日志记录过滤器
 * <ul>
 *     <li>采用WebFilter注解的方式需要在spring boot 启动类中加入ServletComponentScan注解 </li>
 * </ul>
 */
//@WebFilter(filterName = "logFilter",urlPatterns = "/*")
public class LogFilter implements Filter {
    private final static Logger logger = LoggerFactory.getLogger(LogFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 防止流读取一次后就没有了, 所以需要将流继续写出去
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(httpServletRequest);
        Optional<String> username  = Optional.fromNullable(((BodyReaderHttpServletRequestWrapper) requestWrapper).getHeader(Global.SECURITY_TOKEN_USERNAME));
        MDC.put("username", Encodes.decodeBase64String(username.or("dW5rb3du")));
        //记录请求入参
        if (!((BodyReaderHttpServletRequestWrapper) requestWrapper).getRequestURL().toString().contains("/actuator")){
            logger.info("请求URL：{} 请求参数：{} 请求体：{}"
                    ,((BodyReaderHttpServletRequestWrapper) requestWrapper).getRequestURL()
                    ,JsonMapper.toJsonString(requestWrapper.getParameterMap())
                    ,((BodyReaderHttpServletRequestWrapper) requestWrapper).getBody()
            );
        }

        chain.doFilter(requestWrapper, response);//把重新定义的requestWrapper 放置进后续chain链中
    }

    @Override
    public void destroy() {

    }
}
