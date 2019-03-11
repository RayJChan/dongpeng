package com.dpmall.db.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.dpmall.db.bean.ReportDetailEntity;

/** * @author  cwj
 * 	  说明：报表的详细信息 包括历史趋势，下级排名 
	  @return  */
public interface ReportDetailDao {
	
	//----------------------------------------历史趋势-------------------

	/**实物  历史记录**/
	public List<ReportDetailEntity> orderHistory (@Param("code")String code, @Param("format")String format, @Param("offset")Integer offset,@Param("pageSize")Integer pageSize,@Param("endTime")String endTime);

	/**特权  历史记录**/
	public List<ReportDetailEntity> prePayHistory (@Param("code")String code, @Param("format")String format,@Param("storeId")String storeId, @Param("isStore")String isStore,@Param("offset")Integer offset,@Param("pageSize")Integer pageSize,@Param("endTime")String endTime);

	/**留资  历史记录**/
	public List<ReportDetailEntity> saleHistory (@Param("code")String code, @Param("format")String format,@Param("storeId")String storeId,@Param("isStore")String isStore, @Param("offset")Integer offset,@Param("pageSize")Integer pageSize,@Param("endTime")String endTime);

	
	
	//------------------------------------------排名-------------------------
		//-----------------------------通用-----------------------------
	
	
	/**获取下级的gropuCodes**/
	public List<String> getGroupCodes (@Param("list")List<String> list);
	
	/**获取下级的gropuPKs  适用于经销商以上级别**/
	public List<String> getGroupPKs (@Param("list")List<String> list);
	
	/**获取下级的gropuPKs 仅适用于经销商级别**/
	public List<String> getGroupPKsOfAgency (@Param("list")List<String> list,@Param("bigList")List<List<String>> bigList);
	
	/**获取门店StorePKs 仅适用于门店**/
	public List<String> getStorePKsOfStore (@Param("list")List<String> list,@Param("bigList")List<List<String>> bigList);
	
	
	//------------具体值------------
	/**获取排序中的平均值**/
	public ReportDetailEntity getSortAVG (@Param("list")List<String> list,@Param("bigList")List<List<String>> bigList,
			@Param("startTime")String startTime,@Param("endTime")String endTime,
			@Param("sortType")String sortType,@Param("dateFormat")String dateFormat,@Param("isStore")String isStore);
	
	
	/**： 排名信息**/
	public List<ReportDetailEntity> sortInfo (@Param("list")List<String> list,@Param("bigList")List<List<String>> bigList,
												@Param("startTime")String startTime,@Param("endTime")String endTime,
												@Param("offset")Integer offset,@Param("pageSize")Integer pageSize,
												@Param("sortType")String sortType,@Param("dateFormat")String dateFormat,@Param("isStore")String isStore);
	
	
	//------------------------------------------区域分布-------------------------
	
	/**实物  区域分布   (省派接单、市派接单)**/
	public List<ReportDetailEntity> orderRegion (@Param("list")List<String> list,@Param("bigList")List<List<String>> bigList,
															@Param("isCity")String isCity,	@Param("isStore")String isStore,@Param("JdOrPd")String JdOrPd,@Param("storeId")String storeId,@Param("provinceName")String provinceName,
															@Param("offset")Integer offset,@Param("pageSize")Integer pageSize,@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	/**特权订金  区域分布   (省派接单、市派接单)**/
	public List<ReportDetailEntity> prePayRegion (@Param("list")List<String> list,@Param("bigList")List<List<String>> bigList,
															@Param("isCity")String isCity,	@Param("isStore")String isStore,@Param("storeId")String storeId,@Param("provinceName")String provinceName,
															@Param("offset")Integer offset,@Param("pageSize")Integer pageSize,@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	/**留资  区域分布   (省派接单、市派接单)**/
	public List<ReportDetailEntity> saleRegion (@Param("list")List<String> list,@Param("bigList")List<List<String>> bigList,
															@Param("isCity")String isCity,	@Param("isStore")String isStore,@Param("storeId")String storeId,@Param("provinceName")String provinceName,
															@Param("offset")Integer offset,@Param("pageSize")Integer pageSize,@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	
	//------------------------------------------品类销售top5-------------------------
	/**实物  区域分布   (省派接单、市派接单)**/
	public List<ReportDetailEntity> getCategoryTop5 (@Param("list")List<String> list,@Param("bigList")List<List<String>> bigList,
															@Param("isStore")String isStore,@Param("JdOrPd")String JdOrPd,@Param("storeId")String storeId,
															@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	/**实物  区域分布   (省派接单、市派接单)**/
	public long categoryTop5Count (@Param("list")List<String> list,@Param("bigList")List<List<String>> bigList,
															@Param("isStore")String isStore,@Param("JdOrPd")String JdOrPd,@Param("storeId")String storeId,
															@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	
}
