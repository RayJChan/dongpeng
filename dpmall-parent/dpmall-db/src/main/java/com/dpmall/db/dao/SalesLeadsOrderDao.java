package com.dpmall.db.dao;

import com.dpmall.db.bean.SalesLeadsOrderEntity;
import com.dpmall.db.bean.StoreOfSalEntity;
import com.dpmall.db.bean.po.SalesLeadsOrderPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SalesLeadsOrderDao {


    /**
     * 获取列表
     */
    List<SalesLeadsOrderEntity> getList4AgencyOrStore(@Param("isAgency") String isAgency,
                                                      @Param("agencyId") String agencyId,
                                                      @Param("storeId") String storeId,
                                                      @Param("listStatus") String listStatus,
                                                      @Param("pageNum") Integer pageNum,
                                                      @Param("pageSize") Integer pageSize,
                                                      @Param("search") String search,
                                                      @Param("statusSearch") String statusSearch);


    /**
     * 获取列表数量
     */
    String getListCount(@Param("isAgency") String isAgency,
                        @Param("agencyId") String agencyId,
                        @Param("storeId") String storeId,
                        @Param("listStatus") String listStatus,
                        @Param("search") String search,
                        @Param("statusSearch") String statusSearch);



    /**
     * 编辑留资单
     *  entity
     *  1为成功，0为失败
     */
    int edit(@Param("entity") SalesLeadsOrderPo entity);


    /**
     * 编辑留资线索
     *
     * @param entity
     * @return 1为成功，0为失败
     */
    int editSaleLeads(@Param("entity") SalesLeadsOrderPo entity);

    /***
     * 获取留资单详情
     * @return
     */
    public SalesLeadsOrderEntity getDetails(@Param("id") String id );


    /**
     * 获取上一个操作状态
     */
    String getLastStatus (@Param("id")String salesLeadsOrderId);

    /**
     * 判断是否经销商
     */
    String getIsAgency (@Param("id")String salesLeadsOrderId);


    /**
     * 过去6天没有操作的订单数量
     */
    int getCountUnOperate (@Param("agencyId")String agencyId);


    /**
     * 过去6天没有操作的订单数量
     */
    StoreOfSalEntity getStoreBySal (@Param("salPk")String salPk);






}
