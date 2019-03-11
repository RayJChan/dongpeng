package com.dpmall.datasvr.serviceImpl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dpmall.common.HybrisUtils;
import com.dpmall.common.OssInfoUtils;
import com.dpmall.datasvr.api.IFirstPageService;
import com.dpmall.db.bean.FirstPageEntity;
import com.dpmall.db.bean.PictureEntity;
import com.dpmall.db.dao.FirstPageDao;
import com.dpmall.db.dao.PictureDao;
import com.dpmall.db.dao.ReportDao;
import com.dpmall.enums.EPictureType;
import com.dpmall.model.FirstPageModel;
import com.dpmall.model.PictureModel;

/**
 * 实物订单服务实现
 * 
 * @author river
 * @date 2017-07-14
 */
@Service(value = "firstPageService")
public class FirstPageServiceImpl implements IFirstPageService {

	@Autowired
	private FirstPageDao firstPageDao;
	
	@Autowired
	private ReportDao reportDao;
	
	@Autowired
	private PictureDao pictureDao;

	@Autowired
	private HybrisUtils hybrisUtils;
	
	private static final String OSSURL  = OssInfoUtils.getOssUrl();
	
	/**
	 * 图片entity转成Model
	 * @param in
	 * @return
	 */
	private PictureModel entityToModel (PictureEntity in) {
		PictureModel out = new PictureModel();
		if (in !=null) {
			BeanUtils.copyProperties(in, out);
			out.setImgUrl(OssInfoUtils.getOssUrl()+in.getName());//图片访问url路径
		}
		return out;
	}
	
	/**
	 * 小数点后为00，则去掉
	 */
	private String  removeZero(String in) {
		 String judgetment = StringUtils.substringAfter(in, ".");
		 if ("00".equals(judgetment)) {
			 in = StringUtils.substringBeforeLast(in, ".");
		 }
		return in;
	}
	
	/**
	 * 小数点后为00，则去掉(百分比)
	 */
	private String  removeZeroOfPrecent(String in) {
		 String judgetment = StringUtils.substringAfter(in, ".");
		 if ("00%".equals(judgetment)) {
			 in = StringUtils.substringBeforeLast(in, ".")+"%";
		 }
		return in;
	}
	
