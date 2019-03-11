package com.dpmall.common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebUtils {
	private static Properties prop = new Properties();
	
	private final static Logger LOG =LoggerFactory.getLogger(WebUtils.class);
	
	private static String webImagesUrl;
	
	static {
		try {
			InputStream inputStream = WebUtils.class.getResourceAsStream("/web.properties");
			prop.load(inputStream);
		} catch (FileNotFoundException e) {
			LOG.error(e.getMessage());
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
	}
	
	
	
	/**
	 * @return the webImagesUrl
	 */
	public static String getWebImagesUrl() {
		if (webImagesUrl==null) {
			return prop.getProperty("webImagesUrl");
		}
		else {
			return webImagesUrl;
		}
	}

	public static String get(String key) {
		return prop.getProperty(key);
	}
	
	public static void main(String[] args) {
		System.out.println(prop.getProperty("webImagesUrl"));
	}
}
