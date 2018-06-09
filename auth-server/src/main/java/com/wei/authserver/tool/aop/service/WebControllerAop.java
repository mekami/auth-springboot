package com.wei.authserver.tool.aop.service;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class WebControllerAop {


    // @Pointcut("execution (* findById*(..))")
    @Pointcut("execution(* com.wei.authserver.*.get*(..))")
    public void excudeService(){}
    /*
     * 通过连接点切入
     */
    @Before("execution(* get*ById*(..)) &&" + "args(id,..)")
    public void getSomeBodyById(Long id){
        System.err.println ("获取了用户的实例数据+" + id);

    }

//    @Before("execution(* add*(..)) &&" + "args(object,..)")
//    public void addSomeBody(Object object){
//        System.err.println ("添加用户的某些实例数据:"+ JSON.parse(object.toString())  );
//
//    }
    @Before("execution(* get*ById*(..)) &&" + "args(id,..)")
    public void updateSomeBody(Long id){
        System.err.println ("更新了用户的实例数据+" + id);

    }
    @Before("execution(* del*ById*(..)) &&" + "args(id,..)")
    public void delSomeBody(Long id){
        System.err.println ("删除了用户的实例数据+" + id);

    }

    @Around("excudeService()")
    public Object twiceAsOld(ProceedingJoinPoint thisJoinPoint){
        System.err.println ("切面执行了。。。。");
        try {
//            需要记录的事情
        } catch (Throwable e) {
            e.printStackTrace ();
        }
        return null;
    }
}
