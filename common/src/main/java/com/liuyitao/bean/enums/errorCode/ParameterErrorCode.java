package com.liuyitao.bean.enums.errorCode;

/***
 *@Author: liuyitao
 *@CreateDate:11:23 PM 1/31/2018
 *@DESC:
 *
 *
 *@Modify:
 ***/
public enum ParameterErrorCode implements ErrorCode {
    PARAMETER_ERROR_CODE(601,"wrong parameter"),REPEAT_PASS_NAME(602,"pass with name is already exists in db");

    private int code;
    private String desc;

    ParameterErrorCode(int code, String desc) {
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
