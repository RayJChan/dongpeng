package com.dpmall.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.RandomUtils;

public class PrivateKeyUtils {

	/**
	 * 用uuid生成主键
	 * 
	 * @return
	 */
	public static String getPKByUUID() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String date = dateFormat.format(new Date());
		String pk = date + UUID.randomUUID().toString().replaceAll("-", "") + RandomUtils.nextInt(0, 1000);
		return pk;
	}

	/**
	 * 用随机数生成id
	 * 8+6位随机数+年月日时分秒
	 */
	public static long getPkByRandom() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");//2017-11-12 13:14:15-->171112131415
		StringBuffer buffer = new StringBuffer();
		String date = dateFormat.format(new Date());
		String randomMath = String.valueOf((Math.random() * 9 + 1) * 100000);
		randomMath = randomMath.substring(0, randomMath.indexOf("."));
		buffer.append("8");
		buffer.append(randomMath);
		buffer.append(date);
		String pkStr = buffer.toString();
		long pk = Long.parseLong(pkStr);
		return pk;
	}
	
	/**
	 * 用随机数生成id
	 * 8+6位随机数+年月日毫秒
	 */
	public static long getPkByRandom_ms() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddSSS");//2017-11-12 
		StringBuffer buffer = new StringBuffer();
		String date = dateFormat.format(new Date());
		String randomMath = String.valueOf((Math.random() * 9 + 1) * 10000);
		randomMath = randomMath.substring(0, randomMath.indexOf("."));
		buffer.append("8");
		buffer.append(date);
		buffer.append(randomMath);
		String pkStr = buffer.toString();
		long pk = Long.parseLong(pkStr);
		return pk;
	}

	/**
	 * 用随机数生成id
	 * 8+6位随机数+年月日毫秒
	 */
	public static String getPkByRandomStr() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddSSS");//2017-11-12
		StringBuffer buffer = new StringBuffer();
		String date = dateFormat.format(new Date());
		String randomMath = String.valueOf((Math.random() * 9 + 1) * 10000);
		randomMath = randomMath.substring(0, randomMath.indexOf("."));
		buffer.append("8");
		buffer.append(date);
		buffer.append(randomMath);
		String pkStr = buffer.toString();
		return pkStr;
	}

	
}
