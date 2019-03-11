package com.dpmall.web.controller;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

@ControllerAdvice
public class MyResponseBodyAdvice implements ResponseBodyAdvice {
//	private static final String[] SPECIAL_STRING = {
//            "\\n"
//    };
	@Override
	public boolean supports(MethodParameter returnType, Class converterType) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
		String bodyString = JSON.toJSONString(body,SerializerFeature.WriteNullStringAsEmpty);
		return JSON.parse(bodyString);
	}
}
