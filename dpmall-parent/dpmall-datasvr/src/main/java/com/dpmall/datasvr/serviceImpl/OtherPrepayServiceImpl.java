package com.dpmall.datasvr.serviceImpl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dpmall.common.DateUtils;
import com.dpmall.common.HybrisUtils;
import com.dpmall.common.OssInfoUtils;
import com.dpmall.common.PrivateKeyUtils;
import com.dpmall.datasvr.api.IOthersPrepayService;
import com.dpmall.db.bean.*;
import com.dpmall.db.bean.po.OthersPrePayItemsPo;
import com.dpmall.db.bean.po.PrePayOperationPo;
import com.dpmall.db.bean.po.PrepayPo;
import com.dpmall.db.dao.OthersPrepayDao;
import com.dpmall.db.dao.OthersPrepayItemsDao;
import com.dpmall.db.dao.UtilsDao;
import com.dpmall.db.dao.PrePayOperationDao;
import com.dpmall.enums.PrePayStatus;
import com.dpmall.model.OthersPrePayItemsModel;
import com.dpmall.model.SaleLeadsOrderPictureModel;
import com.dpmall.model.in.OtherPrePayInfoModelIn;
import com.dpmall.model.in.OthersPrePayItemsModelIn;
import com.dpmall.model.in.PrepayModelIn;
import com.dpmall.model.prePay.*;
import com.dpmall.param.SaleLeadsOrderStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service(value = "othersPrepayService")
public class OtherPrepayServiceImpl implements IOthersPrepayService {

    @Autowired
    private OthersPrepayDao othersPrepayDao;

    @Autowired
    private OthersPrepayItemsDao othersPrepayItemsDao;

    @Autowired
    private PrePayOperationDao prePayOperationDao;

    @Autowired
    private UtilsDao utilsDao;

    @Autowired
    private HybrisUtils hybrisUtils;



    /**
     * 工具类
     */
    private OthersPrePayModel entityToModel(OthersPrePayEntity in) {
        OthersPrePayModel out = new OthersPrePayModel();
        if (in == null) {
            return out;
        }
        BeanUtils.copyProperties(in, out);
        out.setPrepayStatusName(SaleLeadsOrderStatus.ENGLISHTOCHS(out.getPrepayStatus()));
        return out;
    }

