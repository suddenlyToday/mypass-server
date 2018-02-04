package com.liuyitao.bean;

/***
 *@Author: liuyitao
 *@CreateDate:9:12 PM 2/4/2018
 *@DESC:
 *
 *
 *@Modify:
 ***/
public class UserRequestRecord {

    private String ip;
    private long date;

    public String getIp() {
        return ip;
    }

    public UserRequestRecord setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public long getDate() {
        return date;
    }

    public UserRequestRecord setDate(long date) {
        this.date = date;
        return this;
    }
}
