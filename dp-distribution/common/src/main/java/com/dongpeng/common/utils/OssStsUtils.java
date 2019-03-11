package com.dongpeng.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.dongpeng.common.config.Global;
import com.dongpeng.common.mapper.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 获取OSS图片服务器的Sts Token
 */
public class OssStsUtils {

    public static final Logger LOG = LoggerFactory.getLogger(OssStsUtils.class);

    // STS服务必须为 HTTPS
    public static final ProtocolType PROTOCOLTYPE = ProtocolType.HTTPS;

    /**
     * 根据子用户信息向Oss发送请求
     *
     * @return
     */
    private static AssumeRoleResponse assumeRole(String assumeAccessKeyId, String assumeAccessKeySecret, String roleArn,
                                                 String roleSessionName, String policy, Long expired, ProtocolType protocolType) throws ClientException {
        try {
            // 创建一个 Aliyun Acs Client, 用于发起 OpenAPI 请求
            IClientProfile profile = DefaultProfile.getProfile(Global.getConfig("aliyun.oss.domainName"), assumeAccessKeyId,
                    assumeAccessKeySecret);
            DefaultAcsClient client = new DefaultAcsClient(profile);

            // 创建一个 AssumeRoleRequest 并设置请求参数
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setVersion(Global.getConfig("aliyun.oss.stsApiVersion"));// 版本
            request.setMethod(MethodType.POST);// 请求方法
            request.setProtocol(protocolType);// 请求方式

            request.setRoleArn(roleArn);// 指定角色的全局资源描述符
            request.setRoleSessionName(roleSessionName);// 用户自定义参数。此参数用来区分不同的Token，可用于用户级别的访问审计。
            request.setPolicy(policy);// 授权策略,您可以通过此参数限制生成的Token的权限，不指定则返回的Token将拥有指定角色的所有权限。
            request.setDurationSeconds(expired);// 过期时间

            final AssumeRoleResponse response = client.getAcsResponse(request);
            return response;

        } catch (ClientException e) {
            LOG.error(e.getMessage(), e);
            System.out.println(e.getMessage());
            throw e;
        }
    }

    /**
     * 获取临时登陆的信息
     * @return
     */
    public static Map<String, String> getOssTemporary() {

        String accessKeyId = Global.getConfig("aliyun.oss.accessketId");// 子用户

        String accessKeySecret = Global.getConfig("aliyun.oss.secret");// 子用户密码
        String roleArn = Global.getConfig("aliyun.oss.roleArn");// 临时Token的会话名称，自己指定用于标识你的用户，主要用于审计，或者用于区分Token颁发给谁


        String roleSessionName = "xx";// 这个可以为空，不好写，格式要对，无要求建议为空

        // 如何定制你的policy，如果policy为null，则STS的权限与roleArn的policy的定义的权限
        String policy = null;

        // 过期时间设置默认是一小时，单位秒有效值是[900, 3600]，即15分钟到60分钟。
        long expired = 3600;

        Map<String, String> returnMap = new HashMap<String, String>();
        try {
            // 发送请求获取临时的id、密码、和token（密匙）
            AssumeRoleResponse response = OssStsUtils.assumeRole(accessKeyId, accessKeySecret, roleArn,
                    roleSessionName, policy, expired, PROTOCOLTYPE);
            String STSAccessKeyId = response.getCredentials().getAccessKeyId();
            String STSAccessKeySecret = response.getCredentials().getAccessKeySecret();
            String STSSecurityToken = response.getCredentials().getSecurityToken();

            returnMap.put("STSAccessKeyId", STSAccessKeyId);// 临时登陆的id
            returnMap.put("STSAccessKeySecret", STSAccessKeySecret);// 临时登陆的密码
            returnMap.put("STSSecurityToken", STSSecurityToken);// 临时登陆token
            returnMap.put("bucketName", Global.getConfig("aliyun.oss.bucketName"));
            returnMap.put("endpoint", Global.getConfig("aliyun.oss.endpoint"));
            returnMap.put("imgDomain", Global.getConfig("aliyun.oss.imgDomain"));
            returnMap.put("floder", DateUtils.formatDate(new Date(),"yyyy-MM-dd"));

        } catch (ClientException e) {
            LOG.error(e.getMessage(), e);
        }
        return returnMap;

    }

    public static Map<String, String> getOssTemporary2(){

        String accessId = Global.getConfig("aliyun.oss.accessketId"); // 请填写您的AccessKeyId。
        String accessKey = Global.getConfig("aliyun.oss.secret"); // 请填写您的AccessKeySecret。
        String endpoint = Global.getConfig("aliyun.oss.endpoint"); // 请填写您的 endpoint。
        String bucket = Global.getConfig("aliyun.oss.bucketName"); // 请填写您的 bucketname 。
        String host = "https://" + bucket + "." + endpoint; // host的格式为 bucketname.endpoint
        // callbackUrl为 上传回调服务器的URL，请将下面的IP和Port配置为您自己的真实信息。
        //String callbackUrl = "http://88.88.88.88:8888";
        String dir = DateUtils.formatDate(new Date(),"yyyy-MM-dd"); // 用户上传文件时指定的前缀。

        String roleArn = Global.getConfig("aliyun.oss.roleArn");// 临时Token的会话名称，自己指定用于标识你的用户，主要用于审计，或者用于区分Token颁发给谁
        String roleSessionName = "xx";// 这个可以为空，不好写，格式要对，无要求建议为空
        // 过期时间设置默认是一小时，单位秒有效值是[900, 3600]，即15分钟到60分钟。
        long expired = 3600;

        CredentialsProvider credentialProvider=new DefaultCredentialProvider(accessId, accessKey );
        OSSClient client =new OSSClient(endpoint, credentialProvider, null);
        //OSSClient client = new OSSClient(endpoint, accessId, accessKey);
        try {
            long expireTime = 3600;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = client.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = client.calculatePostSignature(postPolicy);

            Map<String, String> respMap = new LinkedHashMap<String, String>();
            respMap.put("accessid", accessId);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", dir);
            respMap.put("host", host);
            // respMap.put("expire", formatISO8601Date(expiration));
            respMap.put("imgDomain", Global.getConfig("aliyun.oss.imgDomain"));

            /*JSONObject jasonCallback = new JSONObject();
            jasonCallback.put("callbackUrl", callbackUrl);
            jasonCallback.put("callbackBody",
                    "filename=${object}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}");
            jasonCallback.put("callbackBodyType", "application/x-www-form-urlencoded");
            String base64CallbackBody = BinaryUtil.toBase64String(jasonCallback.toString().getBytes());
            respMap.put("callback", base64CallbackBody);*/

            // 发送请求获取临时的id、密码、和token（密匙）
            /*AssumeRoleResponse response = OssStsUtils.assumeRole(accessId, accessKey, roleArn,
                    roleSessionName, postPolicy, expireTime, PROTOCOLTYPE);
            String STSAccessKeyId = response.getCredentials().getAccessKeyId();
            String STSAccessKeySecret = response.getCredentials().getAccessKeySecret();
            String STSSecurityToken = response.getCredentials().getSecurityToken();

            respMap.put("STSAccessKeyId", STSAccessKeyId);// 临时登陆的id
            respMap.put("STSAccessKeySecret", "STS."+STSAccessKeySecret);// 临时登陆的密码
            respMap.put("STSSecurityToken", "STS."+STSSecurityToken);// 临时登陆token*/

            return respMap;

        } catch (Exception e) {
            // Assert.fail(e.getMessage());
            LOG.error(e.getMessage());
        }
        return null;
    }


}
