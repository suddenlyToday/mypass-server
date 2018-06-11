package com.liuyitao.controller;

import com.liuyitao.bean.JsonResult;
import com.liuyitao.service.PassService;
import com.liuyitao.bean.enums.errorCode.SuccessCode;
import com.liuyitao.entity.Pass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/***
 *@Author: liuyitao
 *@CreateDate:10:37 PM 1/28/2018
 *@DESC:
 *
 *
 *@Modify:
 ***/

@RestController
public class PassController {

    @Autowired
    private PassService passService;


    @RequestMapping(value="pass/list",method = RequestMethod.GET)
    public @ResponseBody
    JsonResult list()
    {
        return new JsonResult().setCode(SuccessCode.SUCCESS_CODE.getCode()+"").setData(passService.findAll());
    }

    @RequestMapping(value="pass/{id}",method = RequestMethod.GET)
    public @ResponseBody
    JsonResult pass(@PathVariable long id)
    {
        return new JsonResult().setCode(SuccessCode.SUCCESS_CODE.getCode()+"").setData(passService.find(id));
    }

    @RequestMapping(value="pass",method = RequestMethod.PUT)
    public @ResponseBody JsonResult add(@RequestBody Pass pass)
    {
        passService.add(pass);
        return new JsonResult().setCode(SuccessCode.SUCCESS_CODE.getCode()+"").setMsg("add successful");
    }

    @RequestMapping(value="pass",method = RequestMethod.POST)
    public @ResponseBody JsonResult update(@RequestBody Pass pass)
    {
        passService.update(pass);
        return new JsonResult().setCode(SuccessCode.SUCCESS_CODE.getCode()+"").setMsg("update successful");
    }

    @RequestMapping(value="pass/{id}",method = RequestMethod.DELETE)
    public @ResponseBody JsonResult delete(@PathVariable long id)
    {
        passService.delete(id);
        return new JsonResult().setCode(SuccessCode.SUCCESS_CODE.getCode()+"").setMsg("delete successful");

    }

}
