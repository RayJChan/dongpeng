package com.dpmall.dubbo.facade;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dpmall.dubbo.api.IProductService;
import com.dpmall.model.ProductModel;

/**
 * 获取产品信息
 * @author cwj
 * @date 2017-12-26
 */
public class ProductServiceFacade implements IProductService {
	
	private final Logger LOG = LoggerFactory.getLogger(ProductServiceFacade.class);
	
	@Autowired
	private IProductService productService;


	public List<ProductModel> getProductInfo(int startNum, int pageSize) {
		if (LOG.isInfoEnabled()) {
			LOG.info("{method:'ProductServiceFacade::getProductInfo',in:{startNum:'" + startNum +"pageSize"+pageSize+"'}}");
		}
		
		List<ProductModel> out = productService.getProductInfo(startNum, pageSize);
		
		if(LOG.isDebugEnabled()){
			LOG.info("{method:'ProductServiceFacade::getProductInfo',out:"+JSON.toJSONString(out)+"}");
		}
		return out;
	}


	public ProductModel getInfoByProductCode(String productCode) {
		if (LOG.isInfoEnabled()) {
			LOG.info("{method:'ProductServiceFacade::getInfoByProductCode',in:{productCode:'" + productCode +"'}}");
		}
		
		ProductModel out = productService.getInfoByProductCode(productCode);
		
		if(LOG.isDebugEnabled()){
			LOG.info("{method:'ProductServiceFacade::getInfoByProductCode',out:"+JSON.toJSONString(out)+"}");
		}
		return out;
	}


	
	
}
