package com.dpmall.datasvr.api;

import java.util.List;

import com.dpmall.common.TimeScope;
import com.dpmall.model.OrderModel;
import com.dpmall.model.in.OrderDistributeModelIn;
import com.dpmall.model.in.OrderEditStatusModelIn;
import com.dpmall.model.in.OrderShippedModelIn;

/**
 * 实物订单服务接口
 * @author river
 * @since 2017-07-10
 */
public interface IOrderService {
	/**
	 * 实物类经销商订单状态列表
	 */
    public List<OrderModel> getOnePage4Distribute(String distributorId,String status,String search,Integer offset, Integer pageSize,String statusSearch);
    
    
    /**
     * 实物类经销商订单状态条数
     */
    public Integer get2DistributeCount(String distributorId,String status);


    /**
     * 经销商下接单
     */
    public int acceptOfAgency(OrderDistributeModelIn modelIn);


    /**
     * 经销商拒单
     */
    public int rejectOfAgency(OrderDistributeModelIn modelIn);

    /**
     * 导购员接单
     */
    public int acceptOfStore(OrderDistributeModelIn modelIn);


    /**
     * 经销商获取待跟进的一页实物订单数据
	 * @param distributorId 经销商Id
	 * @param startItemId 上一次加载的最后项ID
	 * @param pageSize 页的大小
     * @return 经销商待跟进的一页实物订单数据
     */
    public List<OrderModel> getOnePage4Followup(String distributorId,Integer offset, Integer pageSize);
    
    /**
     * 根据条件查询已发货的实物订单
     * @param distributorId 经销商Id
     * @param distributeTime 订单下派时间
     * @param storeId
     * @param orderCode
     * @param clientName
     * @param clientTel
     * @param startNum
     * @param pageSize
     * @return 根据条件查询已发货的实物订单
     */
    public List<OrderModel> getOnePageClosedOrder(String distributorId,TimeScope distributeTime, String storeId,String orderCode, String clientName,String clientTel,Integer startNum, Integer pageSize);
    
    
	/**
	 * 实物类门店订单状态列表
	 * @param storeId 店铺ID
	 * @param status 状态
	 * @param offset 上一次加载的位移
	 * @param pageSize 页的大小
	 * @return 实物类门店订单状态条数
	 */
    public List<OrderModel> getOnePage4StoreId(String storeId,String status,String acceptorId,String search,Integer offset, Integer pageSize,String statusSearch);
    
    /**
     * 实物类门店订单状态条数
     * @param storeId 经销商ID
     * @param status 状态
     * @return 经销商待分配的实物订单数
     */
    public Integer get2StoreCount(String storeId,String acceptorId,String status);
    
    /**
     * 实物类导购员订单状态条数
     * @param acceptorId 导购员ID
     * @param status 状态
     * @return 实物类导购员订单状态条数
     */
    public Integer get2AcceptorCount(String acceptorId,String status);
    
    

    /**
     * 确认发货
     * @param model
     * @return 成功返回200
     */
    public int deliver(String orderCode);
 
    
    /**
     * 实物类导购员订单状态列表
     * @param acceptorId 导购员ID
     * @param status 状态
     * @param startItemId 上一次加载的最后项ID
     * @param pageSize 页大小
     * @return 导购员已接单的一页实物订单信息
     */
    public List<OrderModel> getOnePage4AcceptorId(String acceptorId,String status,Integer startNum, Integer pageSize);
    
    /**
     * 获取导购员已结单的一页实物订单信息
     * @param acceptorId 导购员ID
     * @param startItemId 上一次加载的最后项ID
     * @param pageSize 页大小
     * @return 导购员已结单的一页实物订单信息
     */
    public List<OrderModel> getOnePage4AcceptorClosed(String acceptorId,Integer startItemId, Integer pageSize);
    
    /**
     * 实物类获取单据明细
     * @param consignmentId 发货单ID
     * @return 订单详情
     */
    public OrderModel getOrderDetails(String consignmentId);
    
    /**
     * 实物类获取单据明细
     * @param consignmentId 发货单ID
     * @return 订单详情
     */
    public OrderModel getReturnRequestDetails(String consignmentId);


    /**
     * 编辑状态
     * @param modelIn
     * @return
     */
    public int editStatus (OrderEditStatusModelIn modelIn);

//    /**
//     * 发货
//     */
//    public int shipped (OrderShippedModelIn modelIn);

}
