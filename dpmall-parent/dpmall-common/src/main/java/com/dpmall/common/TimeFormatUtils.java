package com.dpmall.common;

import java.text.SimpleDateFormat;
import java.util.Date;


public class TimeFormatUtils {

	static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	static SimpleDateFormat SDF2 = new SimpleDateFormat("yyyyMMdd");
	/**
	 * 转换时间格式（Long类型）
	 * @param millis
	 * @return
	 */
	public static  String timeFormate(Long millis) {
		Date date = new Date(millis);
		return SDF.format(date);
	}
	/**
	 * 转换时间格式（date类型）
	 * @param date
	 * @return
	 */
	public static  String timeFormate(Date date) {
		return SDF.format(date);
	}
	
	/**
	 * 转换时间格式（date类型）
	 * @param date
	 * @return
	 */
	public static  String timeFormate2(Date date) {
		return SDF2.format(date);
	}
	
	
	
}
