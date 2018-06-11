package com.liuyitao.common;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.liuyitao.bean.SystemException;
import com.liuyitao.bean.enums.errorCode.PermissionErrorCode;
import com.liuyitao.bean.UserRequestRecord;
import com.liuyitao.bean.UserSessionBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/***
 *@Author: liuyitao
 *@CreateDate:11:18 PM 2/1/2018
 *@DESC:
 *
 *
 *@Modify:
 ***/
public class CacheUtil {
    private CacheUtil(){}
    private static Logger logger= LoggerFactory.getLogger(CacheUtil.class);

    private static Cache<String, UserSessionBean> mySession = CacheBuilder.newBuilder()
            .expireAfterAccess(10, TimeUnit.DAYS)
            .expireAfterWrite(100, TimeUnit.DAYS)
            .maximumSize(100)
            .initialCapacity(1)
            .build();

    public static void setSession(UserSessionBean userSessionBean)
    {
        if(userSessionBean.getUsername()==null)
            throw new NullPointerException("username can not be null to establish session");
        mySession.put(userSessionBean.getUsername(),userSessionBean);
    }

    public static UserSessionBean getSession(String username)
    {
        return mySession.getIfPresent(username);
    }

    public static void deleteSession(String username)
    {
        mySession.invalidate(username);
    }

    public static String getUserSessionUuid(String ip, String username)
    {
        return CommonUtil.encoderByMd5(ip+username);
    }

    //十分钟内80次，即可定义为请求次数太多
    private static int Max_Request_Count_Minutes_Limit=10;
    private static int Max_Request_Count_Limit=80;
    private static LoadingCache<String,MyFixedList<UserRequestRecord>> requestCordCache=
            CacheBuilder.newBuilder()
                    .expireAfterWrite(1,TimeUnit.DAYS)
                    .expireAfterAccess(Max_Request_Count_Minutes_Limit, TimeUnit.MINUTES)
                    .maximumSize(1000)
                    .initialCapacity(10)
                    .build(new CacheLoader<String, MyFixedList<UserRequestRecord>>() {
                        @Override
                        public MyFixedList<UserRequestRecord> load(String key) {
                            return new MyFixedList<>(Max_Request_Count_Limit);
                        }
                    });

    public static void addRequestRecord(String ip)
    {
        try {
            MyFixedList<UserRequestRecord> userRequestRecords = requestCordCache.get(ip);
            userRequestRecords.add(new UserRequestRecord().setIp(ip).setDate(new Date().getTime()));
            checkRequestCount(userRequestRecords);
        } catch (ExecutionException e) {
            logger.error("add request record to cache failed",e);
        }
    }

    private static void checkRequestCount(MyFixedList<UserRequestRecord> list)
    {
        UserRequestRecord start=list.getFirst();
        UserRequestRecord end=list.getLast();

        if(start!=null&&end!=null&&(end.getDate()-start.getDate())<=Max_Request_Count_Minutes_Limit*60*1000) {
            throw new SystemException("too many requests,take a time for a while...", PermissionErrorCode.TooManyRequestInTimePeriod);
        }
    }

}
