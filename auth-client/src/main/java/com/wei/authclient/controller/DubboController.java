package com.wei.authclient.controller;

import com.wei.authclient.bean.ResultInfo;
import com.wei.authclient.service.RPCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DubboController {
    @Autowired
    private RPCService rpcService;


    @RequestMapping("/dubbo")
    @ResponseBody
    public String errorNginxTest(){
        ResultInfo resultInfo=rpcService.getRPCInfo();
        return "result:"+resultInfo.getResultInfo();
    }
}