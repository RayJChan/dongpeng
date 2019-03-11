package com.dpmall.datasvr.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dpmall.common.DateUtils;
import com.dpmall.common.TimeScope;
import com.dpmall.common.WebUtils;
import com.dpmall.datasvr.api.IOrderService;
import com.dpmall.db.bean.OrderEntity;
import com.dpmall.db.bean.OrderHistoryItemEntity;
import com.dpmall.db.bean.OrderItemEntity;
import com.dpmall.db.bean.OrderReturnDetailsEntity;
import com.dpmall.db.bean.OrderReturnEntity;
import com.dpmall.db.dao.AppOrderDao;
import com.dpmall.model.OrderHistoryItemModel;
import com.dpmall.model.OrderItemModel;
import com.dpmall.model.OrderModel;
import com.dpmall.model.OrderReturnDetailsModel;
import com.dpmall.model.OrderReturnModel;

/**
 * 实物订单服务实现
 * @author river
 * @date 2017-07-14
 */
@Service(value = "orderService")
public class OrderServiceImpl implements IOrderService {
	
	@Autowired
	private AppOrderDao orderDao;
	
	private OrderEntity modelToEntity(OrderModel model) {
		OrderEntity entity=new OrderEntity();
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
		entity.OrderStatus=model.OrderStatus;
		entity.waitShipments = model.waitShipments;
		entity.returnCheck = model.returnCheck;
		for(Object obj:model.items) {
			entity.items.add((OrderItemEntity) obj);
		}
		return entity;
	}
	
