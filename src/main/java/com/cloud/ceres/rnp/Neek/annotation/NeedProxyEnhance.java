package com.cloud.ceres.rnp.Neek.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author heian
 * @create 2020-02-02-12:55 上午
 * @description 定义切点：指明哪些类需要返回增强,如果范围较大可以使用Spring的el表达式Pointcut
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NeedProxyEnhance {

}
