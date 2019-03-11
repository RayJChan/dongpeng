package com.dpmall.db.dao;

import com.dpmall.db.bean.TmallPrePayDetailEntity;
import com.dpmall.db.bean.TmallPrePayEntity;
import com.dpmall.db.bean.po.PrepayPo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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
									  @Param("search") String search);

	/**
	 * 获取列表
	 */

	 String getListCount (@Param("agencyId") String agencyId,
						  @Param("storeId") String storeId,
						  @Param("isAgency") String isAgency,
						  @Param("listStatus") String listStatus,
						  @Param("search") String search);

	/**
	 * 查询经销商名字
	 */
	@Select("select p_name from agency where pk = #{pk}")
	String getAgencyNameByPk (@Param("pk")String pk);


    /**
     * 更新o2oConsignment
     */
	int editO2oConsignment (@Param("po") PrepayPo po);

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


}
