package com.dpmall.datasvr.serviceImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dpmall.common.DateUtils;
import com.dpmall.datasvr.api.IProductStatisticService;
import com.dpmall.db.Page;
import com.dpmall.db.bean.ProductStatisticEntity;
import com.dpmall.db.dao.ProductStatisticDao;
import com.dpmall.model.ProductStatisticModel;


@Service(value = "productStatisticService")
public class ProductStatisticServiceImpl implements IProductStatisticService {
	private static final Logger LOG = LoggerFactory.getLogger(ProductStatisticServiceImpl.class);
	
	private ProductStatisticDao productStatisticDao;

	public List<ProductStatisticModel> search(String productCode, String fromTime, String endTime, int start,
			int pageSize) {
		List<ProductStatisticModel> out = null;
		Timestamp startTimestamp = null;
		Timestamp endTimestamp = null;
		if(!StringUtils.isEmpty(fromTime) && !StringUtils.isEmpty(endTime)){
			try{
				startTimestamp = new Timestamp(DateUtils.parse(fromTime, DateUtils.YYYY_MM_DD_HH_MM_SS).getTime());
				endTimestamp = new Timestamp(DateUtils.parse(endTime, DateUtils.YYYY_MM_DD_HH_MM_SS).getTime());
			} catch(Throwable e){
				LOG.error(e.getMessage(),e);
			}
		}

		List<ProductStatisticEntity> outEntityList = productStatisticDao.search(productCode, startTimestamp, endTimestamp, new Page<ProductStatisticEntity>(start,pageSize));
		if(outEntityList == null || outEntityList.isEmpty()){
			return null;
		}
		
		out = this.entity2Model(outEntityList);
		return out;
	}
	
	private ProductStatisticModel entity2Model(ProductStatisticEntity in){
		if(in == null){
			return null;
		}
		
		ProductStatisticModel out = new ProductStatisticModel();
		out.createTime = in.createTime;
		out.endTime = in.endTime;
		out.fromTime = in.fromTime;
		out.id = in.id;
		out.productCode = in.productCode;
		out.productId = in.productId;
		out.saleTotal = in.saleTotal;
		out.totalAccess = in.totalAccess;
		out.totalAmount = in.totalAmount;
		out.totalDeals = in.totalDeals;
		out.updateTime = in.updateTime;
		
		return out;
	}
	
	private List<ProductStatisticModel> entity2Model(List<ProductStatisticEntity> in){
		if(in == null || in.isEmpty()){
			return null;
		}
		
		List<ProductStatisticModel> out = new ArrayList<ProductStatisticModel>();
		for(ProductStatisticEntity tmp : in){
			out.add(entity2Model(tmp));
		}
		
		return out;
		
	}

	public void setProductStatisticDao(ProductStatisticDao productStatisticDao) {
		this.productStatisticDao = productStatisticDao;
	}

}
