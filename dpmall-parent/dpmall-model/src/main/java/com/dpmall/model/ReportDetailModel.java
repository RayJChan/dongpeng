package com.dpmall.model;

import java.io.Serializable;

/**
 * 实物订单信息
 * @author river
 * @since 2017-07-10
 */
public class ReportDetailModel implements Serializable {

	private static final long serialVersionUID = 4320644331168657374L;

	/**历史时间**/
	public String historyDate ;
	
	/**图表x轴时间**/
	public String xDate ;

	/**对应的结果**/
	public String result;
	
	/**图表Y轴结果**/
	public String yResult;
	
	/**分组名称**/
	public String groupName;
	
	/**分组pk**/
	public String groupPk;
	
	/**序号**/
	public String rowNum;
	
	/**排序的结果与平均值比较的结果**/
	public String resultCon;


	
	
	/**省名**/
	public String provinceName;
	
	/**城市名**/
	public String cityName;
	
	/**区域分布结果**/
	public String regionDistributionResult;
	
	/**区域分布最大结果**/
	public String regionMaxResult;
	
	
	
	/**产品名称**/
	public String productName;

	/**产品数量**/
	public String productCount;
	
	/**产品型号**/
	public String productModelNumber;
	
	/**销售比率（带单位）**/
	public String productSalesRateStr;
	
	/**销售比率（不带单位）**/
	public String productSalesRate;
	
	

	@Override
	public String toString() {
		return "ReportDetailModel [historyDate=" + historyDate + ", xDate=" + xDate + ", result=" + result
				+ ", yResult=" + yResult + ", groupName=" + groupName + ", groupPk=" + groupPk + ", rowNum=" + rowNum
				+ ", resultCon=" + resultCon + ", provinceName=" + provinceName + ", cityName=" + cityName
				+ ", regionDistributionResult=" + regionDistributionResult + ", regionMaxResult=" + regionMaxResult
				+ ", productName=" + productName + ", productCount=" + productCount + ", productModelNumber="
				+ productModelNumber + ", productSalesRateStr=" + productSalesRateStr + ", productSalesRate="
				+ productSalesRate + "]";
	}

	
	
	
	

	
	
	
	
}
