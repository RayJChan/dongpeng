package com.dpmall.web.controller.form;

public class UserForm extends RequestForm{
	/**逻辑主键*/
	public Long id;
	
	/**用户名*/
    public String username;
    
    /**密码**/
    public String password;
    
    /**旧密码**/
    public String oldPasswd;
    
    /**中文名*/
    public String cnName;
    
    /**角色编码*/
    public String roleCode;
    
    /**经销商ID*/
    public Long agencyId;
    
    /**门店ID*/
    public Long storeId;
    
    /**本机地址**/
	public String localIP;
	
	/**设备ID**/
	public String deviceId;
	
	/**系统版本**/
	public String bulidVersion;
	
	/**手机品牌**/
	public String phoneBrand;
	
	/**手机型号**/
	public String phoneModel;
	
	/**网络**/
	public String network;
	
	/**网络地址**/
	public String netIP;
	
	/**app版本**/
	public String appVersion;
	
	/**登录token**/
	public String token;
	
	/**手机号码**/
	public String phoneNum;
	
}
