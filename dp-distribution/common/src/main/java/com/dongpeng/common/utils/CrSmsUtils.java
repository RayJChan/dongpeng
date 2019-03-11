package com.dongpeng.common.utils;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.entity.ResponseCode;
import com.dongpeng.entity.system.Dictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * cr6868 短信工具类
 */
public class CrSmsUtils {
    private static final Logger logger = LoggerFactory.getLogger(CrSmsUtils.class);
    private final static String URL="http://web.cr6868.com/asmx/smsservice.aspx";
    private final static String PWD="4F91A65013E201BD4C73540022E7";
    private final static String USER="15205819329";

    /**
     * 发送短信
     * @param content 短信内容
     * @param phoneNo 电话号码
     * @return true表示发送成功，false表示发送失败
     */
    public static boolean sendSms(String content,String phoneNo) {
        String returnStr=null;
        if(StringPlusUtils.isPhone(phoneNo)){
            String sign="";
            // 创建StringBuffer对象用来操作字符串
            StringBuffer sb = new StringBuffer(URL).append("?");
            // 向StringBuffer追加用户名
            sb.append("name=").append(USER);
            // 向StringBuffer追加密码（登陆网页版，在管理中心--基本资料--接口密码，是28位的）
            sb.append("&pwd=").append(PWD);
            // 向StringBuffer追加手机号码
            sb.append("&mobile=").append(phoneNo);

            try{
                // 向StringBuffer追加消息内容转URL标准码
                sb.append("&content=").append(URLEncoder.encode(content,"UTF-8"));
                //追加发送时间，可为空，为空为及时发送
                sb.append("&stime=");
                //加签名
                sb.append("&sign=").append(URLEncoder.encode(sign,"UTF-8"));
                //type为固定值pt  extno为扩展码，必须为数字 可为空
                sb.append("&type=pt&extno=123");
                // 创建url对象
                //String temp = new String(sb.toString().getBytes("GBK"),"UTF-8");
                java.net.URL url = new URL(sb.toString());
                // 打开url连接
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                // 设置url请求方式 ‘get’ 或者 ‘post’
                connection.setRequestMethod("POST");
                // 发送
                InputStream is =url.openStream();
                //转换返回值
                returnStr = IOUtil.toString(is);
            }catch (IOException e){
                logger.error("短信发送失败", e);
            }

            // 返回结果为‘0，20140009090990,1，提交成功’ 发送成功   具体见说明文档
            logger.info("短信结果: "+returnStr);
            //int temp = returnStr.indexOf(',');
            //String code=returnStr.substring(0, temp);
        }
        if(StringPlusUtils.contains(returnStr, "提交成功")){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 发送短信验证码
     * @param content 短信模板内容
     * @param phoneNo 手机号码
     * @return 结果说明
     */
    public static String sendVerificationCode(String content,String phoneNo){
        String result=null;
        //缓存未过期，不能再次发送
        System.out.println(J2CacheUtils.get(Global.SMS_VERIFICATION_CODE_REGION, phoneNo));
        if(null==J2CacheUtils.get(Global.SMS_VERIFICATION_CODE_TIME_REGION, phoneNo)
                || (System.currentTimeMillis()-(Long)J2CacheUtils.get(Global.SMS_VERIFICATION_CODE_TIME_REGION, phoneNo)>60000)){
            String randomCode = String.valueOf((int) (Math.random() * 900000 + 100000));//6位验证码
            //logger.info(String.format(content,randomCode));
            boolean rs=sendSms(String.format(content,randomCode), phoneNo);
            if(rs){
                //发送成功，存入缓存
                J2CacheUtils.put(Global.SMS_VERIFICATION_CODE_REGION, phoneNo, randomCode);
                J2CacheUtils.put(Global.SMS_VERIFICATION_CODE_TIME_REGION, phoneNo, System.currentTimeMillis());//记录发送短信的时间戳
                result=ResponseCode.SUCCESS.getMsg();
            }else{
                result="短信发送失败，请联系管理员";
            }
        }else {
            result="1分钟内不可重复发送";
        }
        return result;
    }

    /**
     * 发送 登录/注册 短信验证码
     * @param phoneNo 手机号码
     * @return
     */
    public static String sendVerificationCodeForLoginOrRegister(String phoneNo){
        Dictionary smsLoginOrRegisterTemplate=DictUtils.getByCode(Global.DICT_SMS_LOGIN_REGISTER);//从字典获取 登录/注册 短信模板
        String rs=sendVerificationCode(smsLoginOrRegisterTemplate.getDetailName(),phoneNo);
        return rs;
    }

    /**
     * 验证 验证码 有效性
     * @param phone 电话号码
     * @param code 待验证的 验证码
     * @return true 表示验证通过，false表示验证失败
     */
    public static boolean varifySmsCode(String phone,String code){
        String sysCreateCode= (String) J2CacheUtils.get(Global.SMS_VERIFICATION_CODE_REGION, phone);//缓存获取系统生成的验证码
        if(code.equals(sysCreateCode)){
            J2CacheUtils.remove(Global.SMS_VERIFICATION_CODE_REGION, phone );//验证通过后，移除该验证码
            return true;
        }else{
            return false;
        }
    }


    public static void main(String[] args){
        CrSmsUtils.sendSms("【东鹏控股】您的注册\\登录验证码是[777777]，请不要把验证码泄漏给其他人，如非本人操作，请忽略该短信。","13702653778");
    }
}
