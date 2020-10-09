package com.cloud.ceres.rnp.Neek;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author heian
 * @create 2020-02-02-1:13 上午
 * @description 切面：返回通知增强
 */
@Aspect
@Component
public class SetFieldValueAspect {

    @Autowired
    private BeanUtil beanUtil;

    //返回类型任意，方法参数任意,方法名任意
    @Pointcut(value = "@annotation(com.cloud.ceres.rnp.Neek.annotation.NeedProxyEnhance)")
    public void myPointcut(){

    }

    @Around("myPointcut()")
    public Object doSetFieldValue(ProceedingJoinPoint pjp) throws Throwable{
        Object ret = pjp.proceed();//[{{id=1, custId=3, name=null},{xx}}]
        //具体的增强逻辑
        beanUtil.setNeedField((Collection) ret);
        return ret;
    }

}
