package com.dongpeng.common.entity;
/**
 * 服务端发送的消息实体
 */
public class WsServerMessage {
    private int type; //消息类型
    private Long id;  //消息携带的目标id
    private String name;//消息名称
    private String body;//消息主要内容
    private String icon;//消息图标
    private Long fromUser;
    private Long toUser;

    public WsServerMessage(){

    }

    public WsServerMessage(int type, Long id, String name) {
        this.type = type;
        this.id = id;
        this.name = name;
    }

    public WsServerMessage(int type, Long id, String name, String body, String icon) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.body = body;
        this.icon = icon;
    }

    public WsServerMessage(WsClientMessage wsClientMessage){
        this.type=wsClientMessage.getType();
        this.id=wsClientMessage.getId();
        this.name=wsClientMessage.getName();
        this.body=wsClientMessage.getBody();
        this.fromUser=wsClientMessage.getFromUser();
        this.toUser=wsClientMessage.getToUser();
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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
}
