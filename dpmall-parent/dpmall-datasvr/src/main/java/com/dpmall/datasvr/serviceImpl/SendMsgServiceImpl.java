package com.dpmall.datasvr.serviceImpl;

import com.dpmall.common.SendMsgUtils;
import com.dpmall.datasvr.api.ISendMsgService;
import com.dpmall.db.bean.StoreOfSalEntity;
import com.dpmall.db.dao.SendMsgDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 发送短信实现
 */
@Service(value = "sendMsgService")
public class SendMsgServiceImpl implements ISendMsgService {


	@Autowired
	private SendMsgDao sendMsgDao;

	@Override
	public boolean sendMsgSalAccept(StoreOfSalEntity en) {
		String msgTemplate  = sendMsgDao.getMsgtemplateByCode("accepted");//获取模板

		boolean cn= false;
		//替换发送内容
		if (StringUtils.isNotEmpty(msgTemplate)&& en!=null){
			msgTemplate=msgTemplate.replace("[salName]", en.getCustbenefit());
			msgTemplate=msgTemplate.replace("[storeName]", "“"+en.getStoreName()+"”" );
			msgTemplate=msgTemplate.replace("[storeAddress]", en.getStoreAddress());
			msgTemplate=msgTemplate.replace("[storePhone]", en.getStorePhone());
		}
		return this.sedMsg(en.getCustomerPhone(),msgTemplate);


	}

	@Override
	public boolean sendMsgSalSuccess(String phoneNumber,String payPrice) {
		String msgTemplate  = sendMsgDao.getMsgtemplateByCode("success");//获取模板

		//替换发送内容
		if (StringUtils.isNotEmpty(payPrice)){
			msgTemplate=msgTemplate.replace("[payPrice]", payPrice);
		}

		return this.sedMsg(phoneNumber,msgTemplate);
	}

	@Override
	public boolean sendMsgSalFailed(String phoneNumber,String storeName) {
		String msgTemplate  = sendMsgDao.getMsgtemplateByCode("failed");//获取模板

		//替换发送内容
		if (StringUtils.isNotEmpty(storeName)){
			msgTemplate=msgTemplate.replace("[storeName]", "“"+storeName+"”");
		}

		return this.sedMsg(phoneNumber,msgTemplate);
	}

	//发送短信通用
	private boolean sedMsg(String phoneNumber, String content){

		StringBuffer mobileString = new StringBuffer(phoneNumber);
		StringBuffer msgcontent=new StringBuffer(content);
		String result ="";
		boolean cn= false;
		try
		{
			result = SendMsgUtils.doPost(mobileString, msgcontent, "东鹏商城", "", new StringBuffer());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		String[] codeStr = result.split(",");
		if("0".equals(codeStr[0])){//短信平台返回结果码，0：成功发送
			cn = true;
		}else{
			cn = false;
		}

		return cn;
	}
}
