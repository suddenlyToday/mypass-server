package com.liuyitao.bean.enums.errorCode;

/***
 *@Author: liuyitao
 *@CreateDate:11:40 PM 1/31/2018
 *@DESC:
 *
 *
 *@Modify:
 ***/
public enum SuccessCode implements Code {

    SUCCESS_CODE(200);

    private int code;

    SuccessCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