	private OrderModel entityToModel(OrderEntity entity) {
		if (entity==null) {
			return null;
		}
		OrderModel model=new OrderModel();
	
		model.clientName=entity.clientName;
		model.allocatCode=entity.allocatCode;
		model.shippingAddress=entity.shippingAddress;
		model.buyerNick=entity.buyerNick;
		model.productQuantity=entity.productQuantity;
		model.productBaseprice=entity.productBaseprice;
		model.productTotal=entity.productTotal;
		model.phone1=entity.phone1;
		model.firstName=entity.firstName;
		model.address=entity.address;
		
		model.deliveryTime=DateUtils.dateToStr(entity.deliveryTime);
		model.acceptedTime = DateUtils.dateToStr(entity.acceptedTime);
		model.orderShipmentsDate = DateUtils.dateToStr(entity.orderShipmentsDate);
		model.appointmentDate = DateUtils.dateToStr(entity.appointmentDate);
		model.agencyO2OStatus=entity.agencyO2OStatus;
		model.storeO2OStatus=entity.storeO2OStatus;
		model.status=entity.status;
		model.deliveryPointOfService=entity.deliveryPoint;
		model.id = entity.id;
		model.waitShipments=entity.waitShipments;
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
		model.agencyComment=entity.agencyComment;
		model.cusComment=entity.cusComment;
		model.deliverPic=entity.deliverPic;
		model.deliveryMode=entity.deliveryMode;
	    model.returnOderCode = entity.returnOderCode;
		if (model.deliveryMode!=null) {
			if (model.deliveryMode.equals("自己送")) {
				model.deliveryMode="1";
			}
			else if (model.deliveryMode.equals("物流公司")) {
				model.deliveryMode="2";
			}
		}
		model.logisticsCompany=entity.logisticsCompany;
		model.serverComment=entity.serverComment;
		model.returnStatus=entity.returnStatus;
		model.RegionName=entity.RegionName;
		model.CityName=entity.CityName;
		model.DistrictName=entity.DistrictName;
		model.isDeliverySelf=entity.isDeliverySelf;
		model.deliveryRemark=entity.deliveryRemark;
		model.cusRefuseComment=entity.cusRefuseComment;
		model.acceptedRefuseComment=entity.acceptedRefuseComment;
		if (entity.salesApplication!=null&&entity.salesApplication.equals("Web")) {
			model.OrderStatus=entity.webStatus;
		}
		else {
			model.OrderStatus=entity.OrderStatus;
		}	
		model.deliveryPointOfServiceId=entity.deliveryPointOfServiceId;
		BigDecimal returnTotalPrice=BigDecimal.ZERO;
		
		if (entity.referencedCode == null || "".equals(entity.referencedCode)) {//参考单号为空，则显示发货单号
			model.referencedCode = entity.consignmentCode;
		}else {
			model.referencedCode = entity.referencedCode;
		}
		//为returnCheck重新赋值
		model.returnCheck = entity.returnCheck;
		 if(model.returnCheck == null || "".equals(model.returnCheck) ) {
			 model.returnCheck = "0";
			}
		if ("WAIT_SELLER_AGREE".equals(model.OrderStatus)) {//买家已申请退款
			if ("ACCEPT".equals(model.status)||"BOOKED".equals(model.status)) {//待发货、已预约
				model.returnCheck = "2";//2的意思是前端显示“确定取消订单”按钮
			}
		}
//		else if("TRADE_CLOSED".equals(model.OrderStatus)||"TRADE_CANCELED".equals(model.OrderStatus)) {
//			model.returnCheck = "2";//2的意思是前端显示“确定取消订单”按钮
//		}
				

		for (OrderItemEntity item:entity.items) {
			item.totalPrice=BigDecimal.ZERO;
			//换算总价格
			 if (model.salesApplication!=null&&model.salesApplication.equals("Web")) {
				item.totalPrice=(item.deliveryCost==null?BigDecimal.ZERO:item.deliveryCost).add(item.juntanPrice==null?BigDecimal.ZERO:item.juntanPrice ).add(item.serviceAmount==null?BigDecimal.ZERO:item.serviceAmount);
				model.orderTotal=model.orderTotal.add(item.deliveryCost==null?BigDecimal.ZERO:item.deliveryCost).add(item.juntanPrice==null?BigDecimal.ZERO:item.juntanPrice).add(item.serviceAmount==null?BigDecimal.ZERO:item.serviceAmount);
			}
			else {
				item.totalPrice=(item.deliveryCost==null?BigDecimal.ZERO:item.deliveryCost).add(item.payAmount==null?BigDecimal.ZERO:item.payAmount).add(item.serviceAmount==null?BigDecimal.ZERO:item.serviceAmount);
				model.orderTotal=model.orderTotal.add(item.deliveryCost==null?BigDecimal.ZERO:item.deliveryCost).add(item.payAmount==null?BigDecimal.ZERO:item.payAmount).add(item.serviceAmount==null?BigDecimal.ZERO:item.serviceAmount);
			}
			//计算单价
			 if (item.quantity == null || "0".equals(item.quantity)) {
				 item.basePrice = new BigDecimal(0);
			 }else {

				 item.basePrice = item.totalPrice.divide(new BigDecimal(item.quantity),2,BigDecimal.ROUND_HALF_UP);//总价/数量
//				 item.basePrice = item.basePrice.setScale(2,BigDecimal.ROUND_HALF_UP);//保留两位小数  

			 }
			
			//格式化单位
			 if (item.unit != null) {
				 item.unit = orderDao.formatUnit(item.unit);
			 }
//			 //计算退款金额
//			 if (item.returnMony!=null&&item.returnQuantity!=null) {
//				item.returnBasePrice=item.returnMony.divide(item.returnQuantity);
//				returnTotalPrice = returnTotalPrice.add(item.returnMony);
//			}
//			if (item.returnOderCode!=null) {
//				model.returnOderCodes.add(item.returnOderCode);
//			}
			
			OrderItemModel itemsModel = new OrderItemModel();
			//Items的entity转换为Model
			itemsModel = itemsEntityToModel(item);
			
			model.items.add(itemsModel);
		}
		
		
		if (entity.signPic!=null) {
			List<String> signPics = Arrays.asList(entity.signPic.substring(1, entity.signPic.length()-1).split(","));
			for(String image:signPics) {
				model.signPics.add(WebUtils.get("httpWebImagesUrl")+image.trim());
			}		
		}
		if (entity.deliverPic!=null) {
			List<String> deliverPics = Arrays.asList(entity.deliverPic.substring(1, entity.deliverPic.length()-1).split(","));
			for(String image:deliverPics) {
				model.deliverPics.add(WebUtils.get("httpWebImagesUrl")+image.trim());
			}
		}

//		if (entity.historyItems != null) {
//			model.historyItems = historyEntityToModel(entity.historyItems);
//		}

//		if(entity.returnOderCode == null) {//如果退货单号为空，直接跳过下面为退货订单赋值的步骤
//			return model;
//		}
		
		if(entity.returnDetails == null||entity.returnDetails.isEmpty() ) {//非空处理
			return model;
		}	
		
 
		OrderReturnModel returnModel = new OrderReturnModel();
		for(OrderReturnEntity returnEntity : entity.returnDetails) {
			List<OrderReturnDetailsEntity> returnDetailsList =  new ArrayList<OrderReturnDetailsEntity>();
			
			//添加退货单号到集合
			if (returnEntity.returnOderCode!=null) {
				model.returnOderCodes.add(returnEntity.returnOderCode);
			}
			
			//赋值给退货明细
			for (OrderReturnDetailsEntity item:returnEntity.returnDetailsList) {
				//格式化单位
				 if (item.unit != null) {
					 item.unit = orderDao.formatUnit(item.unit);
				 }
				//计算退款金额总价
				 if (item.returnPayAmount!=null) {
					 returnEntity.returnTotalPrice = returnEntity.returnTotalPrice.add(item.returnPayAmount);
				}
				//为returnCheck重新赋值
				 item.returnCheck = model.returnCheck;
				 returnDetailsList.add(item);
			}
			returnEntity.returnDetailsList = returnDetailsList;
			 //returnDetail的entity转换为Model
			returnModel = returnEntityToModel(returnEntity);
			
			 model.returnDetails.add(returnModel);
		}
		
		return model;
	}
	
