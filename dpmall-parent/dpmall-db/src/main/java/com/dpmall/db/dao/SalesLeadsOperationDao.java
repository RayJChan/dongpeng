package com.dpmall.db.dao;

import com.dpmall.db.bean.SalOperationRemarkEntity;
import com.dpmall.db.bean.SalOrderOperationDetailEntity;
import com.dpmall.db.bean.SalesLeadsOperationEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface SalesLeadsOperationDao {
	 
	public int insert(@Param(value="template")SalesLeadsOperationEntity template);

	/***
	 *留资单操作状态的时间记录
	 */
	public List<SalOrderOperationDetailEntity> getTimeAndStatus (@Param("salesLeadsOrderId")String salesLeadsOrderId,@Param("operatorById")String operatorById);

	/***
	 *查看更新状态时的备注
	 */
	public SalOperationRemarkEntity getRemarkByOperationId (@Param("id")String operationId);


	/**获取用户名**/
	@Select("SELECT P_USERNAME FROM APPUSER WHERE pk = #{id}")
	public String getAgencyCodeById (@Param("id") String operatorById);

	/**获取用户名**/
	@Select("SELECT P_CNNAME FROM APPUSER WHERE pk = #{id}")
	public String getOperatorByNameById (@Param("id") String OperatorById);

	/**
	 * 查询历史数据
	 **/
	List<SalesLeadsOperationEntity> getHistory4Oms(@Param("salOrderCode") String salOrderCode,
												   @Param("agencyCode") String agencyCode,
												   @Param("startTime") String startTime,
												   @Param("endTime") String endTime,
												   @Param("pageNum") Integer pageNum,
												   @Param("pageSize") Integer pageSize);


	/**
	 * 导出历史数据
	 **/
	List<SalesLeadsOperationEntity> exportHistory4Oms(@Param("salOrderCode") String salOrderCode,
												   @Param("agencyCode") String agencyCode,
												   @Param("startTime") String startTime,
												   @Param("endTime") String endTime);



	/**查询历史数据数量**/
	String  getHistoryConut4Oms(@Param("salOrderCode") String salOrderCode,
							@Param("agencyCode") String agencyCode,
							@Param("startTime") String startTime,
							@Param("endTime") String endTime
							);






	/**查询留资单商品明细数量 oms**/
	List<SalOrderOperationDetailEntity> getOperateRemark(@Param("orderCodeList")List<String> orderCodeList);



}
