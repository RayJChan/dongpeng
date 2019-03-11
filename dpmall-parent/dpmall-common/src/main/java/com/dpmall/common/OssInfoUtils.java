package com.dpmall.common;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 获取oss登陆信息
 * 
 * @author Administrator
 *
 */
public class OssInfoUtils {

	private static final Logger LOG = LoggerFactory.getLogger(OssInfoUtils.class);

	private static Properties pro = new Properties();

	/**
	 * 初始化加载配置文件
	 */
	static {
		try {
			InputStream inputStream = OssInfoUtils.class.getResourceAsStream("/ossSts.properties");
			pro.load(inputStream);
		} catch (FileNotFoundException e) {
			LOG.error(e.getMessage(), e);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}

	/**
	 * 根据key获取对应的值
	 */
	private static String getByKey(String key) {
		String result = pro.getProperty(key);
		return result;
	}

	/**
	 * 子账号
	 */
	public String getAccessketId() {
		return getByKey("accessketId");
	}

	/**
	 * 子账号密码
	 */
	public String getSecret() {
		return getByKey("secret");
	}

	/**
	 * RoleArn可以到控制台上获取，路径是 访问控制 > 角色管理 > 角色名称 > 基本信息 > Arn 返回上传图片的RoleArn
	 */
	public String getWriteRoleArn() {
		return getByKey("writeRoleArn");
	}

	/**
	 * 返回下载图片的RoleArn
	 */
	public String getReadRoleArn() {
		return getByKey("readRoleArn");
	}

	/**
	 * 返回域名
	 */
	public String getDomain() {
		return getByKey("domainName");
	}

	/**
	 * 当前 STS API 版本
	 */
	public String getStsVersion() {
		return getByKey("stsApiVersion");
	}

	/**
	 * 返回bucketName
	 */
	public static String getBucketName() {
		return getByKey("bucketName");
	}

	/**
	 * 返回endpoint
	 */
	public static String getEndpoint() {
		return getByKey("endpoint");
	}

	/**
	 * 返回域名
	 */
	public static String getOssUrl() {
		return getByKey("ossUrl");
	}

	/**
	 * 返回文件名称
	 */
	public static String getFiledirName(String key) {
		String out = "";
		if (("dpmallFiledir").equals(key)) {
			out = getByKey("dpmallFiledir");
		} else if (("dpwsFiledir").equals(key)) {
			out = getByKey("dpwsFiledir");
		}
		return out;
	}

	//
	// /**
	// * 测试
	// * @param args
	// */
	// public static void main(String[] args) {
	// OssInfoUtils stsTokenUtils = new OssInfoUtils();
	// System.out.println("\naccessketId:"+stsTokenUtils.getAccessketId());
	// System.out.println("\nsecret:"+stsTokenUtils.getSecret());
	// System.out.println("\nwriteRoleArn:"+stsTokenUtils.getWriteRoleArn());
	// System.out.println("\nreadRoleArn:"+stsTokenUtils.getReadRoleArn());
	// System.out.println("\nbucketName:"+stsTokenUtils.getBucketName());
	// }

}
