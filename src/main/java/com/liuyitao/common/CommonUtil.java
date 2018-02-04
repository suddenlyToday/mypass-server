package com.liuyitao.common;

import com.liuyitao.bean.SystemException;
import com.liuyitao.bean.enums.errorCode.WebErrorCode;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/***
 *@Author: liuyitao
 *@CreateDate:11:46 PM 2/1/2018
 *@DESC:
 *
 *
 *@Modify:
 ***/
public class CommonUtil {

    private CommonUtil() {
    }

    public static String encoderByMd5(String str) {
        //确定计算方法
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");

            BASE64Encoder base64en = new BASE64Encoder();
            //加密后的字符串
            return base64en.encode(md5.digest(str.getBytes("utf-8")));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new SystemException(WebErrorCode.InnerSystemError,e);
        }
    }
}