	/**
	 * 经销商账号
	 */
	public FirstPageModel getFirstPageOfAgency(String code) {
		FirstPageModel model = new FirstPageModel();
		FirstPageEntity entity = new FirstPageEntity();
		// 1、获取经销商ids
		List<String> agencyIds = new ArrayList<String>();
		if ("dptest".equals(code)) {//正式机的特殊测试账号
			agencyIds.add(code);
		}else {//其他正常账号
			//暂时注释
//			agencyIds = getAppGroupInfo(code);
			agencyIds.add(code);
		}
		if (agencyIds == null || agencyIds.isEmpty()) {
			return model;
		}
		// 2.分割List
		List<List<String>> bigList = new ArrayList<List<String>>();
		if (agencyIds.size() > 500) {
			bigList = listToBigList(agencyIds);
			agencyIds = bigList.get(0);
		}
		// 3.获取当月和上月的时间
		String startdate, enddate ,lastStartdate,lastEnddate= null;
		startdate = this.getTime().get("currentFirstday");
		enddate = this.getTime().get("currentLastday");
		lastStartdate = this.getTime().get("lastFirstDay");
		lastEnddate = this.getTime().get("lastLastDay");
		
		
		// 4、留资---------------------------------------------------------------------------
		// 待接单数数量
		BigDecimal salesWaitAcceptCount = firstPageDao.salesLeadsCount(agencyIds, null, bigList, startdate, enddate,"1","N");
		salesWaitAcceptCount = salesWaitAcceptCount == null ? BigDecimal.ZERO : salesWaitAcceptCount;
		// 跟进中数量
		BigDecimal salesFollowingCount = firstPageDao.salesLeadsCount(agencyIds, null, bigList, startdate, enddate,"2","N");
		salesFollowingCount = salesFollowingCount == null ? BigDecimal.ZERO : salesFollowingCount;
		// 已完成数量
		BigDecimal salesFinishedCount = firstPageDao.salesLeadsCount(agencyIds, null, bigList, startdate, enddate,"3","N");
		salesFinishedCount = salesFinishedCount == null ? BigDecimal.ZERO : salesFinishedCount;

		// 今日待处理数量
		BigDecimal salWaitHandleCount_today = firstPageDao.salesLeadsCount_Today(agencyIds, "", bigList, "2","N");
		salWaitHandleCount_today = salWaitHandleCount_today == null ? BigDecimal.ZERO : salWaitHandleCount_today;

		// 上个月已完成数量
//		BigDecimal lastSalesFinishedCount = firstPageDao.salesLeadsCount(agencyIds, null, bigList, lastStartdate, lastEnddate,"3","N");
//		lastSalesFinishedCount = lastSalesFinishedCount == null ? BigDecimal.ZERO : lastSalesFinishedCount;
//		//比较上个月数据
//		String salesComparison  = this.comparison(salesFinishedCount, lastSalesFinishedCount);
//
//		//接单数量
//		BigDecimal salesAcceptCount = firstPageDao.salesLeadsCount(agencyIds, null, bigList, startdate, enddate,"JD","N");
//		salesAcceptCount = salesAcceptCount == null ? BigDecimal.ZERO : salesAcceptCount;
//		// 上个月接单数量
//		BigDecimal lastSalesAcceptCount = firstPageDao.salesLeadsCount(agencyIds, null, bigList, lastStartdate, lastEnddate,"JD","N");
//		lastSalesAcceptCount = lastSalesAcceptCount == null ? BigDecimal.ZERO : lastSalesAcceptCount;

		
		
		//为model赋值
		model.salesInfo.put("salesWaitAcceptCount", salesWaitAcceptCount.toString());
		model.salesInfo.put("salesFollowingCount", salesFollowingCount.toString());
		model.salesInfo.put("salesFinishedCount", salesFinishedCount.toString());
		model.salesInfo.put("salWaitHandleCount_today", salWaitHandleCount_today.toString());
//	    model.salesInfo.put("salesComparison", salesComparison);
		model.salesInfo.put("salesComparison", "EQUAL");
		model.salesInfo.put("time", this.getTime().get("currentMonth"));
		
		
		//5、实物订单---------------------------------------------------------------------------
		BigDecimal orderWaitAcceptCount = firstPageDao.orderCount(agencyIds, bigList, startdate, enddate, "1","","Y");//待接单数数量
		orderWaitAcceptCount = orderWaitAcceptCount == null ? BigDecimal.ZERO : orderWaitAcceptCount;

		BigDecimal orderFollowingCount = firstPageDao.orderCount(agencyIds, bigList, startdate, enddate, "2","","Y");// 跟进中数量
		orderFollowingCount = orderFollowingCount == null ? BigDecimal.ZERO : orderFollowingCount;

		BigDecimal orderWaifForRefundCount = firstPageDao.orderCount(agencyIds, bigList, startdate, enddate, "3","","Y");// 待返款
		orderWaifForRefundCount = orderWaifForRefundCount == null ? BigDecimal.ZERO : orderWaifForRefundCount;

		BigDecimal orderFinishedCount = firstPageDao.orderCount(agencyIds, bigList, startdate, enddate, "4","","Y");// 已完成
		orderFinishedCount = orderFinishedCount == null ? BigDecimal.ZERO : orderFinishedCount;
//		// 上个月已完成数量
//		BigDecimal lastOrderFinishedCount = firstPageDao.orderCount(agencyIds,bigList, lastStartdate, lastEnddate,"4");
//		lastOrderFinishedCount = lastOrderFinishedCount == null ? BigDecimal.ZERO : lastOrderFinishedCount;
//
//		String orderComparison  = this.comparison(orderFinishedCount, lastOrderFinishedCount);//比较上个月数据
		
		//为model赋值
		 model.orderInfo.put("orderWaitAcceptCount", orderWaitAcceptCount.toString());
		 model.orderInfo.put("orderFollowingCount", orderFollowingCount.toString());
		 model.orderInfo.put("orderWaifForRefundCount", orderWaifForRefundCount.toString());
		 model.orderInfo.put("orderFinishedCount", orderFinishedCount.toString());
//		 model.orderInfo.put("orderComparison", orderComparison.toString());
		model.orderInfo.put("orderComparison", "EQUAL");

		model.orderInfo.put("time", this.getTime().get("currentMonth"));
		 
		 
		 //6、天猫特权订金-----------------------------------------------------------------------
		//待接单数数量
		 BigDecimal tmallPrePayWaitAcceptCount = firstPageDao.tmallPrepayCount(agencyIds, bigList, startdate, enddate, "1","","Y");
		tmallPrePayWaitAcceptCount = tmallPrePayWaitAcceptCount == null ? BigDecimal.ZERO : tmallPrePayWaitAcceptCount;
		//已接单数数量
		 BigDecimal tmallPrePayFollowingCount = firstPageDao.tmallPrepayCount(agencyIds, bigList, startdate, enddate, "2","","Y");
		tmallPrePayFollowingCount = tmallPrePayFollowingCount == null ? BigDecimal.ZERO : tmallPrePayFollowingCount;

		//完成数量
		BigDecimal tmallPrePayFinishedCount = firstPageDao.tmallPrepayCount(agencyIds, bigList, startdate, enddate, "3","","Y");
		tmallPrePayFinishedCount = tmallPrePayFinishedCount == null ? BigDecimal.ZERO : tmallPrePayFinishedCount;

		//今天待处理的单
		BigDecimal tmallPrePayWaitHandleCount_today = firstPageDao.tmallPrepayCount_Today(agencyIds, bigList, "2","","Y");
		tmallPrePayWaitHandleCount_today = tmallPrePayWaitHandleCount_today == null ? BigDecimal.ZERO : tmallPrePayWaitHandleCount_today;


//		// 上个月已接单数数量
//		 BigDecimal lastTPrePayFinishedCount = firstPageDao.tmallPrepayCount(agencyIds,bigList, lastStartdate, lastEnddate,"2","","Y");
//		lastTPrePayFinishedCount = lastTPrePayFinishedCount == null ? BigDecimal.ZERO : lastTPrePayFinishedCount;
//		//比较上个月数据
//		 String tmallPrePayComparison  = this.comparison(tmallPrePayFinishedCount, lastTPrePayFinishedCount);

		 //为model赋值
		 model.tmallPrePayInfo.put("tmallPrePayWaitAcceptCount", tmallPrePayWaitAcceptCount.toString());
		 model.tmallPrePayInfo.put("tmallPrePayFollowingCount", tmallPrePayFollowingCount.toString());
		model.tmallPrePayInfo.put("tmallPrePayFinishedCount", tmallPrePayFinishedCount.toString());
		model.tmallPrePayInfo.put("tmallPrePayWaitHandleCount_today", tmallPrePayWaitHandleCount_today.toString());

//		 model.tmallPrePayInfo.put("tmallPrePayComparison", tmallPrePayComparison);
		model.tmallPrePayInfo.put("tmallPrePayComparison", "EQUAL");
		model.tmallPrePayInfo.put("time", this.getTime().get("currentMonth"));

//		//6-2、其他特权订金-----------------------------------------------------------------------
//		//待接单数数量
//		BigDecimal otherPrePayWaitAcceptCount = firstPageDao.othersPrepayCountOfAgency(agencyIds, bigList, startdate, enddate, "1");
//		otherPrePayWaitAcceptCount = otherPrePayWaitAcceptCount == null ? BigDecimal.ZERO : otherPrePayWaitAcceptCount;
//		//待接单数数量
//		BigDecimal otherPrePayFollowingCount = firstPageDao.othersPrepayCountOfAgency(agencyIds, bigList, startdate, enddate, "2");
//		otherPrePayFollowingCount = otherPrePayFollowingCount == null ? BigDecimal.ZERO : otherPrePayFollowingCount;
//		//已完成数量
//		BigDecimal otherPrePayFinishedCount = firstPageDao.othersPrepayCountOfAgency(agencyIds, bigList, startdate, enddate, "3");
//		otherPrePayFinishedCount = otherPrePayFinishedCount == null ? BigDecimal.ZERO : otherPrePayFinishedCount;
//		// 上个月已完成数量
//		BigDecimal lastprePayFinishedCount = firstPageDao.othersPrepayCountOfAgency(agencyIds,bigList, lastStartdate, lastEnddate,"3");
//		lastprePayFinishedCount = lastprePayFinishedCount == null ? BigDecimal.ZERO : lastprePayFinishedCount;
//		//比较上个月数据
//		String otherPrePayComparison  = this.comparison(otherPrePayFinishedCount, lastprePayFinishedCount);

		//为model赋值
//		model.otherPrePayInfo.put("otherPrePayWaitAcceptCount", otherPrePayWaitAcceptCount.toString());
//		model.otherPrePayInfo.put("otherPrePayFollowingCount", otherPrePayFollowingCount.toString());
//		model.otherPrePayInfo.put("otherPrePayFinishedCount", otherPrePayFinishedCount.toString());
//		model.otherPrePayInfo.put("otherPrePayComparison", otherPrePayComparison);

		model.otherPrePayInfo.put("otherPrePayWaitAcceptCount", "0");
		model.otherPrePayInfo.put("otherPrePayFollowingCount", "0");
		model.otherPrePayInfo.put("otherPrePayFinishedCount","0");
		model.otherPrePayInfo.put("otherPrePayComparison", "EQUAL");
		model.otherPrePayInfo.put("time", this.getTime().get("currentMonth"));

		//暂时
        model.prePayInfo.put("prePayWaitAcceptCount", "0");
        model.prePayInfo.put("prePayFollowingCount", "0");
        model.prePayInfo.put("prePayFinishedCount", "0");
        model.prePayInfo.put("prePayComparison", "EQUAL");
        model.prePayInfo.put("time", this.getTime().get("currentMonth"));







		//7、报表数据-----------------------------------------------------------------------
//		//--------------------------留资-----------------
//		 BigDecimal conversionRateBig,lastConversionRateBig = BigDecimal.ZERO;//留资转换率
//		 String conversionRate,conversionRateComparison = "";//与上个月比较
//		 //留资转化率（上一个月）
//		 if (lastSalesAcceptCount.equals(BigDecimal.ZERO)||lastSalesFinishedCount.equals(BigDecimal.ZERO)) {//判断是否为空
//				lastConversionRateBig = BigDecimal.ZERO;
//		 }else {
//		     //转化率 = 转化笔数/接单数量 *100
//			 lastConversionRateBig = (lastSalesAcceptCount.divide(lastSalesFinishedCount,4,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100));//转化率
//		 }
//		//留资转化率（这一个月）
//		 if (salesAcceptCount.equals(BigDecimal.ZERO)||salesFinishedCount.equals(BigDecimal.ZERO)) {//判断是否为空
//				conversionRate = "0";
//				conversionRateComparison  = this.comparison(salesAcceptCount, lastSalesAcceptCount);//比较上个月数据//与历史记录比较
//		 }else {
//			 //转化率 = 转化笔数/接单数量 *100
//			  conversionRateBig = (salesAcceptCount.divide(salesFinishedCount,4,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100));//转化率
//			 BigDecimal conversionRateChange = conversionRateBig.setScale(2, BigDecimal.ROUND_HALF_UP);
//			 conversionRate = conversionRateChange+"";
//			 conversionRate = removeZero(conversionRate);//小数点后为00则去除掉
//			 conversionRateComparison = this.comparison(conversionRateBig,lastConversionRateBig);//与历史记录比较
//		 }
//
//		 //-------------------------实物------------------
//		 BigDecimal distributeCount, acceptCount= BigDecimal.ZERO;//实物派单数，接单数
//		 BigDecimal acceptRate,lastAcceptRate = BigDecimal.ZERO;//本月接单率，上个月接单率
//		 String acceptRateComparison ="";//与上个月的比较
//
//		 //上个月接单率
//		 entity = firstPageDao.getHistory(code, lastStartdate, lastEnddate,"N",null);
//		 if (entity!=null) {
//			 lastAcceptRate = entity.lastAcceptRate;
//		 }
//
//
//		 //这个月的派单数、接单数、接单率
//		 distributeCount =  reportDao.distributedOrders4Agency(agencyIds, bigList, startdate, enddate);//派单数
//		 distributeCount = distributeCount==null?BigDecimal.ZERO:distributeCount;
//		 acceptCount = reportDao.acceptOrders4Agency(agencyIds, bigList, startdate, enddate);//接单数
//		 acceptCount = acceptCount==null?BigDecimal.ZERO:acceptCount;
//
//		//接单率
//		if (distributeCount.equals(BigDecimal.ZERO) || acceptCount.equals(BigDecimal.ZERO) ) {//判断是否为空
//			acceptRate = BigDecimal.ZERO;
//			acceptRateComparison = this.comparison(acceptRate,lastAcceptRate);//与历史记录比较
//		}else {
//			 //累计接单率 = 接单数/派单数 *100
//			 BigDecimal acceptRateBig = (acceptCount.divide(distributeCount,4,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100));
//			 BigDecimal acceptRatechanage = acceptRateBig.setScale(2, BigDecimal.ROUND_HALF_UP);
//			 if (acceptRatechanage.compareTo(new BigDecimal(100))==0) {//等于100时，去掉后两位小数
//				 acceptRatechanage = new BigDecimal(100);
//			 }
//			 acceptRate = acceptRatechanage;
//			 acceptRateComparison = this.comparison(acceptRate,lastAcceptRate);//与历史记录比较
//		}
//
//		//-------------------------特权------------------
//		 BigDecimal writeOffCount, prepayAcceptCount= BigDecimal.ZERO;//特权派单数，接单数
//		 BigDecimal writeOffRat,lastWriteOffRat = BigDecimal.ZERO;//本月和核销率，上个月核销率
//		 String writeOffRateComparison ="";//与上个月的比较
//
//		 //上个月核销率
//		 if (entity!=null) {
//			 lastWriteOffRat = entity.lastWriteOffRate;
//		 }
//		 //这个月的核销数、接单数、核销率
//		 writeOffCount = reportDao.prePayWriteOffOfAgency(agencyIds, bigList,startdate, enddate);//核销数量
//		 writeOffCount = writeOffCount==null?BigDecimal.ZERO:writeOffCount;
//		 prepayAcceptCount = reportDao.acceptPrepay4Agency(agencyIds,bigList, startdate, enddate);//接单数量
//		 prepayAcceptCount = prepayAcceptCount==null?BigDecimal.ZERO:prepayAcceptCount;
//
//
//		//核销率
//		 if (acceptCount.equals(BigDecimal.ZERO)||writeOffCount.equals(BigDecimal.ZERO)) {//判断是否为空
//			 writeOffRat = BigDecimal.ZERO;
//			writeOffRateComparison = this.comparison(writeOffRat,lastWriteOffRat);//与历史记录比较
//		 }else {
//			 //核销率 = 核销数/接单数 *100
//			 BigDecimal writeOffRateBig = (writeOffCount.divide(acceptCount,4,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100));
//			 BigDecimal writeOffRatechange = writeOffRateBig.setScale(2, BigDecimal.ROUND_HALF_UP);
//			 if (writeOffRatechange.compareTo(new BigDecimal(100))==0) {//等于100时，去掉后两位小数
//				 writeOffRatechange = new BigDecimal(100);
//			 }
//			 writeOffRat = writeOffRatechange;
//			 writeOffRateComparison = this.comparison(writeOffRat,lastWriteOffRat);//与历史记录比较
//		 }
//
//		model.reportInfo.put("conversionRate", conversionRate);
//		model.reportInfo.put("conversionRateComparison", conversionRateComparison);
//		model.reportInfo.put("acceptRate", acceptRate.toString());
//		model.reportInfo.put("acceptRateComparison", acceptRateComparison);
//		model.reportInfo.put("writeOffRate",writeOffRat.toString());
//		model.reportInfo.put("writeOffRateComparison",writeOffRateComparison);

		model.reportInfo.put("conversionRate", "0");
		model.reportInfo.put("conversionRateComparison", "EQUAL");
		model.reportInfo.put("acceptRate", "0");
		model.reportInfo.put("acceptRateComparison", "EQUAL");
		model.reportInfo.put("writeOffRate","0");
		model.reportInfo.put("writeOffRateComparison","EQUAL");
		model.reportInfo.put("time",this.getTime().get("currentMonth"));



		//首页的横幅
		List<PictureEntity> entitiesHomePicture = pictureDao.getHomePagePicture();
		if (entitiesHomePicture != null && (entitiesHomePicture.size())>0 && !entitiesHomePicture.isEmpty()) {
			for (PictureEntity pictureEntity : entitiesHomePicture) {
				PictureModel pictureModel = new PictureModel();
				pictureModel = this.entityToModel(pictureEntity);
				model.homePictures.add(pictureModel);	
			}
		}
		
		//祝福卡
		List<PictureEntity> entities = pictureDao.getPicture(0, 1,EPictureType.HOME_CARDS_GUIDE.getType());
		if (entities != null && (entities.size())>0 && !entities.isEmpty()) {
			for (PictureEntity picture : entities) {
				String pictureUrl = OSSURL+picture.getName();
				model.cardsPicture.add(pictureUrl);	
			}
		}


		return model;
	}

