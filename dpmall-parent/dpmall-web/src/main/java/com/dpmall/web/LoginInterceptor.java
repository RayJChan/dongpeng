package com.dpmall.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.dpmall.common.RedisUtils;
import com.dpmall.err.ErrorCode;
import com.dpmall.web.controller.form.Response;

import redis.clients.jedis.Jedis;

public class LoginInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//不需拦截的请求
		String[] allowUrls= {"/user/login",//登陆
				"/images/",//上传图片
				"/user/updatePassword",//修改密码
				"/jsp/",//跳转jsp
				"/resources/",//静态资源
				"/picture/ossUpload"//上传图片至Oss
				};
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		String url = request.getRequestURI();
		for(String allow:allowUrls) {
			if (url.contains(allow)) {
				return true;
			}
		}
		Jedis jedis=RedisUtils.getClient();
		Response res = new Response();
		try {
			 String id = request.getHeader("id");
			 String token = request.getHeader("token");
			 if (StringUtils.isEmpty(id)||StringUtils.isEmpty(token)) {
				res.resultCode=ErrorCode.TOKEN_ERR;
				res.message="请先登录";
				response.getWriter().append(JSON.toJSONString(res));
				return false;
			}
			 String checkToken=jedis.get(id);
			 if (token==null||!token.equals(checkToken)) {
				res.resultCode=ErrorCode.TOKEN_ERR;
				res.message="请先登录";
				response.getWriter().append(JSON.toJSONString(res));
				return false;
			}
			 else {
				 return true;
			 }
			
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(500);
		}
		finally {
			RedisUtils.returnResource(jedis);		
		}
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
