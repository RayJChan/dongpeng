package com.dpmall.common;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;

/**
 * 获取OSS图片服务器的Sts Token
 * 
 * @author cwj
 *
 */
public class OssStsUtils {

	public static final Logger LOG = LoggerFactory.getLogger(OssStsUtils.class);

	// STS服务必须为 HTTPS
	public static final ProtocolType PROTOCOLTYPE = ProtocolType.HTTPS;
	public static OssInfoUtils ossInfoUtils = new OssInfoUtils();// 获取对应信息

	/**
	 * 根据子用户信息向Oss发送请求
	 * 
	 * @return
	 */
	private static AssumeRoleResponse assumeRole(String assumeAccessKeyId, String assumeAccessKeySecret, String roleArn,
			String roleSessionName, String policy, Long expired, ProtocolType protocolType) throws ClientException {
		try {
			// 创建一个 Aliyun Acs Client, 用于发起 OpenAPI 请求
			IClientProfile profile = DefaultProfile.getProfile(ossInfoUtils .getDomain(), assumeAccessKeyId,
					assumeAccessKeySecret);
			DefaultAcsClient client = new DefaultAcsClient(profile);

			// 创建一个 AssumeRoleRequest 并设置请求参数
			final AssumeRoleRequest request = new AssumeRoleRequest();
			request.setVersion(ossInfoUtils .getStsVersion());// 版本
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
	 * @param readOrWrite
	 * @return
	 */
	public Map<String, String> getOssTemporary(String readOrWrite) {

		String accessKeyId = ossInfoUtils.getAccessketId();// 子用户

		String accessKeySecret = ossInfoUtils.getSecret();// 子用户密码
		String roleArn = null;// 临时Token的会话名称，自己指定用于标识你的用户，主要用于审计，或者用于区分Token颁发给谁
		if ("read".equals(readOrWrite)) {
			roleArn = ossInfoUtils.getReadRoleArn();
		} else if ("write".equals(readOrWrite)) {
			roleArn = ossInfoUtils.getWriteRoleArn();
		}
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
			returnMap.put("bucketName", OssInfoUtils.getBucketName());
			returnMap.put("endpoint", OssInfoUtils.getEndpoint());

		} catch (ClientException e) {
			LOG.error(e.getMessage(), e);
		}
		return returnMap;

	}
}
