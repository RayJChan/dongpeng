package com.dpmall.db.dao;

import com.dpmall.db.bean.OperatorRoleEntity;
import com.dpmall.db.bean.po.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * 工具查询方法
 */
public interface UtilsDao {

    /**查询省pk**/
    @Select("SELECT  r.itempk FROM LANGUAGES l, REGIONSLP r WHERE NVL (l.p_ISOCODE, 'zh') = 'zh' AND r.LANGPK = l.pk AND r.P_NAME = #{regionName}")
    String getPkByRegionName(@Param("regionName")String regionName);

    /**查询市pk**/
    @Select("SELECT c.itempk FROM LANGUAGES l, CITIESLP c WHERE NVL (l.p_ISOCODE, 'zh') = 'zh' AND c.LANGPK = l.pk AND c.P_NAME = #{cityName}")
    String getPkByCityName(@Param("cityName")String cityName);

    /**查询区pk**/
    @Select("SELECT ad.itempk FROM LANGUAGES l, districtslp ad WHERE NVL (l.p_ISOCODE, 'zh') = 'zh' AND ad.LANGPK = l.pk AND ad.P_NAME =  #{districtName}")
    String getPkByDistrictName(@Param("districtName")String districtName);

    /**
     * 根据状态名字查找pk
     */
    @Select("SELECT pk FROM ( SELECT pk, SEQUENCENUMBER FROM ENUMERATIONVALUES WHERE 1 = 1 AND P_EXTENSIONNAME IN ( 'dongpengcore', 'basecommerce' ) AND code = #{statusName} ORDER BY SEQUENCENUMBER DESC ) r1 WHERE ROWNUM = 1")
    String getStatusPk (@Param("statusName") String statusName);


    /**获取用户角色**/
    OperatorRoleEntity getOperatorByRole (@Param("OperatorById") String OperatorById);


    /**获取经销商名字**/
    @Select("SELECT P_NAME from AGENCY WHERE pk  = #{agencyId}")
    String getNameAgencyId (@Param("agencyId") String agencyId);


    /**获取经销商名字**/
    @Select("select 1111")
    String testconnect ();

    /**获取操作人ID**/
    String getOperatorBy(@Param("userId")String operatorBy);
}
