package com.cloud.ceres.rnp.Neek;

import com.cloud.ceres.rnp.Neek.annotation.NeedSetValue;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author heian
 * @create 2020-02-02-1:21 上午
 * @description
 */
@Component
public class BeanUtil implements ApplicationContextAware {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (applicationContext == null){
           this.applicationContext = applicationContext;
        }
    }

    /**
     * 通过返回执行结果 利用反射对其进行赋值
     */
    public void setNeedField(Collection collection) throws Exception{
        //1、拿到单个对象（order），然后根据单个对象某个字段（name）上  去拿到注解
        Object retObj = collection.iterator().next();
        Field[] fields = retObj.getClass().getDeclaredFields();//field.getFields();父类的变量,别搞错了
        Map<String,Object> cacheMap = new HashMap<>();//假装自己是个redis 缓存  形式为：UserDao-queryUserInfo-name-custId一个
        //一个大对象中字段 可能不止一个注解变量  逐个遍历
        for (Field field : fields) {
            NeedSetValue annotation = field.getAnnotation(NeedSetValue.class);
            if (annotation == null){
                continue;
            }
            field.setAccessible(true);
            //2、取得注解后，再从容器中取得dao层实例，再取得该dao对用的方法Method
            Object bean = applicationContext.getBean(annotation.beanClass());//拿到dao层的实例对象
            //有了注解就可以拿到查询的方法 方法名 + 方法入参 --> userDao.queryUserInfo(int custId)
            String methodName = annotation.method();
            Field custIdField = retObj.getClass().getDeclaredField(annotation.param());
            Class<?> parpmClassType = custIdField.getType();
            Method method = bean.getClass().getMethod(methodName, parpmClassType);
            //3、反射拿到结果值
            custIdField.setAccessible(true);
            boolean bool = !StringUtils.isEmpty(annotation.targetFiled());
            // UserDao-queryUserInfo-name-（user类中的name字段）
            String keyPrefix = annotation.beanClass() + "-" + annotation.method() + "-";
            for (Object ret : collection) {
                Object paramValue = custIdField.get(ret);//获取当前对象中当前Field的value  也可以通过反射getCustId()
                if (paramValue == null)
                    continue;
                //interface com.cloud.ceres.rnp.Neek.dao.UserDao-queryUserInfo-name-custId(1)
                String key = keyPrefix + paramValue;//redis 中的kev
                Object needValue = null;
                if (cacheMap.containsKey(key)){
                    //假设缓存中存在custId,则利用反射去执行方法，拿到name
                    needValue = cacheMap.get(key);
                }else {
                    //缓存不存在，则利用刚拿到的custId,反射去执行方法，拿到name
                    Object userRet = method.invoke(bean, paramValue);
                    //注解上必须标明要拿哪个对象的哪个值(这里指user对象的name),并且redis中必须存在该对象
                    if (bool && userRet != null){
                        Field userNameField = userRet.getClass().getDeclaredField(annotation.targetFiled());
                        userNameField.setAccessible(true);
                        needValue = userNameField.get(userRet);
                        cacheMap.put(key,needValue);
                    }
                }
                //4、对结果进行返回增强
                Object currentNeedVlaue = field.get(ret);
                if (currentNeedVlaue == null){
                    field.set(ret,needValue);
                }
            }
        }


    }

}
