package com.liuyitao.controller;

import com.liuyitao.bean.enums.errorCode.PermissionErrorCode;
import com.liuyitao.bean.JsonResult;
import com.liuyitao.bean.UserSessionBean;
import com.liuyitao.bean.enums.errorCode.SuccessCode;
import com.liuyitao.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.liuyitao.common.CacheUtil.*;

/***
 *@Author: liuyitao
 *@CreateDate:11:44 PM 1/29/2018
 *@DESC:
 *
 *
 *@Modify:
 ***/
@RestController
public class UserController {

    @Autowired
    private UserService userservice;

    @RequestMapping(value = "pass/login",method = RequestMethod.GET)
    public @ResponseBody JsonResult login(HttpServletRequest request, String username, String password)
    {
        JsonResult res=new JsonResult();
        if(userservice.checkExist(username,password))
        {
            //根据IP和username生成md5值，当ip发生改变时，删除session
            String sessionId=getUserSessionUuid(getIpAddr(request),username);
            setSession(new UserSessionBean(username).setUserUuid(sessionId));
            res.setCode(SuccessCode.SUCCESS_CODE.getCode()+"").setMsg("login successful").setData(sessionId);
        }else{
            res.setCode(PermissionErrorCode.InvalidUsernameOrPassword.getCode()+"").setMsg(PermissionErrorCode.InvalidUsernameOrPassword.getDesc());
        }

        return res;
    }


    @RequestMapping(value="pass/logout",method = RequestMethod.GET)
    public @ResponseBody JsonResult logout(String username)
    {
        deleteSession(username);
        return new JsonResult().setCode(SuccessCode.SUCCESS_CODE.getCode()+"").setMsg("logout successful");
    }

    private  String getIpAddr(HttpServletRequest request){
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }



}
