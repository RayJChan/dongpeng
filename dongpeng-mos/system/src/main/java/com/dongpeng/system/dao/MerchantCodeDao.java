package com.dongpeng.system.dao;

import com.dongpeng.common.db.annotation.MyBatisDao;
import com.dongpeng.common.db.dao.BaseCrudDao;
import com.dongpeng.entity.system.MerchantCode;
import com.dongpeng.entity.system.Product;

@MyBatisDao
public interface MerchantCodeDao extends BaseCrudDao<MerchantCode> {

    /**
     * 根据商品编码查询商品
     * @param productCode
     * @return
     */
    Long getByProductCode(String productCode);

    /**
     * 根据商家编码查询
     * @param merchantCode
     * @return
     */
    MerchantCode getMerchantCode(String merchantCode);

    /**
     * 根据id查询商家编码
     * @param id
     * @return
     */
    MerchantCode getByMerchantId(Long id);

    /**
     * 根据商品编码查询商品
     * @param productCode
     * @return
     */
    Product getProduct(String productCode);
}
