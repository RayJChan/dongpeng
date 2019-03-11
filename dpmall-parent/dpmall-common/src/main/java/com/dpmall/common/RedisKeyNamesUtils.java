package com.dpmall.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class RedisKeyNamesUtils {

	private static final Logger LOG	=LoggerFactory.getLogger(RedisKeyNamesUtils.class);
	
	private static Properties pro = new Properties();
	
	/**
	 * 初始化加载配置文件
	 */
	static {
		try {
			InputStream inputStream = RedisKeyNamesUtils.class.getResourceAsStream("/redisKeyNames.properties");
			pro.load(inputStream);
		} catch (FileNotFoundException e) {
			LOG.error(e.getMessage(),e);
		}catch (Exception e) {
			LOG.error(e.getMessage(),e);
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
	 * 晒图对应 的产品型号 缓存
	 */
	public static String getProductNumOfShowInfo() {
		return getByKey("productNumOfShowInfo");
	}
	
	/**
	 * 产品信息  缓存
	 */
	public static String getProductInfo() {
		return getByKey("productInfo");
	}
	
	/**
	 * 场景和风格  缓存
	 */
	public static String getSceneAndStyle() {
		return getByKey("sceneAndStyle");
	}
	
	/**
	 * 用户信息  缓存
	 */
	public static String getUserInfo() {
		return getByKey("userInfo");
	}

	/**
	 *
	 * 更新状态的历史记录 缓存
	 */
	public static String getUpdateStatusHistory(){
		return getByKey("updateStatusHistory");
	}

	
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("userInfo:"+RedisKeyNamesUtils.getUserInfo());
		System.out.println("sceneAndStyle:"+RedisKeyNamesUtils.getSceneAndStyle());
		System.out.println("productInfo:"+RedisKeyNamesUtils.getProductInfo());
		System.out.println("productNumOfShowInfo:"+RedisKeyNamesUtils.getProductNumOfShowInfo());
	}
	
	
}
