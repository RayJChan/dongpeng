package com.dpmall.db.dao;

import org.apache.ibatis.annotations.Param;

public interface SendMsgDao {

	 /**
     * 获取短信模板
     */
	 public String getMsgtemplateByCode(  @Param("code") String code);


}
