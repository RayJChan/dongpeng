package com.dpmall.datasvr.serviceImpl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.dpmall.common.CglibBeanCopierUtils;
import com.dpmall.common.PrivateKeyUtils;
import com.dpmall.datasvr.api.ITmallPrepayService;
import com.dpmall.db.bean.*;
import com.dpmall.db.bean.po.PrepayPo;
import com.dpmall.db.bean.po.TmallOrderGoodsPo;
import com.dpmall.db.bean.po.TmallOrderItemsPo;
import com.dpmall.db.bean.po.UserEntity;
import com.dpmall.db.dao.PrePayDao;
import com.dpmall.db.dao.TmallPrepayDao;
import com.dpmall.db.dao.UtilsDao;
import com.dpmall.model.in.TmallOrderGoodsModelIn;
import com.dpmall.model.in.TmallOrderInfoModelIn;
import com.dpmall.model.prePay.TmallPrePayDetailModel;
import com.dpmall.model.prePay.TmallPrePayModel;
import com.dpmall.model.prePay.TmallPrepayListCountModel;
import com.dpmall.param.SaleLeadsOrderStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service(value = "tmallPrepayService")
public class TmallPrepayServiceImpl implements ITmallPrepayService {

    @Autowired
    private TmallPrepayDao tmallPrepayDao;

    @Autowired
    private UtilsDao utilsDao;

    /**已完成（列表状态）**/
    public static final String COMPLETED ="COMPLETED";

    /**跟进中（列表状态）**/
    public static final String FLLOWING ="FLLOWING";

    /**已成交*/
    public static final String SUCCESS ="SUCCESS";

    /**已接单（待处理）*/
    public static final  String ACCEPTED="ACCEPTED";

    /**未成交*/
    public static final  String FAILED="FAILED";

    /**
     * 工具类
     */
    private TmallPrePayModel entityToModel(TmallPrePayEntity in) {
        TmallPrePayModel out = new TmallPrePayModel();
        if (in == null) {
            return out;
        }
        CglibBeanCopierUtils.copyProperties(in, out);
        out.setEffectiveTime(this.limitedTimeFormat(in.getEffectiveTime()));
        if (StringUtils.isEmpty(in.getPhone())|| StringUtils.isEquals("null",in.getPhone())){
            out.setPhone("");
        }
        return out;
    }

    private List<TmallPrePayModel> entityListToModleList(List<TmallPrePayEntity> entities) {
        List<TmallPrePayModel> out = new ArrayList<>();
        if (entities == null && CollectionUtils.isEmpty(entities)) {
            return out;
        }
        for (TmallPrePayEntity entity : entities) {
            TmallPrePayModel model = new TmallPrePayModel();
            model = entityToModel(entity);
            out.add(model);
        }
        return out;
    }

