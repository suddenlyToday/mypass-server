package com.liuyitao.service;


import com.liuyitao.bean.SystemException;
import com.liuyitao.bean.enums.errorCode.ParameterErrorCode;
import com.liuyitao.common.PassEDUtil;
import com.liuyitao.dao.PassDao;
import com.liuyitao.entity.Pass;
import com.liuyitao.service.IService.IPassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/***
 *@Author: liuyitao
 *@CreateDate:11:49 PM 1/28/2018
 *@DESC:
 *
 *
 *@Modify:
 ***/
@Service
public class PassService implements IPassService {

    @Autowired
    private PassDao passDao;

    @Override
    public void add(Pass pass) {
        if(pass==null||pass.getUsername()==null||pass.getPassword()==null||pass.getName()==null)
            throw new SystemException("pass name&username&password can't be empty where add pass",ParameterErrorCode.PARAMETER_ERROR_CODE);

        if(checkNameExits(pass.getName()))
        {
            throw new SystemException(String.format( "pass with name(%s) is already exists.",pass.getName()),ParameterErrorCode.REPEAT_PASS_NAME);
        }

        pass.setPassword(PassEDUtil.encodePass(pass.getPassword()));
        pass.setCreatedate(new Date());
        passDao.add(pass);
    }

    @Override
    public boolean checkNameExits(String name) {
        return passDao.checkNameExists(name)>0;
    }

    @Override
    public void delete(long id) {
        passDao.delete(id);
    }

    @Override
    public void update(Pass pass) {
        if(pass==null||pass.getUsername()==null||pass.getPassword()==null||pass.getName()==null)
            throw new SystemException("pass&username&password&name can't be empty where update pass",ParameterErrorCode.PARAMETER_ERROR_CODE);

        pass.setPassword(PassEDUtil.encodePass(pass.getPassword()));
        pass.setLastupdatedate(new Date());
        passDao.update(pass);
    }

    @Override
    public List<Pass> findAll() {
        List<Pass> res=passDao.findAll();
        if(res!=null)res.forEach(p->p.setPassword(PassEDUtil.decodePass(p.getPassword())));
        return res;
    }

    @Override
    public Pass find(long id) {
        Pass res= passDao.find(id);
        if(res!=null) res.setPassword(PassEDUtil.decodePass(res.getPassword()));
        return res;
    }
}
