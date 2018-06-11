package com.liuyitao.service.IService;

import com.liuyitao.entity.Pass;

import java.util.List;

/***
 *@Author: liuyitao
 *@CreateDate:11:47 PM 1/28/2018
 *@DESC:
 *
 *
 *@Modify:
 ***/
public interface IPassService {

    void add(Pass pass);

    boolean checkNameExits(String name);
    void delete(long id);
    void update(Pass pass);
    List<Pass> findAll();
    Pass find(long id);
}
