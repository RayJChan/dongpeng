package com.dongpeng.apigateway.fallbackProvider;

import com.dongpeng.common.entity.ResponseCode;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.mapper.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * zuul 熔断类
 * 当服务不可用时，熔断
 * <br/>仅作为demo使用
 */
@Deprecated
@Component
public class MyFallbackProvider implements FallbackProvider {
    private final Logger logger = LoggerFactory.getLogger(MyFallbackProvider.class);
    /**
     * 返回值表示需要针对此微服务做回退处理（该名称一定要是注册进入 eureka 微服务中的那个 serviceId 名称）；
     * 不能配置多个，也不能配置通配符
     * @return
     */
    @Override
    public String getRoute() {
        return "consumer-db";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        logger.info("route{"+route+"} Excption {}",cause.getMessage());
        return fallbackResponse();
    }

    public ClientHttpResponse fallbackResponse() {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return this.getStatusCode().value();
            }

            @Override
            public String getStatusText() throws IOException {
                return this.getStatusCode().getReasonPhrase();
            }

            @Override
            public void close() {

            }

            /**
             * 当 springms 微服务出现宕机后，客户端再请求时候就会返回 fallback 等字样的字符串提示；
             * @return
             * @throws IOException
             */
            @Override
            public InputStream getBody() throws IOException {
                String json = JsonMapper.toJsonString(ResponseResult.fail(ResponseCode.SERVER_ERROR.getCode(), "Service不可用"));
                return new ByteArrayInputStream(json.getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                MediaType mt = new MediaType("application", "json", Charset.forName("UTF-8"));
                headers.setContentType(mt);
                return headers;
            }
        };
    }
}
