package com.dongpeng.apigateway.fallbackProvider;

import com.dongpeng.common.entity.BaseZuulFallbackResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

/**
 * zuul 熔断类
 * 当system模块服务不可用时，熔断
 */
@Component
public class SystemFallbackProvider extends BaseZuulFallbackResponse implements FallbackProvider {
    private final Logger logger = LoggerFactory.getLogger(SystemFallbackProvider.class);

    @Override
    public String getRoute() {
        return "system";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        logger.info("route{"+route+"} Excption {}",cause.getMessage());
        return fallbackResponse();
    }
}
