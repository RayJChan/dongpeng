package com.dpmall.model;

import java.io.Serializable;
import java.util.Set;

/**
 * 登陆者权限信息
 */
public class AppGroupModel implements Serializable {
	
	private static final long serialVersionUID = 1806717603786059701L;


	/**分组编码*/
    public String code;
    
    /**分组名称*/
    public String name;
    
    /**分组类型*/
    public String type;
    
    /**直属门店*/
    public String store;

    /**分组父级Code*/
    public String parentCode;
    
    /**分组父级ID*/
    public Long parentId;

    /**所有父级ID*/
    public String parentIdList;

    public String getParentIdList() {
        return parentIdList;
    }

    public void setParentIdList(String parentIdList) {
        this.parentIdList = parentIdList;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }
}
