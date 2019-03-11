package com.dpmall.datasvr.api;

import com.dpmall.model.*;
import com.dpmall.model.in.SalOrderInfoModelIn;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * 销售线索类服务接口
 *
 * @author river
 * @since 2017-07-10
 */
public interface ISaleLeadsOrderService {

    /**
     * 获取列表-经销商
     */
    public List<SaleLeadsOrderModel> getList4Agency(String agencyId, String listStatus, Integer pageNum, Integer pageSize, String search,String statusSearch);

    /**
     * 获取列表--门店
     */
    public List<SaleLeadsOrderModel> getList4Store(String storeId, String listStatus, Integer pageNum, Integer pageSize, String search,String statusSearch);

    /**
     * 获取列表数量-经销商
     */
    public SalOrderListCountModel getListConut4Agency(String agencyId);

    /**
     * 获取列表数量--门店
     */
    public SalOrderListCountModel getListConut4Store(String storeId);


    /***
     * 获取留资订单详情
     */
    public SaleLeadsOrderDetailsModel getDetails(String saleLeadsOrderId,String operatorById);

    /**
     * 拒单
     */
    public Integer reject(List<String> saleLeadsOrderIdList, String rejectType, String rejectRemark, String operatorBy);

    /**
     * 经销商批量接单
     */
    public Integer agencyAccept(List<String> saleLeadsOrderId, String stroeId, String agencyRemark, String operatorBy);

    /**
     * 门店批量接单
     */
    public Integer storeAccept(List<String> saleLeadsOrderIds, String storeRemark, String operatorBy);


    /**
     * 添加备注
     */
    public String addRemarks(String saleLeadsOrderId, String agencyRemark, String storeRemark, String operatorBy);

    /**
     * 更新客户信息
     */
    public int updateCustomerInfo(SaleLeadsOrderModel model, String operatorBy);

    /**
     * 更新订单进度
     */
    public int updateOrderProgress(String statusName, Long saleLeadsOrderId, String remark, String operatorBy, String failType);


    /**
     * 填写订单信息(提交)
     */
    public int updateSalOrderInfo(SalOrderInfoModelIn salOrderInfoModelIn);

    /**
     * 填写订单信息(只保存不提交)
     */
    public int saveSalOrderInfo(SalOrderInfoModelIn salOrderInfoModelIn);


    /**
     * 查看更新状态时的备注
     */
    public String getUpdateStatusRemark(String operationId);


    /**
     * 查询历史数据 oms
     **/
    List<SalesLeadsOperationModel> getHistory4Oms(String salOrderCode, String agencyCode, String startTime, String endTime, Integer pageNum, Integer pageSize);


    /**
     * 查询留资单商品明细 oms
     **/
    List<SalOrderItem4OmsModel> getSalOrderItem4Oms(String salesLeadsOrderId);

    /**
     * 查询历史数据 oms
     **/
    String exportHistory4Oms(OutputStream out, String salOrderCode, String agencyCode, String startTime, String endTime) throws IOException;


    /**
     * 查询操作状态时备注 oms
     **/
    Map<String,String> getOperateRemark(List<String> orderCode) ;


    /**
     * 过去6天没有操作的订单数量
     **/
    int getCountUnOperate(String agency) ;



}
