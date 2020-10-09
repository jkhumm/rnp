package com.cloud.ceres.rnp.control.aop;


/**
 * @author heian
 * @create 2020-01-18-10:28 下午
 * @description 整合之前所写的pointcut + advice,便于后续使用
 */
public class Aspect {

    private Advice advice;
    private Pointcut pointcut;

    public Aspect(Advice advice, Pointcut pointcut) {
        this.advice = advice;
        this.pointcut = pointcut;
    }

    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    public Pointcut getPointcut() {
        return pointcut;
    }

    public void setPointcut(Pointcut pointcut) {
        this.pointcut = pointcut;
    }
}
