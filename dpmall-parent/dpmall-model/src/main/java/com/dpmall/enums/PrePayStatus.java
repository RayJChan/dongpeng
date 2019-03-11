package com.dpmall.enums;

public class PrePayStatus {


    /**
     * 已拒单
     **/
    public static final String REFUSE = "REFUSE";
    /**
     * 已撤回
     **/
    public static final String RECALL = "RECALL";
    /**
     * 待分配
     **/
    public static final String READY = "READY";
    /**
     * 已发货
     **/
    public static final String SHIPPED = "SHIPPED";
    /**
     * 确认收货
     **/
    public static final String RECEIVE = "RECEIVE";
    /**
     * 已返款
     **/
    public static final String REMITTED = "REMITTED";

    /**
     * 待发货
     **/
    public static final String ACCEPT = "ACCEPT";
    /**
     * 已取消
     **/
    public static final String CANCELLED = "CANCELLED";
    /**
     * 已创建
     **/
    public static final String CREATED = "CREATED";
    /**
     * 取消中
     **/
    public static final String CANCELLING = "CANCELLING";
    /**
     * 已终止
     **/
    public static final String ABORTED = "ABORTED";
    /**
     * 经销商待接单
     **/
    public static final String DISTRIBUTED = "DISTRIBUTED";
    /**
     * 已预约
     **/
    public static final String BOOKED = "BOOKED";
    /**
     * 门店待接单
     **/
    public static final String STOREWAIT = "STOREWAIT";
    /**
     * 已到店
     **/
    public static final String TOSTORE = "TOSTORE";
    /**
     * 已签收
     **/
    public static final String DPRECEIVE = "DPRECEIVE";
    /**
     * 未成交
     **/
    public static final String NOTRADED = "NOTRADED";

    /**
     * 未成交
     **/
    public static final String NOTTRADED = "NOTTRADED";

    /**
     * 已完成
     **/
    public static final String COMPLETED = "COMPLETED";



    /**待接单（列表状态）**/
    public static final String WAITTING ="WAITFORRECEIVE";

    /**跟进中（列表状态）**/
    public static final String FLLOWING ="INPROGRESS";

    /**已完成（列表状态）**/
    public static final String COMPLETEDLIST ="COMPLETED";


    /**
     * 根据英文转换为中文
     */
    public static String ENGLISHTOCHS(String statusName) {
        String out = "";
        switch (statusName) {
            case "REFUSE":
                out = "已拒单";
                break;
            case "RECALL":
                out = "已撤回";
                break;
            case "READY":
                out = "待分配";
                break;
            case "SHIPPED":
                out = "已发货";
                break;
            case "RECEIVE":
                out = "确认收货";
                break;
            case "REMITTED":
                out = "已返款";
                break;
            case "ACCEPT":
                out = "待发货";
                break;
            case "CANCELLED":
                out = "已取消";
                break;
            case "CREATED":
                out = "已创建";
                break;
            case "CANCELLING":
                out = "取消中";
                break;
            case "ABORTED":
                out = "已终止";
                break;
            case "DISTRIBUTED":
                out = "经销商待接单";
                break;
            case "BOOKED":
                out = "已预约";
                break;

            case "STOREWAIT":
                out = "门店待接单";
                break;
            case "TOSTORE":
                out = "已到店";
                break;
            case "DPRECEIVE":
                out = "已签收";
                break;
            case "NOTRADED":
                out = "未成交";
                break;
            case "NOTTRADED":
                out = "未成交";
                break;
            case "COMPLETED":
                out = "已完成";
                break;

            default:
                out = "";
        }
        return out;
    }




}
