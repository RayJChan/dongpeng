package com.dpmall.datasvr.serviceImpl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.dpmall.common.RedisUtils;
import com.dpmall.datasvr.api.IProductService;
import com.dpmall.db.bean.ProductEntity;
import com.dpmall.db.dao.ProductDao;
import com.dpmall.model.ProductModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取图片路径
 * 
 * @author cwj
 * @date 2017-12-26
 */
@Service("productService2")
public class ProductServiceImpl implements IProductService {

	@Autowired
	private ProductDao productDao;

	/**
	 * entity转Model
	 *
	 * @param in
	 * @return
	 */
	private ProductModel entityToModel(ProductEntity in) {
		ProductModel out = new ProductModel();
		if (in != null) {
			BeanUtils.copyProperties(in, out);
		}
		return out;
	}

	/***
	 * entityList转ModelList
	 * @param in
	 * @return
	 */
	private List<ProductModel> entityListToModelList(List<ProductEntity> in) {
		List<ProductModel> out = new ArrayList<ProductModel>();
		if (in == null && CollectionUtils.isEmpty(in)) {
			return out;
		}
		for (ProductEntity entity : in) {
			ProductModel model = new ProductModel();
			model = this.entityToModel(entity);
			out.add(model);
		}
		return out;
	}

	public List<ProductModel> getProductInfo(int startNum, int pageSize) {
		List<ProductEntity> in = productDao.getProductInfo(startNum, pageSize);
		List<ProductModel> result = this.entityListToModelList(in);
		return result;
	}

	public ProductModel getInfoByProductCode(String productCode) {
		ProductEntity in = productDao.getInfoByProductCode(productCode);
		ProductModel out = this.entityToModel(in);
		return out;
	}


	@Override
	public long getProductCode() {
		Jedis jedis = RedisUtils.getClient();
		Pipeline pipeline = jedis.pipelined();
		long successCount = 0 ;
		try {
			int i = 1;
			jedis.del("productCodes");
			while (true) {
				List<String> productCodes = productDao.getProductCode(i, 1000);
				//当没数据时，退出程序
				if (CollectionUtils.isEmpty(productCodes)) {
					pipeline.close();
					break;
				}
				i++;

				//存入数据到Redis
				pipeline.clear();
				for (String str : productCodes){
					pipeline.sadd("productCodes",str);
					successCount ++;
				}
				pipeline.sync();
			}
			pipeline.sync();
			pipeline.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			RedisUtils.returnResource(jedis);
		}
		return successCount;
	}

}
