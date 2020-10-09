package com.cloud.ceres.rnp.cglib.enhancer;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author heian
 * @create 2020-01-20-2:57 下午
 * @description
 */
public class Test1 {

    public void fun(){
        System.out.println("本体");
    }

    public static void main(String[] args) {
        //增强器
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Test1.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] objects, MethodProxy proxy) throws Throwable {
                System.out.println("前置");
                Object result = proxy.invokeSuper(obj, args);
                System.out.println("后置");
                return result;
            }
        });
        Test1 proxyObj = (Test1) enhancer.create();
        proxyObj.fun();
    }


}