	/**
	 * 退货订单单个对象转换
	 */
	private OrderReturnDetailsModel returnDetailsEntityToModel(OrderReturnDetailsEntity returnEntity) {
		if (returnEntity == null) {
			return null;
		}
		OrderReturnDetailsModel model = new OrderReturnDetailsModel();
		model.productCode = returnEntity.productCode;
		model.category = returnEntity.category;
		model.returnQuantity= returnEntity.returnQuantity;
		model.returnPayAmount= returnEntity.returnPayAmount;
		model.productName= returnEntity.productName;
		model.returnOderCode = returnEntity.returnOderCode;
		model.unit = returnEntity.unit;
		model.returnCheck = returnEntity.returnCheck;
		model.returnStatus = returnEntity.returnStatus;
		return model;
	}
	
	/**
	 * 退货订单对象List转换
	 */
	private List<OrderReturnDetailsModel> returnDetailEntityListToModelList(List<OrderReturnDetailsEntity> in){
		if(in == null || in.isEmpty()){
			return null;
		}
		
		List<OrderReturnDetailsModel> out = new ArrayList<OrderReturnDetailsModel>();
		for(OrderReturnDetailsEntity tmp : in){
			out.add(returnDetailsEntityToModel(tmp));
		}
		
		return out;
	}
	
	
	/**
	 * 退货订单整个集合转换
	 */
	private OrderReturnModel returnEntityToModel(OrderReturnEntity in) {
		if (in == null) {
			return null;
		}
		OrderReturnModel out = new OrderReturnModel();
		out.returnTotalPrice = in.returnTotalPrice;
		out.returnOderCode = in.returnOderCode;
		out.returnStatus = this.changeReturnStatus(in.returnStatus);
		//根据退货状态赋值
		if("WAIT".equals(in.returnStatus)||"APPROVING".equals(in.returnStatus)) {
			out.isShowTick = "Y";
		}
		if("APPROVING".equals(in.returnStatus)) {
			out.isPass = "Y";
		}
		out.returnDetailsList = returnDetailEntityListToModelList(in.returnDetailsList);
		return out;
	}
	
