package com.dpmall.db.bean;

import java.math.BigDecimal;

/**
 * @author cwj
 * 报表历史记录
 *  
 */
public class ReportDetailEntity {
	
	//
	/**历史记录时间**/
	public String historyDate;
	
//	/**历史记录对应结果**/
//	public String historyResult;
	
	/**分组pk**/
	public String appGroup;
	
	/**门店pk**/
	public String storeId;
	
	
	/**派单数**/
	public BigDecimal distributeCount;
	
	/**派单变化比率**/
	public String distributeCountRate;
	
	/**接单数**/
	public BigDecimal acceptCount;
	
	/**接单变化比率**/
	public String acceptCountRate;
	
	/**派单平均值**/
	public BigDecimal distributeAVG;
	
	/**接单平均值**/
	public BigDecimal acceptAVG;
	
	/**累计接单率**/
	public BigDecimal acceptRate;
	
	/**平均发货时长**/
	public BigDecimal deliverAVGTime;
	
	
	/**派单数**/
	public BigDecimal writeOffDistributeCount;
	
	/**核销数**/
	public BigDecimal writeOffCount;
	
	/**核销平均值**/
	public BigDecimal writeOffAVG;
	
	/**核销率**/
	public BigDecimal writeOffRate;
	
	/**核销平均时长**/
	public BigDecimal writeOffTime;
	
	
	/**转化数量**/
	public BigDecimal conversionCount;
	
	/**转化平均值**/
	public BigDecimal conversionAVG;
	
	/**转化率**/
	public BigDecimal conversionRate;
	
	/**平均转化时长**/
	public BigDecimal conversionAVGTime;
	
	
	
	
	
	
	
	/**分组名称**/
	public String groupName;
	
	/**分组pk**/
	public String groupPk;
	
	/**序号**/
	public Integer rowNum;
	
	/**排序中的平均值**/
	public BigDecimal sortAVG;
	
	/**排序的结果**/
	public BigDecimal sortResult;
	
	/**排序的结果与平均值比较的结果**/
	public String sortResultCon;

	/**区域分布对应名称**/
	public String regionResultName;
	
	/**区域分布结果**/
	public Long regionDistributionResult;
	
	
	
	/**产品名称**/
	public String productName;

	/**产品派单量**/
	public Long productCount;
	
	/**产品型号**/
	public String productModelNumber;


	@Override
	public String toString() {
		return "ReportDetailEntity [historyDate=" + historyDate + ", appGroup=" + appGroup + ", storeId=" + storeId
				+ ", distributeCount=" + distributeCount + ", acceptCount=" + acceptCount + ", distributeAVG="
				+ distributeAVG + ", acceptAVG=" + acceptAVG + ", acceptRate=" + acceptRate + ", deliverAVGTime="
				+ deliverAVGTime + ", writeOffDistributeCount=" + writeOffDistributeCount + ", writeOffCount="
				+ writeOffCount + ", writeOffAVG=" + writeOffAVG + ", writeOffRate=" + writeOffRate + ", writeOffTime="
				+ writeOffTime + ", conversionCount=" + conversionCount + ", conversionAVG=" + conversionAVG
				+ ", conversionRate=" + conversionRate + ", conversionAVGTime=" + conversionAVGTime + ", groupName="
				+ groupName + ", groupPk=" + groupPk + ", rowNum=" + rowNum + ", sortAVG=" + sortAVG + ", sortResult="

				+ sortResult + ", sortResultCon=" + sortResultCon + "]"

				+ sortResult + ", sortResultCon=" + sortResultCon + ", regionResultName=" + regionResultName
				+ ", regionDistributionResult=" + regionDistributionResult + ", productName=" + productName
				+ ", productCount=" + productCount + ", productModelNumber=" + productModelNumber
				+ "]";

	}
	
	




	

	




	
	
	
	
}
