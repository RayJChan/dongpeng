package com.dpmall.datasvr.serviceImpl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dpmall.common.ExportExcelUtil;
import com.dpmall.common.HybrisUtils;
import com.dpmall.common.OssInfoUtils;
import com.dpmall.common.PrivateKeyUtils;
import com.dpmall.datasvr.api.ISaleLeadsOrderService;
import com.dpmall.datasvr.api.ISendMsgService;
import com.dpmall.db.bean.*;
import com.dpmall.db.bean.po.SalOrderItemsPo;
import com.dpmall.db.bean.po.SalesLeadsOrderPo;
import com.dpmall.db.dao.SalesLeadsOperationDao;
import com.dpmall.db.dao.SalesLeadsOrderDao;
import com.dpmall.db.dao.SalesLeadsOrderItemDao;
import com.dpmall.db.dao.UtilsDao;
import com.dpmall.model.*;
import com.dpmall.model.in.SalOrderGoodsModelIn;
import com.dpmall.model.in.SalOrderInfoModelIn;
import com.dpmall.param.SaleLeadsOrderStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;


@Service(value = "saleLeadsOrderService")
public class SaleLeadsOrderServiceImpl implements ISaleLeadsOrderService{

	@Autowired
	private SalesLeadsOrderDao salesLeadsOrderDao;

	@Autowired
	private SalesLeadsOrderItemDao salesLeadsOrderItemDao;

	@Autowired
	private SalesLeadsOperationDao salesLeadsOperationDao;

    @Autowired
    private UtilsDao utilsDao;

	@Autowired
	private ISendMsgService sendMsgService;


	@Autowired
	private HybrisUtils hybrisUtils;





//--------------------------------工具------------------------------

	/***
	 *
	 */

	private String getUserNameById (String id) {
		return salesLeadsOperationDao.getOperatorByNameById(id);
	}

	/**
	 * entity-->model
	 */
	private SaleLeadsOrderModel entityToModel(SalesLeadsOrderEntity in){
		SaleLeadsOrderModel out = new SaleLeadsOrderModel();
		if (in != null){
			BeanUtils.copyProperties(in,out);
		}
		return out;
	}
	/**
	 * entityList-->modelList
	 */
	private List<SaleLeadsOrderModel> entityListToModelList(List<SalesLeadsOrderEntity> in){
		List<SaleLeadsOrderModel> out = new ArrayList<>();
		if (CollectionUtils.isEmpty(in)){
			return out;
		}
		for (SalesLeadsOrderEntity entity :in){
			SaleLeadsOrderModel model = this.entityToModel(entity);
			out.add(model);
		}
		return out;
	}


	/**
	 * 价钱格式化
	 * 
	 */
	private  String priceFormat (Double num){
		if (num == null||num==0)	{
			return "0";
		}
//		DecimalFormat format = new DecimalFormat("￥##,###,###,###,##0.00");
//		return format.format(num);
		return num.toString();
	}

	/**
	 * 价钱格式化
	 *
	 */
	private  String priceFormat (String price){
		if (StringUtils.isEmpty(price) || "0".equals(price)) {
			price =   "0";
		}
//		double num = Double.valueOf(price);
//		DecimalFormat format = new DecimalFormat("￥##,###,###,###,##0.00");
//		return format.format(num);
		return price;
	}


	/**
	 * 价钱格式化(短信用)
	 *
	 */
	private  String priceFormatOfMsg (String price){
		if (StringUtils.isEmpty(price) || "0".equals(price)) {
			price =   "0";
		}
		double num = Double.valueOf(price);
		DecimalFormat format = new DecimalFormat("￥##,###,###,###,##0.00");
		return format.format(num);
	}



	/**
	 * 价钱反格式化
	 * 
	 */
	private  String priceFormatSwitch (String price){
		String result = "";
		if (StringUtils.isEmpty(price) || "0".equals(price)) {
			result = "0";
		}
//		result = price.replaceAll(",","").replace("￥","");
		result =price;
		return result;
	}


	/**
	 * 待接单
	 */
	private   List<SaleLeadsOrderModel> waitAcceptOfList(List<SalesLeadsOrderEntity> in){
		List<SaleLeadsOrderModel> out = new ArrayList<SaleLeadsOrderModel>();
		if (CollectionUtils.isEmpty(in)){
			return out;
		}
		//处理电话号码和地址
		StringBuffer buffer = new StringBuffer();
		if (CollectionUtils.isNotEmpty(in)){
			for (SalesLeadsOrderEntity entity :in){
				SaleLeadsOrderModel model = new SaleLeadsOrderModel();
				model = this.entityToModel(entity);
				//地址
				model.setServerAddress("********");

				//电话
				buffer.append(entity.getClientTel());
				buffer.replace(3, buffer.length(), "********");
				model.setClientTel(buffer.toString());

				out.add(model);
				//清空buffer
				buffer.delete(0, buffer.length());
			}
		}
		return out;
	}