	private String changeReturnStatus(String returnStatus) {
		String returnStatusZH = "";
		if ("CANCELED".equals(returnStatus)) {
			returnStatusZH = "审核未通过";
		}else if ("CANCEL_RETURNREQUEST".equals(returnStatus)) {
			returnStatusZH = "已取消退货退款申请";
		}else if ("INSPECT_RETURNREQUEST".equals(returnStatus)) {
			returnStatusZH = "退货已审";
		}else if ("WAIT".equals(returnStatus)) {
			returnStatusZH = "等待审核";
		}else if ("RECEIVED".equals(returnStatus)) {
			returnStatusZH = "所收到的商品";
		}else if ("SEND_RETURNREQUEST".equals(returnStatus)) {
			returnStatusZH = "退货发货";
		}else if ("APPROVAL_PENDING".equals(returnStatus)) {
			returnStatusZH = "等待批准";
		}else if ("ARRIVE_RETURNREQUEST".equals(returnStatus)) {
			returnStatusZH = "退货到货";
		}else if ("APPROVING".equals(returnStatus)) {
			returnStatusZH = "待退货入库";
		}else if ("CHECK_RETURNREQUEST".equals(returnStatus)) {
			returnStatusZH = "退货到检";
		}else if ("RECEIVING".equals(returnStatus)) {
			returnStatusZH = "正在接收";
		}else if ("CANCELLING".equals(returnStatus)) {
			returnStatusZH = "正在取消";
		}else if ("PAYMENT_REVERSED".equals(returnStatus)) {
			returnStatusZH = "已回收";
		}else if ("PAYMENT_REVERSAL_FAILED".equals(returnStatus)) {
			returnStatusZH = "付款冲销失败";
		}else if ("TAX_REVERSED".equals(returnStatus)) {
			returnStatusZH = "税费冲销";
		}else if ("TAX_REVERSAL_FAILED".equals(returnStatus)) {
			returnStatusZH = "税费冲销失败";
		}else if ("COMPLETED".equals(returnStatus)) {
			returnStatusZH = "已完成";
		}else if ("REVERSING_PAYMENT".equals(returnStatus)) {
			returnStatusZH = "正在冲销付款";
		}else if ("REVERSING_TAX".equals(returnStatus)) {
			returnStatusZH = "正在冲销税费";
		}
		
		return returnStatusZH;
	}
	
	
	
	/**
	 * 历史记录对象转换
	 * @param historyEntity
	 * @return
	 */
	private OrderHistoryItemModel historyEntityToModel(OrderHistoryItemEntity historyEntity) {
		OrderHistoryItemModel historyModel = new OrderHistoryItemModel();
		
		historyModel.deliveryTime = com.dpmall.common.DateUtils.dateToStr(historyEntity.deliveryTime);
		historyModel.acceptedTime = com.dpmall.common.DateUtils.dateToStr(historyEntity.acceptedTime);
		historyModel.finishTime = com.dpmall.common.DateUtils.dateToStr(historyEntity.finishTime);
		historyModel.orderShipmentsDate = com.dpmall.common.DateUtils.dateToStr(historyEntity.orderShipmentsDate);
		historyModel.appointmentDate = com.dpmall.common.DateUtils.dateToStr(historyEntity.appointmentDate);
		
		historyModel.recommendStore = historyEntity.recommendStore;
		historyModel.cusComment = historyEntity.cusComment;
		historyModel.serverComment = historyEntity.serverComment;
		historyModel.cusRefuseComment = historyEntity.cusRefuseComment;
		historyModel.agencyComment = historyEntity.agencyComment;
		historyModel.refuseType = historyEntity.refuseType;
		historyModel.refuseComment = historyEntity.refuseComment;
		historyModel.acceptedRefuseComment = historyEntity.acceptedRefuseComment;
		historyModel.deliverPic = historyEntity.deliverPic;
		historyModel.acceptedBy = historyEntity.acceptedBy;
		historyModel.acceptedComment = historyEntity.acceptedComment;
		historyModel.isDeliverySelf = historyEntity.isDeliverySelf;
		historyModel.stockout = historyEntity.stockout;
		historyModel.returnStatus = historyEntity.returnStatus;
		historyModel.deliveryRemark = historyEntity.deliveryRemark;
		historyModel.signPic = historyEntity.signPic;
		historyModel.waitShipments = historyEntity.waitShipments;
		historyModel.storeO2OStatus = historyEntity.storeO2OStatus;
		historyModel.agencyO2OStatus = historyEntity.agencyO2OStatus;
		
		return historyModel;
	}
	
