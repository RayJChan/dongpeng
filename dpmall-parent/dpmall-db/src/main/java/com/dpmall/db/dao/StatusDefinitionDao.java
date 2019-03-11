package com.dpmall.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dpmall.db.bean.StatusDefinitionEntity;
import org.apache.ibatis.annotations.Select;

public interface StatusDefinitionDao {
	 
	/**
	  * 获取拒单原因
	  */
	 public List<StatusDefinitionEntity> getInfoByType(@Param("type") String type);

	/**
	 * 获取留资状态(跟进中的状态)
	 */
	@Select("SELECT P_NAME FROM STATUSDEFINITION WHERE P_TYPE = 'SaleLeadsOrderStatus' AND P_STATUSDEFINITIONS > 03 AND P_STATUSDEFINITIONS < 11 ORDER BY P_STATUSDEFINITIONS ASC")
	public List<String> getSalOrderStatus();



}
