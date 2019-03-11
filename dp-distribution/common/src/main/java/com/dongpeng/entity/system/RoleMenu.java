package com.dongpeng.entity.system;

import com.dongpeng.common.entity.DataEntity;
/**
 * 角色菜单表 实体类
 */
public class RoleMenu extends DataEntity<RoleMenu> {
    private static final long serialVersionUID = 1L;
    private Long dictionaryId; //角色id
    private Long menuId; //菜单id

    public RoleMenu(){
        super();
    }

    public RoleMenu(Long roleId,Long menuId){
        super();
        this.dictionaryId=roleId;
        this.menuId=menuId;
    }

    public RoleMenu(Long roleId){
        super();
        this.dictionaryId=roleId;
    }

    public Long getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(Long dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
}
