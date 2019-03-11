package com.dpmall.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dpmall.db.bean.AppGroupEntity;
import com.dpmall.db.bean.AppUserEntity;

public interface AppUserDao {
	AppUserEntity login(@Param("username")String username, @Param("passwd")String passwd);
	
	/** int createStoreUser(@Param("user")AppUserEntity user);
	
	 int updateUser(@Param("user")AppUserEntity user);*/
	 
	 List<AppUserEntity> getStoreAllUser(@Param("storeId")Long storeId);
	 
	 Integer changePassword(@Param("id")String id,@Param("password")String password,@Param("oldPassword")String oldPassword);
	 
	 /**经销商信息*/
	 AppUserEntity getAgencyUserInfo(@Param("id")String id);
	 
	 /**门店信息*/
	 AppUserEntity getStoreUserInfo(@Param("id")String id);
	 
	 /**其他类型信息*/
	 AppUserEntity  getOrderUserInfo(@Param("id")String id);
	 
	/**
	 * 获取权限分组信息
	 */
	public AppGroupEntity getAppGroupInfo(@Param("userName")String userName);
	/**
	 * 
	 无需旧密码修改 密码
	 */
	public Integer updatePassword(@Param("userName")String userName,@Param("password")String password);
	 
	/**
	 * 根据用户名获取id
	 */
	public String getIdByUserName(@Param("userName")String userName);
	
	/**
	 * 根据id查询信息
	 * @param userId
	 * @return
	 */
	public AppUserEntity getUserByUserId(@Param("userId")String userId);

	/**
	 * 判断用户是否存在
	 * @return
	 */
	public String checkUsreName(@Param("userName")String userName);




}
