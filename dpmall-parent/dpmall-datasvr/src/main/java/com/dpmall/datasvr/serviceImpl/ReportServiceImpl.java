package com.dpmall.datasvr.serviceImpl;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.alibaba.dubbo.common.utils.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dpmall.common.HybrisUtils;
import com.dpmall.datasvr.api.IReportService;
import com.dpmall.db.bean.ReportDetailEntity;
import com.dpmall.db.bean.ReportEntity;
import com.dpmall.db.dao.ReportDao;
import com.dpmall.model.ReportModel;


@Service(value = "reportService")
public class ReportServiceImpl implements IReportService {

	@Autowired
	private ReportDao reportDao;


	@Autowired
	private HybrisUtils hybrisUtils;

	NumberFormat RMBFormat = NumberFormat.getCurrencyInstance(Locale.CHINA);//换算价钱

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
	
	private ReportModel entityToModel(ReportEntity entity) {
		if (entity == null) {
			return null;
		}
		ReportModel model = new ReportModel();
		model.agencyId = entity.agencyId;
		model.storeId = entity.storeId;
		model.count = String.valueOf(entity.count);
		return model;
	}

	private List<ReportModel> entitysaleModel(List<ReportEntity> in) {
		if (in == null || in.isEmpty()) {
			return null;
		}
		List<ReportModel> out = new ArrayList<ReportModel>();
		for (ReportEntity tmp : in) {
			out.add(entityToModel(tmp));
		}
		return out;
	}

	public int orderDistributCount(String agencyId, String startTime, String endTime) {

		return reportDao.orderDistributCount(agencyId, startTime, endTime);
	}

	public int orderAgencyAcceptCount(String agencyId, String startTime, String endTime) {
		return reportDao.orderAgencyAcceptCount(agencyId, startTime, endTime);
	}

	public int orderStoreAcceptCount(String storeId, String startTime, String endTime) {
		if (StringUtils.isNotEmpty(storeId) && StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
			return reportDao.orderStoreAcceptCount(storeId, startTime, endTime);
		} else {
			return 0;
		}
	}

	public int prePayDistributCount(String agencyId, String startTime, String endTime) {
		if (StringUtils.isNotEmpty(agencyId) && StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
			return reportDao.prePayDistributCount(agencyId, startTime, endTime);
		} else {
			return 0;
		}
	}

	public int prePayAgencyAcceptCount(String agencyId, String startTime, String endTime) {
		if (StringUtils.isNotEmpty(agencyId) && StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
			return reportDao.prePayAgencyAcceptCount(agencyId, startTime, endTime);
		} else {
			return 0;
		}

	}

	public int prePayStoreAcceptCount(String storeId, String startTime, String endTime) {
		if (StringUtils.isNotEmpty(storeId) && StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
			return reportDao.prePayStoreAcceptCount(storeId, startTime, endTime);
		} else {
			return 0;
		}
	}

	public int prePayWriteOffCount(String storeId, String startTime, String endTime) {
		if (StringUtils.isNotEmpty(storeId) && StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
			return reportDao.prePayWriteOffCount(storeId, startTime, endTime);
		} else {
			return 0;
		}
	}

	public List<ReportModel> prePayStoreAcceptCountOfAgency(String agencyId, String startTime, String endTime) {
		List<ReportModel> models = new ArrayList<ReportModel>();
		if (StringUtils.isNotEmpty(agencyId) && StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
			List<ReportEntity> entityList = reportDao.prePayStoreAcceptCountOfAgency(agencyId, startTime, endTime);
			if (entityList.isEmpty() || entityList == null) {
				return null;
			}
			models = this.entitysaleModel(entityList);
		}
		return models;
	}

	/**
	 * 转时间格式
	 */
	private String dateFormate(Long mss) {
		String DateTimes = "0";
//		long days = mss / (60 * 60 * 24);
//		long hours = (mss % (60 * 60 * 24)) / (60 * 60);
		long hours = mss  / (60 * 60);
		long minutes = (mss % (60 * 60)) / 60;

//		if (days > 0) {
//			DateTimes = days + "天" + hours + "小时" + minutes + "分钟";
//		} else 
			if (hours > 0) {
			DateTimes = hours + "小时" + minutes + "分钟";
		} else if (minutes > 0) {
			DateTimes = minutes + "分钟";
		}
		return DateTimes;
	}

	/**
	 * 与历史比较
	 */
	private String compareTo(BigDecimal result, BigDecimal theLastResult) {

		if (result == null) {
			result = BigDecimal.ZERO;
		}
		if (theLastResult == null) {
			theLastResult = BigDecimal.ZERO;
		}

		String compareResult = "EQUAL";
		if (result.compareTo(theLastResult) > 0) {
			compareResult = "UP";
		}
		if (result.compareTo(theLastResult) < 0) {
			compareResult = "DOWN";
		}

		return compareResult;
	}

	/**
	 * 与历史比较（时间）
	 */
	private String compareToOfTime(BigDecimal result, BigDecimal theLastResult) {
		if (result == null) {
			result = BigDecimal.ZERO;
		}
		if (theLastResult == null) {
			theLastResult = BigDecimal.ZERO;
		}
		String compareResult = "EQUAL";
		if (result.compareTo(theLastResult) > 0) {
			compareResult = "DOWN";
		}
		if (result.compareTo(theLastResult) < 0) {
			compareResult = "UP";
		}
		return compareResult;
	}
	
	/***
	 * 计算变化比值
	 */
	private String calculateChangeRate(BigDecimal now, BigDecimal past,String change) {
		String out = "";
		if (past.compareTo(BigDecimal.ZERO) ==0 ||  now.compareTo(past)==0) {//两者相等，或上个月为0则不显示变化
			return out;
		}
		BigDecimal result = (((now.subtract(past)).abs()).divide(past,4,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100));//(now-past)/past
		 BigDecimal resultBig = result.setScale(2, BigDecimal.ROUND_HALF_UP);
		if ("UP".equals(change)) {//显示增加
			out = "+"+resultBig + "%";
		}else if("DOWN".equals(change)) {//显示减少
			out = "-"+resultBig+ "%";
		}
		return out;
	}

	// ---------------------------------new-------------------------------------------------------

