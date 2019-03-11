package com.dpamll.datasvr;

import com.alibaba.fastjson.JSON;
import com.dpmall.common.SpringTestCase;
import com.dpmall.datasvr.api.IProductService;
import com.dpmall.model.ProductModel;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductInfoTest extends SpringTestCase {
    private static final Logger LOG = LoggerFactory.getLogger(ProductInfoTest.class);
    
    @Autowired
    private IProductService productService;
    
    @Test
    public void testSearch(){
        ProductModel result= productService.getInfoByProductCode("FG805343_A");
        LOG.info("\nresult:\n" + JSON.toJSONString(result)+"\n");
    }

    @Test
    public void testQuartz() throws InterruptedException {
        Thread.sleep(10000000);


        LOG.info("\nresult:\n" + JSON.toJSONString("Test quartz")+"\n");
    }

    @Test
    public void test() throws InterruptedException {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+ "★★★★★★★★★★★");
        LOG.info("\nresult:\n" + JSON.toJSONString(productService.getProductCode())+"\n");
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+ "★★★★★★★★★★★");


    }
}
