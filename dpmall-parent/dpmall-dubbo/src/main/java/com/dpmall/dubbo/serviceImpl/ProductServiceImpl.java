package com.dpmall.dubbo.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.dpmall.db.bean.ProductEntity;
import com.dpmall.db.dao.ProductDao;
import com.dpmall.dubbo.api.IProductService;
import com.dpmall.model.ProductModel;

/**
 * 获取图片路径
 * 
 * @author cwj
 * @date 2017-12-26
 */
public class ProductServiceImpl implements IProductService {

	@Autowired
	private ProductDao productDao;
	
	/**
	 * entity转Model
	 * @param in
	 * @return
	 */
	private ProductModel entityToModel (ProductEntity in) {
		ProductModel out = new ProductModel();
		if (in !=null) {
			BeanUtils.copyProperties(in, out);
		}
		return out;
	}
	
	/***
	 * entityList转ModelList
	 * @param in
	 * @return
	 */
	private List<ProductModel> entityListToModelList(List<ProductEntity> in){
		List<ProductModel> out = new ArrayList<ProductModel>();
		if (in == null && CollectionUtils.isEmpty(in)) {
			return out;
		}
		for(ProductEntity entity:in) {
			ProductModel model = new  ProductModel();
			model = this.entityToModel(entity);
			out.add(model);
		}
		return out;
	}

	public List<ProductModel> getProductInfo(int startNum, int pageSize) {
		List<ProductEntity> in =productDao.getProductInfo(startNum, pageSize);
		List<ProductModel> result = this.entityListToModelList(in);
		return result;
	}

	public ProductModel getInfoByProductCode(String productCode) {
		ProductEntity in = productDao.getInfoByProductCode(productCode);
		ProductModel out = this.entityToModel(in);
		return out;
	}

	

	
	

	

}
