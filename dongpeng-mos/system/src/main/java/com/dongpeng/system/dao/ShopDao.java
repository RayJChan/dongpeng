package com.dongpeng.system.dao;

import com.dongpeng.common.db.annotation.MyBatisDao;
import com.dongpeng.common.db.dao.BaseCrudDao;
import com.dongpeng.entity.system.Shop;
@MyBatisDao
public interface ShopDao extends BaseCrudDao<Shop> {

    /**
     * 根据门店编码查询门店
     * @param shopCode 门店编码
     * @return
     *
     */
    public Shop getByShopCode(String shopCode);

    /**
     * 根据门店ID查询门店
     * @param id 门店id
     * @return
     *
     */
    public Shop getById(Long id);

    /**
     * 更新门店
     * @param shop
     */
    void upShop(Shop shop);

    /**
     * 根据客编查询客户id
     * @param clientCode 客编
     * @return 返回id
     */
    Long getClientId(String clientCode);
}
