package com.dpmall.datasvr.serviceImpl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dpmall.common.DateUtils;
import com.dpmall.common.TimeScope;
import com.dpmall.common.WebUtils;
import com.dpmall.datasvr.api.IPrepayService;
import com.dpmall.db.bean.PrePayEntity;
import com.dpmall.db.bean.PrePayItemEntity;
import com.dpmall.db.dao.PrePayDao;
import com.dpmall.model.PrePayItemModel;
import com.dpmall.model.PrepayModel;
import com.dpmall.model.SaleLeadsGoodsModel;

/**
 * 特权定金实现
 * @author river
 * @date 2017-07-14
 */
@Service(value = "prepayService")
public class PrepayServiceImpl implements IPrepayService {
	
	@Autowired
	private PrePayDao prePayDao;
	
	private PrePayEntity modelToEntity(PrepayModel model) {
		PrePayEntity entity=new PrePayEntity();
		entity.clientName=model.clientName;
		entity.clientTel=model.clientTel;
		
		entity.allocatCode=model.allocatCode;
		entity.shippingAddress=model.shippingAddress;
		entity.buyerNick=model.buyerNick;
		entity.productQuantity=model.productQuantity;;
		entity.productBaseprice=model.productBaseprice;
		entity.productTotal=model.productTotal;
		entity.phone1=model.phone1;
		entity.firstName=model.firstName;
		entity.address=model.address;
		entity.orderTotal=model.orderTotal;
		entity.status=model.status;
		entity.id=model.id;
		entity.consignmentCode=model.consignmentCode;
		entity.logisticsInfo=model.logisticsInfo;
		entity.trackingId=model.trackingId;
		entity.deliveryCost=model.deliveryCost;
		entity.salesApplication=model.salesApplication;
		entity.juntanPrice=model.juntanPrice;
		entity.payAmount=model.payAmount;
		entity.serviceAmount=model.serviceAmount;
		entity.deliveryMethods=model.deliveryMethods;
		entity.operateStatus=model.operateStatus;
		entity.name=model.name;
		entity.acceptedBy=model.acceptedBy;
		entity.acceptedComment=model.acceptedComment;
//		entity.acceptedTime = model.acceptedTime;
		entity.agencyComment=model.agencyComment;
		entity.cusComment=model.cusComment;
		entity.deliverPic=model.deliverPic;
		entity.deliveryMode=model.deliveryMode;
		entity.logisticsCompany=model.logisticsCompany;
		entity.serverComment=model.serverComment;
		entity.deliveryPoint=model.deliveryPointOfService;
		entity.returnStatus=model.returnStatus;
		entity.RegionName=model.RegionName;
		entity.CityName=model.CityName;
		entity.DistrictName=model.DistrictName;
		entity.deliveryRemark=model.deliveryRemark;
		entity.cusRefuseComment=model.cusRefuseComment;
		entity.acceptedRefuseComment=model.acceptedRefuseComment;
		entity.discountPrice=model.discountPrice;
//		entity.LimitedTime = model.LimitedTime;
//		entity.finishTime = DateUtils.strToDate(model.finishTime);
		entity.actualPrice = model.actualPrice;
		for(Object obj:model.items) {
			entity.items.add((PrePayItemEntity) obj);
		}
		for(Object obj:model.productItems) {
			entity.productItems.add((PrePayItemEntity) obj);
		}
		
		return entity;
	}
	
