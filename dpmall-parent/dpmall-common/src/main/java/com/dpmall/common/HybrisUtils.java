package com.dpmall.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.dongliu.requests.Requests;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.io.*;
import java.util.*;

@Component
public class HybrisUtils {
    private final static String DpMallUrl = "https://www.dpmall.com/";
    private final static String OmsUrl = "https://mos.dpmall.com/";
        //private final static String OmsUrl = "https://172.16.2.122:9002/";
//    private final static String OmsUrl = "https://172.16.2.121:9002/";
//    private final static String TokenUrl = "https://172.16.1.121:9002/";

    private final static String TOKENURL = "authorizationserver/oauth/token";

    //添加登陆信息
    private final static String SAVELOGININFOURL = "dongpengcommercewebservices/v2/dongpeng/app/addLoginMessage";
    //获取所有当前账号所有经销商
    private final static String GETAPPGROUPURL = "dongpengcommercewebservices/v2/dongpeng/app/appGroupFindAllAgency";
    //刷新留资订单
    private final static String REFRESHSTATUS = "dongpengcommercewebservices/v2/dongpeng/app/refreshModel";
    //保存特权明细
    private final static String SAVE_PREPAYITEMS = "dongpengcommercewebservices/v2/dongpeng/app/savePrePayItems";
    //将核销状态回调商城
    private final static String UPDATE_PREPAY_STATUS = "dongpengcommercewebservices/v2/dongpeng/hybris/updatePriDepositInfoStatus";


    private static Map<String, Object> tokenParams(){
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("grant_type", "password");
        params.put("username", "dpApp");
        params.put("password", "123456");
//        params.put("client_id", "mobile_android");
        params.put("client_id", "mobile_client1");
        params.put("client_id", "app_client");
        params.put("client_secret", "secret");


//        params.put("grant_type", "password");
//        params.put("username", "rubu@rubu.com");
//        params.put("password", "123?56");
//        params.put("client_id", "rubu_client");
//        params.put("client_secret", "secret");
        return params;
    }

    //获取hybristoken
    public static String getToken() {
        JSONObject jsonObject = JSON.parseObject(Requests.post(OmsUrl+TOKENURL).verify(false).body(HybrisUtils.tokenParams()).send().readToText());
        return jsonObject.getString("access_token");
    }

