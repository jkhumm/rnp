package com.cloud.ceres.rnp.control.ioc;

import com.cloud.ceres.rnp.control.aop.AopInvocationHandler;
import com.cloud.ceres.rnp.control.aop.Aspect;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.util.*;

/**
 * @author heian
 * @create 2020-01-18-10:56 下午
 * @description 工厂类实现  返回你需要的对象
 */
@Component
public class DeafultApplicationContext implements ApplicationContext {

    //存入target目标类对象  (很多源码都是将一些信息  通过map方式存于内存中)
    private Map<String,Class<?>> beanFactoryMap = new HashMap<>();
    //存入切面对象
    private Set<Aspect> aspects = new HashSet<>();


    @Override
    public Object getBean(String beanName) throws IllegalAccessException, InstantiationException {
        //要获得类，必须要制定规则，也就是Bean的定义信息
        Object obj = null;
        if (this.beanFactoryMap.get(beanName) != null){
            Class<?> targetClass = this.beanFactoryMap.get(beanName);
            //代理增强
            obj = proxyEnhance(targetClass.newInstance());//通过反射获取新对象，而不是让用户来new　对象
        }
        return obj;
    }

    /**
     *
     * @param beanName key:类名
     * @param beanClass value：对应存入的目标对象的class
     */
    @Override
    public void registBeanDefinition(String beanName, Class<?> beanClass) {
        beanFactoryMap.put(beanName,beanClass);
    }

    @Override
    public void setAspect(Aspect aspect) {
        this.aspects.add(aspect);
    }

    /**
     * 判断使用者 传过来的bean对象是否属于我们存在的切面中，在则创建代理对象，没切中则直接返回反射创建的工厂类
     * @param target
     */
    private Object proxyEnhance(Object target){
        //判断切面是否存在集合中
        for (Aspect aspect : this.aspects) {
            //传过来的类是否符合我们这个容器内存好的pointcut  这里仅仅比较类名
            if (target.getClass().getName().matches(aspect.getPointcut().getClassPattern())){
                //System.out.println("该bean符合我们pointcut的范围，开始创建代理类");
                Object out_args = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new AopInvocationHandler(target, aspect));
                return out_args;
            }
        }
        return target;
    }

}
