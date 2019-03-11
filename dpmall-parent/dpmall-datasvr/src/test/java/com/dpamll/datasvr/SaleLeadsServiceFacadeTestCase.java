package com.dpamll.datasvr;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dpmall.common.DateUtils;
import com.dpmall.common.SpringTestCase;
import com.dpmall.common.TimeScope;
import com.dpmall.datasvr.api.ISaleLeadsService;
import com.dpmall.model.SaleLeadsModel;
import com.dpmall.param.SaleLeadStatisticParam;

public class SaleLeadsServiceFacadeTestCase extends SpringTestCase {
    private static final Logger LOG = LoggerFactory.getLogger(SaleLeadsServiceFacadeTestCase.class);
    
    @Autowired
    private ISaleLeadsService saleLeadsService;
    
    @Test
    public void testGetOnePage4Distribute(){
    	List<SaleLeadsModel> result = saleLeadsService.getOnePage4Distribute("8796132134458", 0, 20,"");
    	LOG.info("=====================daihx====================");
        LOG.info("\n\nresult:" + JSON.toJSONString(result)+"\n\n");
    }
    @Test
    public void testOnePage4FollowupSearch(){
    	List<SaleLeadsModel> result = saleLeadsService.getOnePage4Followup("8796126203450", 0, 20,null,null);
        LOG.info("\nresult:\n" + JSON.toJSONString(result)+"\n");
    }
    @Test
    public void testGetOnePage4AcceptorClosed() {
    	List<SaleLeadsModel> result = saleLeadsService.getOnePage4AcceptorClosed("310146", 0, 3,"1");
    	LOG.info(JSON.toJSONString(result));
    }
    
    
    @Test
    public void testGetOnePage4Acceptor2Followup() {
    	List<SaleLeadsModel> result = saleLeadsService.getOnePage4Acceptor2Followup("310146", 0, 11,"1","10");
    	LOG.info(JSON.toJSONString(result));
    }
    
   
    @Test
    public void testGet2DistributeCount(){
    	Integer result = saleLeadsService.get2DistributeCount("8796132134458","1");
        LOG.info("result:" + JSON.toJSONString(result));
    }
    @Test
    public void testGet2AcceptCount(){
    	Integer result = saleLeadsService.get2AcceptCount("13");
        LOG.info("result:" + result);
    }
    @Test
    public void testDistribut() {
    	
    	int result = saleLeadsService.distribute("789789", "666", "1","hahah");
    	LOG.info(JSON.toJSONString("result:"+result));
    }
    @Test
    public void testGetOnePage4Accept(){
    	List<SaleLeadsModel> acceptModel = saleLeadsService.getOnePage4Accept("1", 0, 5,"1");
        LOG.info("result:" + JSON.toJSONString(acceptModel));
    }
    
    @Test
    public void testEdit() {
    	SaleLeadsModel model=new SaleLeadsModel();
    	model.id=8799369875038L;
    	model.serviceDate="2017-02-09";
    	model.saleLeadsStatus = "15";
    	model.brand = "东鹏m";
    	model.clientAddr = "yygt池州市青阳县蓉城镇 光明新村二期19栋1单元201室";
    	model.serviceAddress  = "服务地址";
    	model.serviceCatelog  = "全屋装修";
    	model.style = "现代";
    	model.budget  = 666.0; 
    	model.clientName = "全屋hhhhhh";
    	model.clientTel  = "66666666666666";
    	model.storeAcceptorRemark  = "全屋装修";
    	
    	
    	saleLeadsService.edit(model, "8796224278113");
    	
    }
    @Test
    public void testGetSaleLeads(){
    	SaleLeadsModel acceptModel = saleLeadsService.getSaleLeads("8797109014110");
    	LOG.info("=======================开始执行=======================");
        LOG.info("\n\nresult:" + JSON.toJSONString(acceptModel)+"\n\n");
    }
    