	/**
	 * Item的Entiy 转换为 Model
	 * @param itemEntity
	 * @return
	 */
	private OrderItemModel itemsEntityToModel(OrderItemEntity itemEntity) {
		OrderItemModel itemsModel = new OrderItemModel();
		itemsModel.splitOrderType = itemEntity.splitOrderType;
		itemsModel.packQua = itemEntity.packQua;
		itemsModel.code = itemEntity.code;
		itemsModel.name = itemEntity.name;
		itemsModel.description = itemEntity.description;
		itemsModel.category = itemEntity.category;
		itemsModel.unit = itemEntity.unit;
		itemsModel.netWeight = itemEntity.netWeight;
		itemsModel.volume = itemEntity.volume;
		itemsModel.size = itemEntity.size;
		itemsModel.quantity = itemEntity.quantity;
		itemsModel.tmallQuantity = itemEntity.tmallQuantity;
		itemsModel.basePrice = itemEntity.basePrice;
		itemsModel.totalPrice = itemEntity.totalPrice;
		itemsModel.deliveryCost = itemEntity.deliveryCost;
		itemsModel.promotionTotalsaved = itemEntity.promotionTotalsaved;
		itemsModel.payAmount = itemEntity.payAmount;
		itemsModel.juntanPrice = itemEntity.juntanPrice;
		itemsModel.serviceAmount = itemEntity.serviceAmount;
		itemsModel.productCode = itemEntity.productCode;
		itemsModel.productCategory = itemEntity.productCategory;
		itemsModel.returnMony = itemEntity.returnMony;
		itemsModel.returnQuantity = itemEntity.returnQuantity;
		itemsModel.returnBasePrice = itemEntity.returnBasePrice;
		itemsModel.returnOderCode = itemEntity.returnOderCode;
		return itemsModel;
	}
	
	
	private List<OrderModel> entitysaleModel(List<OrderEntity> in){
		if(in == null || in.isEmpty()){
			return null;
		}
		
		List<OrderModel> out = new ArrayList<OrderModel>();
		for(OrderEntity tmp : in){
			out.add(entityToModel(tmp));
		}
		List<OrderModel> result = new ArrayList<OrderModel>();
		for (OrderModel model :out) {//重新为returnCheck赋值
			if(model.returnCheck == null || "".equals(model.returnCheck) ) {
				model.returnCheck = "0";
			}
			if ("WAIT_SELLER_AGREE".equals(model.OrderStatus)) {//买家已申请退款
				if ("ACCEPT".equals(model.status)||"BOOKED".equals(model.status)) {//待发货、已预约
					model.returnCheck = "2";//2的意思是前端显示“确定取消订单”按钮
				}
			}
//			else if("TRADE_CLOSED".equals(model.OrderStatus)||"TRADE_CANCELED".equals(model.OrderStatus)) {
//				model.returnCheck = "2";//2的意思是前端显示“确定取消订单”按钮
//			}
			
			result.add(model);
		}
		
		return result;
		
	}
	
	
	
	public int reject(String distributorId, String orderCode, String rejectType, String rejectRemark) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<OrderModel> getOnePage4Followup(String distributorId, Integer offset, Integer pageSize) {
		// TODO Auto-generated method stub
		List<OrderModel> out = null;

		List<OrderEntity> outEntityList = orderDao.getOnePage4Followup(distributorId,offset,pageSize);
		if(outEntityList == null || outEntityList.isEmpty()){
			return null;
		}
						
		out = this.entitysaleModel(outEntityList);
		return out;
	}

