package com.cloud.ceres.rnp.control.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author heian
 * @create 2020-01-19-12:21 上午
 * @description  代理类
 */
public class AopInvocationHandler implements InvocationHandler {


    private Object targetObj; //目标对象

    private Aspect aspect;//切面类

    public AopInvocationHandler(Object targetObj, Aspect aspect) {
        this.targetObj = targetObj;
        this.aspect = aspect;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //执行增强逻辑
        //执行目标方法，得到增强对象
        if (method.getName().matches(this.aspect.getPointcut().getMethodPattern())) {
           // System.out.println("进入代理类，已经知道目标对象属于这个类，观察其方法，是不是属于正则的方法");
            //执行增强逻辑
            Object out_args = this.aspect.getAdvice().invoke(targetObj, method, args);
            return out_args;
        }
        return method.invoke(targetObj,args);//没有切中，则不执行增强逻辑，直接返回反射的方法即可
    }
}
