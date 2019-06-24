package com.dpmall.db.dao;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dpmall.db.bean.PictureEntity;

public interface PictureDao  {
	 
	 /**
     * 查看图片
     */
	 public List<PictureEntity> getPicture(@Param("offset")Integer offset, @Param("pageSize")Integer pageSize,@Param("pictureType")String pictureType);
	 
	 /**
	  * 插入照片
	  */
	 public int uploadPicture(@Param("entity")PictureEntity entity);
	 
	 /**
	  * 查看首页照片
	  */
	 public List<PictureEntity> getHomePagePicture();
	 
}
