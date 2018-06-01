package com.wei.authserver.server;

import com.wei.authserver.bean.ResultInfo;
import org.springframework.stereotype.Service;

@Service
public class RPCServiceImpl implements RPCService {
    @Override
    public ResultInfo getRPCInfo() {
        ResultInfo resultInfo=new ResultInfo("我是RPC的返回信息，来自其它系统的实现方法");
        return resultInfo;
    }
}