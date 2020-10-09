package com.cloud.ceres.rnp.Neek.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author heian
 * @create 2020-02-02-12:28 上午
 * @description 元注解:必须用切面来使其生效
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedSetValue {

    //@NeedSetValue(beanClass = UserDao.class,param = "custId",method = "queryUserInfo",targetFiled = "name")
    //method.invoke(bean,args)  通过传参的形式告诉

    //该注解 哪个对象
    Class<?> beanClass();

    //该注解 哪个方法
    String method();

    //该注解 哪些入参 最好定义为数组 (int custId,String xx) 复杂点就数组
    String param();

    //该注解  传入的哪个值，这里也可以数组，因为返回的user对象可能不止一个属性
    String targetFiled();

}
