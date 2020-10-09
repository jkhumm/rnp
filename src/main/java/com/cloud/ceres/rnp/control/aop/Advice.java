package com.cloud.ceres.rnp.control.aop;

import java.lang.reflect.Method;

/**
 * @author heian
 * @create 2020-01-17-12:28 上午
 * @description 增强接口 Spring中包含：前置增强、后置增强、环绕增强、返回增强、异常增强,
 * 1.实现怎样的增强逻辑，可以实现该接口，比如AdviceImpl就实现了环绕增强
 * 2.具体实现可以通过AdviceInpl来实现
 */
public interface Advice {

    Object invoke(Object target, Method method, Object[] args) throws Throwable;

}
