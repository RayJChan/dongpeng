package com.dpmall.datasvr.api;


import java.util.List;

import com.dpmall.model.PictureModel;

/**
 * 实物订单服务接口
 * @author river
 * @since 2017-07-10
 */
public interface IPictureService {
   
	
	/**
     * 获取祝福卡图片
     * isHomePage:Y 为查询首页图片
     * 	          N 为查询列表图片
     */
    public List<PictureModel> getPictures(Integer offset, Integer pageSize,String pictureType);
 
    
    /**
     * 上传图片
     */
    public int uploadPicture(String pictureName,String title,String content,String pictureType) ;
}
