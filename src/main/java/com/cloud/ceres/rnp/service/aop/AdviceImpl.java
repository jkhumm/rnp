package com.cloud.ceres.rnp.service.aop;

import com.cloud.ceres.rnp.control.aop.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author heian
 * @create 2020-01-18-9:49 下午
 * @description 业务所需的增强逻辑
 */
@Service
public class AdviceImpl implements Advice {

    private Logger logger = LoggerFactory.getLogger(AdviceImpl.class);
    //统计访问次数
    private ThreadLocal<Integer> visitTimes = new ThreadLocal<>();
    //统计接口耗时
    private ThreadLocal<Long> costTime = new ThreadLocal<>();
    //次数自增
    private AtomicInteger num = new AtomicInteger(0);

    @Override
    public Object invoke(Object target, Method method, Object[] args) throws Throwable {
        visitTimes.set(num.incrementAndGet());
        costTime.set(System.currentTimeMillis());
        Object out_args = method.invoke(target, args);//方法调用返回结果值
        logger.info("类名：" + target.getClass().getName() + "，方法名：" + method.getName() + "，接口耗时：" + (System.currentTimeMillis() - costTime.get() )+ "毫秒");
        logger.info("类名：" + target.getClass().getName() + "，方法名：" + method.getName() + "，访问次数：" + (visitTimes.get())+ "次");
        return out_args;
    }
}
