package com.dongpeng.entity.system;

import com.dongpeng.common.annotation.ExcelField;
import com.dongpeng.common.entity.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class Shop extends DataEntity<Shop> {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String shopCode;

    private String shopName;

    private Long clientId;

    private String clientCode;

    private String clientName;

    private String leader;

    private String province;

    private String city;

    private String district;

    private String shopAddress;

    private String shopAccount;

    private String shopLongitude;

    private String shopLatitude;

    private String serviceRadius;

    private String leaderTel;


    public Shop(){
        super();
        this.dataTableName="门店";
    }
    @ExcelField(title = "客编code", sort = 3)
    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ExcelField(title = "门店code", sort = 1)
    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode == null ? null : shopCode.trim();
    }

    @ExcelField(title = "门店名称", sort = 2)
    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName == null ? null : shopName.trim();
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    @ExcelField(title = "客编名称", sort = 4)
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName == null ? null : clientName.trim();
    }

    @ExcelField(title = "联系人", sort = 5)
    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader == null ? null : leader.trim();
    }

    @Length(min = 11, max = 11, message = "电话号码必须为11位数")
    @ExcelField(title = "联系电话", sort = 6)
    public String getLeaderTel() {
        return leaderTel;
    }

    public void setLeaderTel(String leaderTel) {
        this.leaderTel = leaderTel == null ? null : leaderTel.trim();
    }

    @ExcelField(title = "省", sort = 7)
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    @ExcelField(title = "市", sort = 8)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    @ExcelField(title = "区", sort = 9)
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
    }

    @ExcelField(title = "地址", sort = 10)
    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress == null ? null : shopAddress.trim();
    }

    @ExcelField(title = "门店账号", sort = 11)
    public String getShopAccount() {
        return shopAccount;
    }

    public void setShopAccount(String shopAccount) {
        this.shopAccount = shopAccount == null ? null : shopAccount.trim();
    }

    @ExcelField(title = "经度", sort = 12)
    public String getShopLongitude() {
        return shopLongitude;
    }

    public void setShopLongitude(String shopLongitude) {
        this.shopLongitude = shopLongitude == null ? null : shopLongitude.trim();
    }

    @ExcelField(title = "纬度", sort = 13)
    public String getShopLatitude() {
        return shopLatitude;
    }

    public void setShopLatitude(String shopLatitude) {
        this.shopLatitude = shopLatitude == null ? null : shopLatitude.trim();
    }

    @ExcelField(title = "服务半径", sort = 14)
    public String getServiceRadius() {
        return serviceRadius;
    }

    public void setServiceRadius(String serviceRadius) {
        this.serviceRadius = serviceRadius == null ? null : serviceRadius.trim();
    }
}