	/**
	 * 跟进中
	 */
	private   List<SaleLeadsOrderModel> fllowingOfList(List<SalesLeadsOrderEntity> in,String agencyId){
		List<SaleLeadsOrderModel> out = new ArrayList<SaleLeadsOrderModel>();
		if (CollectionUtils.isEmpty(in)){
			return out;
		}
		for (SalesLeadsOrderEntity entity : in){
			SaleLeadsOrderModel model =new SaleLeadsOrderModel();
			model = this.entityToModel(entity);
			//在经销商列表显示 ，状态为下派至门店
			if ("N".equals(entity.getIsAgency())&&StringUtils.isNotEmpty(agencyId)) {
				model.setStatusDescription("门店:"+entity.getStatusDescription());
				//门店未接单,在经销商列表中隐藏电话、地址
				if( "STOREWAITACCEPT".equals(entity.getStatus())){
					//地址
					model.setServerAddress("********");
					//电话
					StringBuffer buffer = new StringBuffer();
					buffer.append(entity.getClientTel());
					buffer.replace(3, buffer.length(), "********");
					model.setClientTel(buffer.toString());
				}
			}

			out.add(model);
		}
		return out;
	}

	/**
	 * 已完成
	 */
	private   List<SaleLeadsOrderModel> completedOfList(List<SalesLeadsOrderEntity> in){
		List<SaleLeadsOrderModel> out = new ArrayList<SaleLeadsOrderModel>();
		if (CollectionUtils.isEmpty(in)){
			return out;
		}
		for (SalesLeadsOrderEntity entity : in){
			SaleLeadsOrderModel model =new SaleLeadsOrderModel();
			model = this.entityToModel(entity);

			//为上一个订单状态赋值
			if (StringUtils.isNotEmpty(model.getLastStatus())){
				model.setLastStatusDescription(SaleLeadsOrderStatus.ENGLISHTOCHS(model.getLastStatus()));
			}
			//格式化总价格
			if(StringUtils.isNotEmpty(model.getPayPrice())){
				model.setPayPrice(this.priceFormat(Double.valueOf(model.getPayPrice())));
			}
			if ("Y".equals(model.getIsAgency())) {
				model.setStoreName(model.getAgencyName());//门店名称显示为经销商名称
			}
			out.add(model);
		}
		return out;
	}

	/**
	 * 图片处理
	 */
private List<SaleLeadsOrderPictureModel> pictrueUtils (String in){
	List<SaleLeadsOrderPictureModel> out = new ArrayList<>();
	if (StringUtils.isEmpty(in)){
		return  out;
	}

	out = JSON.parseArray(in,SaleLeadsOrderPictureModel.class);

	List<SaleLeadsOrderPictureModel> result = new ArrayList<>();
	if (out== null){
		return result;
	}

	//拼接url
	StringBuffer buffer = new StringBuffer();
	for(SaleLeadsOrderPictureModel picture :out){
		buffer.append(OssInfoUtils.getOssUrl());
		buffer.append(picture.getPictureUrl());
		picture.setPictureUrl(buffer.toString());
		result.add(picture);
		buffer.delete(0,buffer.length());//清空buffer
	}
	return out;
}

	/**保存操作记录**/
	private  int operationHistory (String operatorBy,String operatorDesc,String operatorType,String saleLeadsOrderId,String remark,Date operateTime){
		//操作记录
		OperatorRoleEntity role = utilsDao.getOperatorByRole(operatorBy);
		SalesLeadsOperationEntity operationEntity = new SalesLeadsOperationEntity();
		operationEntity.operateId = PrivateKeyUtils.getPkByRandom_ms();
		operationEntity.operatorDesc = operatorDesc;
		operationEntity.operatorType = operatorType;
		operationEntity.salesLeadsOrder = saleLeadsOrderId;
		operationEntity.operatorBy = operatorBy;
		operationEntity.operatorByName  = role.getUserName();
		operationEntity.agencyCode = role.getAgencyCode();
		operationEntity.operatorRemark = remark;
		operationEntity.operatorTimeSave = operateTime;
		if (!"agency".equals(role.getRole())) {
			operationEntity.storeName = role.getStoreName();
			operationEntity.operatorByName  = role.getStoreName();
		}
		int i = salesLeadsOperationDao.insert(operationEntity);//保存
		return i;

	}


	/**
	 * 刷新状态
	 */
	private   String refreshStatus(String pk) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", "SalesLeadsOrder");
		params.put("pk", pk);

		// 调用hybris方法
		String resultStr = hybrisUtils.refreshStatus(params);
		JSONObject jsonObject = JSON.parseObject(resultStr);
		Map<String, Object> resultMap = (Map) jsonObject;
		String result = resultMap.get("resultCode").toString();
		return result;
	}

