//package com.dpmall.datasvr.serviceImpl;
//
//import java.math.BigDecimal;
//import java.sql.Timestamp;
//import java.text.ParseException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.alibaba.dubbo.common.utils.StringUtils;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.dpmall.common.DateUtils;
//import com.dpmall.common.HybrisUtils;
//import com.dpmall.common.TimeScope;
//import com.dpmall.common.WebUtils;
//import com.dpmall.datasvr.api.ISaleLeadsService;
//import com.dpmall.db.bean.SalesLeadsOperationEntity;
//import com.dpmall.db.bean.SalesLeadsOrderEntity;
//import com.dpmall.db.bean.SalesLeadsOrderItemEntity;
//import com.dpmall.db.dao.SalesLeadsOperationDao;
//import com.dpmall.db.dao.SalesLeadsOrderDao;
//import com.dpmall.db.dao.SalesLeadsOrderItemDao;
//import com.dpmall.model.SaleLeadsGoodsModel;
//import com.dpmall.model.SaleLeadsModel;
//import com.dpmall.param.SaleLeadStatisticParam;
//
//
//@Service(value = "saleLeadsService")
//public class SaleLeadsServiceImpl implements ISaleLeadsService {
//
//	private static Logger LOG = LoggerFactory.getLogger(SaleLeadsServiceImpl.class);
//
//	@Autowired
//	private SalesLeadsOrderDao salesLeadsOrderDao;
//
//	@Autowired
//	private SalesLeadsOperationDao salesLeadsOperationDao;
//
//	@Autowired
//	private  SalesLeadsOrderItemDao salesLeadsOrderItemDao;
//
//	/**
//	 * 把entity转换成model
//	 * @param entity 需要转换的entity
//	 * @return 转化后的model
//	 */
//	private SaleLeadsModel entityToModel(SalesLeadsOrderEntity entity) {
//		if (entity==null) {
//			return null;
//		}
//		SaleLeadsModel model=new SaleLeadsModel();
//		model.appointmentTime=entity.appointmentTime == null?null:DateUtils.format(entity.appointmentTime, DateUtils.YYYY_MM_DD_HH_MM_SS);
//		model.budget=entity.budget==null?null:entity.budget.doubleValue();
//		model.callServiceRemark=entity.callServiceRemark;
//		model.callServiceTel=entity.callServiceTel;
//		model.clientAddr=entity.clientAddr;
//		model.finishRemark=entity.finishRemark;
//		model.clientName=entity.clientName;
//		model.clientTel=entity.clientTel;
//		model.closeTime=entity.closeTime==null?null:DateUtils.format(entity.closeTime, DateUtils.YYYY_MM_DD_HH_MM_SS);
//		model.distributeTime=entity.distributeTime==null?null:DateUtils.format(entity.distributeTime, DateUtils.YYYY_MM_DD_HH_MM_SS);
//		model.distributorId=entity.distributorId;
//		model.distributorOperateTime=entity.distributorOperateTime==null?null:DateUtils.format(entity.distributorOperateTime, DateUtils.YYYY_MM_DD_HH_MM_SS);
//		model.distributorUserName=entity.distributorUserName;
//		model.id=entity.id;
//		model.recommendstoreId=entity.recommendstoreId;
//		model.recommendstoreName=entity.recommendstoreName;
//		model.saleLeadsStatus=entity.saleLeadsStatus;
//		model.serviceDate=entity.serviceDate==null?null:DateUtils.format(entity.serviceDate, DateUtils.YYYY_MM_DD_HH_MM_SS);
//		model.serviceAddress=entity.serviceAddress;
//		model.serviceCatelog=entity.serviceCatelog;
//		model.serviceTitle=entity.serviceTitle;
//		model.storeAcceptTime=entity.storeAcceptTime==null?null:DateUtils.format(entity.storeAcceptTime, DateUtils.YYYY_MM_DD_HH_MM_SS);
//		model.style=entity.style;
//		model.total=entity.total==null?null:entity.total.doubleValue();
//		model.orderCode=entity.orderCode;
//		model.acceptStore=entity.acceptStore;
//		model.storeAcceptorRemark=entity.storeAcceptorRemark;
//		model.agencyRemark=entity.agencyRemark;
//		model.storeAcceptor=entity.storeAcceptor;
//		model.brand=entity.brand;
//		model.arriveDate=entity.arriveDate;
//		model.storeAcceptorName=entity.storeAcceptorName;
//		if (entity.isAgency !=null) {
//			model.isAgency  = entity.isAgency;
//		}
//		if (entity.orderPic!=null) {
//			List<String> orderPics = Arrays.asList(entity.orderPic.substring(1,entity.orderPic.length()-1).split(","));
//			for(String pic:orderPics) {
//				model.orderPics.add(WebUtils.get("httpWebImagesUrl")+pic.trim());
//			}
//		}
//		for(SalesLeadsOrderItemEntity item: entity.items) {
//			SaleLeadsGoodsModel goods = new SaleLeadsGoodsModel();
//			goods.category=item.catetory;
//			goods.dealPrice=item.dealPrice.doubleValue();
//			goods.itemNum=item.quantity;
//			model.orderItemList.add(goods);
//		}
//		return model;
//
//	}
//
//	/**
//	 * 把model转换成entity
//	 * @param entity 需要转换的model
//	 * @return 转化后的entity
//	 * @throws ParseException
//	 */
//	private SalesLeadsOrderEntity modelToEntity(SaleLeadsModel model) throws ParseException {
//		SalesLeadsOrderEntity entity=new SalesLeadsOrderEntity();
//		entity.budget=model.budget==null?null:new BigDecimal(model.budget);
//		entity.callServiceRemark=model.callServiceRemark;
//		entity.callServiceTel=model.callServiceTel;
//		entity.clientAddr=model.clientAddr;
//		entity.clientName=model.clientName;
//		entity.clientTel=model.clientTel;
//		entity.distributorId=model.distributorId;
//		entity.distributorUserName=model.distributorUserName;
//		entity.id=model.id;
//		entity.recommendstoreName=model.recommendstoreName;
//		entity.recommendstoreId=model.recommendstoreId;
//		entity.saleLeadsStatus=model.saleLeadsStatus;
//		entity.serviceAddress=model.serviceAddress;
//		entity.serviceCatelog=model.serviceCatelog;
//		entity.serviceTitle=model.serviceTitle;
//		entity.finishRemark=model.finishRemark;
//		entity.serviceDate=StringUtils.isEmpty(model.serviceDate)?null:DateUtils.parse(model.serviceDate, DateUtils.YYYY_MM_DD_HH_MM_SS);
//		entity.style=model.style;
//		entity.total=model.total==null?null:new BigDecimal(model.total);
//		entity.orderCode=model.orderCode;
//		entity.acceptStore=model.acceptStore;
//		entity.storeAcceptorRemark=model.storeAcceptorRemark;
//		entity.agencyRemark=model.agencyRemark;
//		entity.storeAcceptor=model.storeAcceptor;
//		entity.brand=model.brand;
//		entity.isAgency = model.isAgency;
//		entity.orderPic=model.orderPic;
//		return entity;
//	}
//	/**
//	 * 判断是否空值
//	 * @param object
//	 * @return
//	 */
//	private Boolean isNotNull(Object object) {
//		Boolean key = false;
//		if (!(object==null) ) {
//			if (!"".equals(object.toString())) {
//				key = true;
//			}
//		}
//		return key;
//	}
//
//	private Map <String,Object> entityToMap(SalesLeadsOrderEntity entity)  {
//		Map <String,Object> params = new HashMap<String, Object>();
//		if (entity==null) {
//			return params;
//		}
//		params.put("budget", (entity.budget==null?null:entity.budget));
//
//		if(this.isNotNull( entity.callServiceRemark)) {
//			params.put("callServiceRemark", entity.callServiceRemark);
//		}
//		if(this.isNotNull( entity.callServiceTel)) {
//			params.put("callServiceTel", entity.callServiceTel);
//		}
//		if(this.isNotNull( entity.clientAddr)) {
//			params.put("clientAddr", entity.clientAddr);
//		}
//		if(this.isNotNull( entity.clientName)) {
//			params.put("clientName", entity.clientName);
//		}
//		if(this.isNotNull( entity.clientTel)) {
//			params.put("clientTel", entity.clientTel);
//		}
//		if(this.isNotNull( entity.distributorId)) {
//			params.put("distributorId", entity.distributorId);
//		}
//		if(this.isNotNull( entity.distributorId)) {
//			params.put("distributorId", entity.distributorId);
//		}
//		if(this.isNotNull( entity.distributorUserName)) {
//			params.put("distributorUserName", entity.distributorUserName);
//		}
//		if(this.isNotNull( entity.id)) {
//			params.put("id", entity.id);
//		}
//		if(this.isNotNull( entity.recommendstoreName)) {
//			params.put("recommendstoreName", entity.recommendstoreName);
//		}
//		if(this.isNotNull( entity.saleLeadsStatus)) {
//			params.put("saleLeadsStatus", entity.saleLeadsStatus);
//		}
//		if(this.isNotNull( entity.serviceAddress)) {
//			params.put("serviceAddress", entity.serviceAddress);
//		}
//		if(this.isNotNull( entity.serviceCatelog)) {
//			params.put("serviceCatelog", entity.serviceCatelog);
//		}
//		if(this.isNotNull( entity.serviceTitle)) {
//			params.put("serviceTitle", entity.serviceTitle);
//		}
//		if(this.isNotNull( entity.finishRemark)) {
//			params.put("finishRemark", entity.finishRemark);
//		}
//
//		params.put("serviceDate", (entity.serviceDate==null?null:entity.serviceDate));
//
//		if(this.isNotNull( entity.style)) {
//			params.put("style", entity.style);
//		}
//
//		params.put("orderCode", entity.total==null?null:entity.total);
//
//		if(this.isNotNull( entity.orderCode)) {
//			params.put("orderCode", entity.orderCode);
//		}
//		if(this.isNotNull( entity.acceptStore)) {
//			params.put("acceptStore", entity.acceptStore);
//		}
//		if(this.isNotNull( entity.storeAcceptorRemark)) {
//			params.put("storeAcceptorRemark", entity.storeAcceptorRemark);
//		}
//		if(this.isNotNull( entity.agencyRemark)) {
//			params.put("agencyRemark", entity.agencyRemark);
//		}
//		if(this.isNotNull( entity.storeAcceptor)) {
//			params.put("storeAcceptor", entity.storeAcceptor);
//		}
//		if(this.isNotNull( entity.brand)) {
//			params.put("brand", entity.brand);
//		}
//		if(this.isNotNull( entity.isAgency)) {
//			params.put("isAgency", entity.isAgency);
//		}
//		if(this.isNotNull( entity.orderPic)) {
//			params.put("orderPic", entity.orderPic);
//		}
//
//		return params;
//	}
//
//
//	public List<SaleLeadsModel> getOnePage4Distribute(String distributorId, Integer startNum, Integer pageSize,String search) {
//		// TODO Auto-generated method stub
//		List<SaleLeadsModel> out = null;
//		List<SalesLeadsOrderEntity> outEntityList = null;
//		if(StringUtils.isEmpty(distributorId)){
//			outEntityList = salesLeadsOrderDao.getOnePage4Distribute(null,startNum,pageSize,search);
//		}else{
//			outEntityList = salesLeadsOrderDao.getOnePage4Distribute(Long.valueOf(distributorId),startNum,pageSize,search);
//		}
//		if(outEntityList == null || outEntityList.isEmpty()){
//			return null;
//		}
//
//		out = this.entitysaleModel(outEntityList);
//		return out;
//	}
//
//	private List<SaleLeadsModel> entitysaleModel(List<SalesLeadsOrderEntity> in){
//		if(in == null || in.isEmpty()){
//			return null;
//		}
//
//		List<SaleLeadsModel> out = new ArrayList<SaleLeadsModel>();
//		for(SalesLeadsOrderEntity tmp : in){
//			out.add(entityToModel(tmp));
//		}
//
//		return out;
//
//	}
//
//
//	public int distribute(String distributorId, String saleLeadsId, String shopId,String agencyRemark) {
//		String status  = null;
//		String isAgency = null;
//		if ("1".equals(shopId)) {
//			status = "15";//已接单
//			isAgency = "Y";
//		}else {
//			status = "10";//已下派
//			isAgency = "N";
//		}
//
//		int result = salesLeadsOrderDao.distribute(saleLeadsId, shopId,agencyRemark,status,isAgency);
////		if(result>0) {
////			Object distributeResult = this.refuseSalesLeadOrder(saleLeadsId);
////			LOG.info("mothod::distribute::distributeResult:"+distributeResult);
////		}
//
//		LOG.info("result:"+result);
//		return result;
//	}
//	 /**
//     * 经销商拒单
//     * @param distributorId 经销商ID
//     * @param saleLeadsId 销售线索ID
//     * @param rejectType 拒单类型
//     * @param rejectRemark 拒单备注
//     * @return
//     */
//	@Transactional
//	public int reject(String distributorId, String saleLeadsId, String rejectType, String rejectRemark,String operatorBy) {
//
//		Date date = new Date();
//		SalesLeadsOrderEntity entity = new SalesLeadsOrderEntity();
//		entity.id = Long.valueOf(saleLeadsId);
//		entity.refuseTime = date;
//		entity.distributorOperateTime = date;
//		entity.rejectType = rejectType;
//		entity.rejectRemark=rejectRemark;
//		entity.saleLeadsStatus="5";
//
//		int result =  salesLeadsOrderDao.edit(entity);
//		//调用hybris方法更新留资方法
////		if(result>0) {
////		Object rejectResult = this.refuseSalesLeadOrder(saleLeadsId);
////		LOG.info("mothod::reject::rejectResult:"+rejectResult);
////		}else {
////			return 0;
////		}
//
//		SalesLeadsOperationEntity operationEntity = new SalesLeadsOperationEntity();
//		operationEntity.operatorDesc="经销商拒单";
//		operationEntity.operatorType="SaleLeadsServiceImpl：reject";
//		operationEntity.salesLeadsOrder=saleLeadsId;
//		operationEntity.operatorBy=operatorBy==null?"":operatorBy;
//		salesLeadsOperationDao.insert(operationEntity);
//
//		return result;
//
//	}
//
//	/**
//	 * author:daihx
//	 * 经销商 待分配 一页 订单数据
//	 */
//	public List<SaleLeadsModel> getOnePage4Followup(String distributorId, Integer startNum, Integer pageSize,String search,String statusSearch){
//		// TODO Auto-generated method stub
//		List<SaleLeadsModel> out = null;
//
//		List<SalesLeadsOrderEntity> outEntityList = salesLeadsOrderDao.getOnePage4Followup(distributorId, startNum, pageSize,search,statusSearch);
//		if(outEntityList == null || outEntityList.isEmpty()){
//			return null;
//		}
//
//		out = this.entitysaleModel(outEntityList);
//		return out;
//	}
//	/**
//     * 根据条件查询已完结的销售线索订单
//     * @param distributorId 经销商Id
//     * @param distributeTime 订单下派时间
//     * @param storeId
//     * @param saleLeadId
//     * @param clientName
//     * @param clientTel
//     * @param storeName
//     * @param acceptorId
//     * @param startNum
//     * @param pageSize
//     * @return
//     */
//	public List<SaleLeadsModel> getOnePageClosedSaleLeads(String distributorId,TimeScope distributeTime, String storeId,String saleLeadId, String clientName,String clientTel,String storeName,String acceptorId,Integer startNum, Integer pageSize,String search) {
//		List<SaleLeadsModel> accept = null;
//		com.dpmall.common.TimeScope scopeInternal = new com.dpmall.common.TimeScope();
//		scopeInternal.begin = (Timestamp) distributeTime.begin;
//		scopeInternal.end = (Timestamp) distributeTime.end;
//		List<SalesLeadsOrderEntity> acceptEntity = salesLeadsOrderDao.getOnePageClosedSaleLeads(distributorId, scopeInternal, storeId, saleLeadId, clientName, clientTel, storeName, acceptorId,startNum, pageSize,search);
//		if(acceptEntity.isEmpty()){
//			return null;
//		}
//		accept = this.entitysaleModel(acceptEntity);
//		return accept;
//	}
//
//	/**
//     * 根据条件查询已完结的销售线索订单
//     * @param distributorId 经销商Id
//     * @param distributeTime 订单下派时间
//     * @param storeId
//     * @param saleLeadId
//     * @param clientName
//     * @param clientTel
//     * @param storeName
//     * @param acceptorId
//     * @param startNum
//     * @param pageSize
//     * @return
//     */
//	public List<SaleLeadsModel> getOnePageFinishedSaleLeads(String distributorId,String storeId,String acceptorId,String search,Integer startNum, Integer pageSize){
//    		List<SaleLeadsModel> accept = null;
//		List<SalesLeadsOrderEntity> acceptEntity = salesLeadsOrderDao.getOnePageFinishedSaleLeads(distributorId, storeId, acceptorId, search, startNum, pageSize);
//		if(acceptEntity.isEmpty()){
//			return null;
//		}
//		accept = this.entitysaleModel(acceptEntity);
//		return accept;
//	}
//	 /**
//		 * 店铺获取待接单的销售线索
//		 * @param storeId 店铺ID
//		 * @param startNum 上一次加载的最后项位移
//		 * @param pageSize 页的大小
//		 * @return 店铺获取待接单的销售线索列表
//		 */
//	public List<SaleLeadsModel> getOnePage4Accept(String storeId, Integer startNum, Integer pageSize,String search) {
//		List<SaleLeadsModel> accept = null;
//		List<SalesLeadsOrderEntity> acceptEntity = salesLeadsOrderDao.getOnePage4Accept(storeId, startNum, pageSize,search);
//		if(acceptEntity.isEmpty()){
//			return null;
//		}
//		accept = this.entitysaleModel(acceptEntity);
//		return accept;
//	}
//
//	public Integer get2AcceptCount(String storeId) {
//		Integer count = salesLeadsOrderDao.get2AcceptCount(storeId);
//		return count;
//	}
//
//	/**
//	 * author:daihx
//	 * accept方法
//	 * saleLeadsId
//	 */
//	@Transactional
//	public int accept(String acceptorId, String saleLeadsId, String operatorBy) {
//
//		SalesLeadsOrderEntity outEntityList = salesLeadsOrderDao.getSaleLeads(saleLeadsId);
//		if (outEntityList == null) {
//			outEntityList = new SalesLeadsOrderEntity();
//		}
//		outEntityList.saleLeadsStatus = "15";
//		outEntityList.storeAcceptTime = new Date();
//		outEntityList.storeAcceptor = acceptorId;
//
//		int result = salesLeadsOrderDao.edit(outEntityList);
//		// 调用hybris方法更新留资方法
////		if (result > 0) {
////			Object acceptResult = this.refuseSalesLeadOrder(saleLeadsId);
////			LOG.info("mothod::accept::acceptResult:" + acceptResult);
////		} else {
////			return 0;
////		}
//
//		SalesLeadsOperationEntity operationEntity = new SalesLeadsOperationEntity();
//		operationEntity.operatorDesc = "经销商接单";
//		operationEntity.operatorType = "SaleLeadsServiceImpl：accept";
//		operationEntity.salesLeadsOrder = saleLeadsId;
//		operationEntity.operatorBy = operatorBy;
//		salesLeadsOperationDao.insert(operationEntity);
//
//		return result;
//	}
//
//	/**
//	 * @param model 传入的model
//	 * @return 1为更新成功， 0 为失败
//	 * **/
//	@Transactional
//	public int edit(SaleLeadsModel model,String operatorBy) {
//		SalesLeadsOrderEntity entity = null;
//		int result = 0;
//		try {
//			if (model.recommendstoreId != null) {
//				if (model.recommendstoreId == 1) {
//					model.isAgency = "Y";
//				} else {
//					model.isAgency = "N";
//				}
//			}
//
//			entity = modelToEntity(model);
//
//			result=salesLeadsOrderDao.edit(entity);
//
//			// 调用hybris方法更新留资方法
////			if (result > 0) {
////				Object editResult = this.refuseSalesLeadOrder(String.valueOf(entity.id));
////				LOG.info("mothod::edit::editResult:" + editResult);
////			} else {
////				return 0;
////			}
//
//			// 操作类
//			SalesLeadsOperationEntity operationEntity = new SalesLeadsOperationEntity();
//			operationEntity.operatorDesc = "编辑留资订单";
//			operationEntity.operatorType = "SaleLeadsServiceImpl：edit";
//			operationEntity.salesLeadsOrder = String.valueOf(model.id);
//			operationEntity.operatorBy = operatorBy;
//
//			for (SaleLeadsGoodsModel goodsModel : model.orderItemList) {
//				SalesLeadsOrderItemEntity itemEntity = new SalesLeadsOrderItemEntity();
//				itemEntity.catetory = goodsModel.category;
//				itemEntity.dealPrice = new BigDecimal(goodsModel.dealPrice);
//				itemEntity.orderId = Long.parseLong(goodsModel.orderItemId);
//				itemEntity.quantity = goodsModel.itemNum;
//				salesLeadsOrderItemDao.insert(itemEntity);
//			}
//			salesLeadsOperationDao.insert(operationEntity);
//
//
//		}catch (Exception e) {
//			LOG.info(e.getMessage(),e);
//		}
//		return result;
//	}
//
//	 /**
//     * 获取导购员已接单的一页销售线索信息
//     * @param acceptorId 导购员ID
//     * @param startNum 上一次加载的最后项位移
//     * @param pageSize 页大小
//     * @return
//     */
//	public List<SaleLeadsModel> getOnePage4Acceptor2Followup(String acceptorId, Integer startNum, Integer pageSize,String search,String statusSearch){
//		List<SalesLeadsOrderEntity> searchResult = salesLeadsOrderDao.getOnePage4Acceptor2Followup(acceptorId, startNum, pageSize,search,statusSearch);
//		List<SaleLeadsModel> result = new ArrayList<SaleLeadsModel>(searchResult.size());
//		for(SalesLeadsOrderEntity entity:searchResult) {
//			result.add(entityToModel(entity));
//		}
//		return result;
//	}
//
//	/**
//     * 获取导购员已结单的一页销售线索信息
//     * @param acceptorId 导购员ID
//     * @param startNum 上一次加载的最后项位移
//     * @param pageSize 页大小
//     * @return
//     */
//	public List<SaleLeadsModel> getOnePage4AcceptorClosed(String acceptorId, Integer startNum, Integer pageSize,String search) {
//		List<SalesLeadsOrderEntity> searchResult = salesLeadsOrderDao.getOnePage4AcceptorClosed(acceptorId, startNum, pageSize,search);
//		List<SaleLeadsModel> result = new ArrayList<SaleLeadsModel>(searchResult.size());
//		for(SalesLeadsOrderEntity entity:searchResult) {
//			result.add(entityToModel(entity));
//		}
//		return result;
//	}
//	/**
//     * 获取根据form条件查询一页的成功结单的数据
//     * @param form
//     * @param startNum
//     * @param pageSize
//     * @return
//     * @throws ParseException
//     */
//	public List<SaleLeadsModel> getOnePageSuccessOrders(SaleLeadStatisticParam form, Integer startNum,
//			Integer pageSize) {
//		Long storeId = form.storeId;
//		String acceptorName = form.acceptorName;
//		String productCatelog = form.productCatelog;
//		Date fromTime = null;
//		try {
//			if(form.fromTime!=null){
//				fromTime = DateUtils.parse(form.fromTime, DateUtils.YYYY_MM_DD_HH_MM_SS);
//			}
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		Date toTime = null;
//		try {
//			if(form.toTime!=null){
//				toTime = DateUtils.parse(form.toTime, DateUtils.YYYY_MM_DD_HH_MM_SS);
//			}
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		List<SalesLeadsOrderEntity> successOrders =salesLeadsOrderDao.getOnePageSuccessOrders(storeId, acceptorName, productCatelog, fromTime, toTime, startNum, pageSize);
//		List<SaleLeadsModel> result = new ArrayList<SaleLeadsModel>(successOrders.size());
//		for(SalesLeadsOrderEntity entity:successOrders) {
//			result.add(entityToModel(entity));
//		}
//		return result;
//	}
//	/**
//     * 获取根据form条件查询成功结单的金额
//     * @param form
//     * @return
//     */
//	public Double getSuccessOrdersTtlAmount(SaleLeadStatisticParam form) {
//		Long storeId = form.storeId;
//		String acceptorName = form.acceptorName;
//		String productCatelog = form.productCatelog;
//		Date fromTime = null;
//		try {
//			fromTime = DateUtils.parse(form.fromTime, DateUtils.YYYY_MM_DD_HH_MM_SS);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		Date toTime = null;
//		try {
//			toTime = DateUtils.parse(form.toTime, DateUtils.YYYY_MM_DD_HH_MM_SS);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		Double result = salesLeadsOrderDao.getSuccessOrdersTtlAmount(storeId, acceptorName, productCatelog, fromTime, toTime);
//		return result;
//	}
//
//	/**
//     * 经销商批量分配店铺
//     * @param distributorId 经销商ID
//     * @param saleLeadsId2shopId 经销商ID=>shopId
//     * @return 分配的店铺数
//     */
//	@Transactional
//	public int distributeBatch(String distributorId, Map<String, String> saleLeadsId2shopId,String operatorBy,String agencyRemark) {
//		int result=0;
//		try {
//			for(Entry<String, String> entity : saleLeadsId2shopId.entrySet()) {
//				String status  = null;
//				String isAgency = null;
//				if ("1".equals(entity.getValue())) {
//					status = "15";//已接单
//					isAgency = "Y";
////					entity.setValue(distributorId);//经销商id赋值给门店id
//				}else {
//					status = "10";//已下派
//					isAgency = "N";
//				}
//
//
//				int distributeResult =salesLeadsOrderDao.distribute(entity.getKey(), entity.getValue(),agencyRemark,status,isAgency);
////				//调用hybris方法下派订单--distributeSalesLeadOrder
////				if (distributeResult > 0) {
////					Object editResult = this.refuseSalesLeadOrder(entity.getKey());
////					LOG.info("mothod::edit::distributeResult:" + editResult);
////				} else {
////					break;
////				}
//
//
//				//操作类，记录操作人，操作时间
//				SalesLeadsOperationEntity operationEntity=new SalesLeadsOperationEntity();
//				operationEntity.operatorDesc="经销商分配留资订单给店铺";
//				operationEntity.salesLeadsOrder=entity.getKey();
//				operationEntity.operatorType="distributeBatch";
//				operationEntity.operatorBy=operatorBy;
//				salesLeadsOperationDao.insert(operationEntity);
//
//				result++;
//			}
//		} catch (Exception e) {
//			LOG.info(e.getMessage());
//		}
//
//		return result;
//	}
//
//
//	/**
//     * 经销商批量拒单
//     * @param distributorId 经销商ID
//     * @param saleLeadsId 销售线索ID
//     * @param rejectType 拒单类型
//     * @param rejectRemark 拒单备注
//     * @return
//     */
//	@Transactional
//	public int rejectBatch(String distributorId, List<String> saleLeadsIdList, String rejectType, String rejectRemark,String operatorBy) {
//		Integer temp = 1;
//		Integer result = 0;
//		for (String saleLeadsId : saleLeadsIdList) {
//			Date date = new Date();
//			SalesLeadsOrderEntity entity = new SalesLeadsOrderEntity();
//			entity.id = Long.valueOf(saleLeadsId);
//			entity.refuseTime = date;
//			entity.distributorOperateTime = date;
//			entity.rejectType = rejectType;
//			entity.rejectRemark=rejectRemark;
//			entity.saleLeadsStatus="5";
//
//
//			SalesLeadsOperationEntity operationEntity=new SalesLeadsOperationEntity();
//			operationEntity.operatorDesc="经销商拒单";
//			operationEntity.operatorType="SaleLeadsServiceImpl：rejectBatch";
//			operationEntity.salesLeadsOrder=saleLeadsId;
//			operationEntity.operatorBy=operatorBy;
//			salesLeadsOperationDao.insert(operationEntity);//操作类
//
//			Integer result1 = salesLeadsOrderDao.edit(entity);//更细留资下单表
//			Integer result2 = salesLeadsOrderDao.editSaleLeads(entity);//更新留资表中一个字段
//
//
//
//			if (result1 == 1 && result2 ==1) {
//				result = 1;
//			}
//			if(result!=1){
//				temp=0;
//			}
//
//			//调用hybris方法
////			if (temp > 0) {
////				Object rejectBatchResult = this.refuseSalesLeadOrder(saleLeadsId);
////				LOG.info("mothod::rejectBatch::rejectBatchResult:" + rejectBatchResult);
////			} else {
////				break;
////			}
//
//		}
//		return temp;
//	}
//
//	/**
//	 * author:daihx
//	 * 导购员批量接单
//	 * saleLeadsId
//	 */
//	public int acceptBatch(String acceptorId, List<String> saleLeadsId, String operatorBy) {
//
//		Boolean a = true;
//		int b = 0;
//		// 先for循环拿每一条去改状态，得到每一条的结果返回值
//
//		for (String id : saleLeadsId) {
//			SalesLeadsOrderEntity outEntityList = new SalesLeadsOrderEntity();
//			outEntityList.id = Long.parseLong(id);
//			outEntityList.saleLeadsStatus = "15";
//			outEntityList.storeAcceptTime = new Date();
//			outEntityList.storeAcceptor = acceptorId;
//
//			SalesLeadsOperationEntity operationEntity = new SalesLeadsOperationEntity();
//			operationEntity.operatorDesc = "经销商接单";
//			operationEntity.operatorType = "SaleLeadsServiceImpl：acceptBatch";
//			operationEntity.salesLeadsOrder = id;
//			operationEntity.operatorBy = operatorBy;
//			salesLeadsOperationDao.insert(operationEntity);
//			int result = salesLeadsOrderDao.edit(outEntityList);
//			// 调用hybris方法
////			if (result > 0) {
////				Object acceptBatchResult = this.refuseSalesLeadOrder(id);
////				LOG.info("mothod::acceptBatch::acceptBatchResult:" + acceptBatchResult);
////			} else {
////				break;
////			}
//		}
//
//		if (a) {
//			b = 1;
//		} else {
//			b = 0;
//		}
//
//		return b;
//	}
//
//	public void setSalesLeadsOrderDao(SalesLeadsOrderDao salesLeadsOrderDao) {
//		this.salesLeadsOrderDao = salesLeadsOrderDao;
//	}
//
//	/**
//	 * author:daihx
//	 * 根据sealLeadsId 获取详情
//	 * saleLeadsId
//	 */
//	public SaleLeadsModel getSaleLeads(String saleLeadsId) {
//		SaleLeadsModel out = null;
//
//		SalesLeadsOrderEntity outEntityList = salesLeadsOrderDao.getSaleLeads(saleLeadsId);
//		if(outEntityList == null){
//			return null;
//		}
//
//
//		out = this.entityToModel(outEntityList);
//		return out;
//	}
//
//	public int  get2StoreCount(String storeId, String acceptorId, String status) {
//		return salesLeadsOrderDao.get2StoreCount(storeId, acceptorId, status);
//	}
//
//	public int  get2DistributeCount(String distributorId, String status) {
//		return salesLeadsOrderDao.get2DistributeCount(distributorId, status);
//	}
//
//
//
//
//	/**
//	 * 留资订单拒单
//	 */
//	private Object refuseSalesLeadOrder(String pk) {
//		Map<String, Object>params = new HashMap<String, Object>();
//		params.put("type", "SalesLeadsOrder");
//		params.put("pk", pk);
//
//		// 调用hybris方法
//		String resultStr = HybrisUtils.refuseSalesLeadOrder(params);
//		JSONObject jsonObject = JSON.parseObject(resultStr);
//		Map<String, Object> resultMap = (Map) jsonObject;
//		Object result =  resultMap.get("resultCode");
//		return result;
//	}
//
//	public static void main(String[] args) {
//		SaleLeadsServiceImpl impl =new SaleLeadsServiceImpl();
//		Object distributeResults= impl.refuseSalesLeadOrder("8799533944414");
//		LOG.info("mothod::distributeBatch::distributeResult:"+distributeResults);
//	}
//
//
//}
