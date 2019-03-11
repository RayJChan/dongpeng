package com.dpmall.web.controller.form;

import java.util.List;

public class AcceptBatchForm extends RequestForm {
	/**导购员账户ID*/
    public String acceptorId;
    /**线索ID*/
    public List<String> saleLeadsOrderIds;

    /**导购员备注**/
    public String storeRemark;
}
