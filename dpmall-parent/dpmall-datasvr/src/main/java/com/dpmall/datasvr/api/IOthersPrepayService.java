package com.dpmall.datasvr.api;

import com.dpmall.model.OrderDetailsModel;
import com.dpmall.model.in.OtherPrePayInfoModelIn;
import com.dpmall.model.in.PrepayModelIn;
import com.dpmall.model.prePay.*;

import java.util.List;
import java.util.Map;

/**
 * 其他特权订金
 */
public interface IOthersPrepayService {

    /**
     * 获取列表（经销商）
     */
    List<OthersPrePayModel> getListOfAgency(String agencyId, String status, int pageNum, int pageSize, String search);


    /**
     * 获取列表（门店）
     */
    List<OthersPrePayModel> getListOfStore(String storeId, String status, int pageNum, int pageSize, String search);

    /**
     * 获取列表数量 （经销商）
     */
    OthersPrepayListCountModel getListCountOfAgency(String agencyId);

    /**
     * 获取列表数量 （门店）
     */

    OthersPrepayListCountModel getListCountOfStore(String storeId);

    /**
     * 批量接单(经销商)
     */
    int agencyAccept(List<String> prepayId, String storeId,String operatorBy);

    /**
     * 接单(门店)
     */
    int storeAccept(List<String> prepayId,String operatorBy);

    /**
     * 撤回 (只有经销商能操作)
     */
    int withdraw(String prepayId,String operatorBy);

    /**
     * 获取详情
     */
    OthersPrePayDetailModel getDetails(String prepayId);

    /**
     * 更新客户信息
     */
    int updateCustomInfo(PrepayModelIn modelIn,String operatorBy);

    /**
     * 更新订单进度
     */
     int updateOrderProgress(String statusName, String prepayId, String remark, String operatorBy, String failTypeId);


    /**
     * 填写订单信息(提交)
     */
    public int updatePrePayOrderInfo(OtherPrePayInfoModelIn otherPrePayInfoModelIn,String operatorBy);

    /**
     * 填写订单信息(只保存不提交)
     */
    public int savePrePayOrderInfo(OtherPrePayInfoModelIn otherPrePayInfoModelIn);


    /**
     * 添加备注
     */
    public String addRemarks(String prePayId, String agencyRemark, String storeRemark, String operatorBy);


    /**
     * 查看更新状态时的备注
     **/
    public String getUpdateStatusRemark(String operationId) ;


    /**判断核销码**/
    public Boolean judgeWriteOffCode(String prePayId,String writeOffCode);


    /**
     * 将状态回写至商城
     **/
    public int updatePrePayToDpmall(String prePayId);

    /**
     * 根据发货单id获取其他id
     **/
    public String getIdByConsignmentId(String prePayId);

}
