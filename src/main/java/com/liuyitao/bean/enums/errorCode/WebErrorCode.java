package com.liuyitao.bean.enums.errorCode;

/***
 *@Author: liuyitao
 *@CreateDate:11:11 PM 1/31/2018
 *@DESC:
 *
 *
 *@Modify:
 ***/
public enum WebErrorCode implements ErrorCode {
    NotFoundError(404,"resource not found"),InnerSystemError(500,"system inner error");

    private int code;
    private String desc;

    WebErrorCode(int code, String desc) {
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
