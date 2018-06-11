package com.liuyitao.bean;

/***
 *@Author: liuyitao
 *@CreateDate:9:45 PM 1/31/2018
 *@DESC:
 *
 *
 *@Modify:
 ***/
public class JsonResult {

    private String code;
    private String msg;

    private Object data;

    public String getCode() {
        return code;
    }

    public JsonResult setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public JsonResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public JsonResult setData(Object data) {
        this.data = data;
        return this;
    }
}
