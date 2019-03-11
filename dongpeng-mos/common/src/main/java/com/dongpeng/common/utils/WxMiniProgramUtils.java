package com.dongpeng.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.dongpeng.common.config.Global;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 微信小程序工具类
 */
public class WxMiniProgramUtils {
    private static final Logger logger = LoggerFactory.getLogger(WxMiniProgramUtils.class);
    //private final static String GRANT_TYPE="authorization_code";

    /**** 微信接口url *****/
    /** code2Session 登录凭证校验。通过 wx.login() 接口获得临时登录凭证 code 后传到开发者服务器调用此接口完成登录流程。更多使用方法详见 小程序登录。**/
    private final static String URL_CODE_2_SESSION ="https://api.weixin.qq.com/sns/jscode2session";
    /** 获取小程序全局唯一后台接口调用凭据 access_token **/
    private final static String URL_GET_ACCESS_TOKEN ="https://api.weixin.qq.com/cgi-bin/token";

    /**
     * 设置微信appid，secretd等配置
     * @param type 1=员工计划小程序  2=朋友汇小程序
     * @param params 参数map
     */
    private static void setConfig(int type,Map<String,Object> params){
        switch (type){
            case 1:
                //朋友汇小程序
                params.put("appid", "wx047ee1121a183ae9");
                params.put("secret","d3d1148e3be8c292f993fb2a01aeba17");
                break;
            case 2:
                //员工计划小程序
                params.put("appid", "wx047ee1121a183ae9");
                params.put("secret","d3d1148e3be8c292f993fb2a01aeba17");
                break;
            default:
                params.put("appid", "wx047ee1121a183ae9");
                params.put("secret","d3d1148e3be8c292f993fb2a01aeba17");

        }
    }

    /**
     * 登录凭证校验接口
     * <p>通过 wx.login() 接口获得临时登录凭证 code 后传到开发者服务器调用此接口完成登录流程</p>
     * @param type  1=员工计划小程序  2=朋友汇小程序
     * @param code  小程序登录时获取的 code
     * @param callback 执行结果回调函数
     * @return
     */
    public static boolean code2Session(int type,String code,WxCallback callback){
        Map<String,Object> params=Maps.newHashMap();
        setConfig(type, params);
        params.put("js_code", code);
        params.put("grant_type", "authorization_code");
        JSONObject result=HttpClientUtils.doGet(URL_CODE_2_SESSION, params);
        if(result.containsKey("openid")){
            callback.success(result);
            return true;
        }else{
            logger.warn("微信小程序code2Session接口请求失败："+result);
            callback.error(result);
            return false;
        }
    }

    /**
     * 获取小程序全局唯一后台接口调用凭据（access_token）
     * <p>调用各后台接口时都需使用 access_token，开发者需要进行妥善保存</p>
     * @param type  1=员工计划小程序  2=朋友汇小程序
     * @return
     */
    public static String getAccessToken(int type){
        String accessToken= (String) J2CacheUtils.get(Global.WX_ACCESSTOKEN, String.valueOf(type));
        if(StringPlusUtils.isBlank(accessToken)){
            Map<String,Object> params=Maps.newHashMap();
            setConfig(type, params);
            params.put("grant_type", "client_credential");
            JSONObject result=HttpClientUtils.doGet(URL_GET_ACCESS_TOKEN, params);
            if(result.containsKey("access_token")){
                accessToken=result.get("access_token").toString();
                J2CacheUtils.put(Global.WX_ACCESSTOKEN, String.valueOf(type), accessToken);
                return accessToken;
            }else{
                logger.warn("微信小程序getAccessToken接口请求失败："+result);
                return null;
            }
        }else {
            return accessToken;
        }

    }

    public static void main(String[] args){
        WxMiniProgramUtils.code2Session(1,"023bmEFt1TF8Uc0F83Dt1JVnFt1bmEFq",new WxCallback(){

            @Override
            public void success(JSONObject result) {
                System.out.println(result.toJSONString());
            }

            @Override
            public void error(JSONObject result) {

            }
        });
    }

    /**
     * 微信接口结果回调接口
     */
    public interface WxCallback{
        /**
         * 成功时的回调
         * @param result 微信返回的json结果
         */
        public void success(JSONObject result);

        /**
         * 失败时的回调
         * @param result 微信返回的json结果
         */
        public void error(JSONObject result);
    }
}

