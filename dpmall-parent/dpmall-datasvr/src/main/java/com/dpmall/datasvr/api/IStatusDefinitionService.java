package com.dpmall.datasvr.api;


import java.util.List;

import com.dpmall.model.StatusDefinitionModel;

/**
 * 原因选择接口
 * @author river
 * @since 2017-07-10
 */
public interface IStatusDefinitionService {
    
	/**
	  * 获取原因明细
	  */
	 public List<StatusDefinitionModel> getInfoByType(String selectionType);

	/**
	 * 获取留资状态(跟进中的状态)
	 */
	public List<String> getSalOrderStatus();



}
