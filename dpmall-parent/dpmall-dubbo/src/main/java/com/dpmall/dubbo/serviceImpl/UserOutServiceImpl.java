package com.dpmall.dubbo.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.dpmall.db.bean.AppGroupEntity;
import com.dpmall.db.bean.AppUserEntity;
import com.dpmall.db.dao.AppUserDao;
import com.dpmall.dubbo.api.IUserOutService;
import com.dpmall.model.AppGroupModel;
import com.dpmall.model.UserModel;
import com.dpmall.model.UserModelOut;

public class UserOutServiceImpl implements IUserOutService {
	
	@Autowired
	private AppUserDao userDao;
	
	private UserModel entityToModel(AppUserEntity entity) {
		UserModel model=new UserModel();
		model.id=entity.id;
		model.agencyId=entity.agencyId;
		model.cnName=entity.cnName;
		model.roleCode=entity.roleCode;
		model.storeId=entity.storeId;
		model.username=entity.username;
		model.telePhone=entity.telePhone;
		model.storeName=entity.storeName;
		model.storeAddress=entity.storeAddress;
		return model;
	}
	private AppGroupModel groupEntityToMoel(AppGroupEntity entity) {
		AppGroupModel model = new AppGroupModel();
		model.id=entity.id;
		model.code = entity.code;
		model.name = entity.name;
		model.type= entity.type;
		model.store = entity.store;
		model.parentId = entity.parentId;
		return model;
	}
	
	
	private UserModelOut entityToLoginModel(AppUserEntity entity) {
		UserModelOut model=new UserModelOut();
		model.id=entity.id;
		model.agencyId=entity.agencyId;
		model.cnName=entity.cnName;
		model.roleCode=entity.roleCode;
		model.storeId=entity.storeId;
		model.username=entity.username;
		model.telePhone=entity.telePhone;
		return model;
	}

	public UserModelOut getUserById(String userId) {
		AppUserEntity entity= userDao.getUserByUserId(userId);
		if (entity==null) {
			return null;
		}
		else {
			UserModelOut result = entityToLoginModel(entity);
			if (result.roleCode.equals("agency")) {//经销商
				AppUserEntity infoEntity=userDao.getAgencyUserInfo(String.valueOf(result.id));
				result.storeName=infoEntity.storeName;
				result.storeAddress=infoEntity.storeAddress;
				result.telePhone=infoEntity.telePhone;
			}
			else if (result.roleCode.equals("manager") ||result.roleCode.equals("clerk") ) {//门店
				AppUserEntity infoEntity=userDao.getStoreUserInfo(String.valueOf(result.id));
				result.storeName=infoEntity.storeName;
				result.storeAddress=infoEntity.storeAddress;
				result.telePhone=infoEntity.telePhone;
			}else {//其他类型
				AppUserEntity infoEntity=userDao.getOrderUserInfo(String.valueOf(result.id));
				result.roleCode=infoEntity.roleCode;
				result.roleName=infoEntity.roleName;
				result.groupCode=infoEntity.groupCode;
				result.groupName=infoEntity.groupName;
				result.groupType=infoEntity.groupType;
				result.groupStore=infoEntity.groupStore;
			}
			return result;
		}
	}

	
	
	
	
}
