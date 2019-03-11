package com.dpmall.datasvr.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.dpmall.common.HybrisUtils;
import com.dpmall.datasvr.api.IUserService;
import com.dpmall.db.bean.AppGroupEntity;
import com.dpmall.db.bean.AppUserEntity;
import com.dpmall.db.dao.AppUserDao;
import com.dpmall.model.AppGroupModel;
import com.dpmall.model.LoginResModel;
import com.dpmall.model.UserModel;

@Service(value = "userService")
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private AppUserDao userDao;


	@Autowired
	private HybrisUtils hybrisUtils;
	
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
	
	
	private LoginResModel entityToLoginModel(AppUserEntity entity) {
		LoginResModel model=new LoginResModel();
		model.id=entity.id;
		model.agencyId=entity.agencyId;
		model.cnName=entity.cnName;
		model.roleCode=entity.roleCode;
		model.storeId=entity.storeId;
		model.username=entity.username;
		model.telePhone=entity.telePhone;
		return model;
	}

	public LoginResModel login(String username, String passwd) {
		AppUserEntity entity= userDao.login(username, passwd);
		if (entity==null) {
			return null;
		}
		else {
			LoginResModel result = entityToLoginModel(entity);
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

	public int createStoreUser(UserModel usr) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int updateUser(UserModel usr) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<UserModel> getStoreAllUser(Long storeId) {
		List<AppUserEntity> entities = userDao.getStoreAllUser(storeId);
		List<UserModel> result=new ArrayList<UserModel>(entities.size());
		for(AppUserEntity entity:entities) {
			result.add(entityToModel(entity));
		}
		return result;
	}

	public Integer updatePasswd(String username, String passwd,String oldPasswd) {
		// TODO Auto-generated method stub
		return userDao.changePassword(username, passwd, oldPasswd);
	}

	public UserModel getUserInfo(String role, String id) {
		UserModel model =new UserModel(); 
		if (role.equals("1")) {
			model=entityToModel(userDao.getAgencyUserInfo(id));
		}
		else if (role.equals("2")) {
			model=entityToModel(userDao.getStoreUserInfo(id));
		}
		else {
			model=null;
		}
		return model;
	}


	public void saveloginInfo(Map<String, Object> loginInfo) {
		hybrisUtils.saveLoginInfo(loginInfo);
		
	}


	public AppGroupModel getAppGroupInfo(String userName) {
		AppGroupModel groupModel = new AppGroupModel();
		if (StringUtils.isEmpty(userName)) {
			return null;
		}
		AppGroupEntity entity = userDao.getAppGroupInfo(userName);
		if (entity == null ) {
			return null;
		}
		groupModel = groupEntityToMoel(entity);
		return groupModel;
	}
	
	
	public Integer updatePassword(String userName, String password,String phone) {
		AppUserEntity entity= userDao.login(userName, null);
		if (entity==null) {
			return 0;
		}
		else {
			LoginResModel result = entityToLoginModel(entity);
			if (result.roleCode.equals("agency")) {//经销商
				AppUserEntity infoEntity=userDao.getAgencyUserInfo(String.valueOf(result.id));
				result.telePhone=infoEntity.telePhone;
			}
			else if (result.roleCode.equals("manager") ||result.roleCode.equals("clerk") ) {//门店
				AppUserEntity infoEntity=userDao.getStoreUserInfo(String.valueOf(result.id));
				result.telePhone=infoEntity.telePhone;
			}else {//其他类型
				result.telePhone="non-existent";
			}
			if (result.telePhone.equals(phone)) {
				return userDao.updatePassword(userName, password);
			}else {
				return 0;
			}
		}		
		
		
		
		
		
	}
	
	public String getIdByUserName(String userName) {
		// TODO Auto-generated method stub
		return userDao.getIdByUserName(userName);
	}

	public String checkUsreName(String userName) {
		return userDao.checkUsreName(userName);
	}
	
	
	
	
	
	

}
