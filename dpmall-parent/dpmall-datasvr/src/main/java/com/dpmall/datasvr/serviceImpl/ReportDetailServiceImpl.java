package com.dpmall.datasvr.serviceImpl;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

//import org.springframework.util.StringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dpmall.common.HybrisUtils;
import com.dpmall.datasvr.api.IReportDetailService;
import com.dpmall.db.bean.ReportDetailEntity;
import com.dpmall.db.dao.ReportDetailDao;
import com.dpmall.model.ReportDetailModel;


@Service(value = "reportDetailService")
public class ReportDetailServiceImpl implements IReportDetailService {

	@Autowired
	private ReportDetailDao reportDetailDao;

	@Autowired
	private HybrisUtils hybrisUtils;
	
	NumberFormat RMBFormat = NumberFormat.getCurrencyInstance(Locale.CHINA);//换算价钱
	
	/**
	 * entity To model
	 */
	private ReportDetailModel  entityToModel(ReportDetailEntity entity) {
		if (entity==null) {
			return null;
		}
		ReportDetailModel model =new ReportDetailModel();
		model.historyDate = entity.historyDate;
//		model.historyResult = entity.historyResult;
		return model;
	}
	
	
	/**
	 * entityList To modelList
	 */
	private List<ReportDetailModel> entitysToModels(List<ReportDetailEntity> in){
		if(in == null || in.isEmpty()){
			return null;
		}
		List<ReportDetailModel> out = new ArrayList<ReportDetailModel>();
		for (ReportDetailEntity tmp : in) {
			out.add(entityToModel(tmp));
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
	
	
	
	//-----------------------------------------------历史记录---------------------------------------------------------------------------
		//---------------实物---------------------------------------
	/**
	 * 实物 历史记录 通用
	 */
	private List<ReportDetailEntity> orderHistory (String code, String dateFormat, Integer offset,Integer pageSize,String endTime) {
		List<ReportDetailEntity> entityList = new ArrayList<ReportDetailEntity>();
		
		//日期类型
		String format = this.returnDateFormat(dateFormat);
		//调用接口
		if (StringUtils.isEmpty(code) || StringUtils.isEmpty(format) || offset !=null || pageSize !=null) {// 查询，
			 entityList = reportDetailDao.orderHistory(code, format, offset, pageSize,endTime);
		}
		return entityList;
	}

	
	/**
	 * 实物 派单数 历史记录
	 */
	public List<ReportDetailModel> orderDistributedHistory(String code, String dateFormat, Integer offset,Integer pageSize,String endTime) {
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		List<ReportDetailEntity> entityList = this.orderHistory(code, dateFormat, offset, pageSize,endTime);//获取数据
		if (entityList == null) {
			return result;
		}
		for (ReportDetailEntity detailEntity : entityList) {
			ReportDetailModel  model = new ReportDetailModel();
			
			if (detailEntity.distributeCount == null || BigDecimal.ZERO == detailEntity.distributeCount) {
				model.result = "0";
				model.yResult = "0";//Y轴显示的数据
			}else {
				model.result = detailEntity.distributeCount.toString();//历史派单数
				model.yResult = detailEntity.distributeCount.toString();//Y轴显示的数据
			}
			
			model.historyDate = this.dateFormatChinese(dateFormat, detailEntity.historyDate);//历史时间
			
			model.xDate = detailEntity.historyDate;//x轴显示的时间
			if ("QUARTER".equals(dateFormat)) {//季度
				model.xDate  = detailEntity.historyDate+"季";
			}
			result.add(model);
		}
		return result;
	}
		
	
	
	/**
	 * 实物 接单 历史记录
	 */
	public List<ReportDetailModel> orderAcceptHistory(String code,String dateFormat,Integer offset,Integer pageSize,String endTime) {
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		List<ReportDetailEntity> entityList = this.orderHistory(code, dateFormat, offset, pageSize,endTime);//获取数据
		
		if (entityList == null) {
			return result;
		}
		//赋值
		for (ReportDetailEntity detailEntity : entityList) {
			ReportDetailModel  model = new ReportDetailModel();
			if (detailEntity.acceptCount == null || BigDecimal.ZERO == detailEntity.acceptCount) {
				model.result = "0";
				model.yResult = "0";//Y轴显示的数据
			}else {
				model.result = detailEntity.acceptCount.toString();//历史接单数
				model.yResult = detailEntity.acceptCount.toString();//Y轴显示的数据
			}
			
			model.historyDate = this.dateFormatChinese(dateFormat, detailEntity.historyDate);//历史时间
			
			model.xDate = detailEntity.historyDate;//x轴显示的时间
			if ("QUARTER".equals(dateFormat)) {//季度
				model.xDate  = detailEntity.historyDate+"季";
			}
			result.add(model);
		}
		return result;
	}
		
	
	
	/**
	 * 实物 派单平均值 历史记录
	 */
	public List<ReportDetailModel> orderDistributeAVGHistory(String code, String dateFormat, Integer offset,Integer pageSize,String endTime) {
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		List<ReportDetailEntity> entityList = this.orderHistory(code, dateFormat, offset, pageSize,endTime);//获取数据
		
		if (entityList == null) {
			return result;
		}
		//赋值
		for (ReportDetailEntity detailEntity : entityList) {
			ReportDetailModel  model = new ReportDetailModel();
			if (detailEntity.distributeAVG == null || BigDecimal.ZERO == detailEntity.distributeAVG) {
				model.result = "0";
				model.yResult = "0";//Y轴显示的数据
			}else {
				//历史派单平均值
				model.result =RMBFormat.format(detailEntity.distributeAVG);
				model.result = this.removeZero(model.result);
				//Y轴显示的数据
				model.yResult = detailEntity.distributeAVG.toString();
				
			}
			model.historyDate = this.dateFormatChinese(dateFormat, detailEntity.historyDate);//历史时间
			
			model.xDate = detailEntity.historyDate;//x轴显示的时间
			if ("QUARTER".equals(dateFormat)) {//季度
				model.xDate  = detailEntity.historyDate+"季";
			}
			result.add(model);
		}
		return result;
	}
	
	/**
	 * 实物 接单平均值 历史记录
	 */
	public List<ReportDetailModel> orderAcceptAVGHistory(String code, String dateFormat, Integer offset,Integer pageSize,String endTime) {
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		List<ReportDetailEntity> entityList = this.orderHistory(code, dateFormat, offset, pageSize,endTime);//获取数据
		
		if (entityList == null) {
			return result;
		}
		//赋值
		for (ReportDetailEntity detailEntity : entityList) {
			ReportDetailModel  model = new ReportDetailModel();
			
			if(BigDecimal.ZERO == detailEntity.acceptAVG || detailEntity.acceptAVG == null) {
				model.result = "0";
				model.yResult = "0";//Y轴显示的数据
			}else {
				model.result = RMBFormat.format(detailEntity.acceptAVG);//历史接单平均值
				model.result = this.removeZero(model.result);
				model.yResult = detailEntity.acceptAVG.toString();//Y轴显示的数据
			}
			model.historyDate = this.dateFormatChinese(dateFormat, detailEntity.historyDate);//历史时间
			
			
			model.xDate = detailEntity.historyDate;//x轴显示的时间
			if ("QUARTER".equals(dateFormat)) {//季度
				model.xDate  = detailEntity.historyDate+"季";
			}
			result.add(model);
		}
		return result;
	}
	
	/**
	 * 实物 累计接单率 历史记录
	 */
	public List<ReportDetailModel> orderAcceptRateHistory(String code, String dateFormat, Integer offset,Integer pageSize,String endTime) {
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		List<ReportDetailEntity> entityList = this.orderHistory(code, dateFormat, offset, pageSize,endTime);//获取数据
		
		if (entityList == null) {
			return result;
		}
		//赋值
		for (ReportDetailEntity detailEntity : entityList) {
			ReportDetailModel  model = new ReportDetailModel();
			
			if (BigDecimal.ZERO == detailEntity.acceptRate || detailEntity.acceptRate == null) {
				model.result = "0";
				model.yResult = "0";//Y轴显示的数据
			}else {
				//历史累计接单率 
				model.result = detailEntity.acceptRate+"%";
				model.result = this.removeZeroOfPrecent(model.result);
				model.yResult = detailEntity.acceptRate.toString();//Y轴显示的数据

			}
			model.historyDate = this.dateFormatChinese(dateFormat, detailEntity.historyDate);//历史时间
			
			
			model.xDate = detailEntity.historyDate;//x轴显示的时间
			if ("QUARTER".equals(dateFormat)) {//季度
				model.xDate  = detailEntity.historyDate+"季";
			}
			result.add(model);
		}
		return result;
	}
	
	/**
	 * 实物 平均发货时长 历史记录
	 */
	public List<ReportDetailModel> orderDeliverAVGTimeHistory(String code, String dateFormat, Integer offset,Integer pageSize,String endTime) {
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		List<ReportDetailEntity> entityList = this.orderHistory(code, dateFormat, offset, pageSize,endTime);//获取数据
		
		if (entityList == null) {
			return result;
		}
		//赋值
		for (ReportDetailEntity detailEntity : entityList) {
			ReportDetailModel  model = new ReportDetailModel();
			if (BigDecimal.ZERO == detailEntity.deliverAVGTime || detailEntity.deliverAVGTime == null) {//平均发货时长
				model.result = "0";
				model.yResult = "0";//Y轴显示的数据
			}else {
				model.result = this.formatResultTime(detailEntity.deliverAVGTime);
				model.yResult = this.formatYTime(detailEntity.deliverAVGTime);//Y轴显示的数据
			}
			model.historyDate = this.dateFormatChinese(dateFormat, detailEntity.historyDate);//历史时间
			model.xDate = detailEntity.historyDate;//x轴显示的时间
			if ("QUARTER".equals(dateFormat)) {//季度
				model.xDate  = detailEntity.historyDate+"季";
			}
			result.add(model);
		
		}
		return result;
	}
	
	//---------------特权订金---------------------------------------
		/**
		 * 特权订金 历史记录 通用
		 */
		private List<ReportDetailEntity> prePayHistory (String code,String dateFormat,String storeId,String isStore,Integer offset,Integer pageSize,String endTime) {
					List<ReportDetailEntity> entityList = new ArrayList<ReportDetailEntity>();
			
			//日期类型
			String format = this.returnDateFormat(dateFormat);
			//调用接口
			if (StringUtils.isEmpty(code) || StringUtils.isEmpty(format) || StringUtils.isEmpty(storeId) ||StringUtils.isEmpty(isStore) ||offset !=null || pageSize !=null) {//按月度 查询，
				 entityList = reportDetailDao.prePayHistory(code, format, storeId, isStore, offset, pageSize,endTime);//获取数据
			}
			return entityList;
		}
	

		/**
		 * 特权订金 派单  历史趋势
		 */
		public List<ReportDetailModel>prepayDistributeHistory(String code,String dateFormat,String storeId,String isStore,Integer offset,Integer pageSize,String endTime) {
					List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
			List<ReportDetailEntity> entityList = this.prePayHistory(code, dateFormat, storeId, isStore, offset, pageSize,endTime);//获取数据
			if (entityList == null) {
				return result;
			}
			for (ReportDetailEntity detailEntity : entityList) {
				ReportDetailModel  model = new ReportDetailModel();
				if ( detailEntity.distributeCount == null) {
					model.result = "0";
					model.yResult = "0";
				}else {
					model.result = detailEntity.distributeCount.toString();//历史派单数
					model.yResult = detailEntity.distributeCount.toString();//Y轴显示的数据
				}
				model.historyDate = this.dateFormatChinese(dateFormat, detailEntity.historyDate);//历史时间
				model.xDate = detailEntity.historyDate;//x轴显示的时间
				if ("QUARTER".equals(dateFormat)) {//季度
					model.xDate  = detailEntity.historyDate+"季";
				}
				result.add(model);
			}
			return result;
		}
	
		

		/**
		 * 特权订金 核销数 历史记录
		 */
		public List<ReportDetailModel>prepayWriteOffCountHistory(String code,String dateFormat,String storeId,String isStore,Integer offset,Integer pageSize,String endTime) {
					List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
			List<ReportDetailEntity> entityList = this.prePayHistory(code, dateFormat, storeId, isStore, offset, pageSize,endTime);//获取数据
			if (entityList == null) {
				return result;
			}
			for (ReportDetailEntity detailEntity : entityList) {
				ReportDetailModel  model = new ReportDetailModel();
				if ( detailEntity.writeOffCount == null) {
					model.result = "0";
					model.yResult = "0";
				}else {
					model.result = detailEntity.writeOffCount.toString();//历史核销数
					model.yResult = detailEntity.writeOffCount.toString();//Y轴显示的数据
				}
				model.historyDate = this.dateFormatChinese(dateFormat, detailEntity.historyDate);//历史时间
				model.xDate = detailEntity.historyDate;//x轴显示的时间
				if ("QUARTER".equals(dateFormat)) {//季度
					model.xDate  = detailEntity.historyDate+"季";
				}
				result.add(model);
			}
			return result;
		}
		
		/**
		 * 特权订金 核销平均值  历史趋势
		 */
		public List<ReportDetailModel>prepayWriteOffAVGHistory(String code,String dateFormat,String storeId,String isStore,Integer offset,Integer pageSize,String endTime) {
					List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
			List<ReportDetailEntity> entityList = this.prePayHistory(code, dateFormat, storeId, isStore, offset, pageSize,endTime);//获取数据
			if (entityList == null) {
				return result;
			}
			for (ReportDetailEntity detailEntity : entityList) {
				ReportDetailModel  model = new ReportDetailModel();
				if ( detailEntity.writeOffCount == null) {
					model.result = "0";
					model.yResult = "0";
				}else {
					model.result = RMBFormat.format(detailEntity.writeOffAVG);//历史核销平均值
					model.result = this.removeZero(model.result);
					model.yResult = detailEntity.writeOffAVG.toString();//Y轴显示的数据
				}
				model.historyDate = this.dateFormatChinese(dateFormat, detailEntity.historyDate);//历史时间
				model.xDate = detailEntity.historyDate;//x轴显示的时间
				if ("QUARTER".equals(dateFormat)) {//季度
					model.xDate  = detailEntity.historyDate+"季";
				}
				result.add(model);
			}
			return result;
		}
		
		/**
		 * 特权订金 核销率  历史趋势
		 */
		public List<ReportDetailModel>prepayWriteOffRateHistory(String code,String dateFormat,String storeId,String isStore,Integer offset,Integer pageSize,String endTime) {
					List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
			List<ReportDetailEntity> entityList = this.prePayHistory(code, dateFormat, storeId, isStore, offset, pageSize,endTime);//获取数据
			if (entityList == null) {
				return result;
			}
			for (ReportDetailEntity detailEntity : entityList) {
				ReportDetailModel  model = new ReportDetailModel();
				if ( detailEntity.writeOffCount == null) {
					model.result = "0";
					model.yResult = "0";
				}else {
					model.result = detailEntity.writeOffRate + "%";//历史核销率
					model.result = this.removeZeroOfPrecent(model.result);
					model.yResult = detailEntity.writeOffRate.toString();//Y轴显示的数据
				}
				model.historyDate = this.dateFormatChinese(dateFormat, detailEntity.historyDate);//历史时间
				model.xDate = detailEntity.historyDate;//x轴显示的时间
				if ("QUARTER".equals(dateFormat)) {//季度
					model.xDate  = detailEntity.historyDate+"季";
				}
				result.add(model);
			}
			return result;
		}
	
		/**
		 * 特权订金  核销平均时长  历史趋势
		 */
		public List<ReportDetailModel>prepayWriteOffAVGTimeHistory(String code,String dateFormat,String storeId,String isStore,Integer offset,Integer pageSize,String endTime) {
					List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
			List<ReportDetailEntity> entityList = this.prePayHistory(code, dateFormat, storeId, isStore, offset, pageSize,endTime);//获取数据
			if (entityList == null) {
				return result;
			}
			for (ReportDetailEntity detailEntity : entityList) {
				ReportDetailModel  model = new ReportDetailModel();
				if ( detailEntity.writeOffTime == null) {
					model.result = "0";
					model.yResult = "0";
				}else {
					model.result = this.formatResultTime(detailEntity.writeOffTime); //历史核销率
					model.yResult = this.formatYTime(detailEntity.writeOffTime);//Y轴显示的数据
				}
				model.historyDate = this.dateFormatChinese(dateFormat, detailEntity.historyDate);//历史时间
				model.xDate = detailEntity.historyDate;//x轴显示的时间
				if ("QUARTER".equals(dateFormat)) {//季度
					model.xDate  = detailEntity.historyDate+"季";
				}
				result.add(model);
			}
			return result;
		}
		
		//------------留资----------------
	
		/**
		 * 留资 历史记录 通用
		 */
		private List<ReportDetailEntity> saleHistory (String code,String dateFormat,String storeId,String isStore,Integer offset,Integer pageSize,String endTime) {
					List<ReportDetailEntity> entityList = new ArrayList<ReportDetailEntity>();
			
			//日期类型
			String format = this.returnDateFormat(dateFormat);
			//调用接口
			if (StringUtils.isEmpty(code) || StringUtils.isEmpty(format) || StringUtils.isEmpty(storeId) ||StringUtils.isEmpty(isStore) ||offset !=null || pageSize !=null) {//按月度 查询，
				 entityList = reportDetailDao.saleHistory(code, format, storeId, isStore, offset, pageSize,endTime);//获取数据
			}
			return entityList;
		}
	

		/**
		 * 留资   转化笔数  历史趋势
		 */
		public List<ReportDetailModel>saleConversionCountHistory(String code,String dateFormat,String storeId,String isStore,Integer offset,Integer pageSize,String endTime) {
					List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
			List<ReportDetailEntity> entityList = this.saleHistory(code, dateFormat, storeId, isStore, offset, pageSize,endTime);//获取数据
			if (entityList == null) {
				return result;
			}
			for (ReportDetailEntity detailEntity : entityList) {
				ReportDetailModel  model = new ReportDetailModel();
				if ( detailEntity.conversionCount == null) {
					model.result = "0";
					model.yResult = "0";
				}else {
					model.result = detailEntity.conversionCount.toString();//历史 转化笔数
					model.yResult = detailEntity.conversionCount.toString();//Y轴显示的数据
				}
				model.historyDate = this.dateFormatChinese(dateFormat, detailEntity.historyDate);//历史时间
				model.xDate = detailEntity.historyDate;//x轴显示的时间
				if ("QUARTER".equals(dateFormat)) {//季度
					model.xDate  = detailEntity.historyDate+"季";
				}
				result.add(model);
			}
			return result;
		}
	

		/**
		 * 留资   转化笔数  历史趋势
		 */
		public List<ReportDetailModel>saleConversionAVGHistory(String code,String dateFormat,String storeId,String isStore,Integer offset,Integer pageSize,String endTime) {
					List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
			List<ReportDetailEntity> entityList = this.saleHistory(code, dateFormat, storeId, isStore, offset, pageSize,endTime);//获取数据
			if (entityList == null) {
				return result;
			}
			for (ReportDetailEntity detailEntity : entityList) {
				ReportDetailModel  model = new ReportDetailModel();
				if ( detailEntity.conversionAVG == null || BigDecimal.ZERO == detailEntity.conversionAVG) {
					model.result = "0";
					model.yResult = "0";
				}else {
					model.result = RMBFormat.format(detailEntity.conversionAVG);//历史 转化平均值
					model.yResult = detailEntity.conversionAVG.toString();//Y轴显示的数据
				}
				model.historyDate = this.dateFormatChinese(dateFormat, detailEntity.historyDate);//历史时间
				model.xDate = detailEntity.historyDate;//x轴显示的时间
				if ("QUARTER".equals(dateFormat)) {//季度
					model.xDate  = detailEntity.historyDate+"季";
				}
				result.add(model);
			}
			return result;
		}
	

		/**
		 * 留资   转化率 历史趋势
		 */
		public List<ReportDetailModel>saleConversionRateHistory(String code,String dateFormat,String storeId,String isStore,Integer offset,Integer pageSize,String endTime) {
					List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
			List<ReportDetailEntity> entityList = this.saleHistory(code, dateFormat, storeId, isStore, offset, pageSize,endTime);//获取数据
			if (entityList == null) {
				return result;
			}
			for (ReportDetailEntity detailEntity : entityList) {
				ReportDetailModel  model = new ReportDetailModel();
				if ( detailEntity.conversionRate == null || BigDecimal.ZERO == detailEntity.conversionRate) {
					model.result = "0";
					model.yResult = "0";
				}else {
					model.result = detailEntity.conversionRate+"%";//转化率
					model.result = this.removeZeroOfPrecent(model.result);
					model.yResult = detailEntity.conversionRate.toString();//Y轴显示的数据
				}
				model.historyDate = this.dateFormatChinese(dateFormat, detailEntity.historyDate);//历史时间
				model.xDate = detailEntity.historyDate;//x轴显示的时间
				if ("QUARTER".equals(dateFormat)) {//季度
					model.xDate  = detailEntity.historyDate+"季";
				}
				result.add(model);
			}
			return result;
		}
	

		/**
		 * 留资   平均转化时长  历史趋势
		 */
		public List<ReportDetailModel>saleConversionAVGTimeHistory(String code,String dateFormat,String storeId,String isStore,Integer offset,Integer pageSize,String endTime) {
					List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
			List<ReportDetailEntity> entityList = this.saleHistory(code, dateFormat, storeId, isStore, offset, pageSize,endTime);//获取数据
			if (entityList == null) {
				return result;
			}
			for (ReportDetailEntity detailEntity : entityList) {
				ReportDetailModel  model = new ReportDetailModel();
				if (  detailEntity.conversionAVGTime == null || BigDecimal.ZERO == detailEntity.conversionAVGTime) {
					model.result = "0";
					model.yResult = "0";
				}else {
					model.result = this.formatResultTime(detailEntity.conversionAVGTime);//平均转化时长
					model.yResult = this.formatYTime(detailEntity.conversionAVGTime);//Y轴显示的数据
				}
				model.historyDate = this.dateFormatChinese(dateFormat, detailEntity.historyDate);//历史时间
				model.xDate = detailEntity.historyDate;//x轴显示的时间
				if ("QUARTER".equals(dateFormat)) {//季度
					model.xDate  = detailEntity.historyDate+"季";
				}
				result.add(model);
			}
			return result;
		}
	
		
		
	
	//-----------------------------------------------排名---------------------------------------------------------------------------
		//----------------通用----------------
	
	
	/**
	 * 实物 排序 通用方法(与时间无关)
	 * @return
	 */
	private List<ReportDetailEntity> orderSortInfo(String code, Integer index, String roleCode, String startTime,String endTime, Integer offset, Integer pageSize,String sortType,String dateFormat,Integer pastRowNum,BigDecimal pastResult) {
		
		//1.获取需要排名的gropuPKs
		List<String> getGroupPKs = this.getGroupPKs(code, index, roleCode);
		if(getGroupPKs == null ||getGroupPKs.isEmpty() ) {
			return null;
		}
		//2.分割List
		List<List<String>> bigList = new ArrayList<List<String>>();
		if(getGroupPKs.size()>500) {
			bigList= listToBigList(getGroupPKs);
			getGroupPKs = bigList.get(0);//第一个集合
		}
		
		//3.根据roleCode判断是否门店
		String isStore = this.isStoreByRoleCode(roleCode);
		
		//4.调用接口，返回结果--------//
		List<ReportDetailEntity> entityList = reportDetailDao.sortInfo(getGroupPKs, bigList, startTime, endTime, offset, pageSize,sortType,dateFormat,isStore);
		if (entityList == null || entityList.isEmpty()) {
			return null;
		}
		//5.null 转化为0
		entityList = this.nullToZero(entityList);//将null 转换为 “0”
		
		
		//6.查询平均值并插入集合中
		//查询平均值
		ReportDetailEntity avgEntity = reportDetailDao.getSortAVG(getGroupPKs, bigList, startTime, endTime, sortType,dateFormat,isStore);
		BigDecimal avg = new BigDecimal(0);
		if (avgEntity != null) {//非空处理
			if (avgEntity.sortAVG != null) {
				avg = avgEntity.sortAVG;//平均值
			}
		}
		//插入平均值
		BigDecimal theLast = new BigDecimal(0);//该集合的最后一个值
		if ((entityList.get(entityList.size()-1).sortResult) != null) {
			theLast = (entityList.get(entityList.size()-1).sortResult);
		}
		if(offset == 0) {//第一页：如果平均值>该集合最后一个，则插入平均值 
			if ((avg.compareTo(theLast)>0)) {
				ReportDetailEntity entity = new ReportDetailEntity();
				entity.groupName  = "全体平均值";
				entity.sortResult = avg;
				entityList.add(entity);
			}
		}else {//非第一页：如果平均值>该集合最后一个，且平均值<=上一个集合最后一个 则插入平均值 
			if((avg.compareTo(theLast)>0) && ((avg.compareTo(pastResult)<0)||(avg.compareTo(pastResult)==0))) {//
				ReportDetailEntity entity = new ReportDetailEntity();
				entity.groupName  = "全体平均值";
				entity.rowNum  = 0;
				entity.sortResult = avg;
				entityList.add(entity);
			}
		}
		
		//排序结果
		if (entityList.size()<2) {//1个的时候不要排序
			return entityList;
		}
		entityList = this.sortAVG(entityList);//排序结果
		

		if(offset == 0) {
			for (int i =0;i<entityList.size();i++) {//第一页：前3有“平均值”，删除“平均值”
				ReportDetailEntity isAvg = entityList.get(i);
				if ("全体平均值".equals(isAvg.groupName)) {
					entityList.remove(i);
				}
				if (i==3) {
					break;
				}
			}
		}
		
		//排序rowNum
		entityList = this.rowNumSort(entityList, pastResult, pastRowNum, offset);
		
		//每个值与平均值比较，用于显示比较后颜色
		for (ReportDetailEntity entity :entityList) {//与时间无关排序
			if(entity.sortResult.compareTo(avg)<0) {
				entity.sortResultCon = "DOWN";
			}else {
				entity.sortResultCon = "UP";
			}
		}

					
		return entityList;
	}

	
	/**
	 * 实物 排序 通用方法(与时间相关)
	 * @return
	 */
	private List<ReportDetailEntity> orderSortOfTimeInfo(String code, Integer index, String roleCode, String startTime,String endTime, Integer offset, Integer pageSize,String sortType,String dateFormat,Integer pastRowNum,BigDecimal pastResult) {
		
		//1.获取需要排名的gropuPKs
		List<String> getGroupPKs = this.getGroupPKs(code, index, roleCode);
		if(getGroupPKs == null ||getGroupPKs.isEmpty() ) {
			return null;
		}
		//2.分割List
		List<List<String>> bigList = new ArrayList<List<String>>();
		if(getGroupPKs.size()>500) {
			bigList= listToBigList(getGroupPKs);
			getGroupPKs = bigList.get(0);//第一个集合
		}
		
		//3.根据roleCode判断是否门店
		String isStore = this.isStoreByRoleCode(roleCode);
		
		//4.调用接口，返回结果--------//
		List<ReportDetailEntity> entityList = reportDetailDao.sortInfo(getGroupPKs, bigList, startTime, endTime, offset, pageSize,sortType,dateFormat,isStore);
		if (entityList == null || entityList.isEmpty()) {
			return null;
		}
		
		System.out.println(entityList.toString());
		//5.时间数据为“0”或者为null 从集合去除
		entityList = this.removeNullOrZero(entityList);
		if (entityList == null || entityList.isEmpty()) {
			return null;
		}
	
		//6.查询平均值并插入集合中
		//查询平均值
		ReportDetailEntity avgEntity = reportDetailDao.getSortAVG(getGroupPKs, bigList, startTime, endTime, sortType,dateFormat,isStore);
		BigDecimal avg = new BigDecimal(0);
		if (avgEntity != null) {//非空处理
			if (avgEntity.sortAVG != null) {
				avg = avgEntity.sortAVG;//平均值
			}
		}
		//插入平均值
		BigDecimal theLast = new BigDecimal(0);//该集合的最后一个值
		if ((entityList.get(entityList.size()-1).sortResult) != null) {
			theLast = (entityList.get(entityList.size()-1).sortResult);
		}
		if(offset == 0) {//第一页：如果平均值<该集合最后一个，则插入平均值 
			if ((avg.compareTo(theLast)<0)) {
				ReportDetailEntity entity = new ReportDetailEntity();
				entity.groupName  = "全体平均值";
				entity.sortResult = avg;
				entityList.add(entity);
			}
		}else {//非第一页：如果平均值<该集合最后一个，且平均值>=上一个集合最后一个 则插入平均值 
			if((avg.compareTo(theLast)<0) && ((avg.compareTo(pastResult)>0)||(avg.compareTo(pastResult)==0))) {//
				ReportDetailEntity entity = new ReportDetailEntity();
				entity.groupName  = "全体平均值";
				entity.rowNum  = 0;
				entity.sortResult = avg;
				entityList.add(entity);
			}
		}
		
		//排序结果
		if (entityList.size()<2) {//1个的时候不要排序
			return entityList;
		}
		//排序结果
		entityList = this.sortAVGOfTime(entityList);
		

		if(offset == 0) {
			for (int i =0;i<entityList.size();i++) {//第一页：前3有“平均值”，删除“平均值”
				ReportDetailEntity isAvg = entityList.get(i);
				if ("全体平均值".equals(isAvg.groupName)) {
					entityList.remove(i);
				}
				if (i==3) {
					break;
				}
			}
		}
		
		//排序rowNum
		entityList = this.rowNumSortOfTime(entityList, pastResult, pastRowNum, offset);
		
		//每个值与平均值比较，用于显示比较后颜色
		
			for (ReportDetailEntity entity :entityList) {
				if(entity.sortResult.compareTo(avg)>0) {
					entity.sortResultCon = "DOWN";
				}else  {
					entity.sortResultCon = "UP";
				}
			}
	
		
		return entityList;
	}
	//---------------------------实物-----------------------------
	
	 

	/**实物 ： 派单数排名**/
	public List<ReportDetailModel> orderDistributedSort(String code, Integer index, String roleCode, String startTime,
			String endTime, Integer offset, Integer pageSize , String dateFormat,Integer pastRowNum,BigDecimal pastResult){
		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
		String sortType = "PD";//排序类型
		List<ReportDetailEntity> entityList = this.orderSortInfo(code, index, roleCode, startTime, endTime, offset, pageSize, sortType, dateFormat, pastRowNum, pastResult);
		
		if (entityList == null) {
			return null;
		}
		//7、赋值输出
		for (ReportDetailEntity detailEntity : entityList) {//赋值输出
			ReportDetailModel  model = new ReportDetailModel();
			
			model.groupName = detailEntity.groupName;//分组名称
			model.groupPk = detailEntity.groupPk;//分组pk
			model.resultCon = detailEntity.sortResultCon;//与平均值比较的结果
			model.result = (detailEntity.sortResult == null? "0":detailEntity.sortResult+"");//派单数
			model.yResult = (detailEntity.sortResult == null? "0":detailEntity.sortResult+"");//派单数
			if (detailEntity.rowNum == null || detailEntity.rowNum == 0 ) {//序号
				model.rowNum ="";
			}else {
				model.rowNum = detailEntity.rowNum+"";
			}
			
			result.add(model);
			
		}
		return result;
	}
	
	
	/**
	 * 实物  派单平均单值  排名
	 */
	public List<ReportDetailModel> orderDistributeAVGSort ( String code,Integer index,String roleCode,String startTime, String endTime,Integer startNum, Integer pageSize,String dateFormat,Integer pastRowNum,BigDecimal pastResult){

		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
		String sortType = "PDAVG";//排序类型
		List<ReportDetailEntity> entityList = this.orderSortInfo(code, index, roleCode, startTime, endTime, startNum, pageSize, sortType, dateFormat, pastRowNum, pastResult);
		
		if (entityList == null) {
			return null;
		}
		
		//7、赋值输出
		for (ReportDetailEntity detailEntity : entityList) {
			ReportDetailModel  model = new ReportDetailModel();
			model.groupName = detailEntity.groupName;//分组名称
			model.groupPk = detailEntity.groupPk;//分组pk
			model.resultCon = detailEntity.sortResultCon;//与平均值比较的结果
			if(BigDecimal.ZERO == detailEntity.sortResult || detailEntity.sortResult == null) {
				model.result = "0";
				model.yResult = "0";
			}else {
				model.result = RMBFormat.format(detailEntity.sortResult);//派单平均单值
				model.result = this.removeZero(model.result);
				model.yResult = detailEntity.sortResult+"";
			}
			if (detailEntity.rowNum == null || detailEntity.rowNum == 0 ) {//序号
				model.rowNum ="";
			}else {
				model.rowNum = detailEntity.rowNum+"";
			}
			result.add(model);
		}
		
		return result;
	}
	
	
	/**
	 * 实物  接单平均单值  排名
	 */
	public List<ReportDetailModel> orderAcceptAVGSort ( String code,Integer index,String roleCode,String startTime, String endTime,Integer startNum, Integer pageSize,String dateFormat,Integer pastRowNum,BigDecimal pastResult){

		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
		String sortType = "JDAVG";//排序类型
		List<ReportDetailEntity> entityList = this.orderSortInfo(code, index, roleCode, startTime, endTime, startNum, pageSize, sortType, dateFormat, pastRowNum, pastResult);
		
		if (entityList == null) {
			return null;
		}
		
		//7、赋值输出
		for (ReportDetailEntity detailEntity : entityList) {
			ReportDetailModel  model = new ReportDetailModel();
			model.groupName = detailEntity.groupName;//分组名称
			model.groupPk = detailEntity.groupPk;//分组pk
			model.resultCon = detailEntity.sortResultCon;//与平均值比较的结果
			if(BigDecimal.ZERO == detailEntity.sortResult || detailEntity.sortResult == null) {
				model.result = "0";
				model.yResult = "0";
			}else {
				 model.result = RMBFormat.format(detailEntity.sortResult);// 接单平均单值
				 model.result = this.removeZero(model.result);
				model.yResult = detailEntity.sortResult+"";
			}
			if (detailEntity.rowNum == null || detailEntity.rowNum == 0 ) {//序号
				model.rowNum ="";
			}else {
				model.rowNum = detailEntity.rowNum+"";
			}
			result.add(model);
		}
		
		return result;
	}
	
	
	
	/**
	 * 实物  累计接单率  排名
	 */
	public List<ReportDetailModel> orderAcceptRateSort ( String code,Integer index,String roleCode,String startTime, String endTime,Integer startNum, Integer pageSize,String dateFormat,Integer pastRowNum,BigDecimal pastResult){

		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
		String sortType = "JDRATE";//排序类型
		List<ReportDetailEntity> entityList = this.orderSortInfo(code, index, roleCode, startTime, endTime, startNum, pageSize, sortType, dateFormat, pastRowNum, pastResult);
		
		if (entityList == null) {
			return null;
		}
		
		//7、赋值输出
		for (ReportDetailEntity detailEntity : entityList) {
			ReportDetailModel  model = new ReportDetailModel();
			model.groupName = detailEntity.groupName;//分组名称
			model.groupPk = detailEntity.groupPk;//分组pk
			model.resultCon = detailEntity.sortResultCon;//与平均值比较的结果
			if(BigDecimal.ZERO == detailEntity.sortResult || detailEntity.sortResult == null) {
				model.result = "0";
				model.yResult = "0";
			}else {
				model.result =detailEntity.sortResult+"%";//累计接单率
				model.result = this.removeZeroOfPrecent(model.result);
				model.yResult = detailEntity.sortResult+"";
			}
			if (detailEntity.rowNum == null || detailEntity.rowNum == 0 ) {//序号
				model.rowNum ="";
			}else {
				model.rowNum = detailEntity.rowNum+"";
			}
			result.add(model);
		}
		
		return result;
	}
	
	/**
	 * 实物  平均发货时长  排名
	 */
	public List<ReportDetailModel> orderDeliverAVGTimeSort ( String code,Integer index,String roleCode,String startTime, String endTime,Integer startNum, Integer pageSize,String dateFormat,Integer pastRowNum,BigDecimal pastResult){

		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
		String sortType = "FHAVGTIME";//排序类型
		List<ReportDetailEntity> entityList = this.orderSortOfTimeInfo(code, index, roleCode, startTime, endTime, startNum, pageSize, sortType, dateFormat, pastRowNum, pastResult);
		
		if (entityList == null) {
			return null;
		}
		
		//7、赋值输出
		for (ReportDetailEntity detailEntity : entityList) {
			ReportDetailModel  model = new ReportDetailModel();
			model.groupName = detailEntity.groupName;//分组名称
			model.groupPk = detailEntity.groupPk;//分组pk
			model.resultCon = detailEntity.sortResultCon;//与平均值比较的结果
			if (detailEntity.rowNum == null || detailEntity.rowNum == 0 ) {//序号
				model.rowNum ="";
			}else {
				model.rowNum = detailEntity.rowNum+"";
			}
			 //平均发货时长
			if(BigDecimal.ZERO == detailEntity.sortResult || detailEntity.sortResult == null) {
				model.result = "0";
				model.yResult = "0";
			}else {
				model.result = this.formatResultTime(detailEntity.sortResult);
				model.yResult = detailEntity.sortResult+"";
			}
			
			result.add(model);
		}
		
		return result;
	}
	
	//---------------------------特权订金----------------------------
	
	/**
	 * 特权订金   核销平均值   排名  
	 */
	public List<ReportDetailModel> prepayWriteOffAVGSort ( String code,Integer index,String roleCode,String startTime, String endTime,Integer startNum, Integer pageSize,String dateFormat,Integer pastRowNum,BigDecimal pastResult){

		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
		String sortType = "TQHXAVG";//排序类型
		List<ReportDetailEntity> entityList = this.orderSortInfo(code, index, roleCode, startTime, endTime, startNum, pageSize, sortType, dateFormat, pastRowNum, pastResult);
		
		if (entityList == null) {
			return null;
		}
		
		//7、赋值输出
		for (ReportDetailEntity detailEntity : entityList) {
			ReportDetailModel  model = new ReportDetailModel();
			model.groupName = detailEntity.groupName;//分组名称
			model.groupPk = detailEntity.groupPk;//分组pk
			model.resultCon = detailEntity.sortResultCon;//与平均值比较的结果
			if(BigDecimal.ZERO == detailEntity.sortResult || detailEntity.sortResult == null) {
				model.result = "0";
				model.yResult = "0";
			}else {
				model.result = RMBFormat.format(detailEntity.sortResult);// 核销平均值
				model.result = this.removeZero(model.result);
				model.yResult = detailEntity.sortResult+"";
			}
			if (detailEntity.rowNum == null || detailEntity.rowNum == 0 ) {//序号
				model.rowNum ="";
			}else {
				model.rowNum = detailEntity.rowNum+"";
			}
			result.add(model);
		}
		
		return result;
	}
	
	/**
	 * 特权订金   核销率   排名  
	 */
	public List<ReportDetailModel> prepayWriteOffRateSort ( String code,Integer index,String roleCode,String startTime, String endTime,Integer startNum, Integer pageSize,String dateFormat,Integer pastRowNum,BigDecimal pastResult){

		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
		String sortType = "TQHXRATE";//排序类型
		List<ReportDetailEntity> entityList = this.orderSortInfo(code, index, roleCode, startTime, endTime, startNum, pageSize, sortType, dateFormat, pastRowNum, pastResult);
		
		if (entityList == null) {
			return null;
		}
		
		//7、赋值输出
		for (ReportDetailEntity detailEntity : entityList) {
			ReportDetailModel  model = new ReportDetailModel();
			model.groupName = detailEntity.groupName;//分组名称
			model.groupPk = detailEntity.groupPk;//分组pk
			model.resultCon = detailEntity.sortResultCon;//与平均值比较的结果
			if(BigDecimal.ZERO == detailEntity.sortResult || detailEntity.sortResult == null) {
				model.result = "0";
				model.yResult =  "0";
			}else {
				model.result = detailEntity.sortResult+"%";// 核销平均值
				model.result = this.removeZeroOfPrecent(model.result);
				model.yResult = detailEntity.sortResult+"";
			}
			if (detailEntity.rowNum == null || detailEntity.rowNum == 0 ) {//序号
				model.rowNum ="";
			}else {
				model.rowNum = detailEntity.rowNum+"";
			}
			result.add(model);
		}
		
		return result;
	}
	
	/**
	 * 特权订金   核销时长   排名  
	 */
	public List<ReportDetailModel> prepayWriteOffAVGTimeSort ( String code,Integer index,String roleCode,String startTime, String endTime,Integer startNum, Integer pageSize,String dateFormat,Integer pastRowNum,BigDecimal pastResult){

		List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
		
		String sortType = "TQHXTIME";//排序类型
		List<ReportDetailEntity> entityList = this.orderSortOfTimeInfo(code, index, roleCode, startTime, endTime, startNum, pageSize, sortType, dateFormat, pastRowNum, pastResult);
		
		if (entityList == null) {
			return null;
		}
		
		//7、赋值输出
		for (ReportDetailEntity detailEntity : entityList) {
			ReportDetailModel  model = new ReportDetailModel();
			model.groupName = detailEntity.groupName;//分组名称
			model.groupPk = detailEntity.groupPk;//分组pk
			model.resultCon = detailEntity.sortResultCon;//与平均值比较的结果
			if (detailEntity.rowNum == null || detailEntity.rowNum == 0 ) {//序号
				model.rowNum ="";
			}else {
				model.rowNum = detailEntity.rowNum+"";
			}
			 //平均发货时长
			if(BigDecimal.ZERO == detailEntity.sortResult || detailEntity.sortResult == null) {
				model.result = "0";
				model.yResult = "0";
			}else {
				model.result = this.formatResultTime(detailEntity.sortResult);
				model.yResult = detailEntity.sortResult+"";
			}
			
			result.add(model);
		}
		
		return result;
	}
	
	//---------------------------特权订金----------------------------
	
		/**
		 * 特权订金   核销平均值   排名  
		 */
		public List<ReportDetailModel> saleConversionAVGSort ( String code,Integer index,String roleCode,String startTime, String endTime,Integer startNum, Integer pageSize,String dateFormat,Integer pastRowNum,BigDecimal pastResult){

			List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
			
			String sortType = "LZZHAVG";//排序类型
			List<ReportDetailEntity> entityList = this.orderSortInfo(code, index, roleCode, startTime, endTime, startNum, pageSize, sortType, dateFormat, pastRowNum, pastResult);
			
			if (entityList == null) {
				return null;
			}
			
			//7、赋值输出
			for (ReportDetailEntity detailEntity : entityList) {
				ReportDetailModel  model = new ReportDetailModel();
				model.groupName = detailEntity.groupName;//分组名称
				model.groupPk = detailEntity.groupPk;//分组pk
				model.resultCon = detailEntity.sortResultCon;//与平均值比较的结果
				if(BigDecimal.ZERO == detailEntity.sortResult || detailEntity.sortResult == null) {
					model.result = "0";
					model.yResult = "0";
				}else {
					model.result = RMBFormat.format(detailEntity.sortResult);// 转化平均值
					model.result = this.removeZero(model.result);
					model.yResult = detailEntity.sortResult+"";
				}
				if (detailEntity.rowNum == null || detailEntity.rowNum == 0 ) {//序号
					model.rowNum ="";
				}else {
					model.rowNum = detailEntity.rowNum+"";
				}
				result.add(model);
			}
			
			return result;
		}
		
		/**
		 * 留资   转化率  排名
		 */
		public List<ReportDetailModel> saleConversionRateSort ( String code,Integer index,String roleCode,String startTime, String endTime,Integer startNum, Integer pageSize,String dateFormat,Integer pastRowNum,BigDecimal pastResult){

			List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
			
			String sortType = "LZZHRATE";//排序类型
			List<ReportDetailEntity> entityList = this.orderSortInfo(code, index, roleCode, startTime, endTime, startNum, pageSize, sortType, dateFormat, pastRowNum, pastResult);
			
			if (entityList == null) {
				return null;
			}
			
			//7、赋值输出
			for (ReportDetailEntity detailEntity : entityList) {
				ReportDetailModel  model = new ReportDetailModel();
				model.groupName = detailEntity.groupName;//分组名称
				model.groupPk = detailEntity.groupPk;//分组pk
				model.resultCon = detailEntity.sortResultCon;//与平均值比较的结果
				if(BigDecimal.ZERO == detailEntity.sortResult || detailEntity.sortResult == null) {
					model.result = "0";
					model.yResult = "0";
				}else {
					model.result = detailEntity.sortResult+"%";// 核销平均值
					model.result = this.removeZeroOfPrecent(model.result);
					model.yResult = detailEntity.sortResult+"";
				}
				if (detailEntity.rowNum == null || detailEntity.rowNum == 0 ) {//序号
					model.rowNum ="";
				}else {
					model.rowNum = detailEntity.rowNum+"";
				}
				result.add(model);
			}
			
			return result;
		}
	
		/**
		 * 留资 转化时长   排名  
		 */
		public List<ReportDetailModel> saleConversionAVGTimeSort ( String code,Integer index,String roleCode,String startTime, String endTime,Integer startNum, Integer pageSize,String dateFormat,Integer pastRowNum,BigDecimal pastResult){

			List<ReportDetailModel> result = new ArrayList<ReportDetailModel>();
			
			String sortType = "LZZHTIME";//排序类型
			List<ReportDetailEntity> entityList = this.orderSortOfTimeInfo(code, index, roleCode, startTime, endTime, startNum, pageSize, sortType, dateFormat, pastRowNum, pastResult);
			
			if (entityList == null) {
				return null;
			}
			
			//7、赋值输出
			for (ReportDetailEntity detailEntity : entityList) {
				ReportDetailModel  model = new ReportDetailModel();
				model.groupName = detailEntity.groupName;//分组名称
				model.groupPk = detailEntity.groupPk;//分组pk
				model.resultCon = detailEntity.sortResultCon;//与平均值比较的结果
				if (detailEntity.rowNum == null || detailEntity.rowNum == 0 ) {//序号
					model.rowNum ="";
				}else {
					model.rowNum = detailEntity.rowNum+"";
				}
				 //平均发货时长
				if(BigDecimal.ZERO == detailEntity.sortResult || detailEntity.sortResult == null) {
					model.result = "0";
					model.yResult = "0";
				}else {
					model.result = this.formatResultTime(detailEntity.sortResult);
					model.yResult = detailEntity.sortResult+"";
				}
				
				result.add(model);
			}
			
			return result;
		}
	
	
	
	
	//------------------------------------------------工具方法--------------------------------------------------------------------------------------	


	/**
	 * 获取需要排名的gropuPKs
	 * @param code : 分组编码 	如：100001
	 * @param index : 需要查询的以下几级
	 * @param roleCode ：角色编码  	如：“zongbu”
	 * @return
	 */
	public  List<String> getGroupPKs(String code, Integer index, String roleCode) {
		List<String> getGroupPKs = new ArrayList<String>();
		List<String> getGroupCodes = new ArrayList<String>();
		
		//经销商以上级别
		if("zongbu".equals(roleCode) || "daqu".equals(roleCode) || "zigongsi".equals(roleCode) || "xianzu".equals(roleCode)) {
			List<String> singleCode = new ArrayList<String>();
			singleCode.add(code);
			//第一次获取groupPks
			getGroupPKs = reportDetailDao.getGroupPKs(singleCode);
			getGroupCodes = reportDetailDao.getGroupCodes(singleCode);
			
			for(int i =1 ;i<index;i++) {//根据等级判断需要循环多次
				getGroupPKs = reportDetailDao.getGroupPKs(getGroupCodes);
				getGroupCodes = reportDetailDao.getGroupCodes(getGroupCodes);
			}
		}
		
		//经销商
		if("agency".equals(roleCode) || "dailishang".equals(roleCode) || "fenxiaoshang".equals(roleCode) ) {
			//调用Hybris接口获取agencyPk
			getGroupCodes = getAgencysByHybirs(code);
			if (getGroupCodes==null || getGroupCodes.isEmpty()) {
				return null;
			}
			//分割List
			List<List<String>> bigList = new ArrayList<List<String>>();
			if(getGroupCodes.size()>500) {
				bigList= listToBigList(getGroupCodes);
				getGroupCodes = bigList.get(0);//第一个集合
			}
			
			getGroupPKs = reportDetailDao.getGroupPKsOfAgency(getGroupCodes, bigList);
		}
		
		//门店
		if("clerk".equals(roleCode) || "manager".equals(roleCode) || "mendian".equals(roleCode)) {
			//调用Hybris接口获取agencyPk
			getGroupCodes = getAgencysByHybirs(code);
			if (getGroupCodes==null || getGroupCodes.isEmpty()) {
				return null;
			}
			//分割List
			List<List<String>> bigList = new ArrayList<List<String>>();
			if(getGroupCodes.size()>500) {
				bigList= listToBigList(getGroupCodes);
				getGroupCodes = bigList.get(0);//第一个集合
			}
			
			getGroupPKs = reportDetailDao.getStorePKsOfStore(getGroupCodes, bigList);
		}
		
		return getGroupPKs;
	}


	
	/***
	 * 转换时间格式
	 */
	private String dateFormatChinese (String dateFormat,String date) {
		//日期类型
		String  dateChinese = null ;
		if (StringUtils.isEmpty(date)) {//判断非空
			return dateChinese;
		}
		if ("YEAR".equals(dateFormat)) {//年度
			dateChinese = date+"年";
		}
		if ("MONTH".equals(dateFormat)) {//月度
			String [] dateArr = date.split("-");
			dateChinese = dateArr[0]+"年"+dateArr[1]+"月";
		}
		if ("QUARTER".equals(dateFormat)) {//季度
			String [] dateArr = date.split("-");
			dateChinese = dateArr[0]+"年 第"+dateArr[1]+"季度";
		}
		return dateChinese;
	}
	
	
	/***
	 * 日期格式，年度，季度，月度
	 * @param dateFormat
	 * @return
	 */
	private String returnDateFormat (String dateFormat) {
		//日期类型
		String format = null ;
		if ("YEAR".equals(dateFormat)) {//年度
			format = "YYYY";
		}
		if ("MONTH".equals(dateFormat)) {//月度
			format = "YYYY-MM";
		}
		if ("QUARTER".equals(dateFormat)) {//季度
			format = "YYYY-q";
		}
		return format;
	}
	
	/**
	 * 调用hybirs接口
	 */
	private List<String> getAgencysByHybirs(String code) {
//		String token = HybrisUtils.getHybrisToken();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("code", code);
		//调用hybris接口
		String resultStr = hybrisUtils.getAppGroupInfo(param);
		JSONObject jsonObject = JSON.parseObject(resultStr);
        Map<Object, Object> map = (Map)jsonObject;//转成map
		//转List
        String dataStr = map.get("data").toString();
        String [] dataByte = dataStr.split(" ");
        List<String> dataList = Arrays.asList(dataByte);
		return dataList;
	}
	
	/**
	 * 分割大集合
	 * List 转为List<List<String>>
	 */
	private List<List<String>> listToBigList (List<String> list){
		List<List<String>>  bigList = new ArrayList<List<String>>();
		int size = list.size();
		if (size>500) {
			int index = (size/500)+1;//循环的次数
			for(int i = 0;i<index;i++) {
				List<String> newList = new ArrayList<String>(); //复制集合，以500为单位
				if (i == (index-1)) {
					newList.addAll(list.subList((500*i), list.size()));
				}else {
					newList.addAll(list.subList((500*i), (500*(i+1))));
				}
				bigList.add(newList);//添加至大集合
			}
		}
		return bigList;
	}
	
	/**
	 * "插入平均值"排序（与时间无关）
	 */
	private List<ReportDetailEntity> sortAVG (List<ReportDetailEntity> entityList ){
		Collections.sort(entityList, new Comparator<ReportDetailEntity>(){//
            public int compare(ReportDetailEntity o1, ReportDetailEntity o2) {
            	if (o1.sortResult == null) {
            		o1.sortResult = new BigDecimal(0);
            	}if (o2.sortResult == null) {
            		o2.sortResult = new BigDecimal(0);
            	}
            	if((o1.sortResult.compareTo(o2.sortResult)) > 0){//o1>o2
                    return 1;
                }
            	if((o1.sortResult.compareTo(o2.sortResult)) == 0){//o1 = o2
                    return 0;
                }
                return -1;
            }  
        });
		 Collections.reverse(entityList);//倒序
		 return entityList;
	}
	
	/**
	 * "插入平均值"排序（与时间相关）
	 */
	private List<ReportDetailEntity> sortAVGOfTime (List<ReportDetailEntity> entityList ){
		Collections.sort(entityList, new Comparator<ReportDetailEntity>(){//
            public int compare(ReportDetailEntity o1, ReportDetailEntity o2) {
            	if (o1.sortResult == null) {
            		o1.sortResult = new BigDecimal(0);
            	}if (o2.sortResult == null) {
            		o2.sortResult = new BigDecimal(0);
            	}
            	if((o1.sortResult.compareTo(o2.sortResult)) < 0){//o1<o2
                    return 1;
                }
            	if((o1.sortResult.compareTo(o2.sortResult)) == 0){//o1 = o2
                    return 0;
                }
                return -1;
            }  
        });
		 Collections.reverse(entityList);//倒序
		 return entityList;
	}
	
	
	/**
	 *  需要时间格式处理的 y轴数据
	 */
	private String formatYTime (BigDecimal yTimeSeconds ){
	
		if (yTimeSeconds == BigDecimal.ZERO ) {
			return "0";
		}
		Long mss = yTimeSeconds.longValue();//转为long类型
		 String dateTimes = null;
		 long minutes = mss /  60;
		  if(minutes>0){
//			  dateTimes=minutes + "分钟" ; 
			  dateTimes=minutes + "" ; 
		  }
		 return dateTimes;
	}
	
	/**
	 * 需要时间格式处理的 结果
	 */
	private String formatResultTime (BigDecimal resultSeconds ){
		
		if (resultSeconds == BigDecimal.ZERO ) {
			return "0";
		}
		Long mss = (resultSeconds).longValue();//转为long类型
		 String dateTimes = "0";
//		 if (mss<60) {
//			 dateTimes = "1分钟";
//			 return dateTimes;
//		 }
		 long days = mss / ( 60 * 60 * 24);
		 long hours = (mss % ( 60 * 60 * 24)) / (60 * 60);
		 long minutes = (mss % ( 60 * 60)) /60;
		 long seconds = mss % 60;
		 
		 if(days>0){
			 dateTimes= days + "天" + hours + "小时" + minutes + "分钟" ; 
		  }else if(hours>0){
			  dateTimes=hours + "小时" + minutes + "分钟" ; 
		  }else if(minutes>0){
			  dateTimes=minutes + "分钟" + seconds+"秒";
		  } else if (seconds>0) {
				  dateTimes =  seconds+"秒";
		  }
		 return dateTimes;
	}

	/**
	 *  判断是否门店
	 */
	private String  isStoreByRoleCode(String roleCode) {
		String isStore = "N";
		if("clerk".equals(roleCode) || "manager".equals(roleCode) || "mendian".equals(roleCode)) {
			isStore = "Y";	
		}
		return isStore;
	}
	
	
	public static void main(String[] args) {
		ReportDetailServiceImpl detailServiceImpl = new ReportDetailServiceImpl();
		
//		List<ReportDetailEntity> entityList = new ArrayList<ReportDetailEntity>();
//		for(int i=1;i<13;i++) {
//			ReportDetailEntity entity = new ReportDetailEntity();
//			entity.rowNum = i;
//			if (i <= 2) {
//				entity.sortResult = new BigDecimal(10);
//				entityList.add(entity);
//				continue;
//			}
//			entity.sortResult = new BigDecimal(10+i);
//			entityList.add(entity);
//		}
//		
//		BigDecimal pastResult = new BigDecimal(10);
//		entityList = detailServiceImpl.rowNumSort(entityList, pastResult, 13, 1);
//		for (ReportDetailEntity entity :entityList) {
//			System.out.println("rowNum:"+entity.rowNum + "\n---sortResult:"+entity.sortResult);
//		}
		String out = detailServiceImpl.removeZero("d");
		System.out.println(out);
	}
	/**
	 * 排序rowNum（与时间无关）
	 * @param entityList :当前集合
	 * @param pastResult ：上一页最后一个结果
	 * @param pastRowNum：上一页最后一个序号
	 * @param startNum ：开始的页码
	 * @return
	 */
	private List<ReportDetailEntity>  rowNumSort (List<ReportDetailEntity> entityList,BigDecimal pastResult,Integer pastRowNum,Integer startNum) {
		int index ;//为rowNum赋值，除第一个rowNum外
		
		if (startNum == 0) {//第一页
			//若第一个rowNum不为1，且数据为“0”,则赋值所有rowNum为1
			if ((entityList.get(0).rowNum != 1) && (entityList.get(0).sortResult == BigDecimal.ZERO) ) {
				for(int i =0; i< entityList.size() ;i++) {
					entityList.get(i).rowNum = 1;
				}
			}else {
				entityList.get(0).rowNum  = 1;//先赋值第一个的rowNum
			}
			
			index = 2;
		}else {//非第一页
			ReportDetailEntity first = entityList.get(0);//当前集合的第一个结果
			//对比两数据
			if((first.sortResult).compareTo(pastResult)<0) {//小于
				entityList.get(0).rowNum  = pastRowNum+1;//先赋值第一个的rowNum
				index = pastRowNum+2;
			}else {//等于
				entityList.get(0).rowNum  = pastRowNum;//先赋值第一个的rowNum
				index = pastRowNum +1;
			}
		}
		//从第二个参数开始赋值
		for (int i = 1; i<entityList.size();i++) {
			if("全体平均值".equals((entityList.get(i).groupName))) {//是平均值就跳过
				continue;
			}
			if ((entityList.get(i).sortResult).compareTo(entityList.get(i-1).sortResult) == 0) {//与前一个的值相等，序号则相同
				entityList.get(i).rowNum = entityList.get(i-1).rowNum;
			}else {
				entityList.get(i).rowNum = index;
			}
			index++;
		}
		
		return entityList;
	}
	
	/**
	 * 排序rowNum（与时间相关）
	 * @param entityList :当前集合
	 * @param pastResult ：上一页最后一个结果
	 * @param pastRowNum：上一页最后一个序号
	 * @param startNum ：开始的页码
	 * @return
	 */
	private List<ReportDetailEntity>  rowNumSortOfTime (List<ReportDetailEntity> entityList,BigDecimal pastResult,Integer pastRowNum,Integer startNum) {
		int index ;//为rowNum赋值，除第一个rowNum外
		
		if (startNum == 0) {//第一页
			//若第一个rowNum不为1，且数据为“0”,则赋值所有rowNum为1
			if ((entityList.get(0).rowNum != 1) && (entityList.get(0).sortResult == BigDecimal.ZERO) ) {
				for(int i =0; i< entityList.size() ;i++) {
					entityList.get(i).rowNum = 1;
				}
			}else {
				entityList.get(0).rowNum  = 1;//先赋值第一个的rowNum
			}
			
			index = 2;
		}else {//非第一页
			ReportDetailEntity first = entityList.get(0);//当前集合的第一个结果
			if("全体平均值".equals((first.groupName))) {//是平均值就跳过
				first = entityList.get(1);
			}
			//对比两数据
			if((first.sortResult).compareTo(pastResult)>0) {//大于
				first.rowNum  = pastRowNum+1;//先赋值第一个的rowNum
				index = pastRowNum+2;
			}else {//等于
				first.rowNum  = pastRowNum;//先赋值第一个的rowNum
				index = pastRowNum +1;
			}
		}
		//从第二个参数开始赋值
		int j =1;
		if("全体平均值".equals((entityList.get(0).groupName))) {//是平均值就跳过
			j=j+1;
		}
		for ( int i = j; i<entityList.size();i++) {
			
			if("全体平均值".equals((entityList.get(i).groupName))) {//是平均值就跳过
				continue;
			}
			if ((entityList.get(i).sortResult).compareTo(entityList.get(i-1).sortResult) == 0) {//与前一个的值相等，序号则相同
				entityList.get(i).rowNum = entityList.get(i-1).rowNum;
			}else {
				entityList.get(i).rowNum = index;
			}
			index++;
		}
		
		return entityList;
	}
	
	/**
	 * 排名值null转成0
	 */
	private List<ReportDetailEntity> nullToZero (List<ReportDetailEntity> entityList) {
		List<ReportDetailEntity> newList = new ArrayList<ReportDetailEntity>();
		for (ReportDetailEntity entity : entityList) {
			if (entity.sortResult == null) {
				entity.sortResult = BigDecimal.ZERO;
			}
			newList.add(entity);
		}
		return newList;
	}
	
	/**
	 * 排名值  null和0 去除
	 */
	private List<ReportDetailEntity> removeNullOrZero (List<ReportDetailEntity> entityList) {
		List<ReportDetailEntity> newList = new ArrayList<ReportDetailEntity>();
		for (ReportDetailEntity entity : entityList) {
			if (entity.sortResult == null || (BigDecimal.ZERO.equals(entity.sortResult))) {
				continue;
			}
			newList.add(entity);
		}
		return newList;
	}


	
	
	
	
	//----------------------------------------------------区域分布-----------------------------------------------------------------
	/**
	 * 实物  区域分布通用
	 */
	private List<ReportDetailModel> orderRegion(String code, String isCity,  String isStore,String JdOrPd, String storeId, String provinceName,   
			String startTime, String endTime, Integer startNum,Integer pageSize,
			Long pastResult,Integer pastRowNum) {
		List<ReportDetailEntity> entityList = new ArrayList<ReportDetailEntity>();
		List<ReportDetailModel> models = new ArrayList<ReportDetailModel>();
		
		//1、调用hybirs接口查询agencys
		List<String> list = this.getAgencysByHybirs(code);
		
		
		//2、分割集合
		if (list==null || list.isEmpty()) {
			return models;
		}
		List<List<String>> bigList = new ArrayList<List<String>>();
		if(list.size()>500) {
			bigList= listToBigList(list);
			list = bigList.get(0);//第一个集合
		}
		
		//3、返回数据
		entityList = reportDetailDao.orderRegion(list, bigList, isCity, isStore, JdOrPd, storeId, provinceName, startNum, pageSize, startTime, endTime);
		if(entityList==null || entityList.isEmpty()) {
			return models;
		}
		
		//4、查询最大值
		Long regionMaxResult = 0L;//最大值
		ReportDetailEntity maxEntity = reportDetailDao.orderRegion(list, bigList, isCity, isStore, JdOrPd, storeId, provinceName, 0, 2, startTime, endTime).get(0);//最大值的集合
		if (maxEntity != null) {
			if(maxEntity.regionDistributionResult !=null) {
				regionMaxResult =  maxEntity.regionDistributionResult;
			}
		}		
				
		//5.排序序号
		entityList = this.rowNumSortOfRegion(entityList, pastResult, pastRowNum, startNum);
		
	
		//6.赋值给model
		for (ReportDetailEntity entity : entityList) {
			ReportDetailModel model = new ReportDetailModel();
			
			if (entity.regionDistributionResult !=null) {//结果
				model.regionDistributionResult = entity.regionDistributionResult+"";
			}else {
				model.regionDistributionResult = "0";
			}
			model.result = entity.regionResultName;//对应名称
			model.rowNum = entity.rowNum+"";//序号
			model.regionMaxResult = regionMaxResult+"";//最大值
			models.add(model);
		}
		
		return models;
	}
	

	
	/**
	 * 实物  派单  省   区域分布
	 **/
	public List<ReportDetailModel> orderDistributeProvinceRegion(String code,String isStore,String storeId, String startTime, String endTime, Integer startNum,Integer pageSize,BigDecimal pastResult,Integer pastRowNum) {
		
		List<ReportDetailModel> out = new ArrayList<ReportDetailModel>();
		String isCity = "N";//省or市
		String JdOrPd = "PD";//派单or接单
		Long pastResultLong = pastResult.longValue();//转换类型
		
		//返回数据
		List<ReportDetailModel> modelList = this.orderRegion(code, isCity, isStore, JdOrPd, storeId, "", startTime, endTime, startNum, pageSize, pastResultLong, pastRowNum);
		if(modelList == null || modelList.isEmpty()) {
			return out;
		}
		
		//6.赋值给model
		for (ReportDetailModel ex : modelList) {
			ReportDetailModel model = new ReportDetailModel();
			model.regionDistributionResult = ex.regionDistributionResult ;
			model.provinceName = ex.result;//省名
			model.rowNum = ex.rowNum;//序号
			model.regionMaxResult = ex.regionMaxResult;//最大值
			out.add(model);
		}
		
		return out;
	}
	
	/**
	 * 实物  派单 市 区域分布
	 **/
	public List<ReportDetailModel> orderDistributeCityRegion(String code, String provinceName, String isStore,
			String storeId, String startTime, String endTime, Integer startNum, Integer pageSize, BigDecimal pastResult,Integer pastRowNum) {
	
		List<ReportDetailModel> out = new ArrayList<ReportDetailModel>();
		String isCity = "Y";//省or市
		String JdOrPd = "PD";//派单or接单
		Long pastResultLong = pastResult.longValue();//转换类型
		
		//返回数据
		List<ReportDetailModel> modelList = this.orderRegion(code, isCity, isStore, JdOrPd, storeId, provinceName, startTime, endTime, startNum, pageSize, pastResultLong, pastRowNum);
		if(modelList == null || modelList.isEmpty()) {
			return out;
		}
		
		//6.赋值给model
		for (ReportDetailModel ex : modelList) {
			ReportDetailModel model = new ReportDetailModel();
			model.regionDistributionResult = ex.regionDistributionResult ;//结果
			model.cityName = ex.result;//市名
			model.rowNum = ex.rowNum;//序号
			model.regionMaxResult = ex.regionMaxResult;//最大值
			out.add(model);
		}
		
		return out;
	}
	
	

	/**
	 * 实物   接单 省 区域分布
	 **/
	public List<ReportDetailModel> orderAcceptProvinceRegion(String code,String isStore,String storeId, String startTime, String endTime, Integer startNum,Integer pageSize,BigDecimal pastResult,Integer pastRowNum) {
		
		List<ReportDetailModel> out = new ArrayList<ReportDetailModel>();
		String isCity = "N";//省or市
		String JdOrPd = "JD";//派单or接单
		Long pastResultLong = pastResult.longValue();//转换类型
		
		//返回数据
		List<ReportDetailModel> modelList = this.orderRegion(code, isCity, isStore, JdOrPd, storeId, "", startTime, endTime, startNum, pageSize, pastResultLong, pastRowNum);
		if(modelList == null || modelList.isEmpty()) {
			return out;
		}
		
		//6.赋值给model
		for (ReportDetailModel ex : modelList) {
			ReportDetailModel model = new ReportDetailModel();
			model.regionDistributionResult = ex.regionDistributionResult ;//结果
			model.provinceName = ex.result;//省名
			model.rowNum = ex.rowNum;//序号
			model.regionMaxResult = ex.regionMaxResult;//最大值
			out.add(model);
		}
		
		return out;
	}
	
	
	/**
	 * 实物  接单 市 区域分布
	 **/
	public List<ReportDetailModel> orderAcceptCityRegion(String code, String provinceName, String isStore,
			String storeId, String startTime, String endTime, Integer startNum, Integer pageSize, BigDecimal pastResult,Integer pastRowNum) {
	
		List<ReportDetailModel> out = new ArrayList<ReportDetailModel>();
		String isCity = "Y";//省or市
		String JdOrPd = "JD";//派单or接单
		Long pastResultLong = pastResult.longValue();//转换类型
		
		//返回数据
		List<ReportDetailModel> modelList = this.orderRegion(code, isCity, isStore, JdOrPd, storeId, provinceName, startTime, endTime, startNum, pageSize, pastResultLong, pastRowNum);
		if(modelList == null || modelList.isEmpty()) {
			return out;
		}
		
		//6.赋值给model
		for (ReportDetailModel ex : modelList) {
			ReportDetailModel model = new ReportDetailModel();
			model.regionDistributionResult = ex.regionDistributionResult ;
			model.cityName = ex.result;//市名
			model.rowNum = ex.rowNum;//序号
			model.regionMaxResult = ex.regionMaxResult;//最大值
			out.add(model);
		}
		
		return out;
	}
	
	//------------------------------------------------特权定金-------------------------------------------------------------------
	/**
	 * 特权定金 区域分布通用
	 */
	private List<ReportDetailModel> prePayRegion(String code, String isCity,  String isStore, String storeId, String provinceName,   
			String startTime, String endTime, Integer startNum,Integer pageSize,
			Long pastResult,Integer pastRowNum) {
		List<ReportDetailEntity> entityList = new ArrayList<ReportDetailEntity>();
		List<ReportDetailModel> models = new ArrayList<ReportDetailModel>();
		
		//1、调用hybirs接口查询agencys
		List<String> list = this.getAgencysByHybirs(code);
		
		
		//2、分割集合
		if (list==null || list.isEmpty()) {
			return models;
		}
		List<List<String>> bigList = new ArrayList<List<String>>();
		if(list.size()>500) {
			bigList= listToBigList(list);
			list = bigList.get(0);//第一个集合
		}
		
		//3、返回数据
		entityList = reportDetailDao.prePayRegion(list, bigList, isCity, isStore, storeId, provinceName, startNum, pageSize, startTime, endTime);
		if(entityList==null || entityList.isEmpty()) {
			return models;
		}
		
		//4、查询最大值
		Long regionMaxResult = 0L;//最大值
		ReportDetailEntity maxEntity = reportDetailDao.prePayRegion(list, bigList, isCity, isStore, storeId, provinceName, 0, 2, startTime, endTime).get(0);//最大值的集合
		if (maxEntity != null) {
			if(maxEntity.regionDistributionResult !=null) {
				regionMaxResult =  maxEntity.regionDistributionResult;
			}
		}		
				
		//5.排序序号
		entityList = this.rowNumSortOfRegion(entityList, pastResult, pastRowNum, startNum);
		
	
		//6.赋值给model
		for (ReportDetailEntity entity : entityList) {
			ReportDetailModel model = new ReportDetailModel();
			
			if (entity.regionDistributionResult !=null) {//结果
				model.regionDistributionResult = entity.regionDistributionResult+"";
			}else {
				model.regionDistributionResult = "0";
			}
			model.result = entity.regionResultName;//对应名称
			model.rowNum = entity.rowNum+"";//序号
			model.regionMaxResult = regionMaxResult+"";//最大值
			models.add(model);
		}
		
		return models;
	}
	
	/**
	 * 特权  省 区域分布
	 **/
	public List<ReportDetailModel> prePayProvinceRegion(String code,String isStore,String storeId ,String startTime, String endTime, Integer startNum,Integer pageSize,BigDecimal pastResult,Integer pastRowNum){
		

		List<ReportDetailModel> out = new ArrayList<ReportDetailModel>();
		String isCity = "N";//省or市
		Long pastResultLong = pastResult.longValue();//转换类型
		
		//返回数据
		List<ReportDetailModel> modelList = this.prePayRegion(code, isCity, isStore, storeId, "", startTime, endTime, startNum, pageSize, pastResultLong, pastRowNum);
		if(modelList == null || modelList.isEmpty()) {
			return out;
		}
		
		//6.赋值给model
		for (ReportDetailModel ex : modelList) {
			ReportDetailModel model = new ReportDetailModel();
			model.regionDistributionResult = ex.regionDistributionResult ;//结果
			model.provinceName = ex.result;//省名
			model.rowNum = ex.rowNum;//序号
			model.regionMaxResult = ex.regionMaxResult;//最大值
			out.add(model);
		}
		
		return out;
	}
	
	/**
	 * 特权  市 区域分布
	 **/
	public List<ReportDetailModel> prePayCityRegion( String code,String provinceName,String isStore,String storeId ,String startTime, String endTime,Integer startNum, Integer pageSize,BigDecimal pastResult,Integer pastRowNum){

		List<ReportDetailModel> out = new ArrayList<ReportDetailModel>();
		String isCity = "Y";//省or市
		Long pastResultLong = pastResult.longValue();//转换类型
		
		//返回数据
		List<ReportDetailModel> modelList = this.prePayRegion(code, isCity, isStore, storeId, provinceName, startTime, endTime, startNum, pageSize, pastResultLong, pastRowNum);
		if(modelList == null || modelList.isEmpty()) {
			return out;
		}
		
		//6.赋值给model
		for (ReportDetailModel ex : modelList) {
			ReportDetailModel model = new ReportDetailModel();
			model.regionDistributionResult = ex.regionDistributionResult ;//结果
			model.cityName = ex.result;//市名
			model.rowNum = ex.rowNum;//序号
			model.regionMaxResult = ex.regionMaxResult;//最大值
			out.add(model);
		}
		
		return out;
	}
	
	/**
	 * 留资 区域分布通用
	 */
	private List<ReportDetailModel> saleRegion(String code, String isCity,  String isStore, String storeId, String provinceName,   
			String startTime, String endTime, Integer startNum,Integer pageSize,
			Long pastResult,Integer pastRowNum) {
		List<ReportDetailEntity> entityList = new ArrayList<ReportDetailEntity>();
		List<ReportDetailModel> models = new ArrayList<ReportDetailModel>();
		
		//1、调用hybirs接口查询agencys
		List<String> list = this.getAgencysByHybirs(code);
		
		
		//2、分割集合
		if (list==null || list.isEmpty()) {
			return models;
		}
		List<List<String>> bigList = new ArrayList<List<String>>();
		if(list.size()>500) {
			bigList= listToBigList(list);
			list = bigList.get(0);//第一个集合
		}
		
		//3、返回数据
		entityList = reportDetailDao.saleRegion(list, bigList, isCity, isStore, storeId, provinceName, startNum, pageSize, startTime, endTime);
		if(entityList==null || entityList.isEmpty()) {
			return models;
		}
		
		//4、查询最大值
		Long regionMaxResult = 0L;//最大值
		ReportDetailEntity maxEntity = reportDetailDao.saleRegion(list, bigList, isCity, isStore, storeId, provinceName, 0, 2, startTime, endTime).get(0);//最大值的集合
		if (maxEntity != null) {
			if(maxEntity.regionDistributionResult !=null) {
				regionMaxResult =  maxEntity.regionDistributionResult;
			}
		}		
				
		//5.排序序号
		entityList = this.rowNumSortOfRegion(entityList, pastResult, pastRowNum, startNum);
		
	
		//6.赋值给model
		for (ReportDetailEntity entity : entityList) {
			ReportDetailModel model = new ReportDetailModel();
			
			if (entity.regionDistributionResult !=null) {//结果
				model.regionDistributionResult = entity.regionDistributionResult+"";
			}else {
				model.regionDistributionResult = "0";
			}
			model.result = entity.regionResultName;//对应名称
			model.rowNum = entity.rowNum+"";//序号
			model.regionMaxResult = regionMaxResult+"";//最大值
			models.add(model);
		}
		
		return models;
	}
	
	
	/**
	 * 留资  省 区域分布
	 **/
	public List<ReportDetailModel> saleProvinceRegion(String code,String isStore,String storeId ,String startTime, String endTime, Integer startNum,Integer pageSize,BigDecimal pastResult,Integer pastRowNum){
		

		List<ReportDetailModel> out = new ArrayList<ReportDetailModel>();
		String isCity = "N";//省or市
		Long pastResultLong = pastResult.longValue();//转换类型
		
		//返回数据
		List<ReportDetailModel> modelList = this.saleRegion(code, isCity, isStore, storeId, "", startTime, endTime, startNum, pageSize, pastResultLong, pastRowNum);
		if(modelList == null || modelList.isEmpty()) {
			return out;
		}
		
		//6.赋值给model
		for (ReportDetailModel ex : modelList) {
			ReportDetailModel model = new ReportDetailModel();
			model.regionDistributionResult = ex.regionDistributionResult ;//结果
			model.provinceName = ex.result;//省名
			model.rowNum = ex.rowNum;//序号
			model.regionMaxResult = ex.regionMaxResult;//最大值
			out.add(model);
		}
		
		return out;
	}
	
	/**
	 * 特权  市 区域分布
	 **/
	public List<ReportDetailModel> saleCityRegion( String code,String provinceName,String isStore,String storeId ,String startTime, String endTime,Integer startNum, Integer pageSize,BigDecimal pastResult,Integer pastRowNum){

		List<ReportDetailModel> out = new ArrayList<ReportDetailModel>();
		String isCity = "Y";//省or市
		Long pastResultLong = pastResult.longValue();//转换类型
		
		//返回数据
		List<ReportDetailModel> modelList = this.saleRegion(code, isCity, isStore, storeId, provinceName, startTime, endTime, startNum, pageSize, pastResultLong, pastRowNum);
		if(modelList == null || modelList.isEmpty()) {
			return out;
		}
		
		//6.赋值给model
		for (ReportDetailModel ex : modelList) {
			ReportDetailModel model = new ReportDetailModel();
			model.regionDistributionResult = ex.regionDistributionResult ;//结果
			model.cityName = ex.result;//市名
			model.rowNum = ex.rowNum;//序号
			model.regionMaxResult = ex.regionMaxResult;//最大值
			out.add(model);
		}
		
		return out;
	}
	

	//-----------------------------------工具----------------------------------------
	/**
	 * 区域分布排序rowNum
	 * @param entityList :当前集合
	 * @param pastResult ：上一页最后一个结果
	 * @param pastRowNum：上一页最后一个序号
	 * @param startNum ：开始的页码
	 * @return
	 */
	private List<ReportDetailEntity>  rowNumSortOfRegion (List<ReportDetailEntity> entityList,Long pastResult,Integer pastRowNum,Integer startNum) {
		int index ;//为rowNum赋值，除第一个rowNum外
		
		if (startNum == 0) {//第一页
			//若第一个rowNum不为1，且数据为“0”,则赋值所有rowNum为1
			if ((entityList.get(0).rowNum != 1) && (entityList.get(0).regionDistributionResult == 0) ) {
				for(int i =0; i< entityList.size() ;i++) {
					entityList.get(i).rowNum = 1;
				}
			}else {
				entityList.get(0).rowNum  = 1;//先赋值第一个的rowNum
			}
			
			index = 2;
		}else {//非第一页
			ReportDetailEntity first = entityList.get(0);//当前集合的第一个结果
			//对比两数据
			if(first.regionDistributionResult < pastResult) {//小于上一个集合的最后一个结果
				entityList.get(0).rowNum  = pastRowNum+1;//先赋值第一个的rowNum
				index = pastRowNum+2;
			}else {//等于
				entityList.get(0).rowNum  = pastRowNum;//先赋值第一个的rowNum
				index = pastRowNum +1;
			}
		}
		//从第二个参数开始赋值
		for (int i = 1; i<entityList.size();i++) {
			if ((entityList.get(i).regionDistributionResult) == (entityList.get(i-1).regionDistributionResult)) {//与前一个的值相等，序号则相同
				entityList.get(i).rowNum = entityList.get(i-1).rowNum;
			}else {
				entityList.get(i).rowNum = index;
			}
			index++;
		}
		
		return entityList;
	}


	//----------------------------------------------------品类销售top5-----------------------------------------------------------------

	/**
	 * 品类销售top5通用
	 */
	private List<ReportDetailModel> categoryTop5(String code, String category,String JdOrPd, String isStore, String storeId, String startTime, String endTime) {
		List<ReportDetailEntity> entityList = new ArrayList<ReportDetailEntity>();
		List<ReportDetailModel> models = new ArrayList<ReportDetailModel>();
		
		//1、调用hybirs接口查询agencys
		List<String> list = this.getAgencysByHybirs(code);
		
		
		//2、分割集合
		if (list==null || list.isEmpty()) {
			return models;
		}
		List<List<String>> bigList = new ArrayList<List<String>>();
		if(list.size()>500) {
			bigList= listToBigList(list);
			list = bigList.get(0);//第一个集合
		}
		
		//3、返回数据
		entityList = reportDetailDao.getCategoryTop5(list, bigList, isStore, JdOrPd, storeId, startTime, endTime);
		if(entityList==null || entityList.isEmpty()) {
			return models;
		}
		
		//4、查询总数
		Long total = 0L;//总数量
		
		total= reportDetailDao.categoryTop5Count(list, bigList, isStore, JdOrPd, storeId, startTime, endTime);
		BigDecimal totalBig = new BigDecimal(total); //转格式
		
		//5.去除没用数据 
		entityList = this.removeUseless(entityList);
		
		//6.排序序号
		entityList = this.rowNumSortOfCategory(entityList);
		
		//7.赋值给model
		int index = 5;//只输出5个值
		if (entityList.size()<5) {//长度<5，取集合大小
			index = entityList.size();
		}
		for (int i = 0; i<index;i++) {
			ReportDetailEntity entity = entityList.get(i);
			ReportDetailModel model = new ReportDetailModel();
			
			BigDecimal productCountBig = new BigDecimal(entity.productCount);
		    if (productCountBig == null || BigDecimal.ZERO.equals(productCountBig)||BigDecimal.ZERO.equals(totalBig)) {
		    	 model.productSalesRate = "0";
				 model.productSalesRateStr = "0";
		    }else {
		    	BigDecimal rate = (productCountBig.divide(totalBig,4,BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100));
			    BigDecimal rateBig = rate.setScale(2, BigDecimal.ROUND_HALF_UP);
			    model.productSalesRate = rateBig+"";//销售比率（不带单位）
			    model.productSalesRateStr = rateBig+"%";//销售比率（带单位）
		    }
			
		    model.productName = entity.productName;//产品名称
			model.productCount = (entity.productCount == null ? "0":entity.productCount+"");//产品数量
			model.productModelNumber = entity.productModelNumber;//产品型号
			model.rowNum = entity.rowNum+"";//序号

			models.add(model);
		}
		
		return models;
	}
	
	
	
	/**
	 * 品类销售top5  派单
	 * **/
	public List<ReportDetailModel> distributeCategoryTop5(String code, String category, String isStore, String storeId,
			String startTime, String endTime) {
		
		List<ReportDetailModel> out =  new ArrayList<ReportDetailModel>();
		String JdOrPd = "PD";
		out = this.categoryTop5(code, category, JdOrPd, isStore, storeId, startTime, endTime);
		return out;
	}

	/**
	 * 品类销售top5  接单
	 * **/
	public List<ReportDetailModel> acceptCategoryTop5(String code, String category, String isStore, String storeId,
			String startTime, String endTime) {
		List<ReportDetailModel> out =  new ArrayList<ReportDetailModel>();
		String JdOrPd = "JD";
		out = this.categoryTop5(code, category, JdOrPd, isStore, storeId, startTime, endTime);
		return out;
	}

	
	
	//-----------------------------工具类------------------------------------
	/**
	 * 品类销售top5排序rowNum
	 * @return
	 */
	private List<ReportDetailEntity>  rowNumSortOfCategory (List<ReportDetailEntity> entityList) {
		int index ;//为rowNum赋值，除第一个rowNum外
		
		//若第一个rowNum不为1，且数据为“0”,则赋值所有rowNum为1
		if ((entityList.get(0).rowNum != 1) && (entityList.get(0).productCount == 0) ) {
			for(int i =0; i< entityList.size() ;i++) {
				entityList.get(i).rowNum = 1;
			}
		}else {
			entityList.get(0).rowNum  = 1;//先赋值第一个的rowNum
		}
		index = 2;
		
		//从第二个参数开始赋值
		for (int i = 1; i<entityList.size();i++) {
			if ((entityList.get(i).productCount) == (entityList.get(i-1).productCount)) {//与前一个的值相等，序号则相同
				entityList.get(i).rowNum = entityList.get(i-1).rowNum;
			}else {
				entityList.get(i).rowNum = index;
			}
			index++;
		}
		
		return entityList;
	}
	
	/**
	 * 去除没用的数据
	 */
	private List<ReportDetailEntity>   removeUseless(List<ReportDetailEntity> entityList ) {
		List<ReportDetailEntity> newList= new ArrayList<ReportDetailEntity>();
		String YF = "yunf";//运费
		String SJFWF = "SJFWF";//设计服务费
		String CESP = "100N0708";//测试商品（拍下无效）
		String JGF1 = "jiagongfei";//加工费1
		String JGF2 = "jgf";//加工费1
		String SPZY = "SPZY";//商品专用
		
		for(int i = 0 ;i<entityList.size();i++) {
			ReportDetailEntity entity = entityList.get(i);
			if (YF.equals(entity.productModelNumber) || SJFWF.equals(entity.productModelNumber)|| CESP.equals(entity.productModelNumber)
					|| JGF1.equals(entity.productModelNumber) || JGF2.equals(entity.productModelNumber)  || SPZY.equals(entity.productModelNumber) ) {
				continue;
			}
			newList.add(entity);
		}
		return newList;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
