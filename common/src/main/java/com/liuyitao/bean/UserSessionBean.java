package com.liuyitao.bean;


/***
 *@Author: liuyitao
 *@CreateDate:10:50 PM 2/1/2018
 *@DESC:user session bean
 *
 *
 *@Modify:
 ***/
public class UserSessionBean {

    private String username;
    private String userUuid;
    private Object data;

    public UserSessionBean(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public UserSessionBean setUserUuid(String userUuid) {
        this.userUuid = userUuid;
        return this;
    }

    public Object getData() {
        return data;
    }

    public UserSessionBean setData(Object data) {
        this.data = data;
        return this;
    }
}
