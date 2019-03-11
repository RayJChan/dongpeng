package com.dpmall.datasvr.api;

import java.util.List;
import java.util.Map;

import com.dpmall.model.AppGroupModel;
import com.dpmall.model.LoginResModel;
import com.dpmall.model.UserModel;

/**
 * 用户权限服务
 * @author river
 * @date 2017-07-19
 */
public interface IUserService {
	
	/**
	 * <p>
	 * 登录接口
	 * @param username 用户名
	 * @param passwd 密码
	 * @return 用户信息
	 */
    public LoginResModel login(String username, String passwd);
    
    /***
     * 门店创建用户
     * @param usr
     * @return
     */
    public int createStoreUser(UserModel usr);
    
    public void saveloginInfo(Map<String, Object> loginInfo);
    
    /**
     * 更新用户
     * @param usr
     * @return
     */
    public int updateUser(UserModel usr);
    
    /**
     * <p>
     * 获取门店的所有用户
     * @param storeId
     * @return
     */
    public List<UserModel> getStoreAllUser(Long storeId);
    
    
    /**
	 * 修改密码
	 * @param username 用户名
	 * @param passwd 密码
	 * @return 成功返回200
	 */
    public Integer updatePasswd(String username, String passwd,String oldPasswd);

    /**
   	 * 修改密码(无需旧密码)
   	 * @param username 用户名
   	 * @param passwd 密码
   	 * @return 成功返回200
   	 */
     public Integer updatePassword(String userName, String password,String phone);
    
    
    public UserModel getUserInfo(String role,String id);
    
    /**
     * 获取权限分组信息
     */
    public AppGroupModel getAppGroupInfo (String userName);
    
    /**
     * 获取用户pk
     */
    public String getIdByUserName (String userName);

    /**
     * 判断用户是否存在
     * @return
     */
    public String checkUsreName(String userName);







}