	/**
	 * 门店账号
	 */
	public FirstPageModel getFirstPageOfStore( String storeId) {
		FirstPageModel model = new FirstPageModel();
		FirstPageEntity entity = new FirstPageEntity();
		
		// 1.获取当月和上月的时间
		String startdate, enddate ,lastStartdate,lastEnddate= null;
		startdate = this.getTime().get("currentFirstday");
		enddate = this.getTime().get("currentLastday");
		lastStartdate = this.getTime().get("lastFirstDay");
		lastEnddate = this.getTime().get("lastLastDay");
		
		
		// 2、留资---------------------------------------------------------------------------
		// 待接单数数量
		BigDecimal salesWaitAcceptCount = firstPageDao.salesLeadsCount(null, storeId, null, startdate, enddate,"1","Y");
		salesWaitAcceptCount = salesWaitAcceptCount == null ? BigDecimal.ZERO : salesWaitAcceptCount;
		// 跟进中数量
		BigDecimal salesFollowingCount = firstPageDao.salesLeadsCount(null, storeId, null, startdate, enddate,"2","Y");
		salesFollowingCount = salesFollowingCount == null ? BigDecimal.ZERO : salesFollowingCount;
		// 已完成数量
		BigDecimal salesFinishedCount = firstPageDao.salesLeadsCount(null, storeId, null, startdate, enddate,"3","Y");
		salesFinishedCount = salesFinishedCount == null ? BigDecimal.ZERO : salesFinishedCount;

		// 今日待处理数量
		BigDecimal salWaitHandleCount_today = firstPageDao.salesLeadsCount_Today(null, storeId, null, "2","Y");
		salWaitHandleCount_today = salWaitHandleCount_today == null ? BigDecimal.ZERO : salWaitHandleCount_today;
//
//
// 上个月已完成数量
//		BigDecimal lastSalesFinishedCount = firstPageDao.salesLeadsCount(null, storeId, null, lastStartdate, lastEnddate,"3","Y");
//		lastSalesFinishedCount = lastSalesFinishedCount == null ? BigDecimal.ZERO : lastSalesFinishedCount;
//		//比较上个月数据
//		String salesComparison  = this.comparison(salesFinishedCount, lastSalesFinishedCount);
//
//		//接单数量
//		BigDecimal salesAcceptCount = firstPageDao.salesLeadsCount(null, storeId, null, startdate, enddate,"JD","Y");
//		salesAcceptCount = salesAcceptCount == null ? BigDecimal.ZERO : salesAcceptCount;
//		// 上个月接单数量
//		BigDecimal lastSalesAcceptCount = firstPageDao.salesLeadsCount(null, storeId, null, startdate, enddate,"JD","Y");
//		lastSalesAcceptCount = lastSalesAcceptCount == null ? BigDecimal.ZERO : lastSalesAcceptCount;

		
		
		//为model赋值
		model.salesInfo.put("salesWaitAcceptCount", salesWaitAcceptCount.toString());
		model.salesInfo.put("salesFollowingCount", salesFollowingCount.toString());
		model.salesInfo.put("salesFinishedCount", salesFinishedCount.toString());
		model.salesInfo.put("salWaitHandleCount_today", salWaitHandleCount_today.toString());
	    model.salesInfo.put("salesComparison", "EQUAL");
		model.salesInfo.put("time", this.getTime().get("currentMonth"));
		
		
		//5、实物订单---------------------------------------------------------------------------
		BigDecimal orderWaitAcceptCount = firstPageDao.orderCount(null,null,startdate, enddate, "1", storeId,"N");//待接单数数量
		orderWaitAcceptCount = orderWaitAcceptCount == null ? BigDecimal.ZERO : orderWaitAcceptCount;

		BigDecimal orderFollowingCount = firstPageDao.orderCount(null,null,startdate, enddate, "2", storeId,"N");// 跟进中数量
		orderFollowingCount = orderFollowingCount == null ? BigDecimal.ZERO : orderFollowingCount;

		BigDecimal orderFinishedCount = firstPageDao.orderCount(null,null,startdate, enddate, "3", storeId,"N");// 已完成
		orderFinishedCount = orderFinishedCount == null ? BigDecimal.ZERO : orderFinishedCount;
		
//		BigDecimal lastOrderFinishedCount = firstPageDao.orderCount(null,null,lastStartdate, lastEnddate, "3", storeId,"N");// 上个月已完成数量
//		lastOrderFinishedCount = lastOrderFinishedCount == null ? BigDecimal.ZERO : lastOrderFinishedCount;
//
//		String orderComparison  = this.comparison(orderFinishedCount, lastOrderFinishedCount);//比较上个月数据
		
		//为model赋值
		model.orderInfo.put("orderWaitAcceptCount", orderWaitAcceptCount.toString());
		model.orderInfo.put("orderFollowingCount", orderFollowingCount.toString());
		model.orderInfo.put("orderFinishedCount", orderFinishedCount.toString());
//		 model.orderInfo.put("orderComparison", orderComparison.toString());
		model.orderInfo.put("orderComparison", "EQUAL");

		model.orderInfo.put("time", this.getTime().get("currentMonth"));
		 
		 
		 //6、天猫特权订金-----------------------------------------------------------------------
		//待接单数数量
		 BigDecimal tmallPrePayWaitAcceptCount = firstPageDao.tmallPrepayCount(null,null,startdate, enddate, "1", storeId,"N");
		tmallPrePayWaitAcceptCount = tmallPrePayWaitAcceptCount == null ? BigDecimal.ZERO : tmallPrePayWaitAcceptCount;
		//已接单数数量
		 BigDecimal tmallPrePayFollowingCount = firstPageDao.tmallPrepayCount(null,null,startdate, enddate, "2", storeId,"N");
		tmallPrePayFollowingCount = tmallPrePayFollowingCount == null ? BigDecimal.ZERO : tmallPrePayFollowingCount;

		//完成数量
		BigDecimal tmallPrePayFinishedCount = firstPageDao.tmallPrepayCount(null,null,startdate, enddate, "3", storeId,"N");
		tmallPrePayFinishedCount = tmallPrePayFinishedCount == null ? BigDecimal.ZERO : tmallPrePayFinishedCount;

		//今天待处理的单
		BigDecimal tmallPrePayWaitHandleCount_today = firstPageDao.tmallPrepayCount_Today(null, null, "3",storeId,"Y");
		tmallPrePayWaitHandleCount_today = tmallPrePayWaitHandleCount_today == null ? BigDecimal.ZERO : tmallPrePayWaitHandleCount_today;




//		// 上个月已完成数量
//		 BigDecimal lastTPrePayFinishedCount = firstPageDao.tmallPrepayCount(null,null,lastStartdate, lastEnddate, "2", storeId,"N");
//		lastTPrePayFinishedCount = lastTPrePayFinishedCount == null ? BigDecimal.ZERO : lastTPrePayFinishedCount;
//		//比较上个月数据
//		 String tmallPrePayComparison  = this.comparison(tmallPrePayFinishedCount, lastTPrePayFinishedCount);

		model.tmallPrePayInfo.put("tmallPrePayWaitAcceptCount", tmallPrePayWaitAcceptCount.toString());
		model.tmallPrePayInfo.put("tmallPrePayFollowingCount", tmallPrePayFollowingCount.toString());
		model.tmallPrePayInfo.put("tmallPrePayFinishedCount", tmallPrePayFinishedCount.toString());
		model.tmallPrePayInfo.put("tmallPrePayWaitHandleCount_today", tmallPrePayWaitHandleCount_today.toString());

//		 model.tmallPrePayInfo.put("tmallPrePayComparison", tmallPrePayComparison);
		model.tmallPrePayInfo.put("tmallPrePayComparison", "EQUAL");
		model.tmallPrePayInfo.put("time", this.getTime().get("currentMonth"));


//		//6、其他特权订金-----------------------------------------------------------------------
//		//待接单数数量
//		BigDecimal otherPrePayWaitAcceptCount = firstPageDao.othersPrepayCountOfStore(startdate, enddate, "1", storeId);
//		otherPrePayWaitAcceptCount = otherPrePayWaitAcceptCount == null ? BigDecimal.ZERO : otherPrePayWaitAcceptCount;
//		//待接单数数量
//		BigDecimal otherPrePayFollowingCount = firstPageDao.othersPrepayCountOfStore(startdate, enddate, "2", storeId);
//		otherPrePayFollowingCount = otherPrePayFollowingCount == null ? BigDecimal.ZERO : otherPrePayFollowingCount;
//		//待接单数数量
//		BigDecimal otherPrePayFinishedCount = firstPageDao.othersPrepayCountOfStore(startdate, enddate, "3", storeId);
//		otherPrePayFinishedCount = otherPrePayFinishedCount == null ? BigDecimal.ZERO : otherPrePayFinishedCount;
//		// 上个月已完成数量
//		BigDecimal lastprePayFinishedCount = firstPageDao.othersPrepayCountOfStore(lastStartdate, lastEnddate, "3", storeId);
//		lastprePayFinishedCount = lastprePayFinishedCount == null ? BigDecimal.ZERO : lastprePayFinishedCount;
//		//比较上个月数据
//		String otherPrePayComparison  = this.comparison(otherPrePayFinishedCount, lastprePayFinishedCount);

		//为model赋值
		model.otherPrePayInfo.put("otherPrePayWaitAcceptCount", "0");
		model.otherPrePayInfo.put("otherPrePayFollowingCount", "0");
		model.otherPrePayInfo.put("otherPrePayFinishedCount","0");
		model.otherPrePayInfo.put("otherPrePayComparison", "EQUAL");
		model.otherPrePayInfo.put("time", this.getTime().get("currentMonth"));

		//暂时
		model.prePayInfo.put("prePayWaitAcceptCount", "0");
		model.prePayInfo.put("prePayFollowingCount", "0");
		model.prePayInfo.put("prePayFinishedCount", "0");
		model.prePayInfo.put("prePayComparison", "EQUAL");
		model.prePayInfo.put("time", this.getTime().get("currentMonth"));


//
//		//7、报表数据-----------------------------------------------------------------------
//		//--------------------------留资-----------------
//		 BigDecimal conversionRateBig,lastConversionRateBig = BigDecimal.ZERO;//留资转换率
//		 String conversionRate,conversionRateComparison = "";//与上个月比较
//		 //留资转化率（上一个月）
//		 if (lastSalesAcceptCount.equals(BigDecimal.ZERO)||lastSalesFinishedCount.equals(BigDecimal.ZERO)) {//判断是否为空
//				lastConversionRateBig = BigDecimal.ZERO;
//		 }else {
//		     //转化率 = 转化笔数/接单数量 *100
//			 lastConversionRateBig = (lastSalesAcceptCount.divide(lastSalesFinishedCount,4,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100));//转化率
//		 }
//		//留资转化率（这一个月）
//		 if (salesAcceptCount.equals(BigDecimal.ZERO)||salesFinishedCount.equals(BigDecimal.ZERO)) {//判断是否为空
//				conversionRate = "0";
//				conversionRateComparison  = this.comparison(salesAcceptCount, lastSalesAcceptCount);//比较上个月数据//与历史记录比较
//		 }else {
//			 //转化率 = 转化笔数/接单数量 *100
//			  conversionRateBig = (salesAcceptCount.divide(salesFinishedCount,4,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100));//转化率
//			 BigDecimal conversionRateChange = conversionRateBig.setScale(2, BigDecimal.ROUND_HALF_UP);
//			 if (conversionRateChange.compareTo(new BigDecimal(100))==0) {//等于100时，去掉后两位小数
//				 conversionRateChange = new BigDecimal(100);
//			 }
//			 conversionRate = conversionRateChange+"";
//			 conversionRateComparison = this.comparison(conversionRateBig,lastConversionRateBig);//与历史记录比较
//		 }
//
//		 //-------------------------实物------------------
//		 BigDecimal distributeCount, acceptCount= BigDecimal.ZERO;//实物派单数，接单数
//		 BigDecimal acceptRate,lastAcceptRate = BigDecimal.ZERO;//本月接单率，上个月接单率
//		 String acceptRateComparison ="";//与上个月的比较
//
//		 //上个月接单率
//		 entity = firstPageDao.getHistory(null, lastStartdate, lastEnddate,"Y",storeId);
//		 if (entity!=null) {
//			 lastAcceptRate = entity.lastAcceptRate;
//		 }
//
//		 //这个月的派单数、接单数、接单率
//		 distributeCount =  reportDao.distributedOrders4Store(storeId, startdate, enddate);//派单数
//		 distributeCount = distributeCount==null?BigDecimal.ZERO:distributeCount;
//		 acceptCount = reportDao.acceptOrders4Store(storeId, startdate, enddate);//接单数
//		 acceptCount = acceptCount==null?BigDecimal.ZERO:acceptCount;
//
//		//接单率
//		if (distributeCount.equals(BigDecimal.ZERO) || acceptCount.equals(BigDecimal.ZERO) ) {//判断是否为空
//			acceptRate = BigDecimal.ZERO;
//			acceptRateComparison = this.comparison(acceptRate,lastAcceptRate);//与历史记录比较
//		}else {
//			 //累计接单率 = 接单数/派单数 *100
//			 BigDecimal acceptRateBig = (acceptCount.divide(distributeCount,4,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100));
//			 BigDecimal acceptRatechanage = acceptRateBig.setScale(2, BigDecimal.ROUND_HALF_UP);
//			 if (acceptRatechanage.compareTo(new BigDecimal(100))==0) {//等于100时，去掉后两位小数
//				 acceptRatechanage = new BigDecimal(100);
//			 }
//			 acceptRate = acceptRatechanage;
//			 acceptRateComparison = this.comparison(acceptRate,lastAcceptRate);//与历史记录比较
//		}
//
//		//-------------------------特权------------------
//		 BigDecimal writeOffCount, prepayAcceptCount= BigDecimal.ZERO;//特权派单数，接单数
//		 BigDecimal writeOffRat,lastWriteOffRat = BigDecimal.ZERO;//本月和核销率，上个月核销率
//		 String writeOffRateComparison ="";//与上个月的比较
//
//		 //上个月核销率
//		 if (entity!=null) {
//			 lastWriteOffRat = entity.lastWriteOffRate;
//		 }
//		 //这个月的核销数、接单数、核销率
//		 writeOffCount = reportDao.prePayWriteOffOfStrore(storeId, startdate, enddate);//核销数量
//		 writeOffCount = writeOffCount==null?BigDecimal.ZERO:writeOffCount;
//		 prepayAcceptCount = reportDao.acceptPrepayOfStore(storeId, startdate, enddate);//接单数量
//		 prepayAcceptCount = prepayAcceptCount==null?BigDecimal.ZERO:prepayAcceptCount;
//
//
//		//核销率
//		 if (acceptCount.equals(BigDecimal.ZERO)||writeOffCount.equals(BigDecimal.ZERO)) {//判断是否为空
//			 writeOffRat = BigDecimal.ZERO;
//			writeOffRateComparison = this.comparison(writeOffRat,lastWriteOffRat);//与历史记录比较
//		 }else {
//			 //核销率 = 核销数/接单数 *100
//			 BigDecimal writeOffRateBig = (writeOffCount.divide(acceptCount,4,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100));
//			 BigDecimal writeOffRatechange = writeOffRateBig.setScale(2, BigDecimal.ROUND_HALF_UP);
//			 if (writeOffRatechange.compareTo(new BigDecimal(100))==0) {//等于100时，去掉后两位小数
//				 writeOffRatechange = new BigDecimal(100);
//			 }
//			 writeOffRat = writeOffRatechange;
//			 writeOffRateComparison = this.comparison(writeOffRat,lastWriteOffRat);//与历史记录比较
//		 }
//
//		model.reportInfo.put("conversionRate", conversionRate);
//		model.reportInfo.put("conversionRateComparison", conversionRateComparison);
//		model.reportInfo.put("acceptRate", acceptRate.toString());
//		model.reportInfo.put("acceptRateComparison", acceptRateComparison);
//		model.reportInfo.put("writeOffRate",writeOffRat.toString());
//		model.reportInfo.put("writeOffRateComparison",writeOffRateComparison);
//		model.reportInfo.put("time",this.getTime().get("currentMonth"));

		model.reportInfo.put("conversionRate", "0");
		model.reportInfo.put("conversionRateComparison", "EQUAL");
		model.reportInfo.put("acceptRate", "0");
		model.reportInfo.put("acceptRateComparison", "EQUAL");
		model.reportInfo.put("writeOffRate","0");
		model.reportInfo.put("writeOffRateComparison","EQUAL");
		model.reportInfo.put("time",this.getTime().get("currentMonth"));


		
		//首页的横幅
		List<PictureEntity> entitiesHomePicture = pictureDao.getHomePagePicture();
		if (entitiesHomePicture != null && (entitiesHomePicture.size())>0 && !entitiesHomePicture.isEmpty()) {
			for (PictureEntity pictureEntity : entitiesHomePicture) {
				PictureModel pictureModel = new PictureModel();
				pictureModel = this.entityToModel(pictureEntity);
				model.homePictures.add(pictureModel);	
			}
		}
//
		model.pictures.add(OSSURL);//测试机图片1
//		model.pictures.add(PICTUREURL+picture2);//测试机图片2
		model.picturesHDP.add(OSSURL);//测试机图片1
//		model.picturesHDP.add(PICTUREURL+picture2);//测试机图片2
		model.picturesXXHDP.add(OSSURL);
//		model.picturesXXHDP.add(PICTUREURL+picture1080P2);
		
		// 祝福卡导航栏
		List<PictureEntity> entities = pictureDao.getPicture(0, 1, EPictureType.HOME_CARDS_GUIDE.getType());
		if (entities != null && (entities.size()) > 0 && !entities.isEmpty()) {
			for (PictureEntity picture : entities) {
				String pictureUrl = OSSURL + picture.getName();
				model.cardsPicture.add(pictureUrl);
			}
		}


		return model;
	}
	
