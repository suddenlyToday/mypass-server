package com.liuyitao.common;

import com.alibaba.fastjson.JSON;

import java.util.List;

/***
 *@Author: liuyitao
 *@CreateDate:11:54 PM 1/28/2018
 *@DESC:
 *
 *
 *@Modify:
 ***/
public class PassEDUtil {
    private PassEDUtil(){}


    public static String encodePass(String pass)
    {
        return encode(encode(pass));
    }

    public static String decodePass(String source)
    {
        return decode(decode(source));
    }


    private static String encode(String pass)
    {
        byte[] bytes=pass.getBytes();
        Byte[] oBytes=new Byte[bytes.length];

        for(int a=0;a<bytes.length;a++)
        {
            bytes[a]=(byte)(~bytes[a]);
            if(a%2==1)
            {
                bytes[a]=(byte)(bytes[a]^Byte.MAX_VALUE);
            }
            oBytes[a]=bytes[a];
        }

        return JSON.toJSONString(oBytes);
    }

    private static String decode(String source)
    {
        List<Byte> bytelist=JSON.parseArray(source,Byte.class);
        Byte[] bytes=new Byte[bytelist.size()];
        byte[] res=new byte[bytelist.size()];
        bytes=bytelist.toArray(bytes);

        for(int a=0;a<bytes.length;a++)
        {
            if(a%2==1)
            {
                bytes[a]=(byte)(bytes[a]^Byte.MAX_VALUE);
            }
            bytes[a]=(byte)~bytes[a];
            res[a]=bytes[a];
        }
        return new String(res);
    }


}
