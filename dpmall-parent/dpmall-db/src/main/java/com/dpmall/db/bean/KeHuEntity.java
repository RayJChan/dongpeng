package com.dpmall.db.bean;

/**
 * @author cwj
 *
 */
public class KeHuEntity {
	/**逻辑主键*/
	public Long id;
	
	/**客户code*/
    public String kehCode;

    /**客户名称*/
    public String kehName;

    /**orgid*/
    private String orgId;



    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKehCode() {
        return kehCode;
    }

    public void setKehCode(String kehCode) {
        this.kehCode = kehCode;
    }

    public String getKehName() {
        return kehName;
    }

    public void setKehName(String kehName) {
        this.kehName = kehName;
    }
}
