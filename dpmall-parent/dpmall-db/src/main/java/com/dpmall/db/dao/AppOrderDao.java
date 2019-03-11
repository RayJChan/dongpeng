package com.dpmall.db.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.dpmall.db.bean.OrderEntity;
import com.dpmall.db.bean.OrderReturnEntity;

public interface AppOrderDao {
	 
	 /**
     * 实物类经销商订单状态条数
     * @param distributorId 经销商ID
     * @param status 状态
     * @return 经销商待分配的实物订单数
     */
	 public Integer get2DistributeCount(@Param("distributorId")String distributorId,@Param("status")String status);
	 
	 /**
	     * 经销商下派到店铺（o2o表）
	     * @param orderCode 订单编码
	     * @param storeId 店铺ID
	     * @param remark 备注
	     */
	 public int distribute4O2o(@Param("orderCode")String orderCode,	 @Param("storeId")String storeId, @Param("remark") String remark);
	 
	 /**
	  * 
   * 经销商下派到店铺 (consignment表)
   * @param orderCode 订单编码
   * @param storeId 店铺ID
   */
	 public int distribute4Consignment(@Param("orderCode")String orderCode , @Param("storeId")String storeId);
	 
	 public List<OrderEntity> getOnePage4Acceptor2Followup(String acceptorId,Integer offset, Integer pageSize);
	 
	 /**
	  * 获取店铺待接单的实物订单数
	  * @param storeId 经销商ID
	  * @return 经销商待分配的实物订单数
	 */
	public Integer get2AcceptCount(@Param("storeId")String storeId);
	
	/**
	 * 实物类经销商订单状态列表
	 * @param distributorId 经销商Id
	 * @param status 状态
	 * @param offset 上一次加载的最后项offset
	 * @param pageSize 页的大小
	 * @return 经销商获取待分配的实物订单列表
	 */
	 public List<OrderEntity> getOnePage4Distribute(@Param("distributorId")String distributorId,@Param("status")String status,@Param("search") String search,@Param("offset")Integer offset, @Param("pageSize")Integer pageSize,@Param("statusSearch")String statusSearch);
   
   /**
	 * author: crown
	 * 根据经销商ID查询待跟进一页的实物订单数据
	 * @param template
	 * @param page
	 * @return
	 */
  public List<OrderEntity> getOnePage4Followup(@Param(value="distributorId")String distributorId,@Param("offset")Integer offset, @Param("pageSize")Integer pageSize);
  
  /**
	 * 店铺获取待接单的实物订单
	 * @param storeId 店铺ID
	 * @param offset 上一次加载的位移
	 * @param pageSize 页的大小
	 * @return 店铺获取待接单的实物订单列表
	 */
  public List<OrderEntity> getOnePage4Accept(@Param(value="storeId")String storeId,@Param("offset")Integer offset, @Param("pageSize")Integer pageSize);
  
  /**
   * author:crown
   * 导购员接单
   * @param acceptorId 导购员ID
   * @param orderCode 订单编码
   * @return 成功返回200
   */
  public int accept(@Param(value="acceptorId")String acceptorId, @Param("orderCode")String orderCode, @Param("acceptComment")String acceptComment);
   
  
  /**
	 * author : cwj 
	 * 编辑实物订单信息
	 * @param entity
	 * @return 1为成功，0为失败
	 */
	int edit (@Param("entity") OrderEntity entity);

	/**
	 * author : cwj 
	 * 确认发货，更新B2C发货单模型发货状态
	 * @param entity
	 * @return 1为成功，0为失败
	 */
	int deliver4Consignments (@Param("entity") OrderEntity entity);
	
	public List<OrderEntity> getOnePage4StoreId(@Param("storeId")String storeId,@Param("status")String status,@Param("acceptorId") String acceptorId,@Param("search") String search,
			@Param("offset")Integer offset, @Param("pageSize")Integer pageSize,@Param("statusSearch")String statusSearch);
	
	/**
	 * author:crown
     * 实物类获取单据明细
     * @param consignmentId 发货单ID
     * @return 订单详情
     */
	OrderEntity getOrderDetails(@Param("consignmentId")String consignmentId);
	
	/**
     * 实物类导购员订单状态条数
     * @param acceptorId 导购员ID
     * @param status 状态
     * @return 实物类导购员订单状态条数
     */
	public int get2AcceptorCount (@Param("acceptorId") String acceptorId , @Param("status") String status);
	
	/**
	 * 实物类导购员订单状态列表
	 * author:crown
	 * @param acceptorId 导购员ID
     * @param status 状态
     * @param startItemId 上一次加载的最后项ID
     * @param pageSize 页大小
     * @return 导购员已接单的一页实物订单信息
	 */
	 public List<OrderEntity> getOnePage4AcceptorId(@Param("acceptorId")String acceptorId,@Param("status")String status,@Param("startNum")Integer startNum, @Param("pageSize")Integer pageSize);
	 
	 /**
	   * 实物类门店订单状态条数
	   * @param storeId 经销商ID
	   * @param status 状态
	   * @return 经销商待分配的实物订单数
	  */
	 public Integer get2StoreCount(@Param("storeId")String storeId,@Param("acceptorId") String acceptorId,@Param("status")String status);
	 
	 /**
	    * 实物类获取退货单据明细
	    * @param consignmentId 发货单ID
	    * @return 订单详情
	   */
	 public OrderEntity getReturnRequestDetails(@Param("consignmentId")String consignmentId);
	 
	 /**
	  * 转换单位
	  * @param unit 单位
	  * @return 转换好的单位
	  */
	 public String formatUnit(@Param("unit") String unit);
	 
	 /**
	  * 获取客服备注
	  */
	 public List<OrderEntity> getComments(@Param("consignmentId") String consignmentId);

	 public List<OrderEntity> distributedOrders(@Param("list")List<String> list,@Param("startdate")Date startdate,@Param("enddate")Date enddate);

	 
	 /**
	  * 获取部分退货时，退货订单详情
	  */
	 public OrderReturnEntity getPartOfReturnDetails(@Param("consignmentId") String consignmentId,@Param("returnOderCode") String returnOderCode);

	 /**
	  * 部分退货时，退货单单号
	  */
	 public List<String> getPartOfReturnCodes(@Param("consignmentId") String consignmentId);


	 /**
	  * 计算总的退货金额
	  */
	 public String getReturnPriceSum(@Param("consignmentId") String consignmentId);
}
