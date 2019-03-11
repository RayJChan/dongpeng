package com.dongpeng.system.service;

import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.entity.system.Shop;
import com.dongpeng.system.dao.ShopDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional(readOnly = true)
public class ShopService extends BaseCrudService<ShopDao,Shop> {

    @Autowired
    private UserService userService;

    /**
     * 根据门店编码查询门店
     * @param shopCode 门店编码
     * @return
     */
    public Shop getByShopCode(String shopCode){
        return dao.getByShopCode(shopCode);
    }
    /**
     * 更新门店
     * @param shop
     */
    @Transactional(readOnly = false)
    public void upShop(Shop shop){
        dao.upShop(shop);
    }

    /**
     * 根据门店ID查询门店
     * @param id 门店id
     * @return
     */
    public Shop getById(Long id){
        return dao.getById(id);
    }

    @Transactional(readOnly = false)
    public int saveForExcel(Shop shop) throws Exception {
        try {
            if(StringUtils.isNotBlank(shop.getShopCode())) {
                Shop byShopCode = dao.getByShopCode(shop.getShopCode());
                shop.setCurrentUser();
                if (null != byShopCode) {
                    shop.preUpdate();
                    dao.update(shop);
                    return 1;
                } else {
                    shop.preInsert();
                    dao.insert(shop);
                    return 1;
                }
            }
        }catch (Exception e){
            return 0;
        }
       return 0;
    }

    @Override
    public String createDataScopeSql(Shop entity) {
        return null;
    }

    public Long getClientId(String clientCode) {
        return dao.getClientId(clientCode);
    }
}
