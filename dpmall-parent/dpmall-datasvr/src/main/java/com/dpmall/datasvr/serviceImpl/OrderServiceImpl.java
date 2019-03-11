package com.dpmall.datasvr.serviceImpl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.dpmall.common.*;
import com.dpmall.db.bean.*;
import com.dpmall.db.bean.po.OrderDistributePo;
import com.dpmall.db.bean.po.OrderEditStatusPo;
import com.dpmall.db.bean.po.OrderShippedPo;
import com.dpmall.model.in.OrderDistributeModelIn;
import com.dpmall.model.in.OrderEditStatusModelIn;
import com.dpmall.model.in.OrderShippedModelIn;
import com.dpmall.status.OrdersStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dpmall.datasvr.api.IOrderService;
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


	private static String IMAGESURL = "https://sitoms.dpmall.com";

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
		CglibBeanCopierUtils.copyProperties(entity, model);

		model.deliveryTime=DateUtils.dateToStr(entity.deliveryTime);
		model.acceptedTime = DateUtils.dateToStr(entity.acceptedTime);
		model.orderShipmentsDate = DateUtils.dateToStr(entity.orderShipmentsDate);
		model.appointmentDate = DateUtils.dateToStr(entity.appointmentDate);
		model.deliveryPointOfService=entity.deliveryPoint;
		if (model.deliveryMode!=null) {
			if (model.deliveryMode.equals("自己送")) {
				model.deliveryMode="1";
			}
			else if (model.deliveryMode.equals("物流公司")) {
				model.deliveryMode="2";
			}
		}
		if (entity.salesApplication!=null&&entity.salesApplication.equals("Web")) {
			model.OrderStatus=entity.webStatus;
		}
		if (StringUtils.isEmpty(entity.referencedCode)) {//参考单号为空，则显示发货单号
			model.referencedCode = entity.consignmentCode;
		}


		model.orderTotal =BigDecimal.ZERO;
		List<OrderItemModel> newItem = new ArrayList<>();
		for (OrderItemEntity item:entity.items) {
			//合计金额
			model.orderTotal=model.orderTotal.add(item.deliveryCost==null?BigDecimal.ZERO:item.deliveryCost).add(item.payAmount==null?BigDecimal.ZERO:item.payAmount).add(item.serviceAmount==null?BigDecimal.ZERO:item.serviceAmount);
			OrderItemModel itemsModel = new OrderItemModel();
			//Items的entity转换为Model
			itemsModel = itemsEntityToModel(item);
			newItem.add(itemsModel);
		}
		model.items.clear();
		model.items.addAll(newItem);
		
		
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

		if(entity.returnDetails == null||entity.returnDetails.isEmpty() ) {//非空处理
			return model;
		}

		//为returnCheck重新赋值 0：无退货单、1：确定退货、2：确定取消订单”按钮
		if (entity.returnDetails == null) {//退货单为空，为0
			model.returnCheck = "0";
		} else {
			model.returnCheck = "1";//默认1，如无退货单为0，有退货单且订单状态是未发货为2
			if ("WAIT_SELLER_AGREE".equals(model.OrderStatus) && ("ACCEPT".equals(model.status) || "BOOKED".equals(model.status))) {//买家已申请退款
				model.returnCheck = "2";//2的意思是前端显示“确定取消订单”按钮
			}
		}

		List<OrderReturnModel> returnModels  = new ArrayList<>();
		OrderReturnModel returnModel ;

		for(OrderReturnEntity returnEntity : entity.returnDetails) {
			List<OrderReturnDetailsEntity> returnDetailsList =  new ArrayList<OrderReturnDetailsEntity>();
			
			//添加退货单号到集合
			if (returnEntity.returnOderCode!=null) {
				model.returnOderCodes.add(returnEntity.returnOderCode);
			}

//			//赋值给退货明细
			for (OrderReturnDetailsEntity item:returnEntity.returnDetailsList) {
//				//格式化单位
////				 if (item.unit != null) {
////					 item.unit = orderDao.formatUnit(item.unit);
				//为returnCheck重新赋值
				 item.returnCheck = model.returnCheck;
				 returnDetailsList.add(item);
			}

			returnEntity.returnDetailsList = returnDetailsList;
			 //returnDetail的entity转换为Model
			returnModel = returnEntityToModel(returnEntity);

			returnModels.add(returnModel);
		}


		model.returnDetails.clear();
		model.returnDetails.addAll(returnModels);
		
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
		if("1".equals(in.returnStatus)||"2".equals(in.returnStatus)) {//已申请退货
			out.isShowTick = "Y";
		}
		if("2".equals(in.returnStatus)) {//审批通过
			out.isPass = "Y";
		}
		out.returnDetailsList = returnDetailEntityListToModelList(in.returnDetailsList);
		return out;
	}
	
	private String changeReturnStatus(String returnStatus) {
		String returnStatusZH = "";
		if ("1".equals(returnStatus)) {
			returnStatusZH = "审核未通过";
		}else if ("2".equals(returnStatus)) {
			returnStatusZH = "审核通过";
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
		CglibBeanCopierUtils.copyProperties(itemEntity,itemsModel);

		itemEntity.totalPrice=BigDecimal.ZERO;
		itemsModel.totalPrice = (itemEntity.deliveryCost == null ? BigDecimal.ZERO : itemEntity.deliveryCost).add(itemEntity.payAmount == null ? BigDecimal.ZERO : itemEntity.payAmount).add(itemEntity.serviceAmount == null ? BigDecimal.ZERO : itemEntity.serviceAmount);
		//计算单价
		if (itemEntity.quantity == null || "0".equals(itemEntity.quantity)) {
			itemsModel.basePrice = new BigDecimal(0);
		}else {
			itemsModel.basePrice = itemEntity.totalPrice.divide(new BigDecimal(itemEntity.quantity),2,BigDecimal.ROUND_HALF_UP);//总价/数量
		}
		//格式化单位
//		if (itemEntity.unit != null) {
//			itemsModel.unit = orderDao.formatUnit(itemEntity.unit);
//		}
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
		return out;
		
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
		return modelList;
	}

	public Integer get2DistributeCount(String distributorId, String status) {
		return orderDao.get2DistributeCount(distributorId, status);
	}

	/**
	 * 经销商接单
	 */
	@Transactional
	public int acceptOfAgency(OrderDistributeModelIn modelIn) {
		OrderDistributePo po = new OrderDistributePo();
		//转对象
		CglibBeanCopierUtils.copyProperties(modelIn,po);

		po.setAgencyO2OStatus("INPROGRESS");
		if ("1".equals(modelIn.getStoreId())) {// 经销商自己发货，掉发货接口
			po.setStoreId("");
			po.setIsDeliverSelf("Y");
			po.setAcceptTime(new Date());
			po.setOmsStatus(OrdersStatus.ACCEPTED);//待发货
		}else{
			po.setStoreO2OStatus("WAITFORRECEIVE");
			po.setOmsStatus(OrdersStatus.STOREWAITACCEPT);//门店待接单
			po.setIsDeliverSelf("N");
		}

		int result = orderDao.acceptOfAgency(po);//更新
		return result;
	}

	/**
	 * 拒单
	 */
	@Transactional
	public int rejectOfAgency(OrderDistributeModelIn modelIn) {
		OrderDistributePo po = new OrderDistributePo();
		//转对象
		CglibBeanCopierUtils.copyProperties(modelIn,po);
		po.setIsDeliverSelf("Y");
		po.setOmsStatus(OrdersStatus.REJECTED);
		int result = orderDao.rejectOfAgency(po);//更新
		return result;
	}

	/**
	 * 导购员接单
	 * @return 成功返回200
	 */
	@Transactional
	public int acceptOfStore(OrderDistributeModelIn modelIn) {
		OrderDistributePo po = new OrderDistributePo();
		CglibBeanCopierUtils.copyProperties(modelIn,po);

		po.setAcceptTime(new Date());
		po.setAgencyO2OStatus("INPROGRESS");
		po.setStoreO2OStatus("INPROGRESS");
		po.setOmsStatus(OrdersStatus.ACCEPTED);
		po.setIsDeliverSelf("N");

		int result = orderDao.acceptOfStore(po);

		return result;
	}

	/**
	 * 编辑
	 */
	@Override
	@Transactional
	public int editStatus(OrderEditStatusModelIn modelIn) {
		OrderEditStatusPo po = new OrderEditStatusPo();
		int result = 0;
		try {
			CglibBeanCopierUtils.copyProperties(modelIn, po);

			OrderEntity orders = orderDao.getOrderDetails(modelIn.getConsignmentCode());
			if (orders == null) {
				return 0;
			}
			if ("DPRECEIVE".equals(modelIn.getStatus())) {// 如果状态是已签收
				if (StringUtils.isEmpty(orders.getOrderStatus())
						|| "TRADE_FINISHED".equals(orders.getOrderStatus())
						|| "FINISHED_L".equals(orders.getOrderStatus())
						|| "COMPLETED".equals(orders.getOrderStatus())
						) {// 三方状态为空(手工单),订单状态是已完成(正常单)
					po.setAgencyO2OStatus("WAITFORREFUND");// 经销商待还款
					po.setStoreO2OStatus("COMPLETED");// 店员已完成
				} else {
					po.setAgencyO2OStatus("INPROGRESS");// 经销商跟进中
					po.setStoreO2OStatus("COMPLETED");// 店员已完成
				}
			}

			if (StringUtils.isNotEmpty(modelIn.getCertificate())) {//发货凭证
				po.setDeliveryPic(imageProcess(modelIn.getCertificate()));
			}
			if (StringUtils.isNotEmpty(modelIn.getSignUp())) {//签收凭证
				po.setSignPic(imageProcess(modelIn.getSignUp()));
			}

			if (StringUtils.isNotEmpty(modelIn.getStoreId())) {//门店id
				if ("1".equals(modelIn.getStoreId())) {
					po.setStoreId("");
					po.setIsDeliverSelf("Y");
				} else {
					po.setStoreId(modelIn.getStoreId());
					po.setIsDeliverSelf("N");
				}
			}
			result = orderDao.editStatus(po);


		} catch (Exception e) {
			e.printStackTrace();
			result = 0;
		}
		return result;
	}


//	@Override
//	@Transactional
//	public int shipped(OrderShippedModelIn modelIn) {
//		OrderShippedPo po = new OrderShippedPo();
//		CglibBeanCopierUtils.copyProperties(modelIn, po);
//		OrderEntity orders = orderDao.getOrderDetails(modelIn.getConsignmentCode());
//		//不存在该订单
//		if(orders == null){
//			return 0;
//		}
//		if ("Tmall".equals(orders.getSalesApplication())){
//			LogUtil.serviceLogInfo("shipped 天猫订单确认发货流程开始......");
//			try {
////				po.setShipmentsMethod(consignmentType);// 发货方式
//				po.setLogisticsCode("SJZYWL-001");
//				po.setLogisticsName("商家自有物流");
//
//				// 天猫回写
//				if (orders.getReferencedCode() != null) {
//					Map<String, Object> resultMap = synImportDeliverService.deliverGoodsTMall(consignment);// 返回天猫回写结果
//					boolean isSuccess = Boolean.parseBoolean(resultMap.get("isSuccess").toString());
//					if (isSuccess) {
//						LOG.info("AppActionController:shipped 天猫订单确认发货成功");
//					} else {// 回写失败
//						resp.resultCode = ErrorCode.INTERNAL_ERR;
//						resp.message = String.valueOf(resultMap.get("msg"));
//						LOG.info("AppActionController:shipped 天猫订单确认发货失败："+ JSON.toJSONString(resultMap));
//						ChangesRecordUtil.addRecord(consignment, ModifyType.CONSIGNMENT_UPDATE,form.acceptorId, "FAIL","天猫订单确认发货失败：" + JSON.toJSONString(resultMap), form.moblieModel);// 保存操作记录
//						modelService.save(consignment);
//					}
//				} else {
//					LOG.info("AppActionController:shipped 天猫单号为空！");
//				}
//			} catch (Exception e) {
//				LOG.error(e.getMessage(), e);
//			}
//		}
//
//
//		int result = 0;
//	}

	public List<OrderModel> getOnePage4StoreId(String storeId, String status, String acceptorId, String search, Integer offset, Integer pageSize, String statusSearch){
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
		
		OrderEntity outEntityDetail = orderDao.getOrderDetails(consignmentId);
		if (outEntityDetail == null) {
			return null;
		}
		if (!"Tmall".equals(outEntityDetail.salesApplication) && !"JD".equals(outEntityDetail.salesApplication)) {//出去京东以及天猫的订单
			if ("C1".equals(outEntityDetail.splitOrderType)){
				List<OrderItemEntity> newItem = new ArrayList<OrderItemEntity>();
				for (OrderItemEntity item : outEntityDetail.items) {
					String quantityStr =  item.quantity;
					double quantity =Double.parseDouble(quantityStr);//单位：平方厘米
					final double length = outEntityDetail.length.doubleValue();
					final double width = outEntityDetail.width.doubleValue();
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
				outEntityDetail.items = newItem;
			}
		}

		// 获取客户备注 、 客服备注
		if(StringUtils.isNotEmpty(outEntityDetail.getServerComment())) {
			StringBuffer commentBuffer  =new StringBuffer(outEntityDetail.getServerComment());//拼接用
			int a = commentBuffer.indexOf("客服备注");
			int b = commentBuffer.lastIndexOf("客户备注");
			if (a<0||b<0){//不存在以上两个字符，其中的一个
				outEntityDetail.setServerComment("");
				outEntityDetail.setCusComment("");
			}else {
				outEntityDetail.setServerComment(commentBuffer.substring(a,b).toString().trim());
				outEntityDetail.setCusComment(commentBuffer.substring(b,commentBuffer.length()).toString().trim());
			}
		}
		
		//获取部分退货时，退货订单明细
		List<OrderReturnEntity>  returnEntitieList = new ArrayList<OrderReturnEntity>();//获取退货单详情(包括单号)
		returnEntitieList = orderDao.getPartOfReturnDetails(consignmentId);

		if (returnEntitieList == null ) {
			outEntityDetail.returnDetails = null;
		}else {
			outEntityDetail.returnDetails = returnEntitieList;
		}
		out = this.entityToModel(outEntityDetail);
		
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
		returnEntitieList = orderDao.getPartOfReturnDetails(consignmentId);
//		List<String> returnDetailsCode = orderDao.getPartOfReturnCodes(consignmentId);//获取退货号列表
//		if (returnDetailsCode != null) {
//			if (returnDetailsCode.get(0)!=null) {
//				for (String returnCode :returnDetailsCode) {
//					OrderReturnEntity returnEntity = new OrderReturnEntity();
//					returnEntity = orderDao.getPartOfReturnDetails(consignmentId,returnCode);//根据退货单号获取退货单详情
//					returnEntitieList.add(returnEntity);
//				}
//			}
//		}
		
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



	//按照要求处理图片路径
	public String imageProcess(String imagePath) {
		try{
			if (StringUtils.isNotEmpty(imagePath)) {
				imagePath = imagePath.replace("[", "");// 去掉括号
				imagePath = imagePath.replace("]", "");
				if (imagePath.equals("(null)")) {
//					LOG.info("certificate====(null)");
				} else {
					String[] images = imagePath.split(",");
					ArrayList<String> newPath = new ArrayList<String>();
					for (String string : images) {
						if (string.contains(IMAGESURL)) {
							String newimage = string.replace(IMAGESURL, "");
							newPath.add(newimage);
//							LOG.info("imageProcess string=" + string + " new path=" + newimage);
						} else {
							newPath.add(string);
						}
					}
					return newPath.toString();
				}
			}
		}catch(Exception e){
//			LOG.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return null;
	}





}
