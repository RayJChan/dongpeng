package com.dpmall.dubbo.api;

import com.dpmall.model.UserModelOut;

/**
 * 用户权限服务
 * @author river
 * @date 2017-07-19
 */
public interface IUserOutService {
	
	/**
	 * <p>
	 * 登录接口
	 * @param username 用户名
	 * @param passwd 密码
	 * @return 用户信息
	 */
    public UserModelOut getUserById(String userId);
    
   
    
    
    
    
}
