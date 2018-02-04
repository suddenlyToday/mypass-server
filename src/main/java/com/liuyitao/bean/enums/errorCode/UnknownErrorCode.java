package com.liuyitao.bean.enums.errorCode;

/***
 *@Author: liuyitao
 *@CreateDate:10:18 PM 1/31/2018
 *@DESC:
 *
 *
 *@Modify:
 ***/
public enum UnknownErrorCode implements ErrorCode {
    UNKNOWN_ERROR_CODE(100,"unkown error");

    private int code;
    private String desc;

    UnknownErrorCode(int code, String desc) {
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
