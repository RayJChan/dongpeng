package com.dpamll.datasvr;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dpmall.common.SpringTestCase;
import com.dpmall.datasvr.api.IProductStatisticService;
import com.dpmall.model.ProductStatisticModel;

public class ProductStatisticServiceFacadeTestCase extends SpringTestCase {
    private static final Logger LOG = LoggerFactory.getLogger(ProductStatisticServiceFacadeTestCase.class);
    
    @Autowired
    private IProductStatisticService ProductStatisticServiceFacade;
    
    @Test
    public void testSearch(){
    	List<ProductStatisticModel> result = ProductStatisticServiceFacade.search("1234567", "2017-05-01 11:11:11", "2018-05-01 11:11:11", 0,50);
        LOG.info("result:" + JSON.toJSONString(result));
    }
}
