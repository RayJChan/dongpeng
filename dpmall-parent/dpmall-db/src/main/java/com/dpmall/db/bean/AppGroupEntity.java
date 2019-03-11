package com.dpmall.db.bean;


/**
 * 登陆者权限信息
 */
public class AppGroupEntity  {
	

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


    /**分组父级Code*/
    public String parentCode;

    /**所有父级ID*/
    public String parentIdList;

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getParentIdList() {
        return parentIdList;
    }

    public void setParentIdList(String parentIdList) {
        this.parentIdList = parentIdList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
