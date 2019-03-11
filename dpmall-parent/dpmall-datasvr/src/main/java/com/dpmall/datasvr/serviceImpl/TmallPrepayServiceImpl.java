package com.dpmall.datasvr.serviceImpl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dpmall.common.HybrisUtils;
import com.dpmall.datasvr.api.ITmallPrepayService;
import com.dpmall.db.bean.TmallPrePayDetailEntity;
import com.dpmall.db.bean.TmallPrePayEntity;
import com.dpmall.db.bean.po.PrepayPo;
import com.dpmall.db.dao.TmallPrepayDao;
import com.dpmall.db.dao.UtilsDao;
import com.dpmall.model.prePay.TmallPrePayDetailModel;
import com.dpmall.model.prePay.TmallPrePayModel;
import com.dpmall.model.prePay.TmallPrepayListCountModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service(value = "tmallPrepayService")
public class TmallPrepayServiceImpl implements ITmallPrepayService {

    @Autowired
    private TmallPrepayDao tmallPrepayDao;

    @Autowired
    private UtilsDao utilsDao;

    @Autowired
    private HybrisUtils hybrisUtils;

    /**
     * 工具类
     */
    private TmallPrePayModel entityToModel(TmallPrePayEntity in) {
        TmallPrePayModel out = new TmallPrePayModel();
        if (in == null) {
            return out;
        }
        BeanUtils.copyProperties(in, out);
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Date newd = sdf.parse(in);
            Calendar ca = Calendar.getInstance();
            ca.setTime(newd);
            ca.add(Calendar.YEAR, 1);
            buffer.append(sdf.format(ca.getTime()));
            out = buffer.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return out;
    }

    /**
     * 刷新状态
     */
    private   StringBuffer  refreshStatus(String tableName,String pk) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("type", tableName);
        params.put("pk", pk);

        // 调用hybris方法
        String resultStr = hybrisUtils.refreshStatus(params);
        JSONObject jsonObject = JSON.parseObject(resultStr);
        Map<String, Object> resultMap = (Map) jsonObject;
        StringBuffer result = new StringBuffer(resultMap.get("resultCode").toString());
        return result;
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
                listCount = tmallPrepayDao.getListCount(agencyId, "", "Y", String.valueOf(i), "");
            } else {
                listCount = tmallPrepayDao.getListCount("", storeId, "N", String.valueOf(i), "");
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

    private  int acceptOrWithdraw ( List<String>prepayIds, String agencyListStatus,String storeListStatus,String prepayStatus,String storeId,String isDeliverySelf,String isAccept){
        int out = 0;
        StringBuffer consignmentBuffer = new StringBuffer();
        StringBuffer o2oBuffer = new StringBuffer();

        for (String prePayId : prepayIds) {
            PrepayPo vo = new PrepayPo();
            vo.setPrePayId(prePayId);
            vo.setAgencyListStatus(agencyListStatus);
            vo.setStoreListStatus(storeListStatus);
            vo.setSuggestStore(storeId);
            vo.setIsDeliverySelf(isDeliverySelf);
            //如果第三方状态为“已完成”，发货状态修改为“已发货”
            if ("TRADE_FINISHED".equals(tmallPrepayDao.getSfStatusByPrePayId(prePayId)) || "FINISHED_L".equals(tmallPrepayDao.getSfStatusByPrePayId(prePayId))){
                prepayStatus = "SHIPPED";
            }
            //如果第三方状态为“已关闭”，发货状态修改为“已终止”
            else if ("TRADE_CLOSED".equals(tmallPrepayDao.getSfStatusByPrePayId(prePayId)) || "TRADE_CANCELED".equals(tmallPrepayDao.getSfStatusByPrePayId(prePayId))){
                prepayStatus = "ABORTED";
            }
            String statuspk = utilsDao.getStatusPk(prepayStatus);
            vo.setPrepayStatus(statuspk);

            vo.setTmallAcceptTime(isAccept);
            int j= tmallPrepayDao.editO2oConsignment(vo);
            int i = tmallPrepayDao.editConsignment(vo);
            out += i;

            //刷新oms缓存
            if (i > 0 && j > 0) {
                consignmentBuffer = refreshStatus("Consignment", prePayId);//刷新状态~
                o2oBuffer = refreshStatus("O2OConsignmentItems", this.getO2oIdByConsignmentId(prePayId));//刷新状态~
            }
            //刷新失败，回滚事务
            if (!"200".equals(consignmentBuffer.toString()) || !"200".equals(o2oBuffer.toString())){
               out = 0;
            }
        }

        return out;
    }

    /**
     * ------------------------------------------------------------------------------------------------------------------------------------
     **/
    @Override
    public List<TmallPrePayModel> getListOfAgency(String agencyId, String status, int pageNum, int pageSize, String search) {
        List<TmallPrePayModel> out = new ArrayList<>();
        String listCount = tmallPrepayDao.getListCount(agencyId, "", "Y", status, search);
        if ("0".equals(listCount)) {
            return out;
        }
        List<TmallPrePayEntity> entities = tmallPrepayDao.getList(agencyId, "", "Y", status, pageNum, pageSize, search);
        for (TmallPrePayEntity entity : entities) {
            TmallPrePayModel model = new TmallPrePayModel();
            model = entityToModel(entity);
            model.setListCount(listCount);
            if (StringUtils.isEmpty(entity.getSuggestStoreName())) {
                model.setSuggestStoreName(tmallPrepayDao.getAgencyNameByPk(agencyId));
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
    public List<TmallPrePayModel> getListOfStore(String storeId, String status, int pageNum, int pageSize, String search) {
        List<TmallPrePayEntity> entities = tmallPrepayDao.getList("", storeId, "N", status, pageNum, pageSize, search);
        String listCount = tmallPrepayDao.getListCount("", storeId, "N", status, search);
        List<TmallPrePayModel> out = new ArrayList<>();
        if ("0".equals(listCount)) {
            return out;
        }
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
                out = this.acceptOrWithdraw(prepayId, "COMPLETED", "", "ACCEPT", "","Y",isAccept);
            } else {
                out = this.acceptOrWithdraw(prepayId, "WAITFORRECEIVE", "WAITFORRECEIVE", "STOREWAIT", storeId,"N",isAccept);
            }
        } else {
            out = this.acceptOrWithdraw(prepayId, "COMPLETED", "COMPLETED", "ACCEPT", "","N",isAccept);
        }

        if (out<1){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动开启事务回滚
        }

        return out;
    }


    @Override
    @Transactional
    public int withdraw(String prepayId, String isAgency) {
        int out = 0;
        List<String> ids = new ArrayList<>();
        ids.add(prepayId);

        if ("Y".equals(isAgency)) {
            out = this.acceptOrWithdraw(ids, "WAITFORRECEIVE", "", "DISTRIBUTED", "","Y","N");
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
        BeanUtils.copyProperties(in,out);
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
            out.setSuggestStoreName(utilsDao.getNameAgencyId(in.getAgencyId()));
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
        int i = tmallPrepayDao.editO2oConsignment(Po);

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
}
