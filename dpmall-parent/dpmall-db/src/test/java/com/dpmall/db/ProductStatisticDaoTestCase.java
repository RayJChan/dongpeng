package com.dpmall.db;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dpmall.common.SpringTestCase;
import com.dpmall.db.bean.ProductStatisticEntity;
import com.dpmall.db.dao.ProductStatisticDao;

public class ProductStatisticDaoTestCase extends SpringTestCase {
    private static final Logger LOG = LoggerFactory.getLogger(ProductStatisticDaoTestCase.class);
    
    @Autowired
    private ProductStatisticDao productStatisticDao;
    
    
    @Test
    public void testInsert(){
    	ProductStatisticEntity prod = new ProductStatisticEntity();
    	prod.createTime = new Date();
    	prod.fromTime = new Date();
    	prod.endTime = new Date();
    	prod.productCode = "1234567";
    	prod.productId = 1l;
    	prod.saleTotal = 0l;
    	prod.totalAccess = 0l;
    	prod.totalAmount = 0l;
    	prod.totalDeals = 0l;
    	prod.updateTime = new Date();
    	
    	productStatisticDao.insert(prod);
    }

    @Test
    public void testSearch(){
    	Page page = new Page(0, 5);
    	List<ProductStatisticEntity> result = productStatisticDao.search("1234567", new Timestamp(new Date().getTime()-3600*1000*24), new Timestamp(new Date().getTime()), page);
    	LOG.info(JSON.toJSONString(result));
    }
    
    @Test
    public void testUpdateByTemplate(){
    	ProductStatisticEntity prod = new ProductStatisticEntity();
    	prod.id = 1l;
    	prod.saleTotal = 1000l;
    	prod.totalAccess = 5000l;
    	productStatisticDao.updateByTemplate(prod);
    }
	
}
