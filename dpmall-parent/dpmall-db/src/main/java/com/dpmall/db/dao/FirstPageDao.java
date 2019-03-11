package com.dpmall.db.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dpmall.db.bean.FirstPageEntity;
import com.dpmall.db.bean.OrderEntity;
import com.dpmall.db.bean.OrderReturnEntity;

public interface FirstPageDao {

	/**
	 * 留资
	 */
	public BigDecimal salesLeadsCount(@Param("list") List<String> list, @Param("storeId") String storeId,
			@Param("bigList") List<List<String>> bigList, @Param("startdate") String startdate,
			@Param("enddate") String enddate, @Param("status") String status, @Param("isStore") String isStore);

	/**
	 * 实物数量 经销商
	 */
	public BigDecimal orderCountOfAgency(@Param("list") List<String> list, @Param("bigList") List<List<String>> bigList,
			@Param("startdate") String startdate, @Param("enddate") String enddate, @Param("status") String status);

	/**
	 * 实物数量 门店
	 */
	public BigDecimal orderCountOfStore(@Param("startdate") String startdate, @Param("enddate") String enddate,
			@Param("status") String status, @Param("storeId") String storeId);


	/**
	 * 天猫特权订金 经销商
	 */
	public BigDecimal tmallPrepayCountOfAgency(@Param("list") List<String> list,
												@Param("bigList") List<List<String>> bigList, @Param("startdate") String startdate,
												@Param("enddate") String enddate, @Param("status") String status);

	/**
	 * 特权订金 门店
	 */
	public BigDecimal  tmallPrepayCountOfStore(@Param("startdate") String startdate, @Param("enddate") String enddate,
											   @Param("status") String status, @Param("storeId") String storeId);

	/**
	 * 特权订金 经销商
	 */
	public BigDecimal othersPrepayCountOfAgency(@Param("list") List<String> list,
			@Param("bigList") List<List<String>> bigList, @Param("startdate") String startdate,
			@Param("enddate") String enddate, @Param("status") String status);

	/**
	 * 特权订金 门店
	 */
	public BigDecimal othersPrepayCountOfStore(@Param("startdate") String startdate, @Param("enddate") String enddate,
			@Param("status") String status, @Param("storeId") String storeId);

	/**
	 * 查询历史消息
	 */
	public FirstPageEntity getHistory(@Param("code") String code, @Param("startdate") String startdate,
			@Param("enddate") String enddate, @Param("isStore") String isStore,@Param("storeId") String storeId);

}
