package com.dongpeng.entity.system;

import com.dongpeng.common.entity.DataEntity;

/**
 * 用户菜单表 实体类
 */
public class UserMenu extends DataEntity<UserMenu> {
    private static final long serialVersionUID = 1L;
    private Long userId; //用户id
    private String userName;//用户名称
    private Long menuId; //菜单id
    private Long dictionaryId; //角色id

    public UserMenu(){
        super();
    }

    public UserMenu(Long userId,String userName, Long menuId){
        super();
        this.userId=userId;
        this.userName=userName;
        this.menuId=menuId;
    }
    public UserMenu(Long userId,String userName, Long menuId,Long roleId){
        super();
        this.userId=userId;
        this.userName=userName;
        this.menuId=menuId;
        this.dictionaryId=roleId;
    }

    public UserMenu(Long userId){
        super();
        this.userId=userId;
    }

    public UserMenu(Menu menu){
        super();
        this.menuId=null!=menu?menu.getId():null;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(Long dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
