package com.wei.authserver.controller;

import com.wei.authmvc.validator.Validator;
import com.wei.authmvc.validator.annotation.ValidateParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class BaseController  {

    @RequestMapping("/hello")
    public String hello() {
        return "hello,this is a springboot demo";
    }
//
////    ASPECT接口测试
    @ApiOperation("ASPECT接口测试")
    @RequestMapping(value = "/test" ,method = RequestMethod.GET)
    public String testAspect(@ApiParam(value = "登录名", required = true) @ValidateParam({ Validator.NOT_BLANK }) String account,
                             @ApiParam(value = "密码", required = true) @ValidateParam({ Validator.NOT_BLANK }) String password){



        return "hello,this is a springboot demo";
    }

}
