package com.dongpeng.entity.system;

import com.dongpeng.common.annotation.ExcelField;
import com.dongpeng.common.entity.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

public class ClientRecord extends DataEntity<ClientRecord> {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String clientCode;

    private String clientName;

    private String salesman;

    private Long clientTypeId;

    private String clientType;

    private Long departmentId;

    private String departmentName;

    private Long companyId;

    private String companyName;

    private String companyAdress;

    private Date signingDate;

    private String invoiceIssuing;

    private Long channelId;

    private String channelName;

    private String wechat;

    private String interest;

    private Double integralConversion;

    private Date interestStarttime;

    private Date interestEndtime;

    private String bankname;

    private String bankaccount;

    private String thetaxno;

    private String creditlimes;

    private Date creditStarttime;

    private Date creditEndtime;

    private String creditPercent;

    private Boolean clientGrade;

    private Boolean storageFee;

    private Long provinceId;

    private String province;

    private Long cityId;

    private String city;

    private Long districtId;

    private String district;

    private String clientAddress;

    private String clientMan;

    private String clientTel;

    private Boolean salUndertake;

    private Boolean privilegeUndertake;

    private Boolean materialUndertake;

    private String clientAbbreviation;

    private String Alipaynum;

    private Boolean integral;

    private Boolean credit;

    private String integralPercent;

    public ClientRecord(){
        super();
        this.dataTableName="客户";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @ExcelField(title = "客户编码", sort = 1)
    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode == null ? null : clientCode.trim();
    }