	/***
	 * 比较两个数值
	 */
	private String comparison(BigDecimal current,BigDecimal last) {
		String out = null;
		if (current.compareTo(last)>0) {
			out = "UP";
		}else if(current.compareTo(last) == 0){
			out = "EQUAL";
		}else {
			out = "DOWN";
		}
		return out;
	}
	


	/**
	 * 获取当前月份和上个月份的时间
	 * 
	 * @return
	 */
	private Map<String, String> getTime() {
		Map<String, String> out = new HashMap<String, String>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); // 设置时间格式
		String currentFirstday, currentLastday, lastFirstDay, lastLastDay, currentMonth = null;
		Calendar cale = null;
		cale = Calendar.getInstance();
//		cale.add(Calendar.MONTH, -4);
		// 该月的第一天
		cale.add(Calendar.MONTH, 0);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		currentFirstday = format.format(cale.getTime());
		out.put("currentFirstday", currentFirstday);

		// 该月最后一天
		cale.add(Calendar.MONTH, 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		currentLastday = format.format(cale.getTime());
		out.put("currentLastday", currentLastday);

		// 上个月第一天
		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, -1);
//		cale.add(Calendar.MONTH, -5);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		lastFirstDay = format.format(cale.getTime());
		out.put("lastFirstDay", lastFirstDay);

