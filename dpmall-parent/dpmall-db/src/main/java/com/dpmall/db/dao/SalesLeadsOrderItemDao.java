package com.dpmall.db.dao;

import com.dpmall.db.bean.SalOrderItem4OmsEntity;
import com.dpmall.db.bean.SalesLeadsOrderItemEntity;
import com.dpmall.db.bean.po.SalOrderItemsPo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

public interface SalesLeadsOrderItemDao {
//	int insert(@Param("orderItem") SalesLeadsOrderItemEntity orderItem);

	/**
	 * 获取商品明细
	 */
	public List<SalesLeadsOrderItemEntity>getItemByOrderId (@Param("orderId")String salesLeadsOrderId);

	/**
	 * 获取商品id
	 */
	public Set<String> getItemIdByOrderId (@Param("orderId")String salesLeadsOrderId);

	/**
	 * 更新商品
	 */
	public int edit (@Param("entity")SalOrderItemsPo po);

	/**
	 * 添加商品
	 */
	public int insert (@Param("entity")SalOrderItemsPo po);


	/**
	 * 删除商品（软删除）
	 */
	public int delete (@Param("entity")Set<String> entity);


	/**查询留资单商品明细 oms**/
	List<SalOrderItem4OmsEntity> getSalOrderItem4Oms(@Param("salesLeadsOrderId")String salesLeadsOrderId);

	/**查询留资单商品明细数量 oms**/
	@Select("select count(1) FROM SALESLEADSORDERITEM si WHERE 1 = 1 AND si.P_SALESLEADSORDER = #{salesLeadsOrderId} and si.P_ISDELETE = '0'")
	int getSalOrderItemCount4Oms(@Param("salesLeadsOrderId")String salesLeadsOrderId);









}


