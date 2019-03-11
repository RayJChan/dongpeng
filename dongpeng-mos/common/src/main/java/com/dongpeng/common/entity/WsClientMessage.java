package com.dongpeng.common.entity;

/**
 * 客户端发送的消息实体
 */
public class WsClientMessage {
    private int type; //消息类型
    private Long id;  //消息携带的目标id
    private String name;//消息名称
    private String body;//消息主要内容
    private Long fromUser;
    private Long toUser;

    public WsClientMessage(){

    }

    public WsClientMessage(int type, Long id, String name) {
        this.type = type;
        this.id = id;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getFromUser() {
        return fromUser;
    }

    public void setFromUser(Long fromUser) {
        this.fromUser = fromUser;
    }

    public Long getToUser() {
        return toUser;
    }

    public void setToUser(Long toUser) {
        this.toUser = toUser;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