    @ExcelField(title = "客户名称", sort = 2)
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName == null ? null : clientName.trim();
    }

    @ExcelField(title = "客户简称", sort = 3)
    public String getClientAbbreviation() {
        return clientAbbreviation;
    }

    public void setClientAbbreviation(String clientAbbreviation) {
        this.clientAbbreviation = clientAbbreviation;
    }

    @ExcelField(title = "客户类型", sort = 4)
    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType == null ? null : clientType.trim();
    }

    @ExcelField(title = "业务员", sort = 4)
    public String getSalesman() {
        return salesman;
    }

    public void setSalesman(String salesman) {
        this.salesman = salesman;
    }

    @ExcelField(title = "所属公司", sort = 5)
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    @ExcelField(title = "公司地址", sort = 6)
    public String getCompanyAdress() {
        return companyAdress;
    }

    public void setCompanyAdress(String companyAdress) {
        this.companyAdress = companyAdress == null ? null : companyAdress.trim();
    }

    @ExcelField(title = "所属部门", sort = 7)
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    @ExcelField(title = "签约时间", sort = 8)
    public Date getSigningDate() {
        return signingDate;
    }

    public void setSigningDate(Date signingDate) {
        this.signingDate = signingDate;
    }

    @ExcelField(title = "开票单位", sort = 9)
    public String getInvoiceIssuing() {
        return invoiceIssuing;
    }

    public void setInvoiceIssuing(String invoiceIssuing) {
        this.invoiceIssuing = invoiceIssuing == null ? null : invoiceIssuing.trim();
    }

    @ExcelField(title = "渠道", sort = 10)
    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName == null ? null : channelName.trim();
    }

    @ExcelField(title = "联系人", sort = 11)
    public String getClientMan() {
        return clientMan;
    }

    public void setClientMan(String clientMan) {
        this.clientMan = clientMan == null ? null : clientMan.trim();
    }

    @Length(min = 11, max = 11, message = "电话号码必须为11位数")
    @ExcelField(title = "联系电话", sort = 12)
    public String getClientTel() {
        return clientTel;
    }

    public void setClientTel(String clientTel) {
        this.clientTel = clientTel == null ? null : clientTel.trim();
    }

    @ExcelField(title = "客户地址", sort = 13,type = 1)
    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress == null ? null : clientAddress.trim();
    }

    @ExcelField(title = "留资承接", sort = 14,type = 1)
    public Boolean getSalUndertake() {
        return salUndertake;
    }

    public void setSalUndertake(Boolean salUndertake) {
        this.salUndertake = salUndertake == null?false:salUndertake;
    }

    @ExcelField(title = "特权承接", sort = 15,type = 1)
    public Boolean getPrivilegeUndertake() {
        return privilegeUndertake;
    }

    public void setPrivilegeUndertake(Boolean privilegeUndertake) {
        this.privilegeUndertake = privilegeUndertake == null?false:privilegeUndertake;
    }

    @ExcelField(title = "实物承接", sort = 16,type = 1)
    public Boolean getMaterialUndertake() {
        return materialUndertake;
    }

    public void setMaterialUndertake(Boolean materialUndertake) {
        this.materialUndertake = materialUndertake == null?false:materialUndertake;
    }

    @ExcelField(title = "省", sort = 17)
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    @ExcelField(title = "市", sort = 18)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    @ExcelField(title = "区", sort = 19)
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
    }

    @ExcelField(title = "微信", sort = 20)
    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat == null ? null : wechat.trim();
    }

    @ExcelField(title = "支付宝", sort = 21,type = 1)
    public String getAlipaynum() {
        return Alipaynum;
    }

    public void setAlipaynum(String alipaynum) {
        this.Alipaynum = alipaynum == null ? null : alipaynum.trim();
    }

    @ExcelField(title = "启动积分", sort = 22,type = 1)
    public Boolean getIntegral() {
        return integral;
    }

    public void setIntegral(Boolean integral) {
        this.integral = integral == null ? false : integral;
    }

    @ExcelField(title = "积分换算", sort = 22,type = 1)
    public Double getIntegralConversion() {
        return integralConversion;
    }

    public void setIntegralConversion(Double integralConversion) {
        this.integralConversion = integralConversion;
    }

    @ExcelField(title = "积分百分比", sort = 23,type = 1)
    public String getIntegralPercent() {
        return integralPercent;
    }

    public void setIntegralPercent(String integralPercent) {
        this.integralPercent = integralPercent;
    }
    @ExcelField(title = "日利息", sort = 24,type = 1)

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest == null ? null : interest.trim();
    }

    @ExcelField(title = "利息开始时间", sort = 25,type = 1)
    public Date getInterestStarttime() {
        return interestStarttime;
    }

    public void setInterestStarttime(Date interestStarttime) {
        this.interestStarttime = interestStarttime;
    }

    @ExcelField(title = "利息结束时间", sort = 26,type = 1)
    public Date getInterestEndtime() {
        return interestEndtime;
    }

    public void setInterestEndtime(Date interestEndtime) {
        this.interestEndtime = interestEndtime;
    }

    @ExcelField(title = "开户银行", sort = 27,type = 1)
    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname == null ? null : bankname.trim();
    }

    @ExcelField(title = "银行账号", sort = 28,type = 1)
    public String getBankaccount() {
        return bankaccount;
    }

    public void setBankaccount(String bankaccount) {
        this.bankaccount = bankaccount == null ? null : bankaccount.trim();
    }

    @ExcelField(title = "税务号", sort = 29,type = 1)
    public String getThetaxno() {
        return thetaxno;
    }

    public void setThetaxno(String thetaxno) {
        this.thetaxno = thetaxno == null ? null : thetaxno.trim();
    }

    @ExcelField(title = "启动授信", sort = 30,type = 1)
    public Boolean getCredit() {
        return credit;
    }

    public void setCredit(Boolean credit) {
        this.credit = credit == null ? false : credit;
    }

    @ExcelField(title = "授信额度", sort = 31,type = 1)
    public String getCreditlimes() {
        return creditlimes;
    }

    public void setCreditlimes(String creditlimes) {
        this.creditlimes = creditlimes == null ? null : creditlimes.trim();
    }

    @ExcelField(title = "授信开始", sort = 32,type = 1)
    public Date getCreditStarttime() {
        return creditStarttime;
    }

    public void setCreditStarttime(Date creditStarttime) {
        this.creditStarttime = creditStarttime;
    }

    @ExcelField(title = "授信结束", sort = 33,type = 1)
    public Date getCreditEndtime() {
        return creditEndtime;
    }

    public void setCreditEndtime(Date creditEndtime) {
        this.creditEndtime = creditEndtime;
    }

    @ExcelField(title = "授信百分比", sort = 34,type = 1)
    public String getCreditPercent() {
        return creditPercent;
    }

    public void setCreditPercent(String creditPercent) {
        this.creditPercent = creditPercent == null ? null : creditPercent.trim();
    }
    @ExcelField(title = "客户级别", sort = 35,type = 1)
    public Boolean getClientGrade() {
        return clientGrade;
    }

    public void setClientGrade(Boolean clientGrade) {
        this.clientGrade = clientGrade == null ? false : clientGrade;
    }

    @ExcelField(title = "调仓费", sort = 36,type = 1)
    public Boolean getStorageFee() {
        return storageFee;
    }

    public void setStorageFee(Boolean storageFee) {
        this.storageFee = storageFee == null ? false :storageFee ;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public Long getClientTypeId() {
        return clientTypeId;
    }

    public void setClientTypeId(Long clientTypeId) {
        this.clientTypeId = clientTypeId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }










}