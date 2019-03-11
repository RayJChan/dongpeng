package com.dpmall.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * app首页展示的数据
 * @author cwj
 * @since 2017-12-13
 */
public class FirstPageModel implements Serializable {

	private static final long serialVersionUID = -2377839867209702838L;

	/**留资**/
	public Map<String, String> salesInfo = new HashMap<String, String>();
	
	/**实物**/
	public Map<String, String> orderInfo= new HashMap<String, String>();
	
	/**特权**/
	public Map<String, String> prePayInfo= new HashMap<String, String>();

	/**特权**/
	public Map<String, String> tmallPrePayInfo= new HashMap<String, String>();

	/**特权**/
	public Map<String, String> otherPrePayInfo= new HashMap<String, String>();



	/**统计**/
	public Map<String, String> reportInfo= new HashMap<String, String>();


	
	
	
	
	
	/**图片**/
	public List<PictureModel> homePictures = new ArrayList<PictureModel>();
	
	/**祝福卡图片*/
	public List<String> cardsPicture = new ArrayList<String>();
	
	/*暂时无用*/
	/**图片**/
	public List<String> pictures = new ArrayList<String>();
	
	/**图片**/
	public List<String> picturesHDP = new ArrayList<String>();
	
	/**图片**/
	public List<String> picturesXXHDP = new ArrayList<String>();
	

	
	
	
	


	
	
//	
//	/**留资: 待接单数量**/
//	public BigDecimal salesWaitAcceptCount ;
//	
//	/**留资 :跟进中数量**/
//	public BigDecimal salesFollowingCount ;
//	
//	/**留资 :完成数量**/
//	public BigDecimal salesFinishedCount ;
//	
//	/**留资：与上个月对比**/
//	public String salesComparison;
//	
//	
//	/**实物:待接单数量**/
//	public BigDecimal orderWaitAcceptCount ;
//	
//	/**实物:跟进中数量**/
//	public BigDecimal orderFollowingCount ;
//	
//	/**实物:完成数量**/
//	public BigDecimal orderFinishedCount ;
//	
//	/**实物：与上个月对比**/
//	public String orderComparison;
//	
//	
//	/**特权订金:待接单数量**/
//	public BigDecimal prePayWaitAcceptCount ;
//	
//	/**特权订金:跟进中数量**/
//	public BigDecimal prePayFollowingCount ;
//	
//	/**特权订金:完成数量**/
//	public BigDecimal prePayFinishedCount ;
//	
//	/**特权订金：与上个月对比**/
//	public String prePayComparison;
//	
//	
//	
//	/**留资转化率**/
//	public String conversionRate;
//	
//	/**转化率的比较**/
//	public String conversionRateComparison;
//	
//	/**实物订单接单率**/
//	public String acceptRate;
//	
//	/**接单率的比较**/
//	public String acceptRateComparison;
//	
//	/**特权订金核销率**/
//	public String writeOffRate;
//	
//	/**核销率的比较**/
//	public String writeOffRateComparison;
//	
	
	
	
	
	
}
