package com.dpmall.datasvr.api;

import java.util.List;

import com.dpmall.common.TimeScope;
import com.dpmall.model.PrepayModel;

/**
 * 特权定金服务接口
 * @author river
 * @since 2017-07-10
 */
public interface IPrepayService {
    
	/**
	 * 核销
	 * @param prepayCode 核销码
	 * @param ttlAmount 核销订单总金额
	 * @param goodsList 订单数据
	 * @return
	 */
	//public Integer writeoff(String prepayCode,Double ttlAmount,List<SaleLeadsGoodsModel> goodsList);
	
	
	/**
	 * 获取待核销的一页特权定金
	 * @param storeId 门店ID
	 * @param prepayTime 预付时间
	 * @param clientName 客户名称
	 * @param clientTel 客户电话
	 * @param startNum 开始数
	 * @param pageSize 页码
	 * @return
	 */
	public List<PrepayModel> getOnePage2WriteOff(String storeId,TimeScope prepayTime, String clientName, String clientTel,Integer startNum, Integer pageSize);
	
	/**
	 * 获取待核销的一页特权定金
	 * @param storeId 门店ID
	 * @param prepayTime 预付时间
	 * @param clientName 客户名称
	 * @param clientTel 客户电话
	 * @param startNum 开始数
	 * @param pageSize 页码
	 * @return
	 */
	public List<PrepayModel> getOnePageClosedPrepay(String storeId,TimeScope prepayTime, String clientName, String clientTel,Integer startNum, Integer pageSize);
	
	
	/**
	 * 特权定金销商商订单状态列表
	 * @param distributorId 经销商Id
	 * @param status 状态
	 * @param offset 上一次加载的最后项offset
	 * @param pageSize 页的大小
	 * @return 特权定金销商商订单状态列表
	 */
    public List<PrepayModel> getOnePage4Distribute(String distributorId,String status,  String search,Integer offset, Integer pageSize,String statusSearch);
    
    /**
     * 特权定金经销商订单状态条数
     * @param distributorId 经销商ID
     * @param status 状态
     * @return 特权定金经销商订单状态条数
     */
    public Integer get2DistributeCount(String distributorId,String status);
    
    /**
	 * 特权定金门店订单状态列表
	 * @param storeId 门店Id
	 * @param status 状态
	 * @param offset 上一次加载的最后项offset
	 * @param pageSize 页的大小
	 * @return 特权定金门店订单状态列表
	 */
    public List<PrepayModel> getOnePage4StoreId(String storeId,String status, String search,Integer offset, Integer pageSize);
    
    /**
     * 特权定金门店订单状态条数
     * @param storeId 门店ID
     * @param status 状态
     * @return 特权定金门店订单状态条数
     */
    public Integer get2StoreCount(String storeId,String status);
    
    /**
	 * 特权定金导购员状态列表
	 * @param acceptorId 导购员Id
	 * @param status 状态
	 * @param offset 上一次加载的最后项offset
	 * @param pageSize 页的大小
	 * @return 特权定金导购员状态列表
	 */
    public List<PrepayModel> getOnePage4AcceptorId(String acceptorId,String status,  String search,Integer offset, Integer pageSize,String statusSearch);
    
    /**
     * 特权定金导购员状态条数
     * @param acceptorId 导购员ID
     * @param status 状态
     * @return 特权定金导购员状态条数
     */
    public Integer get2AcceptorCount(String acceptorId,String status);
    
    
    /**
     * 特权定金获取单据明细
     * @param consignmentId 发货单ID
     * @return 特权定金获取单据明细
     */
    public PrepayModel get4ConsignmentId(String consignmentId);
    
    
    /**
     * 特权定金经销商接单/下派
     * @param distributorId 经销商ID
     * @param orderCode 发货单id
     * @param storeId 店铺ID
     * @param remark 备注
     * @return 成功返回200
     */
    public Integer distribute(String distributorId,String orderCode, String storeId,String remark);
    
    /**
     * 特权定金编辑订单
     * @param orderCode 发货单id
     * @param status 状态
     * @param remark 备注
     * @return 成功返回200
     */
    public Integer updateOrder(String orderCode, String status,String remark);
    
    
    /**
     * 特权定金被动查询订单
     * @param phone 电话号码
     * @param storeId 门店ID
     * @param acceptorId 导购ID
     * @return 特权定金被动查询订单
     */
    public List<PrepayModel> get4Search(String phone);
    
    /**
     * 特权定金获取拒单原因
     * @param orderStyle 订单类型
     * @return 特权定金获取拒单原因
     */
    public List<PrepayModel> getReason4Order(String orderStyle);
    
    /**
     * 核销码获取单据明细
     * @param priDepositCode 核销码
     * @return 特权定金获取单据明细
     */
    public PrepayModel get4priDepositCode(String priDepositCode);
}