	private PrepayModel entityToModel(PrePayEntity entity) {
		if (entity==null) {
			return null;
		}
		PrepayModel model=new PrepayModel();
		model.productType = entity.productType;
		model.allocatCode=entity.allocatCode;
		model.shippingAddress=entity.shippingAddress;
		model.buyerNick=entity.buyerNick;
		model.productQuantity=entity.productQuantity;
		model.productBaseprice=entity.productBaseprice;
		model.productTotal=entity.productTotal;
		model.phone1=entity.phone1;
		model.firstName=entity.firstName;
		model.address=entity.address;
		model.status=entity.status;
		model.deliveryPointOfService=entity.deliveryPoint;
		model.id = entity.id;
		model.consignmentCode=entity.consignmentCode;
		model.logisticsInfo=entity.logisticsInfo;
		model.trackingId=entity.trackingId;
		model.deliveryCost=entity.deliveryCost;
		model.salesApplication=entity.salesApplication;
		model.juntanPrice=entity.juntanPrice;
		model.payAmount=entity.payAmount;
		model.serviceAmount=entity.serviceAmount;
		model.deliveryMethods=entity.deliveryMethods;
		model.operateStatus=entity.operateStatus;
		model.name=entity.name;
		model.acceptedBy=entity.acceptedBy;
		model.acceptedComment=entity.acceptedComment;
		model.acceptedTime = DateUtils.dateToStr(entity.acceptedTime);
		model.agencyComment=entity.agencyComment;
		model.cusComment=entity.cusComment;
		model.deliverPic=entity.deliverPic;
		model.deliveryMode=entity.deliveryMode;
		model.logisticsCompany=entity.logisticsCompany;
		model.serverComment=entity.serverComment;
		model.returnStatus=entity.returnStatus;
		model.RegionName=entity.RegionName;
		model.CityName=entity.CityName;
		model.deliveryPointOfServiceId=entity.deliveryPointOfServiceId;
		model.deliveryRemark=entity.deliveryRemark;
		model.DistrictName=entity.DistrictName;
		model.deliveryRemark=entity.deliveryRemark;
		model.cusRefuseComment=entity.cusRefuseComment;
		model.acceptedRefuseComment=entity.acceptedRefuseComment;
		entity.discountPrice=model.discountPrice;
		model.LimitedTime = limitedTimeFormat(entity.LimitedTime);
		model.finishTime =  DateUtils.dateToStr(entity.finishTime);
	    model.actualPrice = entity.actualPrice;
		model.recommendStore = entity.recommendStore;
		model.agencyO2OStatus = entity.agencyO2OStatus;
		model.storeO2OStatus = entity.storeO2OStatus;
		model.recommendStore = entity.recommendStore;
		model.isDeliverySelf = entity.isDeliverySelf;
		model.orderShipmentsDate = DateUtils.dateToStr(entity.orderShipmentsDate);
		model.deliveryTime = DateUtils.dateToStr(entity.deliveryTime);
		model.appointmentDate =  DateUtils.dateToStr(entity.appointmentDate);
		if (entity.referencedCode == null || "".equals(entity.referencedCode)) {//参考单号为空，则显示发货单号
			model.referencedCode = entity.consignmentCode;
		}else {
			model.referencedCode = entity.referencedCode;
		}
		
		
		
		
		
	    for (PrePayItemEntity item:entity.items) {
			model.orderTotal=model.orderTotal.add(entity.actualPrice==null?BigDecimal.ZERO:entity.actualPrice);
			PrePayItemModel payItemModel = new PrePayItemModel();
			payItemModel= ItemEntityToModel(item);
			model.items.add(payItemModel);
		}
		
		if (entity != null) {
			if (entity.productItems !=null ) {
				for(PrePayItemEntity productItems: entity.productItems ) {
					model.productItems.add(productItems);
				}
			}
		}
		
		if (entity.deliverPic!=null) {
			List<String> deliverPics = Arrays.asList(entity.deliverPic.substring(1, entity.deliverPic.length()-1).split(","));
			for(String image:deliverPics) {
				model.deliverPics.add(WebUtils.get("httpWebImagesUrl")+image.trim());
			}
		}
		
		return model;
	}

	
	private List<PrepayModel> entitysaleModel(List<PrePayEntity> in){
		if(in == null || in.isEmpty()){
			return null;
		}
		
		List<PrepayModel> out = new ArrayList<PrepayModel>();
		for(PrePayEntity tmp : in){
			out.add(entityToModel(tmp));
		}
		
		return out;
		
	}
	