//----------------------------------正式-------------------------------
	@Override
	public List<SaleLeadsOrderModel> getList4Agency(String agencyId, String listStatus,Integer pageNum, Integer pageSize, String search,String statusSearch) {
		List<SaleLeadsOrderModel> out = new ArrayList<SaleLeadsOrderModel>();

		//列表数量
		String totalCount = salesLeadsOrderDao.getListCount("Y",agencyId,"", listStatus,search,SaleLeadsOrderStatus.CHSTOENGLISH(statusSearch));

		if("0".equals(totalCount)){
			return out;
		}

		//经销商
		List<SalesLeadsOrderEntity> in = salesLeadsOrderDao.getList4AgencyOrStore("Y",agencyId,"", listStatus,pageNum,pageSize,search,SaleLeadsOrderStatus.CHSTOENGLISH(statusSearch));

		if (CollectionUtils.isEmpty(in)){
			return out;
		}
		if ("1".equals(listStatus)) {
			out = this.waitAcceptOfList(in);
		}
		else if ("2".equals(listStatus)) {
			out = this.fllowingOfList(in,agencyId);

		}
		else if ("3".equals(listStatus)) {
			out = this.completedOfList(in);
		}

		out.get(0).setTotalCount(totalCount);//列表数量，只有第一个元素有
		return out;
	}

	@Override
	public List<SaleLeadsOrderModel> getList4Store(String storeId, String listStatus,Integer pageNum, Integer pageSize, String search,String statusSearch) {
		List<SaleLeadsOrderModel> out = new ArrayList<SaleLeadsOrderModel>();

		//列表数量
		String totalCount = salesLeadsOrderDao.getListCount("N", "",storeId, listStatus, search,SaleLeadsOrderStatus.CHSTOENGLISH(statusSearch));
		if("0".equals(totalCount)){
			return out;
		}
		//经销商
		List<SalesLeadsOrderEntity> in = salesLeadsOrderDao.getList4AgencyOrStore("N","",storeId,listStatus,pageNum,pageSize,search,SaleLeadsOrderStatus.CHSTOENGLISH(statusSearch));
        if (CollectionUtils.isEmpty(in)){
            return out;
        }
		if ("1".equals(listStatus)) {
			out = this.waitAcceptOfList(in);
		}
		else if ("2".equals(listStatus)) {
			out = this.fllowingOfList(in,"");
		}
		else if ("3".equals(listStatus)) {
			out = this.completedOfList(in);
		}
		out.get(0).setTotalCount(totalCount);//列表数量，只有第一个元素有

		return out;
	}


	@Override
	public SalOrderListCountModel getListConut4Agency(String agencyId) {

		SalOrderListCountModel out = new SalOrderListCountModel();
		if (StringUtils.isEmpty(agencyId)) {
			return out;
		}
		out.setWaittingCount(salesLeadsOrderDao.getListCount("Y", agencyId, "", "1", "",""));
		out.setFllowingCount(salesLeadsOrderDao.getListCount("Y", agencyId, "", "2", "",""));
		out.setCompletedCount(salesLeadsOrderDao.getListCount("Y", agencyId, "", "3", "",""));
		return out;
	}

	@Override
	public SalOrderListCountModel getListConut4Store(String storeId) {

		SalOrderListCountModel out = new SalOrderListCountModel();
		if (StringUtils.isEmpty(storeId)) {
			return out;
		}
		out.setWaittingCount(salesLeadsOrderDao.getListCount("N", "", storeId, "1", "",""));
		out.setFllowingCount(salesLeadsOrderDao.getListCount("N", "", storeId, "2", "",""));
		out.setCompletedCount(salesLeadsOrderDao.getListCount("N", "", storeId, "3", "",""));
		return out;
	}

	/**
	 * 经销商批量拒单
	 * saleLeadsOrderIdList 留资单ID
	 * rejectType 拒单类型
	 * rejectRemark 拒单备注
	 */
	@Transactional
	public Integer reject(List<String> saleLeadsOrderIdList, String refuseType, String refuseRemark, String operatorBy) {
		int temp = 1;
		int result = 0;
		for (String saleLeadsOrderId : saleLeadsOrderIdList) {
			//更新留资单
			Date date = new Date();
			SalesLeadsOrderPo Po = new SalesLeadsOrderPo();
			Po.setId(Long.valueOf(saleLeadsOrderId));
			Po.setRefuseRemark(refuseRemark);
			Po.setRefuseType(refuseType);
			Po.setRefuseTime(date);
			Po.setSaleLeadsOrderStatus(SaleLeadsOrderStatus.REJECTED);
			Po.setSaleLeadsOrderStatusName(SaleLeadsOrderStatus.ENGLISHTOCHS(SaleLeadsOrderStatus.REJECTED));
			Po.setAppStatusofAgency(SaleLeadsOrderStatus.REJECTED);//目的是取消app列表状态
			Po.setAppStatusofStore(SaleLeadsOrderStatus.REJECTED);

			//保存操作记录
			int operationResult = this.operationHistory(operatorBy,"经销商拒单","agencyReject",saleLeadsOrderId,"",new Date());

			Integer result1 = salesLeadsOrderDao.edit(Po);//更新留资单
			Integer result2 = salesLeadsOrderDao.editSaleLeads(Po);//留资线索

			if (result1 == 1 && result2 == 1) {
				result = 1;
			}
			if (result != 1) {
				temp = 0;
			}

		}
		return temp;
	}

	/**
	 *  经销商接单
	 *  saleLeadsOrderId2shopId
	 *  operatorBy
	 */
	@Override
	@Transactional
	public Integer agencyAccept(List<String> saleLeadsOrderIds,String stroeId, String agencyRemark, String operatorBy) {
		int result = 0;
		Date date = new Date();
		for (String salId:saleLeadsOrderIds) {
			if(StringUtils.isEmpty(salId)){
				continue;
			}
			SalesLeadsOrderPo Po = new SalesLeadsOrderPo();
			Po.setId(Long.valueOf(salId));//留资单id
//			Po.setAcceptTime(date);//接单时间
			Po.setAgencyAcceptTime(date);//经销商接单时间
			Po.setAgencyAccept(operatorBy);//接单经销商
			Po.setAppStatusofAgency(SaleLeadsOrderStatus.FLLOWING);//经销商列表状态
			Po.setAgencyRemark(agencyRemark);//经销商备注

			//判断接单至经销商or门店
			String operatorType = "";
			if ("1".equals(stroeId)) {
				Po.setIsAgency("Y");
				Po.setSaleLeadsOrderStatus(SaleLeadsOrderStatus.ACCEPTED);//状态
				Po.setSaleLeadsOrderStatusName(SaleLeadsOrderStatus.ENGLISHTOCHS(SaleLeadsOrderStatus.ACCEPTED));

				operatorType = "agencyAcceptToAgency";

			} else {
				Po.setIsAgency("N");
				Po.setSuggestStore(stroeId);//推荐门店
				Po.setAppStatusofStore(SaleLeadsOrderStatus.WAITTING);//门店列表状态
				Po.setSaleLeadsOrderStatus(SaleLeadsOrderStatus.STOREWAITACCEPT);//状态
				Po.setSaleLeadsOrderStatusName(SaleLeadsOrderStatus.ENGLISHTOCHS(SaleLeadsOrderStatus.STOREWAITACCEPT));
				operatorType = "agencyAcceptToStore";
			}
			//更新留资单表
			salesLeadsOrderDao.edit(Po);

			//操作类，记录操作人，操作时间
			int operationResult = this.operationHistory(operatorBy,"经销商接单",operatorType,salId,"",new Date());
			result++;
		}

		return result;
	}

	/**
	 *  门店接单
	 */
	@Override
	@Transactional
	public Integer storeAccept(List<String> saleLeadsOrderIds, String storeRemark, String operatorBy) {
		int result = 0;
		Date date = new Date();
		for (String salId:saleLeadsOrderIds){
			SalesLeadsOrderPo Po = new SalesLeadsOrderPo();
			Po.setAppStatusofAgency(SaleLeadsOrderStatus.FLLOWING);//经销商列表状态
			Po.setAppStatusofStore(SaleLeadsOrderStatus.FLLOWING);//门店列表状态
			Po.setSaleLeadsOrderStatus(SaleLeadsOrderStatus.ACCEPTED);//状态
			Po.setSaleLeadsOrderStatusName(SaleLeadsOrderStatus.ENGLISHTOCHS(SaleLeadsOrderStatus.ACCEPTED));
			Po.setId(Long.valueOf(salId));
			Po.setOrderGuide(operatorBy);//接单员
			Po.setStoreAcceptRemark(storeRemark);//店员备注
			Po.setAcceptTime(date);

			//更新留资单表
			int i = salesLeadsOrderDao.edit(Po);

			//操作类，记录操作人，操作时间
			int operationResult = this.operationHistory(operatorBy,"门店接单","storeAccept",salId,"",new Date());

			if (i!=0){
				String hybrisResult = refreshStatus(salId);
				if (!"200".equals(hybrisResult)){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚
					break;
				}
			}
			result++;
//
//			try{
//			//发送短信
//			StoreOfSalEntity store = salesLeadsOrderDao.getStoreBySal(salId);
//			if (store!=null ){
//				boolean sendMsgSuess = sendMsgService.sendMsgSalAccept(store);
//				if (!sendMsgSuess){//发送短息失败
//					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//					break;//手动开启回滚
//				}
//				result++;
//			}
//			}catch (Exception e ){
//				e.printStackTrace();
//				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//				break;//手动开启回滚
//			}
		}

		return result;
	}


	/***
	 * 详情
	 */
	@Override
	public SaleLeadsOrderDetailsModel getDetails(String saleLeadsOrderId,String operatorById) {
		SaleLeadsOrderDetailsModel result = new SaleLeadsOrderDetailsModel();

		//1.获取详情
		SalesLeadsOrderEntity detailsIn = salesLeadsOrderDao.getDetails(saleLeadsOrderId);
		if(saleLeadsOrderId==null){
			return result;
		}
		BeanUtils.copyProperties(detailsIn,result);//复制对象
		//待接单
		if (SaleLeadsOrderStatus.WAITTING.equals(detailsIn.getAppStatusOfAgency())
				|| SaleLeadsOrderStatus.WAITTING.equals(detailsIn.getAppStatusOfStore())) {
			//地址
			result.setServerAddress("********");
			//电话
			StringBuffer buffer = new StringBuffer();
			buffer.append(detailsIn.getClientTel());
			buffer.replace(3, buffer.length(), "********");
			result.setClientTel(buffer.toString());
		}
		//已完成 或 图片有值 显示详情
		else if (SaleLeadsOrderStatus.COMPLETED.equals(detailsIn.getAppStatusOfAgency())
				|| SaleLeadsOrderStatus.COMPLETED.equals(detailsIn.getAppStatusOfStore())
				||StringUtils.isNotEmpty(detailsIn.getPictureNames())){
			//获取商品列表
			List<SalesLeadsOrderItemEntity> item = salesLeadsOrderItemDao.getItemByOrderId(saleLeadsOrderId);
			List<SaleLeadOrderItemModel> itemModels = new ArrayList<>();
			if (CollectionUtils.isNotEmpty(item)){

				double orderPrice = 0;
				for (SalesLeadsOrderItemEntity entity : item){
					SaleLeadOrderItemModel model = new SaleLeadOrderItemModel();
					BeanUtils.copyProperties(entity,model);
					model.setPrice(this.priceFormat(entity.getPrice()));
					itemModels.add(model);
					orderPrice +=(entity.getPrice() == null?0:entity.getPrice());//计算
				}
				result.setItems(itemModels);

				//优惠金额
				result.setDiscountAmount(this.priceFormat(detailsIn.getDiscountAmount()));
				//支付金额
				result.setPayPrice(this.priceFormat(detailsIn.getPayPrice()));
				//订单金额
				result.setOrderPrice((new BigDecimal(result.getPayPrice()).add(new BigDecimal(result.getDiscountAmount()))).toString());
			}
			//获取图片
			result.setPictureNames(this.pictrueUtils(detailsIn.getPictureNames()));
		}


		//2.获取部分操作记录
		SalesLeadsOrderOperationModel operationRecord = new SalesLeadsOrderOperationModel();
		//2-1 获取A部分操作记录
		List<String> operaRecordA = new ArrayList<>();
		if(StringUtils.isNotEmpty(detailsIn.getDistributer())){
			operaRecordA.add("派单人:"+detailsIn.getDistributer());
		}
		if(StringUtils.isNotEmpty(detailsIn.getAgencyAccept())){
			operaRecordA.add("接单经销商:"+detailsIn.getAgencyAccept());
		}
		if(StringUtils.isNotEmpty(detailsIn.getStoreAccept())){
			operaRecordA.add("接单门店:"+detailsIn.getStoreAccept());
		}
		if(StringUtils.isNotEmpty(detailsIn.getOrderGuide())){
			operaRecordA.add("接单导购:"+detailsIn.getOrderGuide());
		}
		if(StringUtils.isNotEmpty(detailsIn.getCreatedTime())){
			operaRecordA.add("建单时间:"+detailsIn.getCreatedTime());
		}
		if(StringUtils.isNotEmpty(detailsIn.getOrdersTime())){
			operaRecordA.add("派单时间:"+detailsIn.getOrdersTime());
		}
		if(StringUtils.isNotEmpty(detailsIn.getAgencyAcceptTime())){
			operaRecordA.add("经销商接单时间:"+detailsIn.getAgencyAcceptTime());
		}
		if(StringUtils.isNotEmpty(detailsIn.getStoreAcceptTime())){
			operaRecordA.add("门店接单时间:"+detailsIn.getStoreAcceptTime());
		}

		operationRecord.setOperationRecord(operaRecordA);
//		BeanUtils.copyProperties(detailsIn,operationRecord);

		//2-2 获取状态和更新时间(B部分)
		List<SalOrderOperationDetailEntity> statusAndTimeIn = salesLeadsOperationDao.getTimeAndStatus(saleLeadsOrderId, operatorById);
		List<SalOrderOperationDetailModel>  statusAndTimeOut = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(statusAndTimeIn)){
			StringBuilder builder=new StringBuilder();
			for (SalOrderOperationDetailEntity entity : statusAndTimeIn){
				SalOrderOperationDetailModel model = new SalOrderOperationDetailModel();
				BeanUtils.copyProperties(entity,model);
				//拼接状态描述 如："订单“已联系”时间:"
//				builder.append("订单“");
//				builder.append(entity.getStatus());
//				builder.append("”时间:");
//				model.setStatus(builder.toString());
//				builder.delete(0,builder.length());//清空builder

				statusAndTimeOut.add(model);
			}
		}
		operationRecord.setStatusAndUpStringTime(statusAndTimeOut);

		result.setOperationRecord(operationRecord);
		return result;
	}

	/**
	 * 添加备注
	 */
	@Override
	@Transactional
	public String addRemarks(String saleLeadsOrderId,String agencyRemark, String storeRemark,String operatorBy) {
		String result = "";
		String operatorDesc = "";
		if(StringUtils.isEmpty(agencyRemark) && StringUtils.isEmpty(storeRemark)){
			return "无备注信息";
		}
		//添加备注
		SalesLeadsOrderPo Po = new SalesLeadsOrderPo();
		Po.setId(Long.valueOf(saleLeadsOrderId));
		Po.setAgencyRemark(agencyRemark);
		Po.setStoreAcceptRemark(storeRemark);
		 int i = salesLeadsOrderDao.edit(Po);

		if (StringUtils.isNotEmpty(agencyRemark)){
			operatorDesc = "更新经销商备注";
			result =  "经销商备注更新成功";
		}else if (StringUtils.isNotEmpty(storeRemark)){
			operatorDesc = "更新门店备注";
			result =  "门店备注更新成功";
		}

		//操作类，记录操作人，操作时间
		int operationResult = this.operationHistory(operatorBy,operatorDesc,"UpdateRemarks",saleLeadsOrderId,"",new Date());

		if (i!=0){
			String hybrisResult = refreshStatus(saleLeadsOrderId);
			if (!"200".equals(hybrisResult)){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚
				result =  "备注更新失败";
			}
		}

		return result;
	}


	/**
	 * 更新客户信息
	 */
	@Override
	@Transactional
	public int updateCustomerInfo(SaleLeadsOrderModel in,String operatorBy) {
		if (in.getId()== null){
			return 0;
		}
		SalesLeadsOrderPo Po = new SalesLeadsOrderPo();
		BeanUtils.copyProperties(in,Po);

		//处理省市区(未做完)

		int i = salesLeadsOrderDao.edit(Po);

		//操作类，记录操作人，操作时间
		if (i!=0){
			int operationResult = this.operationHistory(operatorBy,"更新客户信息","UpdateCustomerInfo",in.getId().toString(),"",new Date());
			String hybrisResult = refreshStatus(in.getId().toString());
			if (!"200".equals(hybrisResult)){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚
				i = 0;
			}
		}

		return i;
	}

	/**
	 * 更新订单进度
	 */
	@Override
	@Transactional
	public int updateOrderProgress(String statusName, Long saleLeadsOrderId,String remark, String operatorBy,String failType) {
		if (saleLeadsOrderId==null) {
			return 0;
		}

		//中文-->英文
		String status = SaleLeadsOrderStatus.CHSTOENGLISH(statusName);
		if (StringUtils.isEmpty(status)) {
			return 0;
		}

		String lastStatus = salesLeadsOrderDao.getLastStatus(saleLeadsOrderId.toString());


		SalesLeadsOrderPo Po = new SalesLeadsOrderPo();
		Po.setId(saleLeadsOrderId);
		Po.setSaleLeadsOrderStatus(status);
		Po.setSaleLeadsOrderStatusName(SaleLeadsOrderStatus.ENGLISHTOCHS(status));
		Po.setLastStatus(lastStatus);
		if(SaleLeadsOrderStatus.FAILED.equals(status)){
			Po.setAppStatusofStore(SaleLeadsOrderStatus.COMPLETED);
			Po.setAppStatusofAgency(SaleLeadsOrderStatus.COMPLETED);
			Po.setFailType(failType);
			Po.setFailReason(remark);
		}


		int i = salesLeadsOrderDao.edit(Po);


		//操作类，记录操作人，操作时间
		if (i != 0) {
			int operationResult = this.operationHistory(operatorBy,Po.getSaleLeadsOrderStatusName(),"UpdateOrderProgress",saleLeadsOrderId.toString(),remark,new Date());
			if(SaleLeadsOrderStatus.FAILED.equals(status)){
				try {
					//发送短信

					StoreOfSalEntity store = salesLeadsOrderDao.getStoreBySal(saleLeadsOrderId.toString());
					if (store != null) {
						boolean sendMsgSuess = sendMsgService.sendMsgSalFailed(store.getCustomerPhone(), store.getStoreName());
						if (!sendMsgSuess) {//发送短息失败
							TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚
							i = 0;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚
				}

			}
			String hybrisResult = refreshStatus(saleLeadsOrderId.toString());
			if (!"200".equals(hybrisResult)){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚
				i = 0;
			}


		}



		return i;
	}

	/**
	 * 填写订单信息(提交)
	 */
	@Override
	@Transactional
	public int updateSalOrderInfo(SalOrderInfoModelIn modelIn) {

		SalesLeadsOrderPo vo = new SalesLeadsOrderPo();
		vo.setAppStatusofAgency(SaleLeadsOrderStatus.COMPLETED);//appStatus
		vo.setAppStatusofStore(SaleLeadsOrderStatus.COMPLETED);//appStatus
		vo.setSaleLeadsOrderStatus(SaleLeadsOrderStatus.SUCCESS);
		vo.setSaleLeadsOrderStatusName(SaleLeadsOrderStatus.ENGLISHTOCHS(SaleLeadsOrderStatus.SUCCESS));
		Date now = new Date();
		vo.setFinishedTime(now);
		int result = this.update(modelIn,vo);
		//操作类，记录操作人，操作时间
//		if (result != modelIn.getSaleLeadsOrderGoods().size()) {
			int operationResult = this.operationHistory(modelIn.getOperatorBy(),"填写订单信息","updateSalOrderInfo",modelIn.getSaleLeadsOrderId().toString(),"",now);

			try {
				//发送短信
				StoreOfSalEntity store = salesLeadsOrderDao.getStoreBySal(modelIn.getSaleLeadsOrderId().toString());
				if (store != null ) {
					boolean sendMsgSuess = sendMsgService.sendMsgSalSuccess(store.getCustomerPhone(), this.priceFormatOfMsg(modelIn.getPayPrice()));
					if (!sendMsgSuess) {//发送短息失败
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚
						result = 0;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚
			}

		if (result!=0){
			String hybrisResult = refreshStatus(modelIn.getSaleLeadsOrderId().toString());
			if (!"200".equals(hybrisResult)){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚
				result=0;
			}
		}

//		}

		return result;
	}

	/**
	 * 填写订单信息(只保存不提交)
	 */
	@Override
	@Transactional
	public int saveSalOrderInfo(SalOrderInfoModelIn modelIn) {

		SalesLeadsOrderPo Po = new SalesLeadsOrderPo();
		Po.setAppStatusofAgency(SaleLeadsOrderStatus.FLLOWING);//appStatus
		Po.setAppStatusofStore(SaleLeadsOrderStatus.FLLOWING);//appStatus
		Po.setSaleLeadsOrderStatus(SaleLeadsOrderStatus.ACCEPTED);
		Po.setSaleLeadsOrderStatusName(SaleLeadsOrderStatus.ENGLISHTOCHS(SaleLeadsOrderStatus.ACCEPTED));

		
		return  this.update(modelIn,Po);

	}


	/***
	 * 跟新订单信息（通用）
	 */
	private int update (SalOrderInfoModelIn modelIn,SalesLeadsOrderPo Po){
		Po.setId(modelIn.getSaleLeadsOrderId());//id
		Po.setPayPrice(this.priceFormatSwitch(modelIn.getPayPrice()));//支付金额
		Po.setDiscountAmount(this.priceFormat(modelIn.getDiscountAmount()));//优惠金额
		String amount = new BigDecimal(Po.getPayPrice()).add(new BigDecimal(Po.getDiscountAmount())).toString();
		Po.setAmount(amount);//总金额
		if (modelIn.getPictureNames() != null) {
			Po.setPictureNames(JSON.toJSONString(modelIn.getPictureNames()));//图片单据
		}
		//更新备注信息 (需要判断门店or经销商)
		String isAgency = salesLeadsOrderDao.getIsAgency(modelIn.getSaleLeadsOrderId().toString());
		if ("N".equals(isAgency)) {
			Po.setStoreAcceptRemark(modelIn.getRemark());
		} else {
			Po.setAgencyRemark(modelIn.getRemark());
		}
		//更新留资单表
		int salOrderResult = salesLeadsOrderDao.edit(Po);

		//1、获取该留资单所含有的商品的id
		int result = 0;
		Set<String> itemIds = salesLeadsOrderItemDao.getItemIdByOrderId(modelIn.getSaleLeadsOrderId().toString());

		//2、判断入参中的商品id是否存在以上集合
		if (modelIn.getSaleLeadsOrderGoods() !=null){


			for (SalOrderGoodsModelIn goodsModelIn : modelIn.getSaleLeadsOrderGoods()) {
				SalOrderItemsPo itemsPo = new SalOrderItemsPo();
				//复制对象
				BeanUtils.copyProperties(goodsModelIn, itemsPo);
				itemsPo.setSalesLeadsOrderId(modelIn.getSaleLeadsOrderId().toString());//留资单id
				itemsPo.setAmount(this.priceFormatSwitch(goodsModelIn.getAmount()));//合计金额
				itemsPo.setUnitPrice(this.priceFormatSwitch(goodsModelIn.getUnitPrice()));//单价
				if ("new".equals(goodsModelIn.getOrderItemId())) {
					//添加
					itemsPo.setOrderItemId(String.valueOf(PrivateKeyUtils.getPkByRandom_ms()));
					int insert = salesLeadsOrderItemDao.insert(itemsPo);
					result += insert;
				} else {
					if (itemIds.contains(goodsModelIn.getOrderItemId())) {//存在
						//更新
						itemIds.remove(goodsModelIn.getOrderItemId());//从set中去除元素
						itemsPo.setIsDelete("0");
						int update = salesLeadsOrderItemDao.edit(itemsPo);
						result += update;
					}
				}
			}
		}
		if (itemIds != null && itemIds.size() > 0) {
			//删除
			int delete = salesLeadsOrderItemDao.delete(itemIds);
		}
		return result == 0?salOrderResult:result;
	}



	/**
	 * 查看更新状态时的备注
	 */
	@Override
	public String getUpdateStatusRemark(String operationId) {
		String remark = "";
		SalOperationRemarkEntity in = salesLeadsOperationDao.getRemarkByOperationId(operationId);
		if ("未成交".equals(in.getOperationType())){
			if(StringUtils.isEmpty(in.getOperatorRemark())){
				remark ="原因:" +in.getFailReasons();
			}else{
				StringBuffer buffer = new StringBuffer();
				buffer.append("原因:");
				buffer.append(in.getFailReasons());
				buffer.append("  备注:");
				buffer.append(in.getOperatorRemark().trim());
				remark = buffer.toString();
			}
		}else if (StringUtils.isEmpty(in.getOperatorRemark())){
			remark = "暂无备注";
		}else {
			remark = in.getOperatorRemark().trim();
		}
		return remark;
	}

	@Override
	public List<SalesLeadsOperationModel> getHistory4Oms(String salOrderCode, String agencyCode, String startTime, String endTime, Integer pageNum, Integer pageSize){
	List<SalesLeadsOperationModel> out = new ArrayList<>();
		String count = salesLeadsOperationDao.getHistoryConut4Oms(salOrderCode.toUpperCase(),agencyCode,startTime,endTime);
		if ("0".equals(count)) {
			return out;
		}
		List<SalesLeadsOperationEntity> in = salesLeadsOperationDao.getHistory4Oms(salOrderCode.toUpperCase(),agencyCode,startTime,endTime,pageNum,pageSize);
		for (SalesLeadsOperationEntity entity :in ){
			SalesLeadsOperationModel model = new SalesLeadsOperationModel();
			BeanUtils.copyProperties(entity,model);
			model.setCount(count);
			out.add(model);
		}
		return out;

	}

	@Override
	public List<SalOrderItem4OmsModel> getSalOrderItem4Oms(String salesLeadsOrderId) {
		List<SalOrderItem4OmsModel> out = new ArrayList<>();
		int count = salesLeadsOrderItemDao.getSalOrderItemCount4Oms(salesLeadsOrderId);
		if (count == 0 ){
			return out;
		}
		List<SalOrderItem4OmsEntity> in = salesLeadsOrderItemDao.getSalOrderItem4Oms(salesLeadsOrderId);
		for (SalOrderItem4OmsEntity entity : in){
			SalOrderItem4OmsModel model = new SalOrderItem4OmsModel();
			BeanUtils.copyProperties(entity,model);
			model.setCount(count);
			out.add(model);
		}
		return out;

	}

	@Override
	public String exportHistory4Oms(OutputStream out, String salOrderCode, String agencyCode, String startTime, String endTime) throws IOException {
		List<SalesLeadsOperationEntity> in = salesLeadsOperationDao.exportHistory4Oms(salOrderCode.toUpperCase(),agencyCode,startTime,endTime);
		List<Map<String,Object>> contents = new ArrayList<>();
		String count = salesLeadsOperationDao.getHistoryConut4Oms(salOrderCode.toUpperCase(),agencyCode,startTime,endTime);
		if ("0".equals(count)) {
			return count;
		}
		for (SalesLeadsOperationEntity entity :in ){
			Map<String,Object> map = new HashMap<>();
			map.put("operatorByName",StringUtils.isEmpty(entity.getOperatorByName())?"":entity.getOperatorByName());
			map.put("operatorTime",StringUtils.isEmpty(entity.getOperatorTime())?"":entity.getOperatorTime());
			map.put("operatorDesc",StringUtils.isEmpty(entity.getOperatorDesc())?"":entity.getOperatorDesc());
			map.put("salOrderCode",StringUtils.isEmpty(entity.getSalOrderCode())?"":entity.getSalOrderCode());
			map.put("agencyCode",StringUtils.isEmpty(entity.getAgencyCode())?"":entity.getAgencyCode());
			map.put("storeName",StringUtils.isEmpty(entity.getStoreName())?"":entity.getStoreName());
			contents.add(map);
		}
		Map<String,String> head = new LinkedHashMap<>();
		head.put("operatorByName","操作人名称");
		head.put("operatorTime","操作时间");
		head.put("operatorDesc","状态描述");
		head.put("salOrderCode","订单编码");
		head.put("agencyCode","经销商编码");
		head.put("storeName","门店名称");

		ExportExcelUtil.exportExcle("留资操作记录", head, contents, out);// 传参：1.表格名称，2.表头，3.数据，4.OutputStream

		return count;

	}

	@Override
	public  Map<String,String> getOperateRemark(List<String> orderCodeList) {
		//分隔List，防止数据超过500条，数据库报错
		int index = (orderCodeList.size()/500)+1;
		List<SalOrderOperationDetailEntity> inList = new ArrayList<>();
		for (int i=0;i<index;i++){
			List<SalOrderOperationDetailEntity>  indexList= new ArrayList<>();
			if (i<index-1){
				  indexList = salesLeadsOperationDao.getOperateRemark(orderCodeList.subList((500*i),(i+1)*500));
			}else {
				if (orderCodeList.size()%500==1){
					indexList = salesLeadsOperationDao.getOperateRemark(orderCodeList.subList(orderCodeList.size()-1,orderCodeList.size()));
				}else{
					indexList = salesLeadsOperationDao.getOperateRemark(orderCodeList.subList((500*i),orderCodeList.size()));
				}
			}
			inList.addAll(indexList);//合并成大集合
		}
		Map<String,String> outMap = new HashMap<>();
		if (CollectionUtils.isEmpty(inList)){
			return outMap;
		}
		for (SalOrderOperationDetailEntity entity :inList){
			if (StringUtils.isEmpty(entity.getSalOrderCode())){
				continue;
			}
			outMap.put(entity.getSalOrderCode(),entity.getRemark());
		}
		return outMap;
	}

	@Override
	public int getCountUnOperate(String agency) {
		return salesLeadsOrderDao.getCountUnOperate(agency);
	}

	public static void main(String[] args) {
		List<String>ll = new ArrayList<>();
		ll.add("11");
		System.out.println(ll.subList(0,1));
	}
}
