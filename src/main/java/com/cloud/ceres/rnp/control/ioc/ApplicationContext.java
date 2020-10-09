package com.cloud.ceres.rnp.control.ioc;

import com.cloud.ceres.rnp.control.aop.Aspect;

/**
 * @author heian
 * @create 2020-01-18-10:54 下午
 * @description 工厂接口
 */
public interface ApplicationContext {

    void registBeanDefinition(String beanName,Class<?> beanClass);//存入目标对象

    void setAspect(Aspect aspect);//存入切面

    Object getBean(String beanName) throws IllegalAccessException, InstantiationException;//拿到代理对象

}
