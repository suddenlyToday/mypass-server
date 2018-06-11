package com.liuyitao.service;

import com.liuyitao.service.IService.IUserService;
import com.liuyitao.dao.UserDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 *@Author: liuyitao
 *@CreateDate:11:06 PM 1/28/2018
 *@DESC:
 *
 *
 *@Modify:
 ***/

@Service
public class UserService implements IUserService {

    @Autowired
    private UserDao userDao;

    @Override
    public boolean checkExist(String username, String password) {
        return !StringUtils.isEmpty(username) && !StringUtils.isEmpty(password) && userDao.count(username, password) > 0;
    }
}
