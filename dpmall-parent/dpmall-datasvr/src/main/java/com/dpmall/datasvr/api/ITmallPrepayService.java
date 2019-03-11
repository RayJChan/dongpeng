package com.dpmall.datasvr.api;

import com.dpmall.model.prePay.TmallPrePayDetailModel;
import com.dpmall.model.prePay.TmallPrePayModel;
import com.dpmall.model.prePay.TmallPrepayListCountModel;

import java.util.List;

/**
 * 天猫特权订金
 */
public interface ITmallPrepayService {

    /**
     * 获取列表（经销商）
     */
    List<TmallPrePayModel> getListOfAgency(String agencyId, String status, int pageNum, int pageSize, String search);


    /**
     * 获取列表（门店）
     */
    List<TmallPrePayModel> getListOfStore(String storeId, String status, int pageNum, int pageSize, String search);

    /**
     * 获取列表数量 （经销商）
     */
    TmallPrepayListCountModel getListCountOfAgency (String agencyId);

    /**
     * 获取列表数量 （门店）
     */

    TmallPrepayListCountModel getListCountOfStore (String storeId);

    /**
     * 批量接单
     */
    int accept (List<String> prepayId,String stroeId,String isAgency);

    /**
     * 撤回
     */
    int withdraw (String prepayId,String isAgency);

    /**
     * 获取详情
     */
    TmallPrePayDetailModel getDetails (String prepayId);

    /**
     * 添加备注
     */
    public String addRemarks(String prePayId, String agencyRemark, String storeRemark, String operatorBy);


    /**
     * 根据发货单id获取o2o id
     **/
    String getO2oIdByConsignmentId(String prePayId);




}
