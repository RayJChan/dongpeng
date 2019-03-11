package com.dpmall.datasvr.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dpmall.datasvr.api.IStatusDefinitionService;
import com.dpmall.db.bean.StatusDefinitionEntity;
import com.dpmall.db.dao.StatusDefinitionDao;
import com.dpmall.model.StatusDefinitionModel;

/**
 * 原因选择接口实现
 * @author cwj
 * @date 2017-10-31
 */
@Service(value = "statusDefinitionService")
public class StatusDefinitionServiceImpl implements IStatusDefinitionService {
	
	@Autowired 
	StatusDefinitionDao definitionDao;
	
	private StatusDefinitionModel entityToModel(StatusDefinitionEntity entity) {
		if (entity==null) {
			return null;
		}
		StatusDefinitionModel model=new StatusDefinitionModel();
		model.id = entity.id;
		model.name = entity.name;
		return model;
	}
	
	private List<StatusDefinitionModel> entitysaleModel(List<StatusDefinitionEntity> in){
		if(in == null || in.isEmpty()){
			return null;
		}
		
		List<StatusDefinitionModel> out = new ArrayList<StatusDefinitionModel>();
		for(StatusDefinitionEntity tmp : in){
			out.add(entityToModel(tmp));
		}
		
		return out;
		
	}
	
	
	public List<StatusDefinitionModel> getInfoByType(String selectionType) {
		List<StatusDefinitionEntity> entities = new ArrayList<StatusDefinitionEntity>();
		if ("prePay".equals(selectionType)) {//特权订金
			entities = definitionDao.getInfoByType("pepayRefund");
		}else if (StringUtils.equals("noPrePay",selectionType)){//非特权订金
			entities = definitionDao.getInfoByType("itemRefund");
		}else if (StringUtils.equals("NoDealReasons",selectionType)){//留资未成交原因
			entities = definitionDao.getInfoByType("NoDealReasons");
		}
		
		List<StatusDefinitionModel> result =  this.entitysaleModel(entities);
		return result;
	}

	@Override
	public List<String> getSalOrderStatus() {
		return definitionDao.getSalOrderStatus();
	}
}
