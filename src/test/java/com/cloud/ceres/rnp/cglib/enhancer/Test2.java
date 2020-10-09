package com.cloud.ceres.rnp.cglib.enhancer;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.FixedValue;

/**
 * @author heian
 * @create 2020-01-20-2:57 下午
 * @description
 */
public class Test2 {

    public String fun(String str ){
        return "本体";
    }

    public static void main(String[] args) {
        //增强器
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Test2.class);
        enhancer.setCallback(new FixedValue() {
            @Override
            public Object loadObject() throws Exception {
                return "回调";
            }
        });
        Test2 proxy = (Test2) enhancer.create();
        System.out.println(proxy.fun(null)); //拦截test，
        System.out.println(proxy.toString());
        System.out.println(proxy.getClass());//没有拦截 final
        System.out.println(proxy.hashCode());
    }


}
