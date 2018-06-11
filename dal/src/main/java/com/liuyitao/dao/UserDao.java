package com.liuyitao.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/***
 *@Author: liuyitao
 *@CreateDate:10:56 PM 1/28/2018
 *@DESC:
 *
 *
 *@Modify:
 ***/
public interface UserDao {

    ///INSERT INTO T_Users (username,PASSWORD) VALUES('liuyitao',MD5(CONCAT(MD5('5211314hf/'),soltStr)))
    @Select("select count(*) from T_Users where username=#{username} and password=md5(concat(#{password},'solt'))")
    int count(@Param("username") String username,@Param("password") String password);
}