	public List<OrderModel> getOnePageClosedOrder(String distributorId, TimeScope distributeTime, String storeId,
			String orderCode, String clientName, String clientTel, Integer startNum, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 店铺获取待接单的实物订单
	 * @param storeId 店铺ID
	 * @param offset 上一次加载的位移
	 * @param pageSize 页的大小
	 * @return 店铺获取待接单的实物订单列表
	 */
	public List<OrderModel> getOnePage4Accept(String storeId, Integer offset, Integer pageSize) {
		List<OrderModel> orderModel = null;
		
		List<OrderEntity> orderEntityList = orderDao.getOnePage4Accept(storeId,offset,pageSize);
		if(orderEntityList == null || orderEntityList.isEmpty()){
			return null;
		}
		orderModel = this.entitysaleModel(orderEntityList);
		return orderModel;
	}

	public Integer get2AcceptCount(String storeId) {
		return orderDao.get2AcceptCount(storeId);
	}

	/**
     * 导购员接单
     * @return 成功返回200
     */
	public int accept(String acceptorId, String orderCode, String acceptComment) {
		OrderEntity entity = new OrderEntity();
		
		entity.acceptedBy = acceptorId;
		entity.acceptedComment = acceptComment;
		entity.acceptedTime = new Date();
		/*entity.status = "15"; */
		entity.orderCode = orderCode;
		
		int result = orderDao.edit(entity);
		
		return result;
	}

	/**
     * 确认发货
     * @return 成功返回200
     */
	public int deliver(String orderCode) {
		int count = 0;
		Date date = new Date();
		OrderEntity entity = new OrderEntity();
		//orderCode 为空 返回错误代码 500
		if (orderCode == null) {	
			return count;
		}
		//赋值给实体类
		entity.orderCode = orderCode;
		entity.deliveryTime = date;
		entity.status = "20";
		
		int count1 = orderDao.deliver4Consignments(entity);
		int count2 = orderDao.edit(entity);
		if(count1 != 0 && count2 != 0 ) {
			count =1;
		}
		return count;
	}

	public List<OrderModel> getOnePage4Acceptor2Followup(String acceptorId, Integer offset, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<OrderModel> getOnePage4AcceptorClosed(String acceptorId, Integer offset, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<OrderModel> getOnePage4Distribute(String distributorId, String status, String search,Integer offset,
			Integer pageSize,String statusSearch){
		List<OrderEntity> entities = orderDao.getOnePage4Distribute(distributorId, status, search,offset, pageSize,statusSearch);
		List<OrderModel> modelList = new ArrayList<OrderModel>(entities.size());
		List<OrderModel> result = new ArrayList<OrderModel>();
		modelList=entitysaleModel(entities);	
		if(modelList != null) {
			for(OrderModel model : modelList) {
				if (model.consignmentCode ==null) {
					continue;
				}
				model.returnPriceSum = orderDao.getReturnPriceSum(model.consignmentCode);
				result.add(model);
			}
		}
		return result;
	}

	public Integer get2DistributeCount(String distributorId, String status) {
		return orderDao.get2DistributeCount(distributorId, status);
	}

	/**
     * 经销商下派到店铺
     * @param distributorId 经销商ID
     * @param orderCode 订单编码
     * @param storeId 店铺ID
     * @param remark 备注
     */
	@Transactional
	public int distribute(String distributorId, String orderCode, String storeId, String remark) {
		int result = 0;
		orderDao.distribute4O2o(orderCode, storeId, remark);//更新备注
		int result2 = orderDao.distribute4Consignment(orderCode, storeId); //更新状态
		
		if( result2 != 0 ) {
			result =1;
		}
		return result;
	}

	public List<OrderModel> getOnePage4StoreId(String storeId, String status,String acceptorId,String search,Integer offset, Integer pageSize,String statusSearch){
		List<OrderEntity> entities = orderDao.getOnePage4StoreId(storeId, status,acceptorId,search, offset, pageSize,statusSearch);
		List<OrderModel> result = new ArrayList<OrderModel>(entities.size());
		result=entitysaleModel(entities);	
		return result;
	}

	/**
     * 实物类门店订单状态条数
     * @param storeId 经销商ID
     * @param status 状态
     * @return 经销商待分配的实物订单数
     */
	public Integer get2StoreCount(String storeId, String acceptorId,String status) {
		return orderDao.get2StoreCount(storeId,acceptorId, status);
	}

	/**
     * 实物类导购员订单状态条数
     * @param acceptorId 导购员ID
     * @param status 状态
     * @return 实物类导购员订单状态条数
     */
	public Integer get2AcceptorCount(String acceptorId, String status) {
		
		return orderDao.get2AcceptorCount(acceptorId, status);
	}

	public List<OrderModel> getOnePage4AcceptorId(String acceptorId, String status, Integer startNum, Integer pageSize) {
		// TODO Auto-generated method stub
		List<OrderModel> out = null;

		List<OrderEntity> outEntityList = orderDao.getOnePage4AcceptorId(acceptorId,status,startNum,pageSize);
		if(outEntityList == null || outEntityList.isEmpty()){
			return null;
		}
								
		out = this.entitysaleModel(outEntityList);
		return out;
	}

	public OrderModel getOrderDetails(String consignmentId) {
		OrderModel out = null;
		
		OrderEntity outEntityList = orderDao.getOrderDetails(consignmentId);
		if (outEntityList == null) {
			return null;
		}
		if (!"Tmall".equals(outEntityList.salesApplication) && !"JD".equals(outEntityList.salesApplication)) {//出去京东以及天猫的订单
			if ("C1".equals(outEntityList.splitOrderType)){
				List<OrderItemEntity> newItem = new ArrayList<OrderItemEntity>();
				for (OrderItemEntity item : outEntityList.items) {
					String quantityStr =  item.quantity;
					double quantity =Double.parseDouble(quantityStr);//单位：平方厘米
					final double length = outEntityList.length.doubleValue();
					final double width = outEntityList.width.doubleValue();
					final double area = ((length * width) / 100);
					final int splice = (int) Math.ceil((quantity / area));//片数
					item.quantity = splice+"";//赋值
//						//价格换算
//						BigDecimal bp = item.basePrice ;
//						bp = bp.multiply(new BigDecimal(10000));//乘1000
//						bp = bp.setScale(2,BigDecimal.ROUND_HALF_UP); //换算后保留两位小数
//						item.basePrice  = bp; //赋值
					
//						//单价
//						 if (item.quantity == null || "0".equals(item.quantity)) {
//							 item.basePrice = new BigDecimal(0);
//						 }else {
//							 item.basePrice = item.totalPrice.divide(new BigDecimal(item.quantity),BigDecimal.ROUND_HALF_UP);
//						 }
					newItem.add(item);
				}
				outEntityList.items = newItem;
			}
		}

		// 获取客户备注 、 客服备注
		List<OrderEntity> commentsList = orderDao.getComments(consignmentId);
		if(commentsList != null) {
			StringBuffer buybuffer  =new StringBuffer();//拼接用
			StringBuffer sellerbuffer = new StringBuffer();
			int i = 1;
			for (OrderEntity entity :commentsList) {
				//拼接字符串
				if (entity.buyerMessage != null && !"null".equals(entity.buyerMessage.trim())) {//客户备注
					buybuffer.append(i+":"+entity.buyerMessage+",");
				}
				if (entity.sellerMessage != null && !"null".equals(entity.sellerMessage.trim()) ) {//客服备注
					sellerbuffer.append(i+":"+entity.sellerMessage+",");
				}
				++i;
			}
			//去除最后一个逗号
			if (buybuffer.length() > 0) {
				outEntityList.cusComment = buybuffer.substring(0, buybuffer.length()-1);
			}
			if (sellerbuffer.length() > 0) {
				outEntityList.serverComment = sellerbuffer.substring(0, sellerbuffer.length()-1);
			}
		}
		
		//获取部分退货时，退货订单明细
		List<OrderReturnEntity>  returnEntitieList = new ArrayList<OrderReturnEntity>();//获取退货单详情(包括单号)

		List<String> returnDetailsCode = orderDao.getPartOfReturnCodes(consignmentId);//获取退货号列表
		if (returnDetailsCode != null && !returnDetailsCode.isEmpty() && returnDetailsCode.size()>0) {
			if (returnDetailsCode.get(0)!=null) {
				for (String returnCode :returnDetailsCode) {
					OrderReturnEntity returnEntity = new OrderReturnEntity();
					returnEntity = orderDao.getPartOfReturnDetails(consignmentId,returnCode);//根据退货单号获取退货单详情
					returnEntitieList.add(returnEntity);
				}
			}
		}
		
		if (returnEntitieList == null ) {
			outEntityList.returnDetails = null;
		}else {
			outEntityList.returnDetails = returnEntitieList;
		}
		out = this.entityToModel(outEntityList);
		
		if (out != null && out.returnDetails != null) {
			out.returnDetails = this.sortReturnDetails(out.returnDetails);
		}
		
	
		return out;
	}

	/**
     * 实物类获取退货单据明细
     * @param consignmentId 发货单ID
     * @return 订单详情
     */
	public OrderModel getReturnRequestDetails(String consignmentId) {
		OrderModel out = null;

		OrderEntity outEntityList = orderDao.getReturnRequestDetails(consignmentId);
					
		

		//获取部分退货时，退货订单明细
		List<OrderReturnEntity>  returnEntitieList = new ArrayList<OrderReturnEntity>();//获取退货单详情(包括单号)

		List<String> returnDetailsCode = orderDao.getPartOfReturnCodes(consignmentId);//获取退货号列表
		if (returnDetailsCode != null) {
			if (returnDetailsCode.get(0)!=null) {
				for (String returnCode :returnDetailsCode) {
					OrderReturnEntity returnEntity = new OrderReturnEntity();
					returnEntity = orderDao.getPartOfReturnDetails(consignmentId,returnCode);//根据退货单号获取退货单详情
					returnEntitieList.add(returnEntity);
				}
			}
		}
		
		if (returnEntitieList == null ) {
			outEntityList.returnDetails = null;
		}else {
			outEntityList.returnDetails = returnEntitieList;
		}
		out = this.entityToModel(outEntityList);
		
		if (out != null && out.returnDetails != null) {
			out.returnDetails = this.sortReturnDetails(out.returnDetails);
		}
		
		out = this.entityToModel(outEntityList);
		
		return out;
	}
	
	/**
	 * 排序选择框
	 */
	private List<OrderReturnModel> sortReturnDetails (List<OrderReturnModel> returnModels ){
		Collections.sort(returnModels, new Comparator<OrderReturnModel>(){//
            public int compare(OrderReturnModel o1, OrderReturnModel o2) {
            	if((o1.isShowTick.compareTo(o2.isShowTick)) < 0){//o1<o2
            		if(o1.isPass.compareTo(o2.isPass)<0) {
            			return 1;
            		}else if (o1.isPass.compareTo(o2.isPass) == 0) {
            			 return 0;
            		}else if (o1.isPass.compareTo(o2.isPass)> 0){
            			return -1;
            		}
                }
            	if((o1.isShowTick.compareTo(o2.isShowTick)) == 0){//o1 = o2
            		if(o1.isPass.compareTo(o2.isPass)<0) {
            			return 1;
            		}else if (o1.isPass.compareTo(o2.isPass) == 0) {
            			 return 0;
            		}else if (o1.isPass.compareTo(o2.isPass)> 0){
            			return -1;
            		}
                }
                return -1;
            }  
        });
		 return returnModels;
	}
	
}
