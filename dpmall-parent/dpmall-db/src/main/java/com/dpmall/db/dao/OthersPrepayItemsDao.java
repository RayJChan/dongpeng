package com.dpmall.db.dao;

import com.dpmall.db.bean.OthersPrePayDetailEntity;
import com.dpmall.db.bean.OthersPrePayItemsEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 其他特权订金
 */
public interface OthersPrepayItemsDao {


	/**
	 * 获取商品明细
	 */
	 List<OthersPrePayItemsEntity>getItemByPrePayId (@Param("prePayId")String prePayId);


	/**
	 * 获取商品明细ids
	 */
	 Set<String> getItemIdsByPrePayId (@Param("prePayId")String prePayId);


	/**
	 * 软删除
	 */
	int delete (@Param("entity")Set<String> entity);




}
