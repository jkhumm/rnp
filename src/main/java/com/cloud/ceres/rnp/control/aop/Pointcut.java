package com.cloud.ceres.rnp.control.aop;

/**
 * @author heian
 * @create 2020-01-18-10:24 下午
 * @description 正则表达式筛选指定类与方法
 */
public class Pointcut {

    private String classPattern;

    private String methodPattern;

    public Pointcut(String classPattern, String methodPattern) {
        this.classPattern = classPattern;
        this.methodPattern = methodPattern;
    }

    public String getClassPattern() {
        return classPattern;
    }

    public void setClassPattern(String classPattern) {
        this.classPattern = classPattern;
    }

    public String getMethodPattern() {
        return methodPattern;
    }

    public void setMethodPattern(String methodPattern) {
        this.methodPattern = methodPattern;
    }
}
