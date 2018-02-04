package com.liuyitao.dao;

import com.liuyitao.entity.Pass;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/***
 *@Author: liuyitao
 *@CreateDate:10:54 PM 1/28/2018
 *@DESC:
 *
 *
 *@Modify:
 ***/
public interface PassDao {
    int add(Pass pass);

    int checkNameExists(@Param("name") String name);

    int update(Pass pass);

    List<Pass> findAll();

    Pass find(long id);

    int delete(long id);
}
