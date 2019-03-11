package com.dpmall.db.bean.po;

public class UserEntity {
    /**用户ID*/
    public Long id;

    /**用户名*/
    public String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
