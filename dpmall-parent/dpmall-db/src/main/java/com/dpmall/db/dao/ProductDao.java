package com.dpmall.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dpmall.db.bean.ProductEntity;

public interface ProductDao {
	 
	/**
	 * 获取产品信息
	 * @param startNum
	 * @param pageSize
	 * @return
	 */
	 public List<ProductEntity> getProductInfo( @Param("startNum")int startNum,@Param("pageSize")int pageSize);
	 
	 ProductEntity getInfoByProductCode( @Param("productCode")String productCode);

	/***
	 * 查询产品编号-并存入缓存
	 * @return
	 */
	public List<String>  getProductCode (@Param("pageNum")int pageNum,@Param("pageSize")int pageSize);
	 
}
