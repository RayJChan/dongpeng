package com.dpmall.db.dao;

import com.dpmall.db.bean.OthersPrePayDetailEntity;
import com.dpmall.db.bean.OthersPrePayEntity;
import com.dpmall.db.bean.TmallPrePayDetailEntity;
import com.dpmall.db.bean.TmallPrePayEntity;
import com.dpmall.db.bean.po.PrepayPo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 其他特权订金
 */
public interface OthersPrepayDao {

	/**
	 * 获取列表
	 */

	 List<OthersPrePayEntity> getList(@Param("agencyId") String agencyId,
									  @Param("storeId") String storeId,
									  @Param("isAgency") String isAgency,
									  @Param("listStatus") String listStatus,
									  @Param("pageNum") int pageNum,
									  @Param("pageSize") int pageSize,
									  @Param("search") String search);

	/**
	 * 获取列表
	 */

	 String getListCount(@Param("agencyId") String agencyId,
                         @Param("storeId") String storeId,
                         @Param("isAgency") String isAgency,
                         @Param("listStatus") String listStatus,
                         @Param("search") String search);

	/**
	 * 查询经销商名字
	 */
	@Select("select p_name from agency where pk = #{pk}")
	String getAgencyNameByPk(@Param("pk") String pk);


    /**
     * 更新o2oConsignment
     */
	int editO2oConsignment(@Param("po") PrepayPo po);

    /**
     * 更新Consignment
     */
    int editConsignment(@Param("po") PrepayPo po);

    /**
     * 根据状态名字查找pk
     */
    @Select("select pk from ENUMERATIONVALUES  where P_EXTENSIONNAME = 'dongpengcore' and code = #{statusName}")
    String getStatusPk(@Param("statusName") String statusName);


	/***
	 * 获取详情
	 */
	OthersPrePayDetailEntity getDetails(@Param("prePayId") String prePayId) ;


	/**
	 * 撤单o2oConsignment
	 */
	int withdrawO2oConsignment(@Param("po") PrepayPo po);

	/**
	 * 撤单Consignment
	 */
	int withdrawConsignment(@Param("po") PrepayPo po);

	/***
	 * 更新地址
	 */
	int updateAddress(@Param("po") PrepayPo po);

	/**根据consignmentPk 查询 addressPk**/
	@Select("SELECT c.P_SHIPPINGADDRESS from CONSIGNMENTS c where c.pk = #{consignmentPk}")
	String getAddressId (@Param("consignmentPk") String consignmentPk);


	/**
	 * 获取上一个操作状态
	 */
	@Select("SELECT C.P_LASTPREPAYSTATUS from CONSIGNMENTS c where c.pk = #{consignmentPk}")
	String getLastStatus(@Param("consignmentPk") String consignmentPk);


	/**
	 * 判断是否经销商
	 */
	@Select("SELECT P_ISDELIVERYSELF FROM O2OCONSIGNMENTITEMS WHERE P_CONSIGNMENT = #{ prePayId }")
	String getIsAgency(@Param("prePayId") String prePayId);

	/**判断核销码是否正确**/
	@Select("SELECT PR.P_PRIDEPOSITCODE FROM PRIDEPOSITINFO pr WHERE pr.P_CONSIGNMENT = #{ prePayId }")
	String getWriteOffCode(@Param("prePayId") String prePayId);

	/**判断核销码是否正确**/
	OthersPrePayEntity getWriteCodeAndOrderCode(@Param("prePayId") String prePayId);

	/**核销**/
	int writeOff (@Param("prePayId") String prePayId,@Param("operatorBy") String operatorBy);

	/**更新订单状态信息**/
	int updateOrders (@Param("prePayId") String prePayId,@Param("statusPk") String statusPk);

	/**
	 * 根据发货单id获取其他id
	 **/
	@Select("SELECT O.PK FROM CONSIGNMENTS C LEFT JOIN ORDERS O ON O.PK = C.P_ORDER WHERE C.PK = #{prePayId }")
	String getIdByConsignmentId(@Param("prePayId") String prePayId);


}
