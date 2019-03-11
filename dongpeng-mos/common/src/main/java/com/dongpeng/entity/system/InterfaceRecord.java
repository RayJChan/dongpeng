package com.dongpeng.entity.system;

import com.dongpeng.common.annotation.DataName;
import com.dongpeng.common.annotation.ExcelField;
import com.dongpeng.common.entity.DataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 接口档案表 实体类
 */
public class InterfaceRecord extends DataEntity<InterfaceRecord> {
    @DataName("帐号名称")
    private String accountName;
    @DataName("APP_KEY")
    private String appKey;
    @DataName("APP_Secret")
    private String appSecret;
    @DataName("APP_TOKEN")
    private String appToken;
    @DataName("接口地址")
    private String interfaceAddress;
    @DataName("接口平台")
    private String interfacePlatform;
    @DataName("接口类型")
    private String interfaceType;

    public InterfaceRecord(){
        super();
        this.dataTableName="接口档案";
    }

    public InterfaceRecord(Long id){
        super(id);
    }

    public InterfaceRecord(Long id,boolean deleteFlag){
        this(id);
        this.deleteFlag=deleteFlag;
    }

    @NotNull
    @Length(min = 2,max = 20,message = "帐号名称必须介于 2 和 20 之间")
    @ExcelField(title = "帐号名称",sort = 10)
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @Length(min = 2,max = 20,message = "APP_KEY必须介于 2 和 20 之间")
    @ExcelField(title = "APP_KEY",sort = 20)
    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    @Length(min = 2,max = 20,message = "APP_Secret必须介于 2 和 20 之间")
    @ExcelField(title = "APP_Secret",sort = 30)
    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    @Length(min = 2,max = 200,message = "APP_TOKEN必须介于 2 和 200 之间")
    @ExcelField(title = "APP_TOKEN",sort = 40)
    public String getAppToken() {
        return appToken;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }

    @NotNull
    @Length(min = 2,max = 200,message = "接口地址必须介于 2 和 200 之间")
    @ExcelField(title = "接口地址",sort = 50)
    public String getInterfaceAddress() {
        return interfaceAddress;
    }

    public void setInterfaceAddress(String interfaceAddress) {
        this.interfaceAddress = interfaceAddress;
    }

    @NotNull
    @Length(min = 2,max = 20,message = "接口平台必须介于 2 和 20 之间")
    @ExcelField(title = "接口平台",sort = 60)
    public String getInterfacePlatform() {
        return interfacePlatform;
    }

    public void setInterfacePlatform(String interfacePlatform) {
        this.interfacePlatform = interfacePlatform;
    }

    @NotNull
    @Length(min = 1,max = 20,message = "接口类型必须介于 1 和 20 之间")
    @ExcelField(title = "接口类型",sort = 70)
    public String getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(String interfaceType) {
        this.interfaceType = interfaceType;
    }
}
