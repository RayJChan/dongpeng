package com.dpmall.datasvr.api;

import java.util.List;

import com.dpmall.model.ProductStatisticModel;




/**
 * 
 * @author river
 * @since 2017-06-30
 */
public interface IProductStatisticService {
	/**
	 * <p>
	 * 根据模板查询
	 * @param template
	 * @param page
	 * @return  根据模板查询
	 */
    public List<ProductStatisticModel> search(String productCode,String fromTime, String endTime, int start, int pageSize);

}
