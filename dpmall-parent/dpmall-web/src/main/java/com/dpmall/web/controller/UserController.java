package com.dpmall.web.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dpmall.common.MD5Utils;
import com.dpmall.common.RedisUtils;
import com.dpmall.datasvr.api.IUserService;
import com.dpmall.err.ErrorCode;
import com.dpmall.model.LoginResModel;
import com.dpmall.web.controller.form.Response;
import com.dpmall.web.controller.form.UserForm;

import redis.clients.jedis.Jedis;

/**
 * <p>
 * 用户相关服务
 * @author river
 * @date 2017-07-19
 */
@Controller
@RequestMapping("/user")
public class UserController {
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
	

	
	
	@Autowired
	private IUserService userService;
	
	/**
	 * <p>
	 * 登录接口
	 *  username
	 *  passwd
	 * @return
	 */
	@RequestMapping(value="/login",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json") 
	@ResponseBody
    public Response login(@RequestBody UserForm form){
		//保存登录信息
		final Map<String, Object> params=new HashMap<String, Object>();
		params.put("username",form.username );
		params.put("localIP", form.localIP);
		params.put("deviceId", form.deviceId);
		params.put("bulidVersion", form.bulidVersion);
		params.put("phoneBrand", form.phoneBrand);
		params.put("phoneModel", form.phoneModel);
		params.put("network", form.network);
		params.put("netIP", form.netIP);
		params.put("appVersion", form.appVersion);
		//登录
		Jedis jedis = RedisUtils.getClient();
    	Response res = new Response();
    	if (StringUtils.isEmpty(form.username)||StringUtils.isEmpty(form.password)) {
			res.resultCode=ErrorCode.INVALID_PARAM;
			res.message="用户名或者密码不能为空";		
		}
    	else {
    		try{
				if (StringUtils.isEmpty(userService.checkUsreName(form.username))) {
					res.resultCode=ErrorCode.LOGIN_ERR;
					res.message="该用户不存在";
					return res;
				}

				LoginResModel resModel = userService.login(form.username.trim(), MD5Utils.MD5Encode(form.password));
				if (resModel == null) {
					res.resultCode = ErrorCode.LOGIN_ERR;
					res.message = "用户名或密码错误";
					return res;

				}
				String token = UUID.randomUUID().toString().replaceAll("-", "") + RandomUtils.nextInt(0, 100);
				jedis.set(String.valueOf(resModel.id), token);

				resModel.token = token;
//				resModel.appGroupModel = userService.getAppGroupInfo(form.username);//权限范围信息

				//将用户信息存入redis数据--供“东鹏我晒”使用
				jedis.hset("userInfo", String.valueOf(resModel.id), JSON.toJSONString(resModel));

				res.data = resModel;
				res.resultCode = ErrorCode.SUCCESS;
				params.put("token", token);

			} catch(Throwable e){
            	LOG.error(e.getMessage(),e);
            	res.resultCode=ErrorCode.INTERNAL_ERR;
            	res.message="系统错误";
        	}
            finally {
    			RedisUtils.returnResource(jedis);
    		}
		}
    	
//				try {
//					userService.saveloginInfo(params);
//				} catch (Exception e) {
//					LOG.error(e.getMessage());
//				}
				

    	return res;
    
	}
    
	
	@RequestMapping(value="/getStoreAllUser",method = {RequestMethod.GET,RequestMethod.POST},produces = "application/json")
	@ResponseBody
	public Response getStoreAllUser(@RequestBody UserForm userForm) {
		Response res = new Response();
		if (userForm.storeId==null) {
			res.resultCode=ErrorCode.INVALID_PARAM;
			res.message="参数错误";
		}
		else {
			try {
				res.data=userService.getStoreAllUser(userForm.storeId);
				res.resultCode=ErrorCode.SUCCESS;
			} catch (Exception e) {
				LOG.error(e.getMessage());
				res.resultCode=ErrorCode.INTERNAL_ERR;
				res.message="未知错误";
			}
		}
		return res;
	}
	
	@RequestMapping(value="/updatePasswd",method= {RequestMethod.GET,RequestMethod.POST},produces = "application/json")
	@ResponseBody
    public Response updatePasswd(@RequestBody UserForm form) {
		Jedis jedis= RedisUtils.getClient();
    	Response res = new Response();
    	if (form.id==null||StringUtils.isEmpty(form.password)||StringUtils.isEmpty(form.oldPasswd)) {
			res.resultCode=ErrorCode.INVALID_PARAM;
			res.message="参数错误";
		}
    	else {
    		try {
    			Integer result = userService.updatePasswd(String.valueOf(form.id), MD5Utils.MD5Encode(form.password), MD5Utils.MD5Encode(form.oldPasswd));
    			res.data=result;
    			if (result<1) {
					res.resultCode=ErrorCode.TOKEN_ERR;
					res.message="密码错误";
				}
    			else {				
    				res.resultCode=ErrorCode.SUCCESS;
    				jedis.del(String.valueOf(form.id));
    			}			
			
			} catch (Exception e) {
				e.printStackTrace();
				res.resultCode=ErrorCode.INTERNAL_ERR;
				res.message="系统错误";
			}
    		finally {
				RedisUtils.returnResource(jedis);
			}
    	}
    	return res;
    }
	
	

	@RequestMapping(value="/updatePassword",method= {RequestMethod.GET,RequestMethod.POST},produces = "application/json")
	@ResponseBody
    public Response updatePassword(@RequestBody UserForm form) {
		Jedis jedis= RedisUtils.getClient();
    	Response res = new Response();
    	if (StringUtils.isEmpty(form.password)||StringUtils.isEmpty(form.username)||StringUtils.isEmpty(form.phoneNum)) {
			res.resultCode=ErrorCode.INVALID_PARAM;
			res.message="参数错误";
		}
    	else {
    		try {
    			
    			Integer result = userService.updatePassword(form.username, MD5Utils.MD5Encode(form.password),form.phoneNum);
    			res.data=result;
    			if (result<1) {
					res.resultCode=ErrorCode.TOKEN_ERR;
					res.message="用户名和登记手机号码不匹配";
				}
    			else {				
    				res.resultCode=ErrorCode.SUCCESS;
    				String id =userService.getIdByUserName(form.username);
    				if (jedis!=null) {
    					jedis.del(String.valueOf(id));
    				}
    				
    			}			
			
			} catch (Exception e) {
				e.printStackTrace();
				res.resultCode=ErrorCode.INTERNAL_ERR;
				res.message="系统错误";
			}
    		finally {
				RedisUtils.returnResource(jedis);
			}
    	}
    	return res;
    }
	


}
