package com.dpmall.db.dao;

import com.dpmall.db.bean.*;
import com.dpmall.db.bean.po.PrepayPo;
import com.dpmall.db.bean.po.TmallOrderGoodsPo;
import com.dpmall.db.bean.po.TmallOrderItemsPo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Set;

public interface TmallPrepayDao {

	/**
	 * 获取列表
	 */

	 List<TmallPrePayEntity> getList (@Param("agencyId") String agencyId,
									  @Param("storeId") String storeId,
									  @Param("isAgency") String isAgency,
									  @Param("listStatus") String listStatus,
									  @Param("pageNum") int pageNum,
									  @Param("pageSize") int pageSize,
									  @Param("search") String search,
									  @Param("orderId") String orderId,
									  @Param("clientName") String clientName,
									  @Param("clientTel") String clientTel,
									  @Param("staRevisitTime") String staRevisitTime,
									  @Param("endRevisitTime") String endRevisitTime
	 );

	/**
	 * 获取列表数量
	 */

	 String getListCount (@Param("agencyId") String agencyId,
						  @Param("storeId") String storeId,
						  @Param("isAgency") String isAgency,
						  @Param("listStatus") String listStatus,
						  @Param("search") String search,
						  @Param("orderId") String orderId,
						  @Param("clientName") String clientName,
						  @Param("clientTel") String clientTel,
						  @Param("staRevisitTime") String staRevisitTime,
						  @Param("endRevisitTime") String endRevisitTime
	 );

	/**
	 * 查询经销商名字
	 */
	@Select("select p_name from agency where pk = #{pk}")
	String getAgencyNameByPk (@Param("pk")String pk);


    /**
     * 更新o2oConsignment
     */
	int editXiaoTitle (@Param("po") PrepayPo po);

	/**
	 * 拒单
	 */
	int withdraw (@Param("po") PrepayPo po);

    /**
     * 更新Consignment
     */
    int editConsignment (@Param("po") PrepayPo po);




	/***
	 * 获取详情
	 */

	TmallPrePayDetailEntity getDetails(@Param("prePayId") String prePayId) ;

	/**
	 * 根据发货单id获取o2o id
	 **/
	@Select("SELECT O.PK FROM CONSIGNMENTS C LEFT JOIN O2OCONSIGNMENTITEMS O ON o.P_CONSIGNMENT = c.PK WHERE C.PK = #{prePayId }")
	String getO2oIdByConsignmentId(@Param("prePayId") String prePayId);

	/**
	 * 查询第三方状态
	 **/
	@Select("SELECT o.p_sfstatus FROM CONSIGNMENTS c LEFT JOIN orders o ON o.pk = c.P_ORDER WHERE   C.PK = #{prePayId }")
	String getSfStatusByPrePayId(@Param("prePayId") String prePayId);



	public int insert(@Param(value="template")TmallOperationEntity template);

	/**
	 * 获取订单商品
	 */
	public List<TmallOrderProductModeIn> getTmallOrderProductList (@Param("orderId")String tmallOrderId);


	/**
	 * 删除商品（软删除）
	 */
	public int delete (@Param("entity")List<String> entity);

	/**
	 * 获取销售订单
	 */
	String getOrderDetails(String prePayId);

	/**
	 * 获取经销商状态
	 */
	String getIsAgency(@Param("tmallOrderId")String tmallOrderId);

	/**
	 * 更新订单商品
	 */
	int updateTmallOrderProduct(@Param("entity")TmallOrderProductModeIn tmallOrderProductModeIn);

	/**
	 *添加商品
	 */
	int addTmallOrderProduct(@Param("entity")TmallOrderGoodsPo itemsPo,@Param("tmallId")String id);

    /**
     * 获取商品
     */
    TmallOrderProductModeIn getProduct(@Param("shangpId")String productId);
}
