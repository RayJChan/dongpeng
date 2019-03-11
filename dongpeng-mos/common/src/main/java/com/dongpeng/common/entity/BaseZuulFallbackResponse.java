package com.dongpeng.common.entity;

import com.dongpeng.common.mapper.JsonMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * zuul熔断基础信息返回类
 */
public class BaseZuulFallbackResponse {

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
