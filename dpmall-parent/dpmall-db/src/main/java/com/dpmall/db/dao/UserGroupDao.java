package com.dpmall.db.dao;

import com.dpmall.db.bean.AppGroupEntity;
import com.dpmall.db.bean.AppUser_NewEntity;
import com.dpmall.db.bean.KeHuEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserGroupDao {

	/**
	 * 批量插入
	 */
	int insertAppGroupBatch(@Param("list") List<AppGroupEntity> list);

	/**
	 * 单条插入
	 */
	int insertAppGroup(@Param("entity") AppGroupEntity entity);

	/**
	 * 查询根据Code查询
	 */
	 AppGroupEntity getByCode (@Param("code")String code);

	/**
	 * 查询appuser
	 */
	AppUser_NewEntity getAppUserByUserName (@Param("userName")String userName);

	/**
	 * 插入Appuser
	 */
	int isnertAppUser(@Param("po") AppUser_NewEntity entity);

	/**
	 * 更新Appuser
	 */
	int updateAppuser(@Param("po") AppUser_NewEntity entity);

	/**
	 * 查询appuser
	 */
	KeHuEntity getKeHu(@Param("kehCode") String kehCode);





}
