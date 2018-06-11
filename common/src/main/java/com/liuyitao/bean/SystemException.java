package com.liuyitao.bean;

import com.liuyitao.bean.enums.errorCode.ErrorCode;
import com.liuyitao.bean.enums.errorCode.UnknownErrorCode;

import java.util.HashMap;
import java.util.Map;

/***
 *@Author: liuyitao
 *@CreateDate:11:38 PM 1/29/2018
 *@DESC:
 *
 *
 *@Modify:
 ***/
public class SystemException extends RuntimeException {

    private Map<String,Object> erroInfo=new HashMap<>();
    private String message;
    private ErrorCode errorCode;
    private Throwable exception;

    public SystemException(String message, Throwable exception, ErrorCode errorCode) {
        this.message = message;
        this.errorCode = errorCode;
        this.exception = exception;
    }

    public SystemException(ErrorCode errorCode, Throwable exception) {
        this.errorCode = errorCode;
        this.exception = exception;
        this.message=exception.getMessage();
    }

    public SystemException(String message, ErrorCode errorCode) {
        this.message = message;
        this.errorCode = errorCode;
        this.exception=new RuntimeException();
    }

    public SystemException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message=errorCode.getDesc();
        this.exception=new RuntimeException();
    }

    public static SystemException wrap(String message,Throwable exception, ErrorCode errorCode)
    {
        if(exception instanceof SystemException)
        {
            SystemException se=(SystemException) exception;
            if(errorCode!=null)
            {
                return new SystemException(message,exception,errorCode);
            }
            return se;
        }else
        {
            return new SystemException(message,exception,errorCode);
        }
    }

    public static SystemException wrap(Throwable exception, ErrorCode errorCode)
    {
        if(exception instanceof SystemException)
        {
            SystemException se=(SystemException) exception;
            if(errorCode!=null)
            {
                return new SystemException(exception.getMessage(),exception,errorCode);
            }
            return se;
        }else
        {
            return new SystemException(exception.getMessage(),exception,errorCode);
        }
    }


    public static SystemException wrap(Throwable exception)
    {
        if(exception instanceof SystemException)
        {
            return (SystemException) exception;
        }else
        {
            return new SystemException(UnknownErrorCode.UNKNOWN_ERROR_CODE,exception);
        }
    }

    public Map<String,Object> setInfo(String key,String info)
    {
        this. erroInfo.put(key,info);
        return this.erroInfo;
    }

    public Map<String, Object> getErroInfo() {
        return erroInfo;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }
}
