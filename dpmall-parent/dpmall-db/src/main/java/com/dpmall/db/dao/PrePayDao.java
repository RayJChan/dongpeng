package com.dpmall.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dpmall.db.bean.PrePayEntity;

public interface PrePayDao {
	Integer get2DistributeCount(@Param("distributorId")String distributorId,@Param("status")String status);
	
    Integer distribute(@Param("distributorId")String distributorId,@Param("orderCode")String orderCode, @Param("storeId")String storeId);
	
	Integer distributeO2o(@Param("orderCode")String orderCode,@Param("remark")String remark);
	
	Integer get2AcceptorCount(@Param("acceptorId")String acceptorId, @Param("status")String status);

    Integer get2StoreCount(@Param("storeId")String storeId,@Param("status")String status);
    
    
    //修改订单状态 
    Integer updateOrder(@Param("orderCode")String orderCode, @Param("status")String status);
    
    //修改o2o经销商备注
    Integer updateO2oOrder(@Param("orderCode")String orderCode,@Param("remark")String remark);
    
    /**
     * 特权定金获取单据明细
     * @param consignmentId 发货单ID
     * @return 特权定金获取单据明细
     */
    public PrePayEntity get4ConsignmentId(@Param("consignmentId")String consignmentId);
    
    /**
     * 被动查询订单
     * @param phone
     * @param storeId
     * @param acceptorId
     * @return
     */
    public List<PrePayEntity> get4Search(@Param("phone")String phone);
    
    /**
	 * 特权定金导购员状态列表
	 * @param acceptorId 导购员Id
	 * @param status 状态
	 * @param offset 上一次加载的最后项offset
	 * @param pageSize 页的大小
	 * @return 特权定金导购员状态列表
	 */
    public List<PrePayEntity> getOnePage4AcceptorId(@Param("acceptorId")String acceptorId,@Param("status")String status,@Param("search")String search,
    		@Param("startNum")Integer startNum, @Param("pageSize")Integer pageSize,@Param("statusSearch")String statusSearch);
   
    /**
	 * 特权定金销商商订单状态列表
	 * @param distributorId 经销商Id
	 * @param status 状态
	 * @param offset 上一次加载的最后项offset
	 * @param pageSize 页的大小
	 * @return 特权定金销商商订单状态列表
	 */
    public List<PrePayEntity> getOnePage4Distribute (@Param("distributorId") String distributorId,@Param("status") String status, @Param("search")String search  
    		,@Param("startNum") Integer startNum, @Param("pageSize")Integer pageSize,@Param("statusSearch")String statusSearch);
   
    /**
	 * 特权定金销商商订单状态列表
	 * @param distributorId 经销商Id
	 * @param status 状态
	 * @param offset 上一次加载的最后项offset
	 * @param pageSize 页的大小
	 * @return 特权定金销商商订单状态列表
	 */
    public List<PrePayEntity> getOnePage4StoreId (@Param("storeId") String storeId,@Param("status") String status,@Param("search") String search ,@Param("startNum") Integer startNum, @Param("pageSize")Integer pageSize);
    
    /**
     * 核销码获取单据明细
     * @param priDepositCode 核销码
     * @return 特权定金获取单据明细
     */
    public PrePayEntity get4priDepositCode(@Param("priDepositCode")String priDepositCode);
   
}
