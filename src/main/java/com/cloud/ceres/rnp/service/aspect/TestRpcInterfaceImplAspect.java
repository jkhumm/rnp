package com.cloud.ceres.rnp.service.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author heian
 * @create 2020-01-17-12:54 上午
 * @description 切面类：单独对AopControl类某个做接口统计访问
 */
//@Component
//@Aspect
public class TestRpcInterfaceImplAspect {

    //统计接口耗时
    private ThreadLocal<Long> timeLocal = new ThreadLocal<>();
    //统计接口访问次数
    private ThreadLocal<Integer> numLocal = new ThreadLocal<>();
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    private static Logger logger = LoggerFactory.getLogger (TestRpcInterfaceImplAspect.class);

    /**
     * @description:定义一个切入点，可以是规则表达式，也可以是某个package下的所有函数方法，也可以是一个注解,其实就是执行条件，满足此条件的就切入
     * 备注：Controller层下的所有类的所有方法，返回类型任意，方法参数任意
     * 从前到后顺序解释：execution  在满足后面的条件的方法执行时触发
     *                第一个*  表示返回值任意，记得有空格
     *                com.cloud.ceres.rnp.control.web.AopControl.类.方法  表示此路径下的任意类的任意方法
     *                (..) 表示方法参数任意
     *举例：对某个包下所有类所有方法 execution(* com.cloud.ceres.rnp.service.aspect.*.*(…))
     */
    @Pointcut("execution(* com.cloud.ceres.rnp.service.aspect.TestRpcInterfaceImpl.*(..))")
    public void myPointCut(){

    }


    /**
     * @description:在切入点之前执行
     * @param joinPoint 切面类和业务类的连接点，其实就是封装了业务方法的一些基本属性，作为通知的参数来解析。
     * 备注：如果前置方法里不需要连接点，joinPoint可以不传入
     */
  /*  @Before("myPointCut()")
    public void printBeforeLog(JoinPoint joinPoint){
        int visitTimes = atomicInteger.incrementAndGet();
        numLocal.set(visitTimes);
        timeLocal.set(System.currentTimeMillis());
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //记录请求日志
        logger.info("前置======>url；"+request.getRequestURL().toString() + "，接口类型" + request.getMethod() + "接口ip:" + request.getMethod());
        logger.info("前置======>classMethod : " + joinPoint.getSignature().getDeclaringTypeName() + "下的" + joinPoint.getSignature().getName() + "参数：" + Arrays.toString(joinPoint.getArgs()));
    }*/

    /**
     * @param pointcut 切入点
     * @param returning 接口方法返回的结果参数
     * 备注：如果后置方法里不需要连接点，joinPoint可以不传入
     */
  /*  @AfterReturning(pointcut = "myPointCut()",returning = "args")
    public void printBeforeLog(JoinPoint joinPoint,Object args){
        logger.info("后置======>response:"+args);//处理返回值信息
        String pkgName = joinPoint.getSignature ().getDeclaringTypeName ();
        String methodName = joinPoint.getSignature ().getName ();
        logger.info("后置======>" + pkgName +"下的" +methodName +
                "方法耗时为：" + (System.currentTimeMillis ()-timeLocal.get ()) + "毫秒"+ "访问次数为：" + numLocal.get ());
    }*/

    //如果不对返回值进行处理 可以直接用around
    @Around("myPointCut()")
    public void printAroundLog(ProceedingJoinPoint joinPoint) throws Throwable {
        numLocal.set(atomicInteger.incrementAndGet());
        timeLocal.set(System.currentTimeMillis());
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        HttpSession session = request.getSession();

        //记录请求日志
        logger.info("前置======>url；"+request.getRequestURL().toString() + "，接口类型" + request.getMethod() + "接口ip:" + request.getMethod());
        logger.info("前置======>classMethod : " + joinPoint.getSignature().getDeclaringTypeName() + "下的" + joinPoint.getSignature().getName() + "参数：" + Arrays.toString(joinPoint.getArgs()));
        Object args = joinPoint.proceed();
        String pkgName = joinPoint.getSignature ().getDeclaringTypeName ();
        String methodName = joinPoint.getSignature ().getName ();
        logger.info("后置======>" + pkgName +"下的" +methodName +
                "方法耗时为：" + (System.currentTimeMillis ()-timeLocal.get ()) + "毫秒"+ "访问次数为：" + numLocal.get ());
    }

}