	private PrePayItemModel ItemEntityToModel(PrePayItemEntity itemEntity) {
		PrePayItemModel itemModel = new PrePayItemModel();
		itemModel.splitOrderType = itemEntity.splitOrderType;
		itemModel.code = itemEntity.code;
		itemModel.name = itemEntity.name;
		itemModel.description = itemEntity.description;
		itemModel.category = itemEntity.category;
		itemModel.unit = itemEntity.unit;
		itemModel.netWeight = itemEntity.netWeight;
		itemModel.volume = itemEntity.volume;
		itemModel.size = itemEntity.size;
		itemModel.quantity = itemEntity.quantity;
		itemModel.tmallQuantity = itemEntity.tmallQuantity;
		itemModel.basePrice = itemEntity.basePrice;
		itemModel.totalPrice = itemEntity.totalPrice;
		itemModel.deliveryCost = itemEntity.deliveryCost;
		itemModel.promotionTotalsaved = itemEntity.promotionTotalsaved;
		itemModel.limitedTime = limitedTimeFormat(itemEntity.limitedTime);
		itemModel.writeoffCode = itemEntity.writeoffCode;
		itemModel.pDescription = itemEntity.pDescription;
		itemModel.pName = itemEntity.pName;
		itemModel.salesApplication = itemEntity.salesApplication;
		return itemModel;
	}
	
