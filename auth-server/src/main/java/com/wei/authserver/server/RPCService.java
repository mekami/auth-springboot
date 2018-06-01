package com.wei.authserver.server;

import com.wei.authserver.bean.ResultInfo;

public interface RPCService {
    /**
     * 通过Dubbo实现远程RPC调用接口信息
     * @return
     */
    public ResultInfo getRPCInfo();
}
