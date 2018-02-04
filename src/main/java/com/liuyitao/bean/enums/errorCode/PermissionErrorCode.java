package com.liuyitao.bean.enums.errorCode;

/***
 *@Author: liuyitao
 *@CreateDate:10:00 PM 1/31/2018
 *@DESC:
 *
 *
 *@Modify:
 ***/
public enum PermissionErrorCode implements ErrorCode {
    InvalidUsernameOrPassword(101,"username or password is invalid"),TooManyRequestInTimePeriod(102,"too many requets"),
    NotLoginError(103,"you should login first"),NoPermisionError(104,"you have no permission to do this operate"),
    EnvirementChanged(105,"network enviroment or else has changed");

    private int code;
    private String desc;

    PermissionErrorCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}
