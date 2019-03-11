package com.dongpeng.entity.system;

import com.dongpeng.common.annotation.DataName;
import com.dongpeng.common.annotation.ExcelField;
import com.dongpeng.common.entity.TreeEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 部门表 实体类
 */
public class Department extends TreeEntity<Department> {
    private static final long serialVersionUID = 1L;
    @DataName("部门编码")
    private String departmentCode;     //部门编码
    @DataName("部门名称")
    private String departmentName;     //部门名称
    /*@DataName("上级部门ID")
    private Long updepartmentId;       //上级部门ID*/
    @DataName("所属公司ID")
    private Long companyId;            //所属公司 ID
    @DataName("所属公司名称")
    private String companyName;        //所属公司名称
    @DataName("是否启用O2O")
    private Boolean o2oFlag;           //是否启用O2O
    @DataName("店铺名称")
    private String shopName;           //店铺名称
    @DataName("单号字母")
    private String singleLetters;      //单号字母
    @DataName("对接系统")
    private String dockSystem;         //对接系统
    @DataName("对接客编")
    private String dockCustNo;         //对接客编
    @DataName("负责人")
    private String leader;             //负责人
    @DataName("联系电话")
    private String phone;              //联系电话
    @DataName("部门类型名称")
    private String departmentTypeName; //部门类型名称


    public Department(){
        super();
        this.dataTableName="部门";
    }

    public Department(Long id){
        super(id);
    }

    public Department(Long id,boolean deleteFlag){
        super(id);
        this.deleteFlag=deleteFlag;
    }
    @ExcelField(title = "部门类型名称")
    public String getDepartmentTypeName() {
        return departmentTypeName;
    }

    public void setDepartmentTypeName(String departmentTypeName) {
        this.departmentTypeName = departmentTypeName;
    }

    @ExcelField(title = "部门编码")
    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    @NotNull
    @Length(min = 2, max = 10, message = "部门名称必须介于 2 和 10 之间")
    @ExcelField(title = "部门名称")
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /*public Long getUpdepartmentId() {
        return updepartmentId;
    }

    public void setUpdepartmentId(Long updepartmentId) {
        this.updepartmentId = updepartmentId;
    }*/

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    @ExcelField(title = "所属公司名称")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @ExcelField(title = "是否启用O2O")
    public Boolean getO2oFlag() {
        return o2oFlag;
    }

    public void setO2oFlag(Boolean o2oFlag) {
        this.o2oFlag = o2oFlag;
    }

    @ExcelField(title = "店铺名称")
    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    @ExcelField(title = "单号字母")
    public String getSingleLetters() {
        return singleLetters;
    }

    public void setSingleLetters(String singleLetters) {
        this.singleLetters = singleLetters;
    }

    @ExcelField(title = "对接系统")
    public String getDockSystem() {
        return dockSystem;
    }

    public void setDockSystem(String dockSystem) {
        this.dockSystem = dockSystem;
    }

    @ExcelField(title = "对接客编")
    public String getDockCustNo() {
        return dockCustNo;
    }

    public void setDockCustNo(String dockCustNo) {
        this.dockCustNo = dockCustNo;
    }

    @ExcelField(title = "负责人")
    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    @Length(max = 11, message = "电话长度不能超过 11 位")
    @ExcelField(title = "联系电话")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
