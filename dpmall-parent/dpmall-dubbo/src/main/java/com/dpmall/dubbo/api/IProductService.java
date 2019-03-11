package com.dpmall.dubbo.api;


import java.util.List;

import com.dpmall.model.ProductModel;

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
	 public List<ProductModel> getProductInfo(int startNum,int pageSize);
	 
	 /**
		 * 根据型号查询产品信息
		 * @return
		 */
	 public  ProductModel getInfoByProductCode( String productCode);
	 
}
