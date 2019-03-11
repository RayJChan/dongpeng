package com.dpmall.db.dao;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.dpmall.db.Page;
import com.dpmall.db.bean.ProductStatisticEntity;


/**
 * 商品统计DAO
 * @author river
 * @since 2017-6-23
 */
@MapperScan
public interface ProductStatisticDao {
	
	/**
	 * <p>
	 * 根据模板查询
	 * @param template
	 * @param page
	 * @return
	 */
    public List<ProductStatisticEntity> search(@Param(value="productCode")String productCode,@Param(value="fromTime")Timestamp fromTime, @Param(value="endTime")Timestamp endTime, @Param(value="page")Page<ProductStatisticEntity> page);

    /**
     * <p>
     * 根据磨薄更新纪录
     * @param template
     * @return
     */
    public int updateByTemplate(@Param(value="template")ProductStatisticEntity template);
    
    
    /**
     * <p>
     * 插入一条数据
     * @param template
     * @return
     */
    public int insert(@Param(value="template")ProductStatisticEntity template);
}