    /**
     * 格式化限制时间
     *
     * @param in
     * @return
     */
    private static String limitedTimeFormat(String in) {
        String out = "";
        if (org.apache.commons.lang.StringUtils.isEmpty(in)) {
            return out;
        }
        try {
            StringBuffer buffer = new StringBuffer();
            buffer.append(in);
            buffer.append(" 至 ");


            LocalDate newDay = LocalDate.parse(in);
            LocalDate afterDay =  newDay.plusDays(45);//有效期45天
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            buffer.append(df.format(afterDay));
            out = buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    /**
     * 获取列表数量
     */
    private TmallPrepayListCountModel getListCount(String isAgency, String agencyId, String storeId) {
        TmallPrepayListCountModel countModel = new TmallPrepayListCountModel();
        for (int i = 1; i <= 4; i++) {//4种列表状态
            String listCount = "0";
            //经销商or门店
            if ("Y".equals(isAgency)) {
                listCount = tmallPrepayDao.getListCount(agencyId, "", "Y", String.valueOf(i), "","","","","","");
            } else {
                listCount = tmallPrepayDao.getListCount("", storeId, "N", String.valueOf(i), "","","","","","");
            }
            //赋值
            if (i == 1) {
                countModel.setWaittingCount(listCount);
            } else if (i == 2) {
                countModel.setAccepetedCount(listCount);
            } else if(i == 3){
                countModel.setWriteOffedCount(listCount);
            }
            else if(i == 4){
                countModel.setClosedCount(listCount);
            }

        }
        return countModel;
    }

    /**
     * 批量接单
     *  prepayId：id
     *  agencyListStatus：经销商列表状态
     *  storeListStatus门店状态
     *  prepayStatus：特权订金状态
     *  storeId ：门店id
     */
    private  int acceptOrWithdraw ( List<String>prepayId, String agencyListStatus,String storeListStatus,String prepayStatus,String storeId,String isDeliverySelf,String isAccept){
        int out = 0;
        for (String id : prepayId) {
            TmallPrePayDetailEntity entity =  tmallPrepayDao.getDetails(id);
            if (entity == null ){
                continue;
            }
            PrepayPo po = new PrepayPo();
            po.setPrePayId(id);
            po.setAgencyListStatus(agencyListStatus);
            po.setStoreListStatus(storeListStatus);
            po.setSuggestStore(storeId);
            po.setIsDeliverySelf(isDeliverySelf);


            //如果第三方状态为“已完成”，发货状态修改为“已发货”
            if ("TRADE_FINISHED".equals(entity.getSfStatus()) || "FINISHED_L".equals(entity.getSfStatus())){
                prepayStatus = "SHIPPED";
            }
            //如果第三方状态为“已关闭”，发货状态修改为“已终止”
            else if ("TRADE_CLOSED".equals(entity.getSfStatus()) || "TRADE_CANCELED".equals(entity.getSfStatus())){
                prepayStatus = "ABORTED";
            }
            po.setPrepayStatus(prepayStatus);
            po.setTmallAcceptTime(isAccept);
            int i = tmallPrepayDao.editXiaoTitle(po);
            out += i;
        }
        return out;
    }

    /**
     * ------------------------------------------------------------------------------------------------------------------------------------
     **/
    @Override
    public List<TmallPrePayModel> getListOfAgency(String agencyId, String status, int pageNum, int pageSize, String search, String orderId, String clientName, String clientTel, String staRevisitTime, String endRevisitTime) {
        List<TmallPrePayModel> out = new ArrayList<>();
        String listCount = tmallPrepayDao.getListCount(agencyId, "", "Y", status, search,orderId,clientName,clientTel,staRevisitTime,endRevisitTime);
        if ("0".equals(listCount)) {
            return out;
        }
        List<TmallPrePayEntity> entities = tmallPrepayDao.getList(agencyId, "", "Y", status, pageNum, pageSize, search,orderId,clientName,clientTel,staRevisitTime,endRevisitTime);
        for (TmallPrePayEntity entity : entities) {
            TmallPrePayModel model = new TmallPrePayModel();
            model = entityToModel(entity);
            model.setListCount(listCount);
            if (StringUtils.isEmpty(entity.getSuggestStoreName())) {
                model.setSuggestStoreName(entity.getAgencyName());
            }
            //如果派至门店，且未接单，在经销商隐藏电话号码和地址
            if ("N".equals(entity.getIsAgency())&&"WAITFORRECEIVE".equals(entity.getStoreStatus())){
                //地址
                StringBuffer buffer = new StringBuffer();
                buffer.append(entity.getServiceAddress());
                buffer.replace(3, buffer.length(), "********");
                model.setServiceAddress(buffer.toString());

                //清空buffer
                buffer.delete(0, buffer.length());

                //电话
                buffer.append(entity.getPhone());
                buffer.replace(3, buffer.length(), "********");
                model.setPhone(buffer.toString());
            }
            out.add(model);
        }
        return out;
    }

    @Override
    public List<TmallPrePayModel> getListOfStore(String storeId, String status, int pageNum, int pageSize, String search, String orderId, String clientName, String clientTel, String staRevisitTime, String endRevisitTime) {
        String listCount = tmallPrepayDao.getListCount("", storeId, "N", status, search,orderId,clientName,clientTel,staRevisitTime,endRevisitTime);
        List<TmallPrePayModel> out = new ArrayList<>();
        if ("0".equals(listCount)) {
            return out;
        }
        List<TmallPrePayEntity> entities = tmallPrepayDao.getList("", storeId, "N", status, pageNum, pageSize, search,orderId,clientName,clientTel,staRevisitTime,endRevisitTime);

        for (TmallPrePayEntity entity : entities) {
            TmallPrePayModel model = new TmallPrePayModel();
            model = entityToModel(entity);
            model.setListCount(listCount);
            out.add(model);
        }
        return out;
    }


    @Override
    public TmallPrepayListCountModel getListCountOfAgency(String agencyId) {
        return this.getListCount("Y", agencyId, "");
    }

    @Override
    public TmallPrepayListCountModel getListCountOfStore(String storeId) {
        return this.getListCount("N", "", storeId);
    }

    @Override
    @Transactional
    public int accept(List<String> prepayId, String storeId, String isAgency) {
        int out = 0;
        //经销商 or 门店 操作
       String isAccept = "Y";
        if ("Y".equals(isAgency)) {
            //判断是否自己仓库发货
            if ("1".equals(storeId)) {
                out = this.acceptOrWithdraw(prepayId, "COMPLETED", "", "ACCEPT", "1","Y",isAccept);
            } else {
                out = this.acceptOrWithdraw(prepayId, "WAITFORRECEIVE", "WAITFORRECEIVE", "STOREWAIT", storeId,"N",isAccept);
            }
        } else {
            out = this.acceptOrWithdraw(prepayId, "COMPLETED", "COMPLETED", "ACCEPT", "","N",isAccept);
        }
        return out;
    }


    @Override
    @Transactional
    public int withdraw(String prepayId, String isAgency) {
        int out = 0;
        List<String> ids = new ArrayList<>();
        ids.add(prepayId);

        PrepayPo po = new PrepayPo();
        po.setPrePayId(prepayId);
        po.setPrepayStatus("DISTRIBUTED");
        po.setAgencyListStatus("WAITFORRECEIVE");
        po.setIsDeliverySelf(isAgency);

        if ("Y".equals(isAgency)) {
            out = tmallPrepayDao.withdraw(po);
        }

//        else {//门店撤单
//            out = this.acceptOrWithdraw(ids, "WAITFORRECEIVE", "WAITFORRECEIVE", "STOREWAIT", "");
//
//        }
        return out;
    }

    @Override
    public TmallPrePayDetailModel getDetails(String prepayId) {
        TmallPrePayDetailModel out =new TmallPrePayDetailModel();
        TmallPrePayDetailEntity in  = tmallPrepayDao.getDetails(prepayId);
        if (in == null){
            return out;
        }
        CglibBeanCopierUtils.copyProperties(in,out);
        out.setEffectiveTime(this.limitedTimeFormat(in.getEffectiveTime()));
       //列表状态(经销商)
        if("WAITFORRECEIVE".equals(in.getListStatusOfAgency())){
            out.setListStatusOfAgency("WAITTING");
        }else if ("INPROGRESS".equals(in.getListStatusOfAgency()) ||"COMPLETED".equals(in.getListStatusOfAgency()) ) {
            out.setListStatusOfAgency("FLLOWING");
            if ("1".equals(in.getIsWriteOff())){
                out.setListStatusOfAgency("COMPLETED");
            }

        }
        //列表状态(门店)
        if("WAITFORRECEIVE".equals(in.getListStatusOfStore())){
            out.setListStatusOfStore("WAITTING");
            //门店未接单经销商账号隐藏电话、地址
            //地址
            StringBuffer buffer = new StringBuffer();
            buffer.append(in.getServiceAddress());
            buffer.replace(3, buffer.length(), "********");
            out.setServiceAddress(buffer.toString());

            //清空buffer
            buffer.delete(0, buffer.length());

            //电话
            buffer.append(in.getPhone());
            buffer.replace(3, buffer.length(), "********");
            out.setPhone(buffer.toString());
        }else if ("INPROGRESS".equals(in.getListStatusOfStore()) ||"COMPLETED".equals(in.getListStatusOfStore()) ) {
            out.setListStatusOfStore("FLLOWING");
            if ("1".equals(in.getIsWriteOff())){
                out.setListStatusOfStore("COMPLETED");
            }
        }
        //列表状态(已关闭)
        if ("TRADE_CLOSED".equals(in.getSfStatus())||"TRADE_CANCELED".equals(in.getSfStatus())){
            out.setListStatusOfAgency("CLOSED");
            out.setListStatusOfStore("CLOSED");
        }

        //推荐门店
        if ("Y".equals(in.getIsAgency())){
            out.setSuggestStoreName(in.getAgencyName());
        }

        // 获取客户备注 、 客服备注
        if(StringUtils.isNotEmpty(in.getServiceRemark())) {
            StringBuffer commentBuffer  =new StringBuffer((in.getServiceRemark()));//拼接用
            int a = commentBuffer.indexOf("客服备注");
            int b = commentBuffer.lastIndexOf("客户备注");
            if (a<0||b<0){//不存在以上两个字符，其中的一个
                out.setServiceRemark("");
            }else {
                out.setServiceRemark(commentBuffer.substring(a,b).toString().trim());
//                outEntityDetail.setCusComment(commentBuffer.substring(b,commentBuffer.length()).toString().trim());
            }
        }
        return out;
    }


    /**
     * 添加备注
     */
    @Override
    @Transactional
    public String addRemarks(String prepayId,String agencyRemark, String storeRemark,String operatorBy) {
        String result = "";
        String operatorDesc = "";
        if(StringUtils.isEmpty(agencyRemark) && StringUtils.isEmpty(storeRemark)){
            return "无备注信息";
        }
        //添加备注
        PrepayPo Po = new PrepayPo();
        Po.setPrePayId(prepayId);
        Po.setAgencyRemark(agencyRemark);
        Po.setStoreRemark(storeRemark);
        int i = tmallPrepayDao.editXiaoTitle(Po);

//        if (StringUtils.isNotEmpty(agencyRemark)){
//            operatorDesc = "更新经销商备注";
//            result =  "经销商备注更新成功";
//        }else if (StringUtils.isNotEmpty(storeRemark)){
//            operatorDesc = "更新门店备注";
//            result =  "门店备注更新成功";
//        }
//
//        //操作类，记录操作人，操作时间
//        int operationResult = this.operationHistory(operatorBy,operatorDesc,"UpdateRemarks",saleLeadsOrderId,"");

        if (i>0){
            result = "添加备注成功";
        }else{
            result = "";
        }

        return result;
    }

    @Override
    public String getO2oIdByConsignmentId(String prePayId) {
        String orderId = "";
        if (StringUtils.isNotEmpty(prePayId)) {
            orderId = tmallPrepayDao.getO2oIdByConsignmentId(prePayId);
            if (StringUtils.isEmpty(orderId)){
                orderId = "";
            }
        }
        return orderId;
    }

    /**
     * 更新订单进度
     */
    @Override
    @Transactional
    public int updateOrderProgress(String statusName, String prePayId, String agencyRemark, String operatorBy, String failType,String isLoss, String orderEvaluation, String RevisitTime) {
        if (prePayId==null) {
            return 0;
        }

        //中文-->英文
        String status = this.CHSTOENGLISH(statusName);
        if (StringUtils.isEmpty(status)) {
            return 0;
        }

        String omsStatus = tmallPrepayDao.getOrderDetails(prePayId);
        if (omsStatus == null) {
            return 0;
        }

//        String lastStatus =omsStatus;

        PrepayPo Po = new PrepayPo();
        Po.setPrePayId(prePayId);
        Po.setIsLoss(isLoss);
        Po.setOrderEvaluation(orderEvaluation);
        Po.setRevisitTime(RevisitTime);
        if("FAILED".equals(status)){
            Po.setPrepayStatus(this.FAILED);
            Po.setPrepayStatusName(this.ENGLISHTOCHS(this.FAILED));
            Po.setLastPrepayStatus(omsStatus);
            Po.setFailType(failType);
            Po.setFailReason(agencyRemark);
        }else{
            Po.setPrepayStatus(status);
            Po.setPrepayStatusName(this.ENGLISHTOCHS(status));
            Po.setLastPrepayStatus(omsStatus);
            Po.setAgencyRemark(agencyRemark);
        }


        int i = tmallPrepayDao.editXiaoTitle(Po);

        //操作类，记录操作人，操作时间
        if (i != 0) {
            int operationResult = this.operationHistory(operatorBy,Po.getPrepayStatusName(),"UpdateOrderProgress",prePayId.toString(),agencyRemark,new Date());
//            if(SaleLeadsOrderStatus.FAILED.equals(status)){
//				try {
//					//发送短信
//
//					StoreOfSalEntity store = salesLeadsOrderDao.getStoreBySal(saleLeadsOrderId.toString());
//					if (store != null) {
//						boolean sendMsgSuess = sendMsgService.sendMsgSalFailed(store.getCustomerPhone(), store.getStoreName());
//						if (!sendMsgSuess) {//发送短息失败
//							TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚
//							i = 0;
//						}
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚
//				}

//            }

        }

        return i;
    }

    /**
     *  填写订单信息(提交)
     */
    @Override
    @Transactional
    public int updateTmallOrderInfo(TmallOrderInfoModelIn modelIn) {
        PrepayPo vo = new PrepayPo();
        vo.setPrepayStatus(this.SUCCESS);
        vo.setPrepayStatusName(this.ENGLISHTOCHS(this.SUCCESS));
        Date now = new Date();
        int result = this.update(modelIn,vo);
        //操作类，记录操作人，操作时间
//		if (result != modelIn.getSaleLeadsOrderGoods().size()) {
        int operationResult = this.operationHistory(modelIn.getOperatorBy(),"提交订单信息","updateTmallOrderInfo",modelIn.getTmallOrderId().toString(),"",now);


//			try {
//				//发送短信
//				StoreOfSalEntity store = salesLeadsOrderDao.getStoreBySal(modelIn.getSaleLeadsOrderId().toString());
//				if (store != null ) {
//					boolean sendMsgSuess = sendMsgService.sendMsgSalSuccess(store.getCustomerPhone(), this.priceFormatOfMsg(modelIn.getPayPrice()));
//					if (!sendMsgSuess) {//发送短息失败
//						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚
//						result = 0;
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚
//			}
//		}

        return result;
    }

    /**
     * 填写订单信息(只保存不提交)
     */
    @Override
    @Transactional
    public int saveSalOrderInfo(TmallOrderInfoModelIn form) {
        PrepayPo Po = new PrepayPo();
//        Po.setAgencyListStatus(this.FLLOWING);//appStatus
//        Po.setStoreListStatus(this.FLLOWING);//appStatus
        Po.setPrepayStatus(this.ACCEPTED);
        Po.setPrepayStatusName(this.ENGLISHTOCHS(this.ACCEPTED));
        Date now = new Date();
        int result = this.update(form,Po);
        //操作类，记录操作人，操作时间
//		if (result != modelIn.getSaleLeadsOrderGoods().size()) {
        int operationResult = this.operationHistory(form.getOperatorBy(),"保存订单信息","saveTmallOrderInfo",form.getTmallOrderId().toString(),"",now);


//			try {
//				//发送短信
//				StoreOfSalEntity store = salesLeadsOrderDao.getStoreBySal(modelIn.getSaleLeadsOrderId().toString());
//				if (store != null ) {
//					boolean sendMsgSuess = sendMsgService.sendMsgSalSuccess(store.getCustomerPhone(), this.priceFormatOfMsg(modelIn.getPayPrice()));
//					if (!sendMsgSuess) {//发送短息失败
//						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚
//						result = 0;
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚
//			}
//		}

        return result;
    }

    public static String ENGLISHTOCHS(String status) {
        String out = "";
        if ("AGENCYWAITACCEPT".equals(status)){
            out  = "待接单";
        }
        else if ("FAILED".equals(status)){
            out  = "未成交";
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
        else if ("STOREWAITACCEPT".equals(status)){
            out  = "待接单";
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
            case "联系不上":
                out = "NOTCONTACTED";
                break;
            default:
                out = "";
        }
        return out;
    }

    /**保存操作记录**/
    private  int operationHistory (String operatorBy,String operatorDesc,String operatorType,String prePayId,String remark,Date operateTime){
        //操作记录
        String userName = utilsDao.getOperatorBy(operatorBy);
        TmallOperationEntity operationEntity = new TmallOperationEntity();
        operationEntity.operatorDesc = operatorDesc;
        operationEntity.operatorType = operatorType;
        operationEntity.prePayOrder = prePayId;
        operationEntity.operatorBy = operatorBy;
        operationEntity.operatorByName = userName;
        operationEntity.operatorTimeSave = operateTime;
        operationEntity.operatorRemark = remark;
        int i = tmallPrepayDao.insert(operationEntity);//保存
        return i;

    }


    /**
     * 价钱反格式化
     *
     */
    private  String priceFormatSwitch (String price){
        String result = "";
        if (StringUtils.isEmpty(price) || "0".equals(price)) {
            result = "0";
        }
//		result = price.replaceAll(",","").replace("￥","");
        result =price;
        return result;
    }

    /**
     * 价钱格式化
     *
     */
    private  String priceFormat (String price){
        if (StringUtils.isEmpty(price) || "0".equals(price)) {
            price =   "0";
        }
//		double num = Double.valueOf(price);
//		DecimalFormat format = new DecimalFormat("￥##,###,###,###,##0.00");
//		return format.format(num);
        return price;
    }


    /***
     * 跟新订单信息（通用）
     */
    private int update (TmallOrderInfoModelIn modelIn,PrepayPo po){
        po.setPrePayId(modelIn.getTmallOrderId().toString());//id
        po.setAmount(this.priceFormatSwitch(modelIn.getPayPrice()));//总金额
        po.setOrderEvaluation(modelIn.getOrderEvaluation());//订单评价
        po.setIsLoss(modelIn.getIsLoss());//流失情况
        po.setRevisitTime(modelIn.getRevisitTime());//回访时间
        po.setPictureNames(JSON.toJSONString(modelIn.getPictureNames()));//图片单据
        //更新备注信息 (需要判断门店or经销商)
        String isAgency = tmallPrepayDao.getIsAgency(modelIn.getTmallOrderId().toString());
        if ("N".equals(isAgency)) {
            po.setStoreRemark(modelIn.getRemark());
        } else {
            po.setAgencyRemark(modelIn.getRemark());
        }
        //更新特权订单表
        int tmallOrderResult = tmallPrepayDao.editXiaoTitle(po);

        int result = 0;

        //更改已删除商品状态
        if(modelIn.getDelProductIds()!=null){
            int flag= tmallPrepayDao.delete(modelIn.getDelProductIds());
        }

        //2、判断入参中的商品id是否存在以上集合
        if (modelIn.getTmallOrderGoods() !=null){
            //1、获取该订单所含有的商品的id
            List<TmallOrderProductModeIn> itemIds = tmallPrepayDao.getTmallOrderProductList(modelIn.getTmallOrderId().toString());

            List<String> ids = new ArrayList<>();
            for (TmallOrderProductModeIn tmallOrderProductModeIn : itemIds){
                ids.add(tmallOrderProductModeIn.getShangpId());
            }
            for ( TmallOrderGoodsModelIn goodsModelIn : modelIn.getTmallOrderGoods()) {
                    if(ids.contains(goodsModelIn.getProductId())){
                        TmallOrderProductModeIn model = tmallPrepayDao.getProduct(goodsModelIn.getProductId());
                        model.setUnitPrice(goodsModelIn.getUnitPrice());
                        model.setQuantity(String.valueOf(Integer.valueOf( model.getQuantity())+ Integer.valueOf(goodsModelIn.getQuantity())));//若包含商品,修改数量与总金额
                        model.setAmount(String.valueOf(Double.valueOf( model.getQuantity())* Double.valueOf( goodsModelIn.getUnitPrice())));
                        //更新商品
                        int up = tmallPrepayDao.updateTmallOrderProduct( model);
                    }else{
                         TmallOrderGoodsPo itemsPo = new TmallOrderGoodsPo();
                        CglibBeanCopierUtils.copyProperties(goodsModelIn, itemsPo);
                        int add =  tmallPrepayDao.addTmallOrderProduct(itemsPo,modelIn.getTmallOrderId().toString());
                    }

            }
        }

        return result == 0?tmallOrderResult:result;
    }

}