	public Integer writeoff(String prepayCode, Double ttlAmount, List<SaleLeadsGoodsModel> goodsList) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<PrepayModel> getOnePage2WriteOff(String storeId, TimeScope prepayTime, String clientName,
			String clientTel, Integer startNum, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<PrepayModel> getOnePageClosedPrepay(String storeId, TimeScope prepayTime, String clientName,
			String clientTel, Integer startNum, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<PrepayModel> getOnePage4Distribute(String distributorId, String status, String search,Integer offset,
			Integer pageSize,String statusSearch){
		
			List<PrePayEntity> entities = prePayDao.getOnePage4Distribute(distributorId, status,search, offset, pageSize,statusSearch);
			if (entities.isEmpty() || entities == null) {
				return null;
			}
			List<PrepayModel> models = new ArrayList<PrepayModel>(entities.size());
			models = this.entitysaleModel(entities);
		return models;
	}

	public Integer get2DistributeCount(String distributorId, String status) {
		
		return prePayDao.get2DistributeCount(distributorId, status);
	}

	public List<PrepayModel> getOnePage4StoreId(String storeId, String status, String search, Integer offset, Integer pageSize) {
		List<PrePayEntity> entities = prePayDao.getOnePage4StoreId(storeId, status,search, offset, pageSize);
		if (entities.isEmpty() || entities == null) {
			return null;
		}
		List<PrepayModel> models = new ArrayList<PrepayModel>(entities.size());
		models = this.entitysaleModel(entities);
	return models;
	}

	public Integer get2StoreCount(String storeId, String status) {
		// TODO Auto-generated method stub
		return prePayDao.get2StoreCount(storeId, status);
	}

	public List<PrepayModel> getOnePage4AcceptorId(String acceptorId, String status, String search, Integer startNum, Integer pageSize,String statusSearch){
		

		List<PrePayEntity> entities = prePayDao.getOnePage4AcceptorId(acceptorId,status,search,startNum,pageSize,statusSearch);
		System.out.println(entities);
		if(entities == null || entities.isEmpty()){
			return null;
		}
								
		List<PrepayModel> models  =  new ArrayList<PrepayModel>(entities.size());
		models = this.entitysaleModel(entities);

		return models;
	}

	public Integer get2AcceptorCount(String acceptorId, String status) {
		// TODO Auto-generated method stub
		int result = prePayDao.get2AcceptorCount(acceptorId, status);
		return result;
	}
	
	/**
     * 特权定金获取单据明细
     * @param consignmentId 发货单ID
     * @return 特权定金获取单据明细
     */
	public PrepayModel get4ConsignmentId(String consignmentId) {
		PrepayModel out = null;
		//获取明细 详情
		PrePayEntity outEntityList = prePayDao.get4ConsignmentId(consignmentId);
		if (outEntityList != null) {
			if(outEntityList.productList != null) {
				//byte[] --> Object
				byte [] productList = outEntityList.productList;
				Object obj = null;        
		        try {          
		            ByteArrayInputStream bis = new ByteArrayInputStream (productList);          
		            ObjectInputStream ois = new ObjectInputStream (bis);          
		            obj = ois.readObject();        
		            ois.close();     
		            bis.close();     
		        } catch (IOException ex) {          
		            ex.printStackTrace();     
		        } catch (ClassNotFoundException ex) {          
		            ex.printStackTrace();     
		        }
		        //将Object-->List<String>
		        List<String> proList = ( List<String>) obj;
		        //定义list
		        List<PrePayItemEntity> productItems = new ArrayList<PrePayItemEntity>();
		        for(int i =0 ;i<proList.size();i++) {
		        	PrePayItemEntity entity = new PrePayItemEntity();
		        	//赋值给PrePayItemEntity
		        	String [] newd = proList.get(i).split(",");
		        	//获取订单明细若只有名称，没有价格，数量。则跳过
		        	if (newd.length<3) {
		        		continue ;
		        	}
		        	entity.category = null;
		        	entity.category = newd[0];
		        	//格式化名称
		        	if (newd[0].matches("^A.*")) {
		        		entity.category = "瓷砖";
		        	}
		        	if (newd[0].matches("^B.*")) {
		        		entity.category = "洁具";
		        	}
		        	if (newd[0].matches("^C.*")) {
		        		entity.category = "木地板";
		        	}
		        	if (newd[0].matches("^D.*")) {
		        		entity.category = "背景板";
		        	}
		        	if (newd[0].matches("^E.*")) {
		        		entity.category = "基础建材";
		        	}
		        	if (newd[0].matches("^F.*")) {
		        		entity.category = "家居饰品";
		        	}
		        	entity.quantity = newd[1];
		        	entity.totalPrice = new BigDecimal(newd[2]).setScale(2, BigDecimal.ROUND_HALF_UP);
		        	productItems.add(entity);
		        }
		        //将遍历好的productItems 赋值给outEntityList的productItems
		        outEntityList.productItems = productItems;
			}
			//entity转换Model
			out = this.entityToModel(outEntityList);
		}
		
		return out;
	}

	public Integer distribute(String distributorId, String orderCode, String storeId, String remark) {
		int result = 0;
		prePayDao.distribute(distributorId, orderCode, storeId);//更新状态
		int result2 = prePayDao.distributeO2o(orderCode, remark); //更新备注
		
		if( result2 != 0 ) {
			result =1;
		}
		return result;
	}
	
	@Transactional
	public Integer updateOrder(String orderCode, String status, String remark){
		Integer result =prePayDao.updateO2oOrder(orderCode, remark);
		Integer result2=prePayDao.updateOrder(orderCode, status);
		return result+result2;
	}

	public List<PrepayModel> get4Search(String phone) {
		List<PrepayModel> out = null;

		List<PrePayEntity> outEntityList = prePayDao.get4Search(phone);
		if(outEntityList == null || outEntityList.isEmpty()){
			return null;
		}
								
		out = this.entitysaleModel(outEntityList);
		return out;
	}

	public List<PrepayModel> getReason4Order(String orderStyle) {
		// TODO Auto-generated method stub
		return null;
	}

	public PrepayModel get4priDepositCode(String priDepositCode) {
		PrepayModel model = entityToModel(prePayDao.get4priDepositCode(priDepositCode));
		return model;
	}

	/**
	 * 格式化限制时间
	 * @param in
	 * @return
	 */
	private static String limitedTimeFormat(String in) {
		String out = "";
		if (StringUtils.isEmpty(in)) {
			return out;
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append(in);
		buffer.append(" 至 ");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date newd = sdf.parse(in);
			Calendar ca = Calendar.getInstance();
			ca.setTime(newd);
			ca.add(Calendar.DAY_OF_MONTH, 45);
			buffer.append(sdf.format(ca.getTime()));
			out = buffer.toString();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return out;
	}


}