    //获取hybristoken
    private String getToken(String clientId) {
        String token = "";
        try {
            Map<String, Object> params = HybrisUtils.tokenParams();
            params.put("client_id", clientId);
            JSONObject jsonObject = JSON.parseObject(Requests.post(OmsUrl+TOKENURL).verify(false).body(params).send().readToText());
            token =jsonObject.getString("access_token");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    //获取hybristoken
    public String getHybrisToken() {
        //1、获取token
        String token = "";
        Jedis jd = RedisUtils.getClient();

        try {  //clientId
            List<String> cilentIds = new LinkedList<>();
//        cilentIds.add("mobile_android");
            cilentIds.add("mobile_client1");
//            cilentIds.add("app_client");

            //2、判断redis中是否有值
            token = jd.get("hybrisToken");
            if (StringUtils.isNotEmpty(token)) {//redis存在值
                //测试token是否有效
               if (this.testToken(token)){
                   return token;
               } else{
                   jd.del("hybrisToken");//无效则删除
               }
            }
            //3、redis中没值或者token无效，从hybris获取
            boolean tokenBollen = false;//token检验
            for (String cilentId : cilentIds) {
                token = this.getToken(cilentId);
                if (StringUtils.isNotEmpty(token)) {
                    //测试token是否有效
                    tokenBollen =  this.testToken(token);
                    if (tokenBollen) {
                        jd.set("hybrisToken", token, "NX", "EX", 300);//有效期5分钟
                        break;
                    }
                }
            }
            //如果token依旧无效，则用如步的验证
            if (!tokenBollen) {
                Map<String, Object> params = HybrisUtils.tokenParams();
                params.put("username", "rubu@rubu.com");
                params.put("password", "123?56");
                params.put("client_id", "rubu_client");
                JSONObject jsonObject = JSON.parseObject(Requests.post(OmsUrl + TOKENURL).verify(false).body(params).send().readToText());
                token = jsonObject.getString("access_token");
                jd.set("hybrisToken", token, "NX", "EX", 120);//有效期2分钟
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jd != null) {
                RedisUtils.returnResource(jd);
            }
        }

        return token;
    }




    //获取商城的token
    public static String getDpMallToken() {
        JSONObject jsonObject = JSON.parseObject(Requests.post(DpMallUrl+TOKENURL).verify(false).body(HybrisUtils.tokenParams()).send().readToText());
        return jsonObject.getString("access_token");
    }

    //保存特权明细
    public  String savePrePayItems(Map<String, Object> params) {
        String result = Requests.post(OmsUrl+SAVE_PREPAYITEMS + "?access_token=" + this.getHybrisToken()).verify(false).jsonBody(params).send().readToText();
        return result;
    }

    //添加登陆信息
    public  String saveLoginInfo(Map<String, Object> params) {
        String result = Requests.post(OmsUrl+SAVELOGININFOURL + "?access_token=" + this.getHybrisToken()).verify(false).jsonBody(params).send().readToText();
        return result;
    }

    //获取所有当前账号所有经销商
    public  String getAppGroupInfo(Map<String, Object> params) {
        //param.put("code", code);
        String result = Requests.post(OmsUrl+GETAPPGROUPURL + "?access_token=" + this.getHybrisToken()).verify(false).jsonBody(params).send().readToText();
        return result;
    }


    //刷新hybris缓存
    public  String refreshStatus(Map<String, Object> params) {
        //params 格式：{"type":"SalesLeadsOrder",pk:"1111"}
        String result = Requests.post(OmsUrl+REFRESHSTATUS + "?access_token=" + this.getHybrisToken()).jsonBody(params).verify(false).send().readToText();
        return result;
    }
    //将核销状态回调至商城
    public  String updatePrePayStatus(Map<String, Object> params) {
        //params 格式：{"orderCode":"1111",priDepositCode:"22"}
        String result = Requests.post(DpMallUrl+UPDATE_PREPAY_STATUS+"?access_token="+HybrisUtils.getDpMallToken()).params(params)
        .verify(false).send().readToText();
        return result;
    }
    //查询状态pk
    public  String getStatusPkByNames(Map<String, Object> params){
        //type", "ConsignmentStatus";statusName", "READY_FOR_PICKUP"
        try{
            String result = Requests.post(OmsUrl+"dongpengcommercewebservices/v2/dongpeng/app/getStatusByName?access_token="+this.getHybrisToken()).jsonBody(params)
                    .verify(false).send().readToText();
            JSONObject jsonObject = JSON.parseObject(result);
            Map<String,Object> map = (Map)jsonObject.get("data");

            return map.get("value").toString();
        }catch (Exception e){
            return "0";

        }
    }

    //批量刷新缓存
    public  int batchRefresh( Map<String,Object> map){
        File file = new File("C:\\Users\\Administrator\\Desktop\\updateHybrisCache.txt");
        int i = 0;
        List<String>list = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            String line;
            while((line = br.readLine()) != null){
                list.add(line);

            }
            br.close();
            isr.close();
            fis.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            i=0;

        } catch (IOException e) {
            e.printStackTrace();
            i=0;
        } catch (Exception e){
            e.printStackTrace();
            i=0;
        }
        for(String pk  :list){
            map.put("pk",pk);
            this.refreshStatus(map);
            i++;
        }

        return i;
    }


    /**
     * 测试token是否有效
     * @param token
     * @return
     */
    private boolean testToken(String token) {
        Map<String,Object> params = new HashMap<>();
//		params 格式：{"code":"317716"}
        params.put("code","317716");
       try {
           String resultStr = Requests.post(OmsUrl+GETAPPGROUPURL + "?access_token=" + token).verify(false).jsonBody(params).send().readToText();
           JSONObject jsonObject = JSONObject.parseObject(resultStr);
           Map<String,Object> result = (Map)jsonObject;
           if (result==null){
               return false;
           }
           if ("200".equals(result.get("resultCode").toString())){
               return true;
           }
           return false;
       }catch (Exception e ){
           e.printStackTrace();
           return false;
       }
    }


    public static void main(String[] args) {

        //测试token
        String token = HybrisUtils.getToken();
        System.out.println(token);
//



    }
}