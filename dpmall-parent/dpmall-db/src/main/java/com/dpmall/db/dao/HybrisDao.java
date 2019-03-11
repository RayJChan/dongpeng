package com.dpmall.db.dao;

import com.dpmall.db.bean.OperatorRoleEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * Hybris相关dao
 */
public interface HybrisDao {

    /**查询clientId**/
    @Select("SELECT P_CLIENTID FROM OAUTHCLIENTDETAILS WHERE 1 = 1 AND P_CLIENTID LIKE 'mobile%'")
    List<String> getClientId();



}