    private List<OthersPrePayModel> entityListToModleList(List<OthersPrePayEntity> entities) {
        List<OthersPrePayModel> out = new ArrayList<>();
        if (entities == null && CollectionUtils.isEmpty(entities)) {
            return out;
        }
        for (OthersPrePayEntity entity : entities) {
            OthersPrePayModel model = new OthersPrePayModel();
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
        if (StringUtils.isEmpty(in)) {
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
            ca.add(Calendar.DAY_OF_MONTH, 45);
            buffer.append(sdf.format(ca.getTime()));
            out = buffer.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return out;
    }



    /**
     * 获取列表数量
     */
    private OthersPrepayListCountModel getListCount(String isAgency, String agencyId, String storeId) {
        OthersPrepayListCountModel countModel = new OthersPrepayListCountModel();
        for (int i = 1; i <= 3; i++) {//3种列表状态
            String listCount = "0";
            //经销商or门店
            if ("Y".equals(isAgency)) {
                listCount = othersPrepayDao.getListCount(agencyId, "", "Y", String.valueOf(i), "");
            } else {
                listCount = othersPrepayDao.getListCount("", storeId, "N", String.valueOf(i), "");
            }
            //赋值
            if (i == 1) {
                countModel.setWaittingCount(listCount);
            } else if (i == 2) {
                countModel.setFllowingCount(listCount);
            } else if(i == 3){
                countModel.setCompletedCount(listCount);
            }
        }
        return countModel;
    }


    /**
     * 待接单
     */
    private   List<OthersPrePayModel> waitAcceptOfList(List<OthersPrePayEntity> in){
        List<OthersPrePayModel> out = new ArrayList<OthersPrePayModel>();
        //处理电话号码
        StringBuffer buffer = new StringBuffer();
        if (CollectionUtils.isNotEmpty(in)){
            for (OthersPrePayEntity entity :in){
                OthersPrePayModel model = new OthersPrePayModel();

                buffer.append(entity.getPhone());
                buffer.replace(3,7,"****");
                entity.setPhone(buffer.toString());
                buffer.delete(0,buffer.length());//清空buffer
                model = this.entityToModel(entity);
                model.setOrderPlatform(this.orderPlatForm(entity.getOrderPlatform()));
                out.add(model);
            }
        }
        return out;
    }

    /**
     * 跟进中
     */
    private   List<OthersPrePayModel> fllowingOfList(List<OthersPrePayEntity> in,String agencyId){
        List<OthersPrePayModel> out = new ArrayList<OthersPrePayModel>();
        if (CollectionUtils.isEmpty(in)){
            return out;
        }
        for (OthersPrePayEntity entity : in){
            OthersPrePayModel model =new OthersPrePayModel();
            //属于经销商 并 订单下派至门店
//            if ("N".equals(entity.getIsAgency())&&StringUtils.isNotEmpty(agencyId)) {//下派至门店
//                entity.setStatusDescription("门店:"+entity.getStatusDescription());
//            }
            model = this.entityToModel(entity);
            model.setOrderPlatform(this.orderPlatForm(entity.getOrderPlatform()));
//            model.setPrepayStatusName(PrePayStatus.CHSTOENGLISH());(显示状态中文~)
            out.add(model);
        }
        return out;
    }


    /**
     * 已完成
     */
    private   List<OthersPrePayModel> completedOfList(List<OthersPrePayEntity> in){
        List<OthersPrePayModel> out = new ArrayList<OthersPrePayModel>();
        if (CollectionUtils.isEmpty(in)){
            return out;
        }
        for (OthersPrePayEntity entity : in){
            OthersPrePayModel model =new OthersPrePayModel();
            model = this.entityToModel(entity);

            //为上一个订单状态赋值
            if (StringUtils.isNotEmpty(model.getLastPrepayStatus())){
                model.setLastPrepayStatus(SaleLeadsOrderStatus.ENGLISHTOCHS(model.getLastPrepayStatus()));
            }
            //格式化总价格
            if(StringUtils.isNotEmpty(model.getPayPrice())){
                model.setPayPrice(this.priceFormat(Double.valueOf(model.getPayPrice())));
            }
            if (StringUtils.isEmpty(entity.getStoreId()) || "1".equals(entity.getStoreId())) {
                model.setServiceStore(entity.getAgencyName());//门店名称显示为经销商名称
            }
            //如果上个状态为空，则是待处理
            if (StringUtils.isEmpty(entity.getLastPrepayStatus())){
                model.setLastPrepayStatus(SaleLeadsOrderStatus.ENGLISHTOCHS(SaleLeadsOrderStatus.ACCEPTED));
            }
            out.add(model);
        }
        return out;
    }

    private String orderPlatForm (String in){
        String out = "";
        if ("JD".equals(in)){
            return "京东商城";
        }else  if ("SUNING".equals(in)){
            return "苏宁商城";
        }else  if ("Web".equals(in)){
            return "东鹏商城";
        }else  if ("VIP".equals(in)){
            return "唯品会";
        }
        return "";
    }

    /**
     * 价钱格式化
     *
     */
    private  String priceFormat (Double num){
        if (num == null||num==0)	{
            return "0";
        }
//		DecimalFormat format = new DecimalFormat("￥##,###,###,###,##0.00");
//		return format.format(num);
        return num.toString();
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

    /**
     * 图片处理
     */
    private List<SaleLeadsOrderPictureModel> pictrueUtils (String in){
        List<SaleLeadsOrderPictureModel> out = new ArrayList<>();
        if (StringUtils.isEmpty(in)){
            return  out;
        }

        out = JSON.parseArray(in,SaleLeadsOrderPictureModel.class);

        List<SaleLeadsOrderPictureModel> result = new ArrayList<>();
        if (out== null){
            return result;
        }

        //拼接url
        StringBuffer buffer = new StringBuffer();
        for(SaleLeadsOrderPictureModel picture :out){
            buffer.append(OssInfoUtils.getOssUrl());
            buffer.append(picture.getPictureUrl());
            picture.setPictureUrl(buffer.toString());
            result.add(picture);
            buffer.delete(0,buffer.length());//清空buffer
        }
        return out;
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
     * 实体类转Map
     */
    private  Map<String,Object> EntityToMap (Object obj){
        Map<String,Object> out = new HashMap<>();
        if (obj == null) return null;
        Field[] fields = obj.getClass().getDeclaredFields();
        try {
            for (int i = 0;i<fields.length;i++){
                try {
                    Field f = obj.getClass().getDeclaredField(fields[i].getName());
                    f.setAccessible(true);
                    Object o = f.get(obj);
                    if (o==null){
                        continue;
                    }
                    out.put(fields[i].getName(), o);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }catch (SecurityException e) {
            e.printStackTrace();
        }

        return out;
    }

    /***
     * 调用hybris接口保存明细
     */
    private  int savePrePayItems (OthersPrePayItemsPo po ) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map = this.EntityToMap(po);
        String resultStr = hybrisUtils.savePrePayItems(map);
        JSONObject json1 = JSON.parseObject(resultStr);
        Map<String,Object> map1 = (Map)json1;
        if ( !"200".equals(map1.get("resultCode").toString())){
//            return 0;
            throw new Exception(map1.get("message").toString());
        }

        JSONObject jsonObject = JSON.parseObject(map1.get("data").toString());
        Map<String,Object> resultMap = (Map)jsonObject;
        Object result = resultMap.get("value");
        return Integer.valueOf(result.toString());
    }

    /***
     * 跟新订单信息（通用）
     */
    private int update(OtherPrePayInfoModelIn modelIn, PrepayPo po) {
        po.setPrePayId(modelIn.getPrePayId());//id
        po.setPayPrice(this.priceFormatSwitch(modelIn.getPayPrice()));//支付金额
        po.setDiscountPrice(this.priceFormatSwitch(modelIn.getDiscountPrice()));//优惠金额
        if (modelIn.getPictureNames() != null) {
            po.setPictureNames(JSON.toJSONString(modelIn.getPictureNames()));//图片单据
        }


        //更新备注信息 (需要判断门店or经销商)
        String isAgency = othersPrepayDao.getIsAgency(modelIn.getPrePayId());
        if ("N".equals(isAgency)) {
            po.setStoreRemark(modelIn.getRemark());
        } else {
            po.setAgencyRemark(modelIn.getRemark());
        }


        //1、更新特权表
        int o2oResult = othersPrepayDao.editO2oConsignment(po);
        int consignmentResult = othersPrepayDao.editConsignment(po);

        //2、获取该留资单所含有的商品的id
        int result = 0;
        Set<String> itemIds = othersPrepayItemsDao.getItemIdsByPrePayId(modelIn.getPrePayId().toString());

        //3、判断入参中的商品id是否存在以上集合

        try {
            for (OthersPrePayItemsModelIn goodsModelIn : modelIn.getOthersPrePayItems()) {
                OthersPrePayItemsPo itemsPo = new OthersPrePayItemsPo();
                //复制对象
                BeanUtils.copyProperties(goodsModelIn, itemsPo);
                itemsPo.setPrePayId(modelIn.getPrePayId());//留资单id
                itemsPo.setTotalPrice(this.priceFormatSwitch(goodsModelIn.getTotalPrice()));//合计金额
                itemsPo.setUnitPrice(this.priceFormatSwitch(goodsModelIn.getUnitPrice()));//单价
                if ("new".equals(goodsModelIn.getItemsId())) {
                    //添加
                    itemsPo.setItemsId("0");//替换“new”，以防格式错误
                    int insert = this.savePrePayItems(itemsPo);
                    result += insert;
                } else {
                    if (itemIds.contains(goodsModelIn.getItemsId())) {//存在
                        //更新
                        itemIds.remove(goodsModelIn.getItemsId());//从set中去除元素
    //                    itemsPo.setIsDelete("0");
                        int update = this.savePrePayItems(itemsPo);
                        result += update;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动开启事务回滚
            result = 0;
        }


        if (itemIds != null && itemIds.size() > 0) {
            //删除
            int delete = othersPrepayItemsDao.delete(itemIds);
        }

        return result;

    }

    /**保存操作记录**/
    private  int operationHistory (String operator,String prePayId,String operatorType,String operatorTypeName,String operationTypeDesc,String remark){
        //操作记录
        OperatorRoleEntity role = utilsDao.getOperatorByRole(operator);

        PrePayOperationPo po = new PrePayOperationPo();
        po.setId(PrivateKeyUtils.getPkByRandomStr());
        po.setPrePayId(prePayId);
        po.setOperator(operator);
        po.setOperatorName(role.getUserName());
        po.setOperationType(operatorType);
        po.setOperationTypeName(operatorTypeName);
        po.setOperationTypeDesc(operationTypeDesc);
        po.setOperatorRemark(remark);
        po.setAgencyCode( role.getAgencyCode());

        if (!"agency".equals(role.getRole())) {
            po.setAcceptedCode(role.getStoreName());
            po.setOperatorName(role.getStoreName());
        }
        int i = prePayOperationDao.insert(po);//保存
        return i;

    }


    /**
     * ------------------------------------------------------------------------------------------------------------------------------------
     **/
    @Override
    public List<OthersPrePayModel> getListOfAgency(String agencyId, String status, int pageNum, int pageSize, String search) {
        List<OthersPrePayEntity> in = othersPrepayDao.getList(agencyId, "", "Y", status, pageNum, pageSize, search);
        String listCount = othersPrepayDao.getListCount(agencyId, "", "Y", status, search);
        List<OthersPrePayModel> out = new ArrayList<>();
        if ("0".equals(listCount)) {
            return out;
        }
        if("1".equals(status)){
            out = this.waitAcceptOfList(in);
        }else if("2".equals(status)){
            out = this.fllowingOfList(in,agencyId);
        }else if("3".equals(status)){
            out = this.completedOfList(in);
        }
        if (out == null ||out.size()==0){
            return out;
        }
        out.get(0).setListCount(listCount);//列表数量，只有第一个元素有
        return out;
    }

    @Override
    public List<OthersPrePayModel> getListOfStore(String storeId, String status, int pageNum, int pageSize, String search) {
        List<OthersPrePayEntity> in = othersPrepayDao.getList("", storeId, "N", status, pageNum, pageSize, search);
        String listCount = othersPrepayDao.getListCount("", storeId, "N", status, search);
        List<OthersPrePayModel> out = new ArrayList<>();
        if ("0".equals(listCount)) {
            return out;
        }
        if("1".equals(status)){
            out = this.waitAcceptOfList(in);
        }else if("2".equals(status)){
            out = this.fllowingOfList(in,"");

        }else if("3".equals(status)){
            out = this.completedOfList(in);
        }
        if (out == null ||out.size()==0){
            return out;
        }
        out.get(0).setListCount(listCount);//列表数量，只有第一个元素有
        return out;
    }


    @Override
    public OthersPrepayListCountModel getListCountOfAgency(String agencyId) {
        return this.getListCount("Y", agencyId, "");
    }

    @Override
    public OthersPrepayListCountModel getListCountOfStore(String storeId) {
        return this.getListCount("N", "", storeId);
    }

    @Override
    public OthersPrePayDetailModel getDetails(String prepayId) {
        OthersPrePayDetailModel result =new OthersPrePayDetailModel();
        //1.获取详情
        OthersPrePayDetailEntity detailsIn  = othersPrepayDao.getDetails(prepayId);
        if (detailsIn == null){
            return result;
        }
        //赋值
        BeanUtils.copyProperties(detailsIn,result);
        result.setOrderPlatform(this.orderPlatForm(detailsIn.getOrderPlatform()));
        result.setPrepayStatusDescription(SaleLeadsOrderStatus.ENGLISHTOCHS(detailsIn.getPrepayStatus()));
        result.setLastPrePayStatus(SaleLeadsOrderStatus.ENGLISHTOCHS(result.getLastPrePayStatus()));
        if("Y".equals(detailsIn.getIsAgency())){
            result.setAcceptTime(detailsIn.getAgencyAcceptTime());
        }

        String isDpMall = "N";
        if ("Web".equals(detailsIn.getOrderPlatform())){
            isDpMall = "Y";
        }
        result.setIsDpMall(isDpMall);


        //待接单
        if (PrePayStatus.WAITTING.equals(detailsIn.getListStatusOfAgency()) || PrePayStatus.WAITTING.equals(detailsIn.getListStatusOfStore())){
            StringBuffer buffer = new StringBuffer();
            buffer.append(result.getPhone());
            buffer.replace(3, 7, "****");
            result.setPhone(buffer.toString());
        }

        //已完成 或 图片有值 显示详情
        if (PrePayStatus.COMPLETEDLIST.equals(detailsIn.getListStatusOfAgency())
                || PrePayStatus.COMPLETEDLIST.equals(detailsIn.getListStatusOfStore())
                ||StringUtils.isNotEmpty(detailsIn.getPictureNames())){
            //获取商品列表
            List<OthersPrePayItemsEntity> item = othersPrepayItemsDao.getItemByPrePayId(prepayId);
            List<OthersPrePayItemsModel> itemModels = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(item)){

                double orderPrice = 0;
                for (OthersPrePayItemsEntity entity : item){
                    OthersPrePayItemsModel model = new OthersPrePayItemsModel();
                    BeanUtils.copyProperties(entity,model);
                    model.setTotalPrice(this.priceFormat(entity.getTotalPrice()));
                    itemModels.add(model);
                    orderPrice +=(entity.getTotalPrice() == null?0:entity.getTotalPrice());//计算
                }
                result.setItems(itemModels);
                //订单总金额
                result.setOrderPrice(this.priceFormat(orderPrice));
                //优惠金额
                result.setDiscountPrice(this.priceFormat(detailsIn.getDiscountPrice()));
                //支付金额
                if ("0".equals(result.getDiscountPrice())){
                    result.setPayPrice(result.getOrderPrice());
                }else{
                    result.setPayPrice(this.priceFormat((orderPrice-Double.valueOf(detailsIn.getDiscountPrice()))));
                }

            }
            //获取图片
            result.setPictureNames(this.pictrueUtils(detailsIn.getPictureNames()));
        }
        //更改列表状态在前端显示名称
        if (PrePayStatus.WAITTING.equals(detailsIn.getListStatusOfAgency())){
            result.setListStatusOfAgency("WAITTING");
        }else if (PrePayStatus.FLLOWING.equals(detailsIn.getListStatusOfAgency())){
            result.setListStatusOfAgency("FLLOWING");
        }else if (PrePayStatus.COMPLETEDLIST.equals(detailsIn.getListStatusOfAgency())){
            result.setListStatusOfAgency("COMPLETED");
        }

        if (PrePayStatus.WAITTING.equals(detailsIn.getListStatusOfStore())){
            result.setListStatusOfStore("WAITTING");
        }else if (PrePayStatus.FLLOWING.equals(detailsIn.getListStatusOfStore())){
            result.setListStatusOfStore("FLLOWING");
        }else if (PrePayStatus.COMPLETEDLIST.equals(detailsIn.getListStatusOfStore())){
            result.setListStatusOfStore("COMPLETED");
        }



        //2.获取部分操作记录
        //2-1 获取A部分操作记录
        List<String> operaRecordA = new ArrayList<>();
        if(StringUtils.isNotEmpty(detailsIn.getDistributer())){
            operaRecordA.add("派单人:"+detailsIn.getDistributer());
        }
        if(StringUtils.isNotEmpty(detailsIn.getAgencyAccept())){
            operaRecordA.add("接单经销商:"+detailsIn.getAgencyAccept());
        }
        if(StringUtils.isNotEmpty(detailsIn.getStoreAccept())){
            operaRecordA.add("接单门店:"+detailsIn.getStoreAccept());
        }
        if(StringUtils.isNotEmpty(detailsIn.getOrderGuide())){
            operaRecordA.add("接单导购:"+detailsIn.getOrderGuide());
        }
        if(StringUtils.isNotEmpty(detailsIn.getCreatedTime())){
            operaRecordA.add("建单时间:"+detailsIn.getCreatedTime());
        }
        if(StringUtils.isNotEmpty(detailsIn.getOrdersTime())){
            operaRecordA.add("派单时间:"+detailsIn.getOrdersTime());
        }
        if(StringUtils.isNotEmpty(detailsIn.getAgencyAcceptTime())){
            operaRecordA.add("经销商接单时间:"+detailsIn.getAgencyAcceptTime());
        }
        if(StringUtils.isNotEmpty(detailsIn.getStoreAcceptTime())){
            operaRecordA.add("门店接单时间:"+detailsIn.getStoreAcceptTime());
        }

        //2-2 获取状态和更新时间(B部分)
        List<OtherPrePayOperationDetailEntity> statusAndTimeIn = prePayOperationDao.getTimeAndStatus(prepayId);
        List<OtherPrePayOperationDetailModel>  operaRecordB = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(statusAndTimeIn)){
            StringBuilder builder=new StringBuilder();
            for (OtherPrePayOperationDetailEntity entity : statusAndTimeIn){
                OtherPrePayOperationDetailModel model = new OtherPrePayOperationDetailModel();
                BeanUtils.copyProperties(entity,model);
                //拼接状态描述 如："订单“已联系”时间:"
//				builder.append("订单“");
//				builder.append(entity.getStatus());
//				builder.append("”时间:");
//				model.setStatus(builder.toString());
//				builder.delete(0,builder.length());//清空builder

                operaRecordB.add(model);
            }
        }

        //赋值
        OtherPrepayOperationModel operationModel = new OtherPrepayOperationModel();
        operationModel.setOperationRecord(operaRecordA);
        operationModel.setStatusAndUpStringTime(operaRecordB);
        result.setOperationRecord(operationModel);



        return result;
    }

    /**
     * 批量接单
     *  prepayId：id
     *  agencyListStatus：经销商列表状态
     *  storeListStatus门店状态
     *  prepayStatus：特权订金状态
     *  storeId ：门店id
     */
    private  int accept ( List<String>prepayId, String agencyListStatus,String storeListStatus,
                          String prePayStatus,String orderStatus, String storeId,String isDeliverySelf,String lastStatus,
                          String operatorBy,String operatorType,String operatorTypeName,String operationTypeDesc){
        int out = 0;
        for (String id : prepayId) {
            PrepayPo po = new PrepayPo();
            po.setPrePayId(id);
            po.setPrepayStatus(prePayStatus);
            if ("Y".equals(isDeliverySelf)){
                 po.setAgencyAcceptTime(new Date());
                 po.setAgencyAccept(operatorBy);
            }else {
                 po.setAcceptTime(new Date());
                po.setStoreListStatus(storeListStatus);
            }
            po.setLastPrepayStatus(lastStatus);
            po.setAgencyListStatus(agencyListStatus);
            po.setSuggestStore(storeId);
            po.setIsDeliverySelf(isDeliverySelf);
            String statuspk = othersPrepayDao.getStatusPk(orderStatus);
            po.setOrderStatus(statuspk);
            othersPrepayDao.editO2oConsignment(po);
            int i = othersPrepayDao.editConsignment(po);
            out += i;

            int operationResult = this.operationHistory(operatorBy,id,operatorType,operatorTypeName,operationTypeDesc,"");


        }
        return out;
    }

    @Override
    @Transactional
    public int agencyAccept(List<String> prepayId, String storeId,String operatorBy) {
        String operatorType = "Others-AgencyAccept";
        String operatorTypeName = "经销商接单";
        String operationTypeDesc = "其他特权定金-经销商接单";

        int out = 0;
        //经销商
        //判断是否自己仓库发货

        if ("1".equals(storeId)) {
            out = this. accept(prepayId, PrePayStatus.FLLOWING, "",SaleLeadsOrderStatus.ACCEPTED, PrePayStatus.ACCEPT, "", "Y",SaleLeadsOrderStatus.ACCEPTED,operatorBy,operatorType,operatorTypeName,operationTypeDesc);
        } else {
            out = this.accept(prepayId, PrePayStatus.FLLOWING, PrePayStatus.WAITTING, SaleLeadsOrderStatus.STOREWAITACCEPT,PrePayStatus.STOREWAIT, storeId, "N",SaleLeadsOrderStatus.STOREWAITACCEPT,operatorBy,operatorType,operatorTypeName,operationTypeDesc);
        }


        return out;
    }

    @Override
    @Transactional
    public int storeAccept(List<String> idList,String operatorBy) {
        String operatorType = "Others-StoreAccept";
        String operatorTypeName = "门店接单";
        String operationTypeDesc = "其他特权定金-门店接单";

        int out = 0;
        out = this.accept(idList, PrePayStatus.FLLOWING, PrePayStatus.FLLOWING, SaleLeadsOrderStatus.ACCEPTED,PrePayStatus.ACCEPT, "", "N",SaleLeadsOrderStatus.ACCEPTED,operatorBy,operatorType,operatorTypeName,operationTypeDesc);
        return out;
    }


    /**
     * 撤回（仅经销商操作）
     */
    @Override
    @Transactional
    public int withdraw(String prepayId ,String operatorBy) {
        int out = 0;
        PrepayPo po = new PrepayPo();
        po.setPrePayId(prepayId);
        po.setAgencyListStatus(PrePayStatus.WAITTING);
        String statuspk = othersPrepayDao.getStatusPk(PrePayStatus.DISTRIBUTED);
        po.setOrderStatus(statuspk);
        po.setPrepayStatus(SaleLeadsOrderStatus.AGENCYWAITACCEPT);
        othersPrepayDao.withdrawO2oConsignment(po);
        out = othersPrepayDao.withdrawConsignment(po);

        int operationResult = this.operationHistory(operatorBy,prepayId,"Others-Withdraw","撤回","其他特权定金-撤回","");


        return out;
    }


    @Override
    @Transactional
    public int updateCustomInfo(PrepayModelIn modelIn,String operatorBy) {
        PrepayPo po = new PrepayPo();
        BeanUtils.copyProperties(modelIn,po);

        //保存客户地址信息
        po.setAddressId(othersPrepayDao.getAddressId(po.getPrePayId()));
        if (StringUtils.isNotEmpty(modelIn.getRegion())){
            po.setRegion(utilsDao.getPkByRegionName(modelIn.getRegion()));
            po.setCity(utilsDao.getPkByCityName(modelIn.getCity()));
            po.setDistrict(utilsDao.getPkByDistrictName(modelIn.getDistrict()));
        }else{//确保字段为空
            po.setRegion("");
            po.setCity("");
            po.setDistrict("");
        }

       int result =  othersPrepayDao.editConsignment(po);//更新consignment
        othersPrepayDao.updateAddress(po);//更新地址

        int operationResult = this.operationHistory(operatorBy,modelIn.getPrePayId(),"Others-UpdateCustomInfo","更新客户信息","其他特权定金-更新客户信息","");

        return result;
    }

    @Override
    @Transactional
    public int updateOrderProgress(String statusName, String prepayId, String remark, String operatorBy, String failType) {
        if (StringUtils.isEmpty(prepayId)) { //
            return 0;
        }

        //英文--中文
        String prepayStatus = SaleLeadsOrderStatus.CHSTOENGLISH(statusName);//可与留资状态共用
        if (StringUtils.isEmpty(prepayStatus)) {
            return 0;
        }
        //查询上一个状态
        String lastStatus = othersPrepayDao.getLastStatus(prepayId);

        PrepayPo po = new PrepayPo();
        po.setPrePayId(prepayId);
        po.setPrepayStatus(prepayStatus);
        String statuspk = othersPrepayDao.getStatusPk(PrePayStatus.ACCEPT);//订单状态为已接单
        po.setOrderStatus(statuspk);
       //若历史状态为空，赋值当前状态
        if(StringUtils.isEmpty(lastStatus)){
            po.setLastPrepayStatus(prepayStatus);
        }else{
            po.setLastPrepayStatus(lastStatus);

        }

        if(SaleLeadsOrderStatus.FAILED.equals(prepayStatus)){
            po.setAgencyListStatus(SaleLeadsOrderStatus.COMPLETED);
            po.setStoreListStatus(SaleLeadsOrderStatus.COMPLETED);
            statuspk = othersPrepayDao.getStatusPk(PrePayStatus.NOTTRADED);//订单状态为未成交
            po.setOrderStatus(statuspk);
            po.setFailType(failType);
            po.setFailReason(remark);
        }

        int i = othersPrepayDao.editConsignment(po);
        othersPrepayDao.editO2oConsignment(po);

        int operationResult = this.operationHistory(operatorBy,prepayId,"Others-UpdateOrderProgress","更新订单进度",statusName,remark);


        return i;
    }

    /**
     * 填写订单信息(提交)
     */
    @Override
    @Transactional
    public int updatePrePayOrderInfo(OtherPrePayInfoModelIn modelIn,String operatorBy) {

        PrepayPo po = new PrepayPo();

        po.setAgencyListStatus(PrePayStatus.COMPLETEDLIST);//appStatus
        po.setStoreListStatus(PrePayStatus.COMPLETEDLIST);//appStatus
        po.setPrepayStatus(SaleLeadsOrderStatus.SUCCESS);//特权订金状态

        //更新发货单状态。由于oms无法显示“已成功”，因此和天猫保持一致，最终发货单状态为“已发货”
        Map<String,Object>params = new HashMap<>();
        params.put("type", "ConsignmentStatus");
        params.put("statusName", PrePayStatus.SHIPPED);
        String statusPk =  hybrisUtils.getStatusPkByNames(params);
        if (!"0".equals(statusPk)){
            po.setOrderStatus(statusPk);//发货单状态
        }
        //更新发货单
        int result = this.update(modelIn,po);

        if (result>0) {
            //更新订单状态
            params.put("type", "OrderStatus");
            params.put("statusName", PrePayStatus.COMPLETED);
            statusPk =  hybrisUtils.getStatusPkByNames(params);
            if (!"0".equals(statusPk)){
                othersPrepayDao.updateOrders(modelIn.getPrePayId(),statusPk);//订单状态
            }
            //核销
            othersPrepayDao.writeOff(modelIn.getPrePayId(),operatorBy);
            //操作类，记录操作人，操作时间
             this.operationHistory(operatorBy,modelIn.getPrePayId(),"Others-UpdateOrderInfo","填写订单信息","其他特权定金-填写订单信息","");

            //将核销状态回写至商城
            int hybrisResult = this.updatePrePayToDpmall(modelIn.getPrePayId());
            if (hybrisResult<1){
                //手动回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                result = 0;
            }
        }

        return result;
    }

    /**
     * 填写订单信息(只保存不提交)
     */
    @Override
    @Transactional
    public int savePrePayOrderInfo(OtherPrePayInfoModelIn modelIn) {
        PrepayPo po = new PrepayPo();
        po.setAgencyListStatus(PrePayStatus.FLLOWING);//appStatus
        po.setStoreListStatus(PrePayStatus.FLLOWING);//appStatus
        po.setPrepayStatus(SaleLeadsOrderStatus.ACCEPTED);//特权订金状态
        po.setOrderStatus( utilsDao.getStatusPk(PrePayStatus.ACCEPT));//订单状态（原状态）
        return  this.update(modelIn,po);
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
        int i = othersPrepayDao.editO2oConsignment(Po);

        if (StringUtils.isNotEmpty(agencyRemark)){
            operatorDesc =  "经销商备注更新成功";
        }else if (StringUtils.isNotEmpty(storeRemark)){
            operatorDesc =  "门店备注更新成功";
        }
        //操作类，记录操作人，操作时间
        int operationResult = this.operationHistory(operatorBy,prepayId,"Others-AddRemarks","添加备注",operatorDesc,"");


        if (i>0){
            result = "添加备注成功";
        }else{
            result = "添加备注失败";
        }

        return result;
    }


    /**
     * 查看更新状态时的备注
     */
    @Override
    public String getUpdateStatusRemark(String operationId) {
        String remark = "";
        PrePayOperationRemarkEntity in = prePayOperationDao.getRemarkByOperationId(operationId);
        if ("未成交".equals(in.getOperationType())){
            if(StringUtils.isEmpty(in.getOperatorRemark())){
                remark ="原因:" +in.getFailReasons();
            }else{
                StringBuffer buffer = new StringBuffer();
                buffer.append("原因:");
                buffer.append(in.getFailReasons());
                buffer.append("  备注:");
                buffer.append(in.getOperatorRemark().trim());
                remark = buffer.toString();
            }
        }else if (StringUtils.isEmpty(in.getOperatorRemark())){
            remark = "暂无备注";
        }else {
            remark = in.getOperatorRemark().trim();
        }
        return remark;
    }


    @Override
    public Boolean judgeWriteOffCode(String prePayId, String writeOffCode) {
        boolean result = false;
        if (StringUtils.isEmpty(prePayId) || StringUtils.isEmpty(writeOffCode)) {
            return result;
        }
        //正确的核销码
        String trueWriteOffCode = othersPrepayDao.getWriteOffCode(prePayId);
        if (StringUtils.isEmpty(trueWriteOffCode)) {
            return result;
        }
        //比较两者
        result = trueWriteOffCode.toUpperCase().equals(writeOffCode.toUpperCase());

        return result;
    }

    /**将状态回写至商城**/
    public int updatePrePayToDpmall(String prePayId) {
        OthersPrePayEntity in = othersPrepayDao.getWriteCodeAndOrderCode(prePayId);
        Map<String, Object> params = new HashMap<>();
        int result = 0;//成功的条数
      try {
          if (in != null){
              params.put("priDepositCode", StringUtils.isEmpty(in.getPridePositCode())?"":in.getPridePositCode()  );
              params.put("orderCode", StringUtils.isEmpty(in.getOrderCode())?"":in.getOrderCode()  );
              String hybrisResult = hybrisUtils.updatePrePayStatus(params);
              JSONObject jsonStr = JSON.parseObject(hybrisResult);
              Map<String,Object> mapResult = (Map)jsonStr;
              if ("3".equals(mapResult.get("code")) && "核销成功".equals(mapResult.get("message"))){
                  result++;
              }
          }
      }catch (Exception e ){
          result = 0;
      }
        return result;
    }

    @Override
    public String getIdByConsignmentId(String prePayId) {
        String orderId ="";
        if (StringUtils.isNotEmpty(prePayId)){
            orderId  = othersPrepayDao.getIdByConsignmentId(prePayId);
        }
        return orderId;
    }
}
