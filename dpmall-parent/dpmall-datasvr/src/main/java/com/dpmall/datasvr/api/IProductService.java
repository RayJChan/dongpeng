package com.dpmall.datasvr.api;


import com.dpmall.model.ProductModel;

import java.util.List;

/**
 * 产品信息服务接口
 * @author cwj
 * @since 2018-2-26
 */
public interface IProductService {
   

	/**
	 * 获取产品信息
	 * @param startNum
	 * @param pageSize
	 * @return
	 */
	 public List<ProductModel> getProductInfo(int startNum, int pageSize);

	 /**
		 * 根据型号查询产品信息
		 * @return
		 */
	 public  ProductModel getInfoByProductCode(String productCode);

	/***
	 * 查询产品编号-并存入缓存
	 * @return
	 */
	public long  getProductCode ();


	 
}
