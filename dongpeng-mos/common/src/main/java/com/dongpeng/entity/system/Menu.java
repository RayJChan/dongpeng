package com.dongpeng.entity.system;

import com.dongpeng.common.annotation.DataName;
import com.dongpeng.common.annotation.ExcelField;
import com.dongpeng.common.entity.TreeEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 菜单表 实体类
 */
public class Menu extends TreeEntity<Menu> {
    private static final long serialVersionUID = 1L;
    @DataName("菜单编码")
    private String menuCode;          //菜单编码
    @DataName("图标")
    private String meunIcon;         //图标
    @DataName("菜单类型")
    private String meunType;         //菜单类型
    @DataName("菜单名称")
    private String menuName;         //菜单名称
    @DataName("菜单链接")
    private String menuLink;         //菜单链接
    @DataName("菜单文件夹路径")
    private String menuFolder;         //菜单文件夹路径
    @DataName("排序号")
    private Integer meunSequence;     //排序号

    public Menu() {
        super();
        this.dataTableName = "菜单";
    }

    public Menu(Long id) {
        super(id);
    }

    public Menu(Long id, boolean deleteFlag) {
        super(id);
        this.deleteFlag = deleteFlag;
    }

    @NotNull
    @Length(min = 1, max = 64, message = "菜单编码必须介于 1 和 64 之间")
    @ExcelField(title = "菜单编码")
    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    @ExcelField(title = "图标")
    public String getMeunIcon() {
        return meunIcon;
    }

    public void setMeunIcon(String meunIcon) {
        this.meunIcon = meunIcon;
    }

    @NotNull
    @Length(min = 1, max = 10, message = "菜单类型必须介于 1 和 10 之间")
    @ExcelField(title = "菜单类型")
    public String getMeunType() {
        return meunType;
    }

    public void setMeunType(String meunType) {
        this.meunType = meunType;
    }

    @NotNull
    @Length(min = 1, max = 10, message = "菜单名称必须介于 1 和 10 之间")
    @ExcelField(title = "菜单名称")
    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    @Length(max = 200, message = "菜单链接必须小于200")
    @ExcelField(title = "菜单链接")
    public String getMenuLink() {
        return menuLink;
    }

    public void setMenuLink(String menuLink) {
        this.menuLink = menuLink;
    }

    @NotNull(message = "排序号不能为空")
    @ExcelField(title = "排序号")
    public Integer getMeunSequence() {
        return meunSequence;
    }

    public void setMeunSequence(Integer meunSequence) {
        this.meunSequence = meunSequence;
    }

    @Length(max = 255, message = "菜单文件夹必须小于255")
    @ExcelField(title = "菜单文件夹")
    public String getMenuFolder() {
        return menuFolder;
    }

    public void setMenuFolder(String menuFolder) {
        this.menuFolder = menuFolder;
    }
}
