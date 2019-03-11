package com.dpmall.datasvr.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dpmall.datasvr.api.IStoreService;
import com.dpmall.db.bean.StoreEntity;
import com.dpmall.db.dao.StoreDao;
import com.dpmall.model.StoreModel;

/**
 * 门店接口服务实现
 * @author river
 * @since 2017-07-10
 */
@Service(value = "storeService")
public class StoreServiceImpl implements IStoreService {
	
	@Autowired
	private StoreDao storeDao;
	
	private StoreModel entityToModel(StoreEntity entity) {
		StoreModel model=new StoreModel();
		model.storeId=entity.storeId;
		model.storeAddr=entity.storeAddr;
		model.storeName=entity.storeName;
		return model;
	}

	public List<StoreModel> listDistributorStores(String distributorId,String storeName) {
		List<StoreEntity> entities = storeDao.listDistributorStores(distributorId,storeName);
		List<StoreModel> result=new ArrayList<StoreModel>(entities.size());
		for(StoreEntity entity:entities) {
			result.add(entityToModel(entity));
		}
		return result;
	}

}
