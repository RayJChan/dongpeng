package com.dpmall.db.bean;

/**
 * @author cwj
 * 表：STATUSDEFINITION
 *
 */
public class StatusDefinitionEntity {
	/**ID**/
	public Long id;
	
	/**原因名称**/
	public String name ;
	
	/**原因类型**/
	public String type;
	
	/**原因类型描述**/
	public String typeDescribe;

	@Override
	public String toString() {
		return "StatusDefinitionEntity [id=" + id + ", name=" + name + ", type=" + type + ", typeDescribe="
				+ typeDescribe + "]";
	}
	
	
	
	
}