	public ReportModel OrdersOfAgency(String code, List<String> agencyPks, String dateFormat, String startdate,
			String enddate) {
		ReportModel model = new ReportModel();
		// 如果经销商ids列表为空，直接返回空数据
		if (agencyPks == null || agencyPks.isEmpty()) {
			model = this.isAgencyPksNull();
			return model;
		}
		// 分割List
		List<List<String>> bigList = new ArrayList<List<String>>();
		if (agencyPks.size() > 500) {
			bigList = listToBigList(agencyPks);
			agencyPks = bigList.get(0);// 第一个集合
		}

		// 历史数据
		// 修改开始时间
		String pastStartTime = "";
		String pastEndTime = "";
		try {
			pastStartTime = this.modifyStartTime(startdate, dateFormat);
			pastEndTime = this.modifyEndTime(enddate, dateFormat);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ReportDetailEntity historyResult = reportDao.getContrastInfo(code, dateFormat, "N", "", pastStartTime,	pastEndTime);
		historyResult = this.historyRecordIsNull(historyResult);// 历史记录非空处理

		BigDecimal distributedOrders4Agency = reportDao.distributedOrders4Agency(agencyPks, bigList, startdate,enddate);// 派单数量
		BigDecimal acceptOrders4Agency = reportDao.acceptOrders4Agency(agencyPks, bigList, startdate, enddate);// 接单数量
		BigDecimal deliveryOrdersCounts = reportDao.deliveryOrdersCounts(agencyPks, null, bigList, startdate, enddate);// 发货数量
		BigDecimal acceptTimeSum4Agency = reportDao.acceptTimeSum4Agency(agencyPks, bigList, startdate, enddate);// 接单时间-派单时间的差值的总和
		BigDecimal deliveryTimeSum4Agency = reportDao.deliveryTimeSum4Agency(agencyPks, bigList, startdate, enddate);// 发货时间-接单时间的差值的总和

		// 派单数量
		BigDecimal distributeCount = BigDecimalIfNull(distributedOrders4Agency);
		model.distributeCount = distributeCount.toString();
		model.distributeCountCon = this.compareTo(distributeCount, historyResult.distributeCount);// 与历史记录比较
		model.distributeCountChangeRate = this.calculateChangeRate(distributeCount, historyResult.distributeCount, model.distributeCountCon);//派单数量变化比值

		// 接单数量
		BigDecimal acceptCount = BigDecimalIfNull(acceptOrders4Agency);
		model.acceptCount = acceptCount.toString();
		model.acceptCountCon = this.compareTo(acceptCount, historyResult.acceptCount);// 与历史记录比较
		model.acceptCountChangeRate = this.calculateChangeRate(acceptCount, historyResult.acceptCount, model.acceptCountCon);//接单数量变化比值
	

		// 累计接单率
		BigDecimal acceptRatechanage = BigDecimal.ZERO;
		if (distributeCount.equals(BigDecimal.ZERO) || acceptCount.equals(BigDecimal.ZERO)) {// 判断是否为空
			model.acceptRate = "0";
			model.acceptRateCon = this.compareTo(new BigDecimal(0), historyResult.acceptRate);// 与历史记录比较
		} else {
			// 累计接单率 = 接单数/派单数 *100
			BigDecimal acceptRateBig = (acceptCount.divide(distributeCount, 4, BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100));
			 acceptRatechanage = acceptRateBig.setScale(2, BigDecimal.ROUND_HALF_UP);
			model.acceptRate = acceptRatechanage + "%";
			model.acceptRate = this.removeZeroOfPrecent(model.acceptRate);
			model.acceptRateCon = this.compareTo(acceptRatechanage, historyResult.acceptRate);// 与历史记录比较
		}
		model.acceptRateChangeRate = this.calculateChangeRate(acceptRatechanage, historyResult.acceptRate, model.acceptRateCon);//累计接单率变化比率
		

		// 平均接单时长
		if (distributedOrders4Agency == null || distributedOrders4Agency.equals(BigDecimal.ZERO)// 接单数为0时，不能作为被除数
				|| acceptTimeSum4Agency == null || acceptTimeSum4Agency.equals(BigDecimal.ZERO)) {// 接单时间为空
			model.acceptAverageTime = "0";
		} else {
			BigDecimal secondsBig = acceptTimeSum4Agency.divide(distributedOrders4Agency, 0, BigDecimal.ROUND_DOWN);// 平均秒数
			Long mss = secondsBig.longValue();// 转为long类型

			model.acceptAverageTime = dateFormate(mss);
		}

		// 平均发货时长
		BigDecimal deliverAverageTimeChangeRate = BigDecimal.ZERO;
		if (deliveryOrdersCounts == null || deliveryOrdersCounts.equals(BigDecimal.ZERO)// 发货数为0时，不能作为被除数
				|| deliveryTimeSum4Agency == null || deliveryTimeSum4Agency.equals(BigDecimal.ZERO)) {// 发货时间为空
			model.deliverAverageTime = "0";
			model.deliverAverageTimeCon = this.compareToOfTime(new BigDecimal(0), historyResult.deliverAVGTime);// 与历史记录比较
			deliverAverageTimeChangeRate = historyResult.deliverAVGTime;// 平均发货时长变化时长

		} else {
			BigDecimal secondsBig = deliveryTimeSum4Agency.divide(deliveryOrdersCounts, 0, BigDecimal.ROUND_DOWN);// 平均秒数
			model.deliverAverageTimeCon = this.compareToOfTime(secondsBig, historyResult.deliverAVGTime);// 与历史记录比较
			deliverAverageTimeChangeRate = (historyResult.deliverAVGTime.subtract(secondsBig)).abs();// 平均发货时长变化时长
			Long mss = secondsBig.longValue();// 转为long类型
			model.deliverAverageTime = dateFormate(mss);
		}
		Long mssChange = deliverAverageTimeChangeRate.longValue();// 转为long类型(变化时长)
		String deliverAverageTimeChangeRateStr = dateFormate(mssChange);// 平均发货时长变化时长
		if (mssChange == 0 || (historyResult.deliverAVGTime.compareTo(BigDecimal.ZERO) == 0)) {// 等于0 或者上个月的数值等于0，增长不显示
			model.deliverAverageTimeChangeRate = "";
		}
		if ("UP".equals(model.deliverAverageTimeCon)) {
			model.deliverAverageTimeChangeRate = "-" + deliverAverageTimeChangeRateStr;
		} else if ("DOWN".equals(model.deliverAverageTimeCon)) {
			model.deliverAverageTimeChangeRate = "+" + deliverAverageTimeChangeRateStr;
		}
		// 派单平均值
		BigDecimal distributeTotalOfYes = reportDao.distributeTotal4AgencyOfYes(agencyPks, bigList, startdate, enddate);// web类型的价格
		BigDecimal distributeTotalOfNo = reportDao.distributeTotal4AgencyOfNo(agencyPks, bigList, startdate, enddate);// 非web类型的价格
		// 派单总价格
		BigDecimal distributeSum = (distributeTotalOfYes == null ? BigDecimal.ZERO : distributeTotalOfYes)
				.add(distributeTotalOfNo == null ? BigDecimal.ZERO : distributeTotalOfNo);
		BigDecimal decimal = BigDecimal.ZERO;//结果数值
		if (distributeSum.equals(BigDecimal.ZERO) || distributeCount.equals(BigDecimal.ZERO)) {
			model.distributeAverageValue = "0";
			model.distributeAverageValueCon = this.compareTo(BigDecimal.ZERO, historyResult.distributeAVG);// 与历史记录比较
		} else {
			decimal = (distributeSum.divide(distributeCount, 2, BigDecimal.ROUND_DOWN));
			model.distributeAverageValue = RMBFormat.format(decimal);//修改价钱格式
			model.distributeAverageValue = this.removeZero(model.distributeAverageValue);
			model.distributeAverageValueCon = this.compareTo(decimal, historyResult.distributeAVG);// 与历史记录比较
		}
		model.distributeAverageValueChangeRate = this.calculateChangeRate(decimal, historyResult.distributeAVG, model.distributeAverageValueCon);//派单平均值变化比率
		

		// 接单平均值
		BigDecimal acceptTotalOfYes = reportDao.acceptTotal4AgencyOfYes(agencyPks, bigList, startdate, enddate);// web类型的价格
		BigDecimal acceptTotalOfNo = reportDao.acceptTotal4AgencyOfNo(agencyPks, bigList, startdate, enddate);// 非web类型的价格
		// 接单总价格
		BigDecimal acceptSum = (acceptTotalOfYes == null ? BigDecimal.ZERO : acceptTotalOfYes)
				.add(acceptTotalOfNo == null ? BigDecimal.ZERO : acceptTotalOfNo);
		decimal = BigDecimal.ZERO;
		if (acceptSum.equals(BigDecimal.ZERO) || acceptCount.equals(BigDecimal.ZERO)) {
			model.acceptAverageValue = "0";
			model.acceptAverageValueCon = this.compareTo(BigDecimal.ZERO, historyResult.acceptAVG);// 与历史记录比较
		} else {
			decimal = (acceptSum.divide(acceptCount, 2, BigDecimal.ROUND_DOWN));
			model.acceptAverageValue = RMBFormat.format(decimal);//修改价钱格式
			model.acceptAverageValue = this.removeZero(model.acceptAverageValue );
			model.acceptAverageValueCon = this.compareTo(decimal, historyResult.acceptAVG);// 与历史记录比较
		}
		model.acceptAverageValueChangeRate = this.calculateChangeRate(decimal, historyResult.acceptAVG, model.acceptAverageValueCon);//接单平均值变化比率
		

		return model;
	}

	public ReportModel OrdersOfStore(String roleCode, String storeId, String startdate, String enddate,
			String dateFormat) {
		ReportModel model = new ReportModel();
		model = this.isShow(roleCode);
		if ("Y".equals(model.judgement)) {
			return model;
		}

		// 历史数据
		// 修改开始时间
		String pastStartTime = "";
		String pastEndTime = "";
		try {
			pastStartTime = this.modifyStartTime(startdate, dateFormat);
			pastEndTime = this.modifyEndTime(enddate, dateFormat);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ReportDetailEntity historyResult = reportDao.getContrastInfo("", dateFormat, "Y", storeId, pastStartTime,
				pastEndTime);
		historyResult = this.historyRecordIsNull(historyResult);// 历史记录非空处理

		BigDecimal distributedOrders4Store = reportDao.distributedOrders4Store(storeId, startdate, enddate);// 派单数量
		BigDecimal acceptOrders4Store = reportDao.acceptOrders4Store(storeId, startdate, enddate);// 接单数量
		BigDecimal deliveryOrdersCounts = reportDao.deliveryOrdersCounts(null, storeId, null, startdate, enddate);// 发货数量
		BigDecimal acceptTimeSum4Store = reportDao.acceptTimeSum4Store(storeId, startdate, enddate);// 接单时间-派单时间的差值的总和
		BigDecimal deliveryTimeSum4Store = reportDao.deliveryTimeSum4Store(storeId, startdate, enddate);// 发货时间-接单时间的差值的总和

		// 派单数量
		BigDecimal distributeCount = BigDecimalIfNull(distributedOrders4Store);
		model.distributeCount = distributeCount.toString();
		model.distributeCountCon = this.compareTo(distributeCount, historyResult.distributeCount);// 与历史记录比较
		model.distributeCountChangeRate = this.calculateChangeRate(distributeCount, historyResult.distributeCount, model.distributeCountCon);//派单数量变化比值


		// 接单数量
		BigDecimal acceptCount = BigDecimalIfNull(acceptOrders4Store);
		model.acceptCount = acceptCount.toString();
		model.acceptCountCon = this.compareTo(acceptCount, historyResult.acceptCount);// 与历史记录比较
		model.acceptCountChangeRate = this.calculateChangeRate(acceptCount, historyResult.acceptCount, model.acceptCountCon);//接单数量变化比值
		

		// 累计接单率
		BigDecimal acceptRatechanage  = BigDecimal.ZERO;
		if (distributeCount.equals(BigDecimal.ZERO) || acceptCount.equals(BigDecimal.ZERO)) {// 判断是否为空
			model.acceptRate = "0";
			model.acceptRateCon = this.compareTo(new BigDecimal(0), historyResult.acceptRate);// 与历史记录比较
		} else {
			// 累计接单率 = 接单数/派单数 *100
			BigDecimal acceptRateBig = (acceptCount.divide(distributeCount, 4, BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100));
			 acceptRatechanage = acceptRateBig.setScale(2, BigDecimal.ROUND_HALF_UP);
			model.acceptRate = acceptRatechanage + "%";
			model.acceptRate = this.removeZeroOfPrecent(model.acceptRate );
			model.acceptRateCon = this.compareTo(acceptRatechanage, historyResult.acceptRate);// 与历史记录比较
		}
		model.acceptRateChangeRate = this.calculateChangeRate(acceptRatechanage, historyResult.acceptRate, model.acceptRateCon);//累计接单率变化比率
		

		// 平均接单时长
		if (distributedOrders4Store == null || distributedOrders4Store.equals(BigDecimal.ZERO)// 接单数为0时，不能作为被除数
				|| acceptTimeSum4Store == null || acceptTimeSum4Store.equals(BigDecimal.ZERO)) {// 接单时间为空
			model.acceptAverageTime = "0";
		} else {
			BigDecimal secondsBig = acceptTimeSum4Store.divide(distributedOrders4Store, 0, BigDecimal.ROUND_DOWN);// 平均秒数
			Long mss = secondsBig.longValue();// 转为long类型

			model.acceptAverageTime =  this. dateFormate(mss);
		}

		// 平均发货时长
		BigDecimal deliverAverageTimeChangeRate = BigDecimal.ZERO;
		if (deliveryOrdersCounts == null || deliveryOrdersCounts.equals(BigDecimal.ZERO)// 发货数为0时，不能作为被除数
				|| deliveryTimeSum4Store == null || deliveryTimeSum4Store.equals(BigDecimal.ZERO)) {// 发货时间为空
			model.deliverAverageTime = "0";
			model.deliverAverageTimeCon = this.compareToOfTime(new BigDecimal(0), historyResult.deliverAVGTime);// 与历史记录比较
			deliverAverageTimeChangeRate = historyResult.deliverAVGTime;// 平均发货时长变化时长
		} else {
			BigDecimal secondsBig = deliveryTimeSum4Store.divide(deliveryOrdersCounts, 0, BigDecimal.ROUND_DOWN);// 平均秒数
			model.deliverAverageTimeCon = this.compareToOfTime(secondsBig, historyResult.deliverAVGTime);// 与历史记录比较
			deliverAverageTimeChangeRate = (historyResult.deliverAVGTime.subtract(secondsBig)).abs();// 平均发货时长变化时长

			Long mss = secondsBig.longValue();// 转为long类型
			model.deliverAverageTime = dateFormate(mss);
		}
		Long mssChange = deliverAverageTimeChangeRate.longValue();// 转为long类型(变化时长)
		String deliverAverageTimeChangeRateStr = dateFormate(mssChange);// 平均发货时长变化时长
		if (mssChange == 0 || (historyResult.deliverAVGTime.compareTo(BigDecimal.ZERO) == 0)) {// 等于0 或者上个月的数值等于0，增长不显示
			model.deliverAverageTimeChangeRate = "";
		}
		if ("UP".equals(model.deliverAverageTimeCon)) {
			model.deliverAverageTimeChangeRate = "-" + deliverAverageTimeChangeRateStr;
		} else if ("DOWN".equals(model.deliverAverageTimeCon)) {
			model.deliverAverageTimeChangeRate = "+" + deliverAverageTimeChangeRateStr;
		}

		// 派单平均值
		BigDecimal distributeTotalOfYes = reportDao.distributeTotal4StoreOfYes(storeId, startdate, enddate);// web类型的价格
		BigDecimal distributeTotalOfNo = reportDao.distributeTotal4StoreOfNo(storeId, startdate, enddate);// 非web类型的价格
		// 派单总价格
		BigDecimal distributeSum = (distributeTotalOfYes == null ? BigDecimal.ZERO : distributeTotalOfYes)
				.add(distributeTotalOfNo == null ? BigDecimal.ZERO : distributeTotalOfNo);
		BigDecimal decimal = BigDecimal.ZERO;//结果数值
		if (distributeSum.equals(BigDecimal.ZERO) || distributeCount.equals(BigDecimal.ZERO)) {
			model.distributeAverageValue = "0";
			model.distributeAverageValueCon = this.compareTo(BigDecimal.ZERO, historyResult.distributeAVG);// 与历史记录比较
		} else {
			 decimal = (distributeSum.divide(distributeCount, 2, BigDecimal.ROUND_DOWN));
			model.distributeAverageValue = RMBFormat.format(decimal);//修改价钱格式
			model.distributeAverageValue = this.removeZero(model.distributeAverageValue);
			
			model.distributeAverageValueCon = this.compareTo(decimal, historyResult.distributeAVG);// 与历史记录比较
		}
		model.distributeAverageValueChangeRate = this.calculateChangeRate(decimal, historyResult.distributeAVG, model.distributeAverageValueCon);//派单平均值变化比率
		

		// 接单平均值
		BigDecimal acceptTotalOfYes = reportDao.acceptTotal4StoreOfYes(storeId, startdate, enddate);// web类型的价格
		BigDecimal acceptTotalOfNo = reportDao.acceptTotal4StoreOfNo(storeId, startdate, enddate);// 非web类型的价格
		// 接单总价格
		BigDecimal acceptSum = (acceptTotalOfYes == null ? BigDecimal.ZERO : acceptTotalOfYes)
				.add(acceptTotalOfNo == null ? BigDecimal.ZERO : acceptTotalOfNo);
		decimal = BigDecimal.ZERO;//结果数值
		if (acceptSum.equals(BigDecimal.ZERO) || acceptCount.equals(BigDecimal.ZERO)) {
			model.acceptAverageValue = "0";
			model.acceptAverageValueCon = this.compareTo(BigDecimal.ZERO, historyResult.acceptAVG);// 与历史记录比较
		} else {
			 decimal = (acceptSum.divide(acceptCount, 2, BigDecimal.ROUND_DOWN));
			model.acceptAverageValue = RMBFormat.format(decimal);//修改价钱格式
			model.acceptAverageValue = this.removeZero(model.acceptAverageValue);
			model.acceptAverageValueCon = this.compareTo(decimal, historyResult.acceptAVG);// 与历史记录比较
		}
		model.acceptAverageValueChangeRate = this.calculateChangeRate(decimal, historyResult.acceptAVG, model.acceptAverageValueCon);//接单平均值变化比率
		

		return model;
	}

	public ReportModel PrepaysOfAgency(List<String> agencyPks, String startdate, String enddate, String code,
			String dateFormat) throws Exception {
		ReportModel model = new ReportModel();
		if (agencyPks == null || agencyPks.isEmpty()) {// 如果经销山ids列表为空，直接返回空数据
			model = this.isAgencyPksNull();
			return model;
		}
		// 分割List
		List<List<String>> bigList = new ArrayList<List<String>>();
		if (agencyPks.size() > 500) {
			bigList = listToBigList(agencyPks);
			agencyPks = bigList.get(0);
		}

		// 历史数据
		// 修改开始时间
		String pastStartTime = "";
		String pastEndTime = "";
		try {
			pastStartTime = this.modifyStartTime(startdate, dateFormat);
			pastEndTime = this.modifyEndTime(enddate, dateFormat);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ReportDetailEntity historyResult = reportDao.getContrastInfo(code, dateFormat, "N", "", pastStartTime,
				pastEndTime);
		historyResult = this.historyRecordIsNull(historyResult);// 历史记录非空处理

		BigDecimal distributedPrepay4Agency = reportDao.distributedPrepay4Agency(agencyPks, bigList, startdate,
				enddate);// 派单数量
		BigDecimal acceptPrepay4Agency = reportDao.acceptPrepay4Agency(agencyPks, bigList, startdate, enddate);// 接单数量
		BigDecimal prePayWriteOffOfAgency = reportDao.prePayWriteOffOfAgency(agencyPks, bigList, startdate, enddate);// 核销数量
		BigDecimal writeOffTimeSum4Agency = reportDao.writeOffTimeSum4Agency(agencyPks, bigList, startdate, enddate); // 核销时间-接单时间的差值的总和（秒）
		BigDecimal writeOffPriceOfAgency = reportDao.writeOffPriceOfAgency(agencyPks, bigList, startdate, enddate);// 核销金额总和

		// 派单数量
		BigDecimal distributeCount = BigDecimalIfNull(distributedPrepay4Agency);
		model.distributeCount = distributeCount.toString();
		model.distributeCountCon = this.compareTo(distributeCount, historyResult.distributeCount);// 与历史记录比较
		model.distributeCountChangeRate = this.calculateChangeRate(distributeCount, historyResult.distributeCount, model.distributeCountCon);//派单数量变化比值
		

		// 接单数量
		BigDecimal acceptCount = BigDecimalIfNull(acceptPrepay4Agency);
		model.acceptCount = acceptCount.toString();
		// model.acceptCountCon =
		// this.compareTo(acceptCount,historyResult.acceptCount);//与历史记录比较

		// 核销数量
		BigDecimal writeOffCount = BigDecimalIfNull(prePayWriteOffOfAgency);
		model.writeOffCount = writeOffCount.toString();
		model.writeOffCountCon = this.compareTo(writeOffCount, historyResult.writeOffCount);// 与历史记录比较
		model.writeOffCountChangeRate = this.calculateChangeRate(writeOffCount, historyResult.writeOffCount, model.writeOffCountCon);//核销数量变化比值
		
		// 核销率
		BigDecimal writeOffRatechange = BigDecimal.ZERO;
		if (acceptCount.equals(BigDecimal.ZERO) || writeOffCount.equals(BigDecimal.ZERO)) {// 判断是否为空
			model.writeOffRate = "0";
			model.writeOffRateCon = this.compareTo(BigDecimal.ZERO, historyResult.writeOffRate);// 与历史记录比较
		} else {
			// 核销率 = 核销数/接单数 *100
			BigDecimal writeOffRateBig = (writeOffCount.divide(acceptCount, 4, BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100));
			writeOffRatechange = writeOffRateBig.setScale(2, BigDecimal.ROUND_HALF_UP);
			model.writeOffRate = writeOffRatechange + "%";
			model.writeOffRate = this.removeZeroOfPrecent(model.writeOffRate);
			model.writeOffRateCon = this.compareTo(writeOffRatechange, historyResult.writeOffRate);// 与历史记录比较
		}
		model.writeOffRateChangeRate = this.calculateChangeRate(writeOffRatechange, historyResult.writeOffRate, model.writeOffRateCon);//核销率变化比值
		

		// 平均核销时长
		BigDecimal writeOffAverageTimeChangeRate = BigDecimal.ZERO;
		if (prePayWriteOffOfAgency == null || prePayWriteOffOfAgency.equals(BigDecimal.ZERO)// 核销数为0时，不能作为被除数
				|| writeOffTimeSum4Agency == null || writeOffTimeSum4Agency.equals(BigDecimal.ZERO)) {// 核销时间为0
			model.writeOffAverageTime = "0";
			model.writeOffAverageTimeCon = this.compareToOfTime(BigDecimal.ZERO, historyResult.writeOffTime);// 与历史记录比较
			writeOffAverageTimeChangeRate = historyResult.writeOffTime;// 平均核销时长变化时长
		} else {
			BigDecimal secondsBig = writeOffTimeSum4Agency.divide(prePayWriteOffOfAgency, 0, BigDecimal.ROUND_DOWN);// 平均秒数
			model.writeOffAverageTimeCon = this.compareToOfTime(secondsBig, historyResult.writeOffTime);// 与历史记录比较
			writeOffAverageTimeChangeRate = (historyResult.writeOffTime.subtract(secondsBig)).abs();// 平均核销时长变化时长
			Long mss = secondsBig.longValue();// 转为long类型
			model.writeOffAverageTime = this.dateFormate(mss);// 转格式

		}
		Long mssChange = writeOffAverageTimeChangeRate.longValue();// 转为long类型(变化时长)
		String writeOffAverageTimeChangeRateStr = dateFormate(mssChange);// 平均核销时长变化时长
		if (mssChange == 0 || (historyResult.writeOffTime.compareTo(BigDecimal.ZERO) == 0)) {// 等于0 或者上个月的数值等于0，增长不显示
			model.writeOffAverageTimeChangeRate = "";
		}
		if ("UP".equals(model.writeOffAverageTimeCon)) {
			model.writeOffAverageTimeChangeRate = "-" + writeOffAverageTimeChangeRateStr;
		} else if ("DOWN".equals(model.writeOffAverageTimeCon)) {
			model.writeOffAverageTimeChangeRate = "+" + writeOffAverageTimeChangeRateStr;
		}

		// 核销平均值
		BigDecimal decimal = BigDecimal.ZERO;
		BigDecimal writeOffPrice = BigDecimalIfNull(writeOffPriceOfAgency);// 核销金额总和
		if (writeOffPrice.equals(BigDecimal.ZERO) || writeOffCount.equals(BigDecimal.ZERO)) {
			model.writeOffAverageValue = "0";
			model.writeOffAverageValueCon = this.compareTo(BigDecimal.ZERO, historyResult.writeOffAVG);// 与历史记录比较
		} else {
			 decimal = (writeOffPrice.divide(writeOffCount, 2, BigDecimal.ROUND_HALF_UP));
			model.writeOffAverageValue = RMBFormat.format(decimal);//修改价钱格式
			model.writeOffAverageValue = this.removeZero(model.writeOffAverageValue);
			model.writeOffAverageValueCon = this.compareTo(decimal, historyResult.writeOffAVG);// 与历史记录比较
		}
		model.writeOffAverageValueChangeRate = this.calculateChangeRate(decimal, historyResult.writeOffAVG, model.writeOffAverageValueCon);//核销率变化比值
		
		// 核销好评率--?
		model.favorableRate = "NO";// 参数不正确
		return model;
	}

	public ReportModel PrepaysOfStore(String storeId, String startdate, String enddate, String dateFormat) {
		ReportModel model = new ReportModel();

		// 历史数据
		// 修改开始时间
		String pastStartTime = "";
		String pastEndTime = "";
		try {
			pastStartTime = this.modifyStartTime(startdate, dateFormat);
			pastEndTime = this.modifyEndTime(enddate, dateFormat);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ReportDetailEntity historyResult = reportDao.getContrastInfo("", dateFormat, "Y", storeId, pastStartTime,
				pastEndTime);
		historyResult = this.historyRecordIsNull(historyResult);// 历史记录非空处理

		BigDecimal distributePrepayOfStore = reportDao.distributePrepayOfStore(storeId, startdate, enddate);// 派单数量
		BigDecimal acceptPrepayOfStore = reportDao.acceptPrepayOfStore(storeId, startdate, enddate);// 接单数量
		BigDecimal prePayWriteOffOfStrore = reportDao.prePayWriteOffOfStrore(storeId, startdate, enddate);// 核销数量
		BigDecimal writeOffTimeSum4Store = reportDao.writeOffTimeSum4Store(storeId, startdate, enddate); // 核销时间-接单时间的差值的总和（秒）
		BigDecimal writeOffPriceOfStore = reportDao.writeOffPriceOfStore(storeId, startdate, enddate);// 核销金额总和

		// 派单数量
		BigDecimal distributeCount = BigDecimalIfNull(distributePrepayOfStore);
		model.distributeCount = distributeCount.toString();
		model.distributeCountCon = this.compareTo(distributeCount, historyResult.distributeCount);// 与历史记录比较
		model.distributeCountChangeRate = this.calculateChangeRate(distributeCount, historyResult.distributeCount, model.distributeCountCon);//派单数量变化比值
		

		// 接单数量
		BigDecimal acceptCount = BigDecimalIfNull(acceptPrepayOfStore);
		model.acceptCount = acceptCount.toString();
		// model.acceptCountCon =
		// this.compareTo(acceptCount,historyResult.acceptCount);//与历史记录比较

		// 核销数量
		BigDecimal writeOffCount = BigDecimalIfNull(prePayWriteOffOfStrore);
		model.writeOffCount = writeOffCount.toString();
		model.writeOffCountCon = this.compareTo(writeOffCount, historyResult.writeOffCount);// 与历史记录比较
		model.writeOffCountChangeRate = this.calculateChangeRate(writeOffCount, historyResult.writeOffCount, model.writeOffCountCon);//核销数量变化比值
		

		// 核销率
		BigDecimal writeOffRatechange = BigDecimal.ZERO;
		if (acceptCount.equals(BigDecimal.ZERO) || writeOffCount.equals(BigDecimal.ZERO)) {// 判断是否为空
			model.writeOffRate = "0";
			model.writeOffRateCon = this.compareTo(BigDecimal.ZERO, historyResult.writeOffRate);// 与历史记录比较
		} else {
			// 核销率 = 核销数/接单数 *100
			BigDecimal writeOffRateBig = (writeOffCount.divide(acceptCount, 4, BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100));
			writeOffRatechange = writeOffRateBig.setScale(2, BigDecimal.ROUND_HALF_UP);
			model.writeOffRate = writeOffRatechange + "%";
			model.writeOffRate = this.removeZeroOfPrecent(model.writeOffRate);
			model.writeOffRateCon = this.compareTo(writeOffRatechange, historyResult.writeOffRate);// 与历史记录比较
		}
		model.writeOffRateChangeRate = this.calculateChangeRate(writeOffRatechange, historyResult.writeOffRate, model.writeOffRateCon);//核销率变化比值
		

		// 平均核销时长
		BigDecimal writeOffAverageTimeChangeRate = BigDecimal.ZERO;
		if (prePayWriteOffOfStrore == null || prePayWriteOffOfStrore.equals(BigDecimal.ZERO)// 核销数为0时，不能作为被除数
				|| writeOffTimeSum4Store == null || writeOffTimeSum4Store.equals(BigDecimal.ZERO)) {// 核销数为0时，不能作为被除数
			model.writeOffAverageTime = "0";
			model.writeOffAverageTimeCon = this.compareToOfTime(BigDecimal.ZERO, historyResult.writeOffTime);// 与历史记录比较
			writeOffAverageTimeChangeRate = historyResult.writeOffTime;// 平均核销时长变化时长
		} else {
			BigDecimal secondsBig = writeOffTimeSum4Store.divide(prePayWriteOffOfStrore, 0, BigDecimal.ROUND_DOWN);// 平均秒数
			model.writeOffAverageTimeCon = this.compareToOfTime(secondsBig, historyResult.writeOffTime);// 与历史记录比较

			writeOffAverageTimeChangeRate = (historyResult.writeOffTime.subtract(secondsBig)).abs();// 平均核销时长变化时长
			Long mss = secondsBig.longValue();// 转为long类型
			model.writeOffAverageTime = this.dateFormate(mss);// 转格式

		}
		Long mssChange = writeOffAverageTimeChangeRate.longValue();// 转为long类型(变化时长)
		String writeOffAverageTimeChangeRateStr = dateFormate(mssChange);// 平均核销时长变化时长
		if (mssChange == 0 || (historyResult.writeOffTime.compareTo(BigDecimal.ZERO) == 0)) {// 等于0 或者上个月的数值等于0，增长不显示
			model.writeOffAverageTimeChangeRate = "";
		}
		if ("UP".equals(model.writeOffAverageTimeCon)) {
			model.writeOffAverageTimeChangeRate = "-" + writeOffAverageTimeChangeRateStr;
		} else if ("DOWN".equals(model.writeOffAverageTimeCon)) {
			model.writeOffAverageTimeChangeRate = "+" + writeOffAverageTimeChangeRateStr;
		}

		// 核销平均值
		BigDecimal writeOffPrice = BigDecimalIfNull(writeOffPriceOfStore);// 核销金额总和
		BigDecimal decimal = BigDecimal.ZERO;
		if (writeOffPrice.equals(BigDecimal.ZERO) || writeOffCount.equals(BigDecimal.ZERO)) {
			model.writeOffAverageValue = "0";
			model.writeOffAverageValueCon = this.compareTo(BigDecimal.ZERO, historyResult.writeOffAVG);// 与历史记录比较
		} else {
			decimal = (writeOffPrice.divide(writeOffCount, 2, BigDecimal.ROUND_HALF_UP));
			model.writeOffAverageValue = RMBFormat.format(decimal);//修改价钱格式
			model.writeOffAverageValue = this.removeZero(model.writeOffAverageValue);
			model.writeOffAverageValueCon = this.compareTo(decimal, historyResult.writeOffAVG);// 与历史记录比较
		}
		model.writeOffAverageValueChangeRate = this.calculateChangeRate(decimal, historyResult.writeOffAVG, model.writeOffAverageValueCon);//核销率变化比值
		
		// 核销好评率--?
		model.favorableRate = "NO";// 参数不正确

		return model;
	}

	public ReportModel SalesLeadesOfAgency(List<String> agencyPks, String startdate, String enddate, String code,
			String dateFormat) throws Exception {
		ReportModel model = new ReportModel();
		if (agencyPks == null || agencyPks.isEmpty()) {// 如果经销山ids列表为空，直接返回空数据
			model = this.isAgencyPksNull();
			return model;
		}
		// 分割List
		List<List<String>> bigList = new ArrayList<List<String>>();
		if (agencyPks.size() > 500) {
			bigList = listToBigList(agencyPks);
			agencyPks = bigList.get(0);
		}

		// 历史数据
		// 修改开始时间
		String pastStartTime = "";
		String pastEndTime = "";
		try {
			pastStartTime = this.modifyStartTime(startdate, dateFormat);
			pastEndTime = this.modifyEndTime(enddate, dateFormat);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ReportDetailEntity historyResult = reportDao.getContrastInfo(code, dateFormat, "N", "", pastStartTime,
				pastEndTime);
		historyResult = this.historyRecordIsNull(historyResult);// 历史记录非空处理

		BigDecimal distributeSalesLeadsOfAgency = reportDao.distributeSalesLeadsOfAgency(agencyPks, bigList, startdate,
				enddate);// 派单数量
		BigDecimal acceptSalesLeadsOfAgency = reportDao.acceptSalesLeadsOfAgency(agencyPks, bigList, startdate,
				enddate);// 接单数量
		BigDecimal salesLeadsSuccessfulCount = reportDao.salesLeadsSuccessfulCount(agencyPks, null, bigList, startdate,
				enddate);// 转化笔数
		BigDecimal salesLeadsSuccessPrice = reportDao.salesLeadsSuccessPrice(agencyPks, null, bigList, startdate,
				enddate);// 转化成功的金额总价
		BigDecimal salesLeadsSuccessTimes = reportDao.salesLeadsSuccessTimes(agencyPks, null, bigList, startdate,
				enddate);// 转化成功时间-接单时间

		// 派单数量
		BigDecimal distributeCount = BigDecimalIfNull(distributeSalesLeadsOfAgency);
		model.distributeCount = distributeCount.toString();

		// 接单数量
		BigDecimal acceptCount = BigDecimalIfNull(acceptSalesLeadsOfAgency);
		model.acceptCount = acceptCount.toString();

		// 转化笔数
		BigDecimal conversionCount = BigDecimalIfNull(salesLeadsSuccessfulCount);
		model.conversionCount = conversionCount.toString();
		model.conversionCountCon = this.compareTo(conversionCount, historyResult.conversionCount);// 与历史记录比较
		model.conversionCountChangeRate = this.calculateChangeRate(conversionCount, historyResult.conversionCount, model.conversionCountCon);//转化笔数变化比值
		

		// 转化率
		BigDecimal conversionRateChange = BigDecimal.ZERO;
		if (acceptCount.equals(BigDecimal.ZERO) || conversionCount.equals(BigDecimal.ZERO)) {// 判断是否为空
			model.conversionRate = "0";
			model.conversionRateCon = this.compareTo(new BigDecimal(0), historyResult.conversionRate);// 与历史记录比较
		} else {
			// 转化率 = 转化笔数/接单数量 *100
			BigDecimal conversionRateBig = (conversionCount.divide(acceptCount, 4, BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100));
			 conversionRateChange = conversionRateBig.setScale(2, BigDecimal.ROUND_HALF_UP);
			model.conversionRate = conversionRateChange + "%";
			model.conversionRate = this.removeZeroOfPrecent(model.conversionRate);
			model.conversionRateCon = this.compareTo(conversionRateChange, historyResult.conversionRate);// 与历史记录比较
		}
		model.conversionRateChangeRate = this.calculateChangeRate(conversionRateChange, historyResult.conversionRate, model.conversionRateCon);//转化率变化比值
		
		

		// 转化平均值 = 转化总金额/转化数量
		BigDecimal successPrice = BigDecimalIfNull(salesLeadsSuccessPrice);
		BigDecimal decimal = BigDecimal.ZERO;
		if (successPrice.equals(BigDecimal.ZERO) || conversionCount.equals(BigDecimal.ZERO)) {
			model.conversionAverageValue = "0";
			model.conversionAverageValueCon = this.compareTo(new BigDecimal(0), historyResult.conversionAVG);// 与历史记录比较

		} else {
			decimal= (successPrice.divide(conversionCount, 2, BigDecimal.ROUND_HALF_UP));
			model.conversionAverageValue =RMBFormat.format(decimal);//修改价钱格式
			model.conversionAverageValue = this.removeZero(model.conversionAverageValue);
			model.conversionAverageValueCon = this.compareTo(decimal, historyResult.conversionAVG);// 与历史记录比较
		}
		model.conversionAverageValueChangeRate = this.calculateChangeRate(decimal, historyResult.conversionAVG, model.conversionAverageValueCon);//转化平均值变化比值
		

		// 平均转化时长
		BigDecimal conversionAverageTimeChangeRate = BigDecimal.ZERO;
		if (salesLeadsSuccessfulCount == null || salesLeadsSuccessfulCount.equals(BigDecimal.ZERO)// 转化笔数为0时，不能作为被除数
				|| salesLeadsSuccessTimes == null || salesLeadsSuccessTimes.equals(BigDecimal.ZERO)) {// 成功时间为0
			model.conversionAverageTime = "0";
			model.conversionAverageTimeCon = this.compareToOfTime(new BigDecimal(0), historyResult.conversionAVGTime);// 与历史记录比较
			conversionAverageTimeChangeRate = historyResult.conversionAVGTime;// 平均转化时长变化时长
		} else {
			BigDecimal secondsBig = salesLeadsSuccessTimes.divide(salesLeadsSuccessfulCount, 0, BigDecimal.ROUND_DOWN);// 平均秒数
			model.conversionAverageTimeCon = this.compareToOfTime(secondsBig, historyResult.conversionAVGTime);// 与历史记录比较
			Long mss = secondsBig.longValue();// 转为long类型
			model.conversionAverageTime = dateFormate(mss);
		}
		Long mssChange = conversionAverageTimeChangeRate.longValue();// 转为long类型(变化时长)
		String conversionAverageTimeChangeRateStr = dateFormate(mssChange);// 平均核销时长变化时长
		if (mssChange == 0 || (historyResult.conversionAVGTime.compareTo(BigDecimal.ZERO) == 0)) {// 等于0
																									// 或者上个月的数值等于0，增长不显示
			model.conversionAverageTimeChangeRate = "";
		}
		if ("UP".equals(model.conversionAverageTimeCon)) {
			model.conversionAverageTimeChangeRate = "-" + conversionAverageTimeChangeRateStr;
		} else if ("DOWN".equals(model.conversionAverageTimeCon)) {
			model.conversionAverageTimeChangeRate = "+" + conversionAverageTimeChangeRateStr;
		}

		return model;
	}

	public ReportModel SalesLeadesOfStore(String storeId, String startdate, String enddate, String dateFormat)
			throws Exception {
		ReportModel model = new ReportModel();

		// 历史数据
		// 修改开始时间
		String pastStartTime = "";
		String pastEndTime = "";
		try {
			pastStartTime = this.modifyStartTime(startdate, dateFormat);
			pastEndTime = this.modifyEndTime(enddate, dateFormat);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ReportDetailEntity historyResult = reportDao.getContrastInfo("", dateFormat, "Y", storeId, pastStartTime,
				pastEndTime);
		historyResult = this.historyRecordIsNull(historyResult);// 历史记录非空处理

		BigDecimal distributeSalesLeadsOfStore = reportDao.distributeSalesLeadsOfStore(storeId, startdate, enddate);// 派单数量
		BigDecimal acceptSalesLeadsOfStore = reportDao.acceptSalesLeadsOfStore(storeId, startdate, enddate);// 接单数量
		BigDecimal salesLeadsSuccessfulCount = reportDao.salesLeadsSuccessfulCount(null, storeId, null, startdate,
				enddate);// 转化笔数
		BigDecimal salesLeadsSuccessPrice = reportDao.salesLeadsSuccessPrice(null, storeId, null, startdate, enddate);// 转化成功的金额总价
		BigDecimal salesLeadsSuccessTimes = reportDao.salesLeadsSuccessTimes(null, storeId, null, startdate, enddate);// 转化成功时间-接单时间

		// 派单数量
		BigDecimal distributeCount = BigDecimalIfNull(distributeSalesLeadsOfStore);
		model.distributeCount = distributeCount.toString();

		// 接单数量
		BigDecimal acceptCount = BigDecimalIfNull(acceptSalesLeadsOfStore);
		model.acceptCount = acceptCount.toString();

		// 转化笔数
		BigDecimal conversionCount = BigDecimalIfNull(salesLeadsSuccessfulCount);
		model.conversionCount = conversionCount.toString();
		model.conversionCountCon = this.compareTo(conversionCount, historyResult.conversionCount);// 与历史记录比较
		model.conversionCountChangeRate = this.calculateChangeRate(conversionCount, historyResult.conversionCount, model.conversionCountCon);//转化笔数变化比值
		

		// 转化率
		BigDecimal conversionRateChange = BigDecimal.ZERO;
		if (acceptCount.equals(BigDecimal.ZERO) || conversionCount.equals(BigDecimal.ZERO)) {// 判断是否为空
			model.conversionRate = "0";
			model.conversionRateCon = this.compareTo(new BigDecimal(0), historyResult.conversionRate);// 与历史记录比较
		} else {
			// 转化率 = 转化笔数/接单数量 *100
			BigDecimal conversionRateBig = (conversionCount.divide(acceptCount, 4, BigDecimal.ROUND_HALF_UP))
					.multiply(new BigDecimal(100));
			 conversionRateChange = conversionRateBig.setScale(2, BigDecimal.ROUND_HALF_UP);
			model.conversionRate = conversionRateChange + "%";
			model.conversionRate = this.removeZeroOfPrecent(model.conversionRate);
			model.conversionRateCon = this.compareTo(conversionRateChange, historyResult.conversionRate);// 与历史记录比较
		}
		model.conversionRateChangeRate = this.calculateChangeRate(conversionRateChange, historyResult.conversionRate, model.conversionRateCon);//转化率变化比值
		

		// 转化平均值 = 转化总金额/转化数量
		BigDecimal decimal = BigDecimal.ZERO;
		BigDecimal successPrice = BigDecimalIfNull(salesLeadsSuccessPrice);
		if (successPrice.equals(BigDecimal.ZERO) || conversionCount.equals(BigDecimal.ZERO)) {
			model.conversionAverageValue = "0";
			model.conversionAverageValueCon = this.compareTo(new BigDecimal(0), historyResult.conversionAVG);// 与历史记录比较

		} else {
			decimal = (successPrice.divide(conversionCount, 2, BigDecimal.ROUND_HALF_UP));
			model.conversionAverageValue = RMBFormat.format(decimal);//修改价钱格式
			model.conversionAverageValue = this.removeZero(model.conversionAverageValue);
			model.conversionAverageValueCon = this.compareTo(decimal, historyResult.conversionAVG);// 与历史记录比较
		}
		model.conversionAverageValueChangeRate = this.calculateChangeRate(decimal, historyResult.conversionAVG, model.conversionAverageValueCon);//转化平均值变化比值
		

		// 平均转化时长
		BigDecimal 	conversionAverageTimeChangeRate = BigDecimal.ZERO;
		if (salesLeadsSuccessfulCount == null || salesLeadsSuccessfulCount.equals(BigDecimal.ZERO)// 转化笔数为0时，不能作为被除数
				|| salesLeadsSuccessTimes == null || salesLeadsSuccessTimes.equals(BigDecimal.ZERO)) {// 成功时间为0
			model.conversionAverageTime = "0";
			model.conversionAverageTimeCon = this.compareToOfTime(new BigDecimal(0), historyResult.conversionAVGTime);// 与历史记录比较
			conversionAverageTimeChangeRate = historyResult.conversionAVGTime;//平均转化时长变化时长
		} else {
			BigDecimal secondsBig = salesLeadsSuccessTimes.divide(salesLeadsSuccessfulCount, 0, BigDecimal.ROUND_DOWN);// 平均秒数
			model.conversionAverageTimeCon = this.compareToOfTime(secondsBig, historyResult.conversionAVGTime);// 与历史记录比较

			Long mss = secondsBig.longValue();// 转为long类型

			model.conversionAverageTime = dateFormate(mss);
		}
		 Long mssChange = conversionAverageTimeChangeRate.longValue();//转为long类型(变化时长)
		 String conversionAverageTimeChangeRateStr = dateFormate(mssChange);//平均核销时长变化时长
		 if(mssChange==0||(historyResult.conversionAVGTime.compareTo(BigDecimal.ZERO)==0)) {//等于0 或者上个月的数值等于0，增长不显示
			 model.conversionAverageTimeChangeRate ="";
		 }
		 if ("UP".equals(model.conversionAverageTimeCon)) {
			 model.conversionAverageTimeChangeRate ="-"+conversionAverageTimeChangeRateStr;
		 }else if("DOWN".equals(model.conversionAverageTimeCon)) {
			 model.conversionAverageTimeChangeRate ="+"+conversionAverageTimeChangeRateStr;
		 }
		
		return model;
	}

	/**
	 * 调用hybirs接口
	 */
	public List<String> getAppGroupInfo(String code) {
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
	 * 空指针处理
	 */
	private BigDecimal BigDecimalIfNull(BigDecimal bigDecimal) {
		if (bigDecimal == null) {
			bigDecimal = BigDecimal.ZERO;
		}
		return bigDecimal;
	}

	/**
	 * 判断agencyPKs为空时处理
	 */
	private ReportModel isAgencyPksNull() {
		ReportModel model = new ReportModel();
		model.distributeCount = "0";
		model.acceptCount = "0";

		model.distributeAverageValue = "";
		model.acceptAverageValue = "";
		model.acceptAverageTime = "";
		model.deliverAverageTime = "";
		model.acceptRate = "";

		model.writeOffCount = "0";
		model.writeOffAverageValue = "";
		model.writeOffRate = "";
		model.writeOffAverageTime = "";
		model.favorableRate = "";

		model.conversionCount = "0";
		model.conversionAverageValue = "";
		model.conversionRate = "";
		model.conversionAverageTime = "";
		return model;
	}

	private ReportModel isShow(String roleCode) {
		ReportModel model = new ReportModel();
		if ("clerk".equals(roleCode) || "manager".equals(roleCode) || "fenxiaoshang".equals(roleCode)) {// 6,7,8级
			model.distributeCount = "NO";
			model.distributeAverageValue = "NO";
			model.acceptCount = "NO";
			model.acceptAverageValue = "NO";
			model.deliverAverageTime = "NO";
			model.acceptRate = "NO";
			model.acceptAverageValue = "NO";
			model.acceptAverageTime = "NO";
			model.judgement = "Y";
		}
		return model;
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

	/***
	 * 修改开始时间
	 */
	private String modifyStartTime(String startTime, String dateFormat) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(startTime);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		if ("YEAR".equals(dateFormat)) {// 一年前
			calendar.add(Calendar.MONTH, -12);
		}
		if ("QUARTER".equals(dateFormat)) {// 一个季度
			calendar.add(Calendar.MONTH, -3);
		}
		if ("MONTH".equals(dateFormat)) {// 一个月之前
			calendar.add(Calendar.MONTH, -1);
		}
		String out = sdf.format(calendar.getTime());
		return out;
	}

	/***
	 * 修改结束时间
	 */
	private String modifyEndTime(String endtTime, String dateFormat) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(endtTime);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		if ("YEAR".equals(dateFormat)) {// 一年前
			calendar.add(Calendar.MONTH, -12);
			calendar.add(Calendar.MONTH, 1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		}
		if ("QUARTER".equals(dateFormat)) {// 一个季度
			calendar.add(Calendar.MONTH, -3);
			calendar.add(Calendar.MONTH, 1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		}
		if ("MONTH".equals(dateFormat)) {// 一个月之前
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
		}
		String out = sdf.format(calendar.getTime());
		return out;
	}

	/**
	 * 历史记录非空处理
	 */
	@SuppressWarnings("null")
	private ReportDetailEntity historyRecordIsNull(ReportDetailEntity historyResult) {
		ReportDetailEntity entity = new ReportDetailEntity();
		if (historyResult == null) {
			entity.distributeCount = BigDecimal.ZERO;
			entity.acceptCount = BigDecimal.ZERO;
			entity.distributeAVG = BigDecimal.ZERO;
			entity.acceptAVG = BigDecimal.ZERO;
			entity.acceptRate = BigDecimal.ZERO;
			entity.deliverAVGTime = BigDecimal.ZERO;

			entity.writeOffDistributeCount = BigDecimal.ZERO;
			entity.writeOffCount = BigDecimal.ZERO;
			entity.writeOffAVG = BigDecimal.ZERO;
			entity.writeOffRate = BigDecimal.ZERO;
			entity.writeOffTime = BigDecimal.ZERO;

			entity.conversionCount = BigDecimal.ZERO;
			entity.conversionAVG = BigDecimal.ZERO;
			entity.conversionRate = BigDecimal.ZERO;
			entity.conversionAVGTime = BigDecimal.ZERO;
			return entity;
		} else {
			return historyResult;
		}

	}

	// 测试
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		// for (int i = 0;i<100;i++) {
		// String iStr = String.valueOf(i);
		// list.add(iStr);
		// }
		//// System.out.println(list.toString());
		// List<List<String>> bigList = new ArrayList<List<String>>();
		// int size = list.size();
		// if (size>8) {
		// int index = (size/8)+1;//循环的次数
		// for(int i = 1;i<index;i++) {
		// List<String> newList = new ArrayList<String>(); //复制集合，以800为单位
		// if (i == (index-1)) {
		// newList.addAll(list.subList((8*i), list.size()));
		// }else {
		// newList.addAll(list.subList((8*i), (8*(i+1))));
		// }
		// bigList.add(newList);//添加至大集合
		// }
		// }
		// System.out.println(bigList.toString());
		//
		ReportServiceImpl serviceImpl = new ReportServiceImpl();
		try {
			String out = serviceImpl.modifyStartTime("2017-01-01", "MONTH");
			System.out.println(out + "------------");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	


}
