package com.dpmall.param;

import org.springframework.util.StringUtils;
import sun.applet.Main;

/**
 * <p>
 * 销售线索状态
 * @author river
 * @date 2017-07-17
 */
public class SaleLeadsOrderStatus {

	/**经销商待接单*/
	public static final  String AGENCYWAITACCEPT ="AGENCYWAITACCEPT";

	/**门店待接单*/
	public static final  String STOREWAITACCEPT="STOREWAITACCEPT";

	/**拒单*/
	public static final  String REJECTED="REJECTED";

	/**已接单（待处理）*/
	public static final  String ACCEPTED="ACCEPTED";

	/**已联系*/
	public static final  String CONTACTED="CONTACTED";

	/**联系不上*/
	public static final  String NOTCONTACTED="NOTCONTACTED";

	/**邀约成功*/
	public static final  String INVITED="INVITED";

	/**邀约失败*/
	public static final  String NOTINVITED="NOTINVITED";

	/**已到店*/
	public static final  String VISITED="VISITED";

	/**未到店*/
	public static final  String NOTVISITED="NOTVISITED";

	/**已成交*/
	public static final  String SUCCESS="SUCCESS";

	/**未成交*/
	public static final  String FAILED="FAILED";

	/**已关闭*/
	public static final  String CLOSED="CLOSED";


	/**待接单（列表状态）**/
	public static final String WAITTING ="WAITTING";

    /**跟进中（列表状态）**/
    public static final String FLLOWING ="FLLOWING";

    /**已完成（列表状态）**/
    public static final String COMPLETED ="COMPLETED";




	public static String ENGLISHTOCHS(String status) {
		String out = "";
		if ("AGENCYWAITACCEPT".equals(status)){
			out  = "待接单";
		}else if ("STOREWAITACCEPT".equals(status)){
			out  = "待接单";
		}
		else if ("REJECTED".equals(status)){
			out  = "拒单";
		}
		else if ("ACCEPTED".equals(status)){
			out  = "待处理";
		}
		else if ("CONTACTED".equals(status)){
			out  = "联系成功";
		}
		else if ("NOTCONTACTED".equals(status)){
			out  = "联系不上";
		}
		else if ("INVITED".equals(status)){
			out  = "邀约成功";
		}
		else if ("NOTINVITED".equals(status)){
			out  = "邀约失败";
		}
		else if ("VISITED".equals(status)){
			out  = "已到店";
		}
		else if ("NOTVISITED".equals(status)){
			out  = "未到店";
		}
		else if ("SUCCESS".equals(status)){
			out  = "交易成功";
		}
		else if ("FAILED".equals(status)){
			out  = "未成交";
		}

		return out;
	}


	/**
	 * 根据中文转换为英文
	 */
	public static String CHSTOENGLISH(String statusName) {
		String out = "";
		if (StringUtils.isEmpty(statusName)){
			return out;
		}
		switch (statusName) {
			case "联系不上":
				out = "NOTCONTACTED";
				break;
			case "联系成功":
				out = "CONTACTED";
				break;
			case "邀约失败":
				out = "NOTINVITED";
				break;
			case "邀约成功":
				out = "INVITED";
				break;
			case "未到店":
				out = "NOTVISITED";
				break;
			case "已到店":
				out = "VISITED";
				break;
			case "未成交":
				out = "FAILED";
				break;
			case "交易成功":
				out = "SUCCESS";
				break;
			case "已接单(待处理)":
				out = "ACCEPTED";
				break;
			default:
				out = "";
		}
		return out;
	}

	public static void main(String[] args) {
		System.out.println(SaleLeadsOrderStatus.CHSTOENGLISH("已成交"));
	}



}
