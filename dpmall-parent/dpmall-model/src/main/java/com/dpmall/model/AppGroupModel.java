package com.dpmall.model;

import java.io.Serializable;
import java.util.Set;

/**
 * 登陆者权限信息
 */
public class AppGroupModel implements Serializable {
	
	private static final long serialVersionUID = 1806717603786059701L;

	/**id*/
	public Long id;
	
	/**分组编码*/
    public String code;
    
    /**分组名称*/
    public String name;
    
    /**分组类型*/
    public String type;
    
    /**直属门店*/
    public String store;
    
    /**分组父级ID*/
    public Long parentId;
    
    
    
    
}
