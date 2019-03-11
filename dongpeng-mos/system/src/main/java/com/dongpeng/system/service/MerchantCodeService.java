package com.dongpeng.system.service;

import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.BeanUtils;
import com.dongpeng.entity.system.MerchantCode;
import com.dongpeng.entity.system.Product;
import com.dongpeng.system.dao.MerchantCodeDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MerchantCodeService extends BaseCrudService<MerchantCodeDao, MerchantCode> {


    /**
     * 新增一条数据
     * @param merchantCode
     * @return
     * @throws OptimisticLockException
     */
    @Transactional(readOnly = false)
    public ResponseResult insert(MerchantCode merchantCode) throws OptimisticLockException {
        Long productId = dao.getByProductCode(merchantCode.getProductCode());
        if(productId == null){
            return ResponseResult.failByParam("商品编码:["+merchantCode.getProductCode()+"]无效");
        }
        MerchantCode flag = dao.getMerchantCode(merchantCode.getMerchantCode());
        if(flag != null){
            return ResponseResult.failByParam("商家编码:["+merchantCode.getMerchantCode()+"]已存在");
        }
        merchantCode.setProductId(productId);
        int rs=this.save(merchantCode);
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("保存失败");
        }
    }


    /**
     * 修改数据
     * @param merchantCode
     * @return
     */
    @Transactional(readOnly = false)
    public ResponseResult update(MerchantCode merchantCode) throws Exception{
        MerchantCode temp = dao.getByMerchantId(merchantCode.getId());
        if(temp == null){
            return ResponseResult.failByParam("id:["+merchantCode.getId()+"]无效");
        }
        Long productId = dao.getByProductCode(merchantCode.getProductCode());
        if(productId == null){
            return ResponseResult.failByParam("商品编码:["+merchantCode.getProductCode()+"]无效");
        }
        merchantCode.setProductId(productId);
        BeanUtils.copyBeanNotNull2Bean(merchantCode, temp);//将非NULL值覆盖temp中的值
        int rs=this.save(merchantCode);
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("保存失败");
        }
    }

    /**
     * 保存导入数据
     * @param merchantCode
     * @return
     */
    public int saveForExcel(MerchantCode merchantCode) {
        try {
            MerchantCode flag = dao.getMerchantCode(merchantCode.getMerchantCode());
            merchantCode.setCurrentUser();
            if (flag != null) {
                merchantCode.preUpdate();
                dao.update(merchantCode);
                return 1;
            } else {
                merchantCode.preInsert();
                dao.insert(merchantCode);
                return 1;
            }
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public String createDataScopeSql(MerchantCode entity) {
        return null;
    }

    /**
     * 根据code查询商品
     * @param productCode
     * @return
     */
    public Product getProduct(String productCode) {
        return dao.getProduct(productCode);
    }
}
