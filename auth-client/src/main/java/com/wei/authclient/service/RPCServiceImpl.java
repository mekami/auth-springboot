package com.wei.authclient.service;

import com.wei.authclient.bean.ResultInfo;

import org.springframework.data.annotation.Reference;

public class RPCServiceImpl implements RPCService{
    @Reference()
    RPCService rpcInterface;//调用远程接口的实现类

    @Override
    public ResultInfo getRPCInfo() {
        ResultInfo resultInfo=rpcInterface.getRPCInfo();
        return resultInfo;
    }

}
