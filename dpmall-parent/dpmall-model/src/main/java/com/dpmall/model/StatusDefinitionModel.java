package com.dpmall.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 原因选择
 * @author cwj
 * @since 2017-10-31
 */
public class StatusDefinitionModel implements Serializable {
	
	private static final long serialVersionUID = -4022735451332634653L;

	/**ID**/
	public Long id;

	/**原因名称**/
	public String name ;





	@Override
	public String toString() {
		return "StatusDefinitionModel [id=" + id + ", name=" + name + "]";
	}
	
	
}
