package com.dpmall.common;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import net.dongliu.requests.Requests;

public class OperationUtils {
	
//	private static final Logger LOGGER = LoggerFactory.getLogger(OperationUtils.class);
	
	//lian
//	static String URL="https://sit.dpo2o.com/authorizationserver/oauth/token";
//	static String USERNAME="dpApp";
//	static String PASSWORD="123456";
//	static String CLIENTSECRET="mobile_android:secret";
	
	
	
	
	
	private final static String GETTOKENURL="https://sitoms.dpmall.com/authorizationserver/oauth/token";
	private final static String SAVEADDRECORDURL="https://sitoms.dpmall.com/dongpengcommercewebservices/v2/dongpeng/app/addRecord";
	private final static String USERNAME="dpApp";
	private final static String PASSWORD="123456";
	
	public static String getrRecordToken() {
		Map<String, Object> params= new HashMap<String, Object>();
		params.put("grant_type", "password");
		params.put("username", USERNAME);
		params.put("password", PASSWORD);
		params.put("client_id", "mobile_android");
		params.put("client_secret", "secret");
		JSONObject jsonObject=JSON.parseObject(Requests.post(GETTOKENURL).body(params).send().readToText());		
		return jsonObject.getString("access_token");
	}
	
	public static String saveRecordInfo(Map<String, Object> params,String access_token) {
		String result = Requests.post(SAVEADDRECORDURL+"?access_token="+access_token).jsonBody(params).send().readToText();
		return result;
	}
//	
//	public static void main2(String[] args) {
//		Map<String, Object> params=new HashMap<String, Object>();
//		params.put("username", "666");
//		params.put("localIP", "2");
//		params.put("deviceId", "2");
//		params.put("bulidVersion", "2");
//		params.put("phoneBrand", "2");
//		params.put("phoneModel", "2");
//		params.put("network", "2");
//		params.put("netIP", "2");
//		params.put("appVersion", "2");
//		params.put("token", "2");
//		System.out.println(saveLoginInfo(params, getrRecordToken()));
//		
//	}
	
	public static void main(String[] args) {
		List<Object> code=new ArrayList<Object>();
		code.add("123");
		code.add("cwj");
		
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("consignmentCode", "aSITB100627006");
		params.put("userID", "777");
		params.put("result", "SUCCESS");
		params.put("content", "对对对");
		params.put("moblieModel", "XXX");
//		params.put("token", "2");
//		params.put("List", code);
		System.out.println(saveRecordInfo(params,getrRecordToken()));
		
		
	
	}
}
