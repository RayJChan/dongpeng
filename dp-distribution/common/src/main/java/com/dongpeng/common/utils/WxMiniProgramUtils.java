package com.dongpeng.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.dongpeng.common.config.Global;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
    /** 获取小程序码 **/
    private final static String URL_GET_WXACODE_UNlIMIT ="https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=%s";

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
        String accessToken= (String) J2CacheUtils.get(Global.WX_ACCESSTOKEN, Global.WX_ACCESSTOKEN);
        Long expiresIn= (Long) J2CacheUtils.get(Global.WX_ACCESSTOKEN_TIMEOUT, Global.WX_ACCESSTOKEN_TIMEOUT);
        //过期时间为空，或过期时间小于当前时间，重新获取token
        if(null==expiresIn || expiresIn < System.currentTimeMillis()){
            Map<String,Object> params=Maps.newHashMap();
            setConfig(type, params);
            params.put("grant_type", "client_credential");
            JSONObject result=HttpClientUtils.doGet(URL_GET_ACCESS_TOKEN, params);
            if(result.containsKey("access_token")){
                accessToken=result.get("access_token").toString();
                expiresIn=Long.valueOf(result.get("expires_in").toString())*1000 + System.currentTimeMillis();//当前时间+有效时间毫秒=过期时间戳
                J2CacheUtils.put(Global.WX_ACCESSTOKEN, Global.WX_ACCESSTOKEN, accessToken);
                J2CacheUtils.put(Global.WX_ACCESSTOKEN_TIMEOUT, Global.WX_ACCESSTOKEN_TIMEOUT, expiresIn);
                return accessToken;
            }else{
                logger.warn("微信小程序getAccessToken接口请求失败："+result);
                return null;
            }
        }else {
            return accessToken;
        }
    }

    /**
     * 获取小程序码，适用于需要的码数量极多的业务场景。通过该接口生成的小程序码，永久有效，数量暂无限制
     * @param accessToken 接口调用凭证
     * @param scene 最大32个可见字符，只支持数字，大小写英文以及部分特殊字
     * @param page 必须是已经发布的小程序存在的页面（否则报错）,默认主页
     * @param width 二维码的宽度，单位 px，最小 280px，最大 1280px。默认430
     * @param autoColor 自动配置线条颜色，如果颜色依然是黑色，则说明不建议配置主色调，默认 false
     * @param lineColor auto_color 为 false 时生效，使用 rgb 设置颜色 例如 {"r":"xxx","g":"xxx","b":"xxx"} 十进制表示
     * @param hyaline 是否需要透明底色，为 true 时，生成透明底色的小程序
     * @return base64编码png格式图片
     */
    public static String getWxacodeUnlimit(String accessToken,String scene,String page,Integer width,Boolean autoColor,String lineColor,Boolean hyaline){
        if(StringPlusUtils.isNotBlank(accessToken) && StringPlusUtils.isNotBlank(scene)){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("scene",scene );
            if(StringPlusUtils.isNotBlank(page)){
                jsonObject.put("page", page);
            }
            if(null!=width && 0<=width){
                jsonObject.put("width",width );
            }
            if(null!=autoColor ){
                jsonObject.put("auto_color",autoColor );
            }
            if(StringPlusUtils.isNotBlank(lineColor) ){
                jsonObject.put("line_color",lineColor );
            }
            if(null!=hyaline ){
                jsonObject.put("is_hyaline",hyaline );
            }

            //logger.info(jsonObject.toString());
            ByteArrayOutputStream outputStream= (ByteArrayOutputStream)
                    HttpClientUtils.doPostAndReturnStream(String.format(URL_GET_WXACODE_UNlIMIT,accessToken), jsonObject);
            StringBuilder result=new StringBuilder();
            result.append("data:image/png;base64,").append(Encodes.encodeBase64(outputStream.toByteArray()));
            return result.toString();
        }else {
            return null;
        }

    }

    public static void main(String[] args){
        /*WxMiniProgramUtils.code2Session(1,"023bmEFt1TF8Uc0F83Dt1JVnFt1bmEFq",new WxCallback(){

            @Override
            public void success(JSONObject result) {
                System.out.println(result.toJSONString());
            }

            @Override
            public void error(JSONObject result) {

            }
        });*/

        String result=WxMiniProgramUtils.getWxacodeUnlimit(WxMiniProgramUtils.getAccessToken(1), null, null, 40, null, null, null);
        logger.warn(result);
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