    @Test
    public void testDistributeBatch() {
    	Map<String, String> map = new HashMap<String, String>();

//    	map.put("666", "1");
    	map.put("8799533944414", "1");
    	LOG.info("result:"+saleLeadsService.distributeBatch("8796126203450", map,"8796224278113","zezeze"));

    }
    @Test
    public void testGetOnePageClosedSaleLeads() throws ParseException{
    	TimeScope scope = new TimeScope();
    	scope.begin = new Timestamp(DateUtils.parse("2016-07-18 00:00:00", DateUtils.YYYY_MM_DD_HH_MM_SS).getTime());
    	scope.end = new Timestamp(DateUtils.parse("2017-09-15 10:30:00", DateUtils.YYYY_MM_DD_HH_MM_SS).getTime());
    	List<SaleLeadsModel> acceptModel = saleLeadsService.getOnePageClosedSaleLeads("8796132134458", scope, null,null, null,null,"dd",null, 0, 999,null);
        LOG.info("result:" + JSON.toJSONString(acceptModel));
    }
    /**
	 * author:daihx
	 * accept方法
	 * saleLeadsId
	 */
    @Test
    public void testAccept(){
    	int result = saleLeadsService.accept("14", "1","");
    	LOG.info("=======================开始执行=======================");
        LOG.info("\n\nresult:" + JSON.toJSONString(result)+"\n\n");
    }
    /**
	 * author:daihx
	 * 导购员批量接单
	 * saleLeadsId
	 */
    @Test
    public void testAcceptBatch(){
    	List<String> saleLeadsId = new ArrayList<String>();
    	saleLeadsId.add("1");
    	saleLeadsId.add("2");
    	saleLeadsId.add("3");
    	int result = saleLeadsService.acceptBatch("14", saleLeadsId,"");
    	LOG.info("=======================开始执行=======================");
        LOG.info("\n\nresult:" + JSON.toJSONString(result)+"\n\n");
    }
    /**
     * 经销商拒单
     * @param distributorId 经销商ID
     * @param saleLeadsId 销售线索ID
     * @param rejectType 拒单类型
     * @param rejectRemark 拒单备注
     * @return
     */
    @Test
    public void testReject(){
    	Integer reject = saleLeadsService.reject("8796132134458","55","10","11sssssss","");
    	LOG.info("=======================拒单更新状态======================="+reject);
    }
    /**
     * 经销商批量拒单
     * @param distributorId 经销商ID
     * @param saleLeadsId 销售线索ID
     * @param rejectType 拒单类型
     * @param rejectRemark 拒单备注
     * @return
     */
    @Test
    public void testRejectBatch(){
    	List<String> saleLeadsIdList = new ArrayList<String>();
    	saleLeadsIdList.add("1");
    	saleLeadsIdList.add("2");
    	Integer reject = saleLeadsService.rejectBatch(null,saleLeadsIdList,"19","21ssaaaassss1111ssss","");
    	LOG.info("=======================拒单更新状态======================="+reject);
    }
    /**
     * 测试获取根据form条件查询一页的成功结单的数据
     * @param form
     * @param startNum
     * @param pageSize
     * @return
     * @throws ParseException 
     */
    @Test
    public void testGetOnePageSuccessOrders(){
    	SaleLeadStatisticParam from = new SaleLeadStatisticParam();
    	from.storeId= 13L;
    	from.acceptorName="310146";
    	from.productCatelog="1111";
    	from.fromTime="2016-07-19 10:30:00";
    	from.toTime="2017-07-22 10:30:00";
    	List<SaleLeadsModel> SuccessOrders = saleLeadsService.getOnePageSuccessOrders(from, 0, 5);
    	LOG.info(JSON.toJSONString(SuccessOrders));
    }
    /**
     * 测试获取根据form条件查询成功结单的金额
     * @param form
     * @return
     */
    @Test
    public void testGetSuccessOrdersTtlAmount(){
    	SaleLeadStatisticParam from = new SaleLeadStatisticParam();
    	from.storeId= 13L;
    	from.acceptorName="310146";
    	from.productCatelog="1111";
    	from.fromTime="2016-07-19 10:30:00";
    	from.toTime="2017-07-22 10:30:00";
    	Double TtlAmount = saleLeadsService.getSuccessOrdersTtlAmount(from);
    	LOG.info("TtlAmount======="+TtlAmount);
    }
   
    @Test
    public void getOnePageFinishedSaleLeadsTest(){
    	LOG.info(JSON.toJSONString("=================getOnePageFinishedSaleLeadsTest============="));
    	List<SaleLeadsModel> result = saleLeadsService.getOnePageFinishedSaleLeads("8796126203450", null, null, "", 0, 10);
    	LOG.info("\n\nresult:" + JSON.toJSONString(result)+"\n\n");
    }
    
    
}
