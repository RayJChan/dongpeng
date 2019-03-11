package com.dpmall.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dpmall.db.bean.StoreEntity;

public interface StoreDao {
	List<StoreEntity> listDistributorStores(@Param("distributorId")String distributorId ,@Param("search") String search);
}
