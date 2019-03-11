package com.dpmall.datasvr.api;

import java.util.List;
import java.util.Map;

import com.dpmall.common.TimeScope;
import com.dpmall.model.SaleLeadsModel;
import com.dpmall.param.SaleLeadStatisticParam;

/**
 * 销售线索类服务接口
 * @author river
 * @since 2017-07-10
 */
public interface ISaleLeadsService {
	
	/**
	 * <p>
	 * 根据sealLeadsId 获取详情
	 * @param saleLeadsId
	 * @return
	 */
	public SaleLeadsModel getSaleLeads(String saleLeadsId);
	
	/**
     * 实物类门店订单状态条数
     * @param storeId 店铺ID
     * @param acceptorId 接单人ID
     * @param status 状态
     * @return 经销商待分配的实物订单数
     */
	public int get2StoreCount(String storeId,String acceptorId,String status);
	
	/**
     * 实物类经销商订单状态条数
     * @param distributorId 经销商ID
     * @param status 状态
     * @return 经销商待分配的实物订单数
     */
    public int get2DistributeCount(String distributorId,String status);
	
	/**
	 * 经销商获取待分配的销售线索
	 * @param distributorId 经销商Id
	 * @param startNum 上一次加载的位移
	 * @param pageSize 页的大小
	 * @return 经销商获取待分配的销售线索列表
	 */
    public List<SaleLeadsModel> getOnePage4Distribute(String distributorId,Integer startNum, Integer pageSize,String search);
    
    
    
    /**
     * 经销商下派到店铺
     * @param distributorId 经销商ID
     * @param saleLeadsId 销售线索ID
     * @param shopId 店铺ID
     * @return
     */
    public int distribute(String distributorId,String saleLeadsId, String shopId,String agencyRemark);
    
    /**
     * 经销商批量下派到店铺
     * @param saleLeadsId2shopId 线索ID=>shopId
     * @return
     */
    public int distributeBatch(String distributorId, Map<String,String> saleLeadsId2shopId,String operatorBy,String agencyRemark );
    
    /**
     * 经销商拒单
     * @param distributorId 经销商ID
     * @param saleLeadsId 销售线索ID
     * @param rejectType 拒单类型
     * @param rejectRemark 拒单备注
     * @return
     */
    public int reject(String distributorId, String saleLeadsId, String rejectType, String rejectRemark,String operatorBy);
    
    /**
     * 经销商拒单
     * @param distributorId 经销商ID
     * @param saleLeadsId 销售线索ID
     * @param rejectType 拒单类型
     * @param rejectRemark 拒单备注
     * @return
     */
    public int rejectBatch(String distributorId, List<String> saleLeadsIdList, String rejectType, String rejectRemark,String operatorBy);
    
    
    /**
     * 经销商获取待跟进的一页线索订单数据
	 * @param distributorId 经销商Id
	 * @param startItemId 上一次加载的最后项ID
	 * @param pageSize 页的大小
     * @return
     */
    public List<SaleLeadsModel> getOnePage4Followup(String distributorId,Integer startNum, Integer pageSize,String search,String statusSearch);
    
    /**
     * 根据条件查询已完结的销售线索订单
     * @param distributorId 经销商Id
     * @param distributeTime 订单下派时间
     * @param storeId
     * @param saleLeadId
     * @param clientName
     * @param storeName
     * @param clientTel
     * @param startNum
     * @param pageSize
     * @param acceptorId 导购员ID
     * @return
     */
    public List<SaleLeadsModel> getOnePageClosedSaleLeads(String distributorId,TimeScope distributeTime, String storeId,String saleLeadId, String clientName,String clientTel,String storeName,String acceptorId,Integer startNum, Integer pageSize,String search);
    
    
    /**
     * 根据条件查询已完结的销售线索订单
     * @param distributorId 经销商Id
     * @param distributeTime 订单下派时间
     * @param storeId
     * @param saleLeadId
     * @param clientName
     * @param storeName
     * @param clientTel
     * @param startNum
     * @param pageSize
     * @param acceptorId 导购员ID
     * @return
     */
    public List<SaleLeadsModel> getOnePageFinishedSaleLeads(String distributorId,String storeId,String acceptorId,String search,Integer startNum, Integer pageSize);
    
    
    
	/**
	 * 店铺获取待接单的销售线索
	 * @param storeId 店铺ID
	 * @param startNum 上一次加载的最后项位移
	 * @param pageSize 页的大小
	 * @return 店铺获取待接单的销售线索列表
	 */
    public List<SaleLeadsModel> getOnePage4Accept(String storeId,Integer startNum, Integer pageSize,String search);
    
    /**
     * 获取店铺待接单的线索数
     * @param storeId 经销商ID
     * @return 经销商待分配的线索数
     */
    public Integer get2AcceptCount(String storeId);
    
    
    
    /**
     * 导购员接单
     * @param acceptorId 导购员ID
     * @param saleLeadsId 线索ID
     * @return
     */
    public int accept(String acceptorId, String saleLeadsId,String operatorBy);
    
    /**
     * 导购员批量接单
     * @param acceptorId 导购员ID
     * @param saleLeadsId 线索ID
     * @return
     */
    public int acceptBatch(String acceptorId, List<String> saleLeadsId,String operatorBy);
    
    /**
     * 编辑销售线索订单信息
     * @param model
     * @return
     */
    public int edit(SaleLeadsModel model,String operatorBy);
 
    
    /**
     * 获取导购员已接单的一页销售线索信息
     * @param acceptorId 导购员ID
     * @param startNum 上一次加载的最后项位移
     * @param pageSize 页大小
     * @return
     */
    public List<SaleLeadsModel> getOnePage4Acceptor2Followup(String acceptorId,Integer startNum, Integer pageSize,String search,String statusSearch);
    
    /**
     * 获取导购员已结单的一页销售线索信息
     * @param acceptorId 导购员ID
     * @param startItemId 上一次加载的最后项位移
     * @param pageSize 页大小
     * @return
     */
    public List<SaleLeadsModel> getOnePage4AcceptorClosed(String acceptorId,Integer startNum, Integer pageSize,String search);
    
    /**
     * 获取根据form条件查询一页的成功结单的数据
     * @param form
     * @param startNum
     * @param pageSize
     * @return
     */
    public List<SaleLeadsModel> getOnePageSuccessOrders(SaleLeadStatisticParam form,Integer startNum, Integer pageSize);
    
    
    /**
     * 获取根据form条件查询成功结单的金额
     * @param form
     * @return
     */
    public Double getSuccessOrdersTtlAmount(SaleLeadStatisticParam form);
}
