package com.cloud.ceres.rnp.service.aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author heian
 * @create 2020-01-19-12:39 下午
 * @description 模拟实现调用XX平台耗时
 */
@Service
public class TestRpcInterfaceImpl implements TestRpcInterface{

    private Logger logger = LoggerFactory.getLogger(TestRpcInterfaceImpl.class);

    @Override
    public void queryXXX() {
        try {
            logger.info("本体======>"+Thread.currentThread().getName() + "queryXXX:welcome to study aop");
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void queryYYY() {
        try {
            logger.info("本体======>"+Thread.currentThread().getName() + "queryYYY:welcome to study aop");
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