		// 上个月最后一天
		cale.add(Calendar.MONTH, 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		lastLastDay = format.format(cale.getTime());
		out.put("lastLastDay", lastLastDay);

		// 当前月份
		format = new SimpleDateFormat("yyyy年MM月");
		cale.add(Calendar.MONTH, 1);
		currentMonth = format.format(cale.getTime());
		currentMonth = currentMonth + "数据";// yyyy年MM月数据
		out.put("currentMonth", currentMonth);
		return out;
	}

	/**
	 * 调用hybirs接口
	 */
	private List<String> getAppGroupInfo(String code) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("code", code);
		// 调用hybris接口
		String resultStr = hybrisUtils.getAppGroupInfo(param);
		JSONObject jsonObject = JSON.parseObject(resultStr);
		Map<Object, Object> map = (Map) jsonObject;// 转成map
		// 转List
		String dataStr = map.get("data").toString();
		String[] dataByte = dataStr.split(" ");
		List<String> dataList = Arrays.asList(dataByte);
		return dataList;
	}

	/**
	 * 分割大集合 List 转为List<List<String>>
	 */
	private List<List<String>> listToBigList(List<String> list) {
		List<List<String>> bigList = new ArrayList<List<String>>();
		int size = list.size();
		if (size > 500) {
			int index = (size / 500) + 1;// 循环的次数
			for (int i = 0; i < index; i++) {
				List<String> newList = new ArrayList<String>(); // 复制集合，以500为单位
				if (i == (index - 1)) {
					newList.addAll(list.subList((500 * i), list.size()));
				} else {
					newList.addAll(list.subList((500 * i), (500 * (i + 1))));
				}
				bigList.add(newList);// 添加至大集合
			}
		}
		return bigList;
	}

	public static void main(String[] args) {
		FirstPageServiceImpl firstPageServiceImpl = new FirstPageServiceImpl();
		 System.out.println(firstPageServiceImpl.getTime().get("currentFirstday"));//
//		 2017-12-01
		 System.out.println(firstPageServiceImpl.getTime().get("currentLastday"));//
//		 2017-12-31
		 System.out.println(firstPageServiceImpl.getTime().get("lastFirstDay"));//
//		 2017-11-01
		 System.out.println(firstPageServiceImpl.getTime().get("lastLastDay"));//
//		 2017-11-30
		 System.out.println(firstPageServiceImpl.getTime().get("currentMonth"));//
//		 2017年12月数据
		// System.out.println(firstPageServiceImpl.getFirstPage("310145"));
		
	}

}
