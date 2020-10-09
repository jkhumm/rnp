package com.cloud.ceres.rnp.cglib.enhancer;

import org.junit.Assert;
import org.springframework.cglib.proxy.CallbackHelper;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.FixedValue;
import org.springframework.cglib.proxy.NoOp;

import java.lang.reflect.Method;

/**
 * @author heian
 * @create 2020-01-20-2:57 下午
 * @description
 */
public class Test3 {

    public String fun(String str ){
        System.out.println("本体");
        return "本体";
    }

    public static void main(String[] args) {
        //增强器
        Enhancer enhancer = new Enhancer();
        CallbackHelper callbackHelper = new CallbackHelper(Test3.class, new Class[0]) {
            @Override
            protected Object getCallback(Method method) {
                if(method.getDeclaringClass() != Object.class && method.getReturnType() == String.class){
                    return new FixedValue() {
                        @Override
                        public Object loadObject() throws Exception {
                            System.out.println(111);
                            return "Hello cglib";
                        }
                    };
                }else{
                    return NoOp.INSTANCE;
                }
            }
        };
        enhancer.setSuperclass(Test3.class);
        enhancer.setCallbackFilter(callbackHelper);
        enhancer.setCallbacks(callbackHelper.getCallbacks());
        Test3 proxy = (Test3) enhancer.create();
        // 1. 如果两者一致, 程序继续往下运行. 2. 如果两者不一致, 中断测试方法,
        Assert.assertEquals("Hello cglib", proxy.fun(null));
        Assert.assertNotEquals("Hello cglib",proxy.toString());
        System.out.println(proxy.hashCode());
    }


}
