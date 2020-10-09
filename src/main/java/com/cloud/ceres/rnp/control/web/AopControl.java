package com.cloud.ceres.rnp.control.web;

import com.cloud.ceres.rnp.control.aop.Advice;
import com.cloud.ceres.rnp.control.aop.Aspect;
import com.cloud.ceres.rnp.control.aop.Pointcut;
import com.cloud.ceres.rnp.control.ioc.DeafultApplicationContext;
import com.cloud.ceres.rnp.service.aop.AdviceImpl;
import com.cloud.ceres.rnp.service.aspect.TestRpcInterface;
import com.cloud.ceres.rnp.service.aspect.TestRpcInterfaceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author heian
 * @create 2020-01-17-12:55 上午
 * @description AOP测试Control层
 */
@RestController
@RequestMapping("/aop")
public class AopControl {
    @Autowired
    private TestRpcInterfaceImpl testRpcInterfaceImpl;
    @Autowired
    private DeafultApplicationContext deafultApplicationContext;

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    private static Logger logger = LoggerFactory.getLogger (AopControl.class);

    @GetMapping("/queryXXX")
    public String queryXXX()  {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        Cookie[] cookies = request.getCookies();
        HttpServletResponse response = requestAttributes.getResponse();
        if (cookies!= null) {
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName() + ":[cooke]:" + cookie.getValue());
                cookie.setHttpOnly(true);
            }
        }
        response.addCookie(new Cookie("name","hmm"));

        HttpSession session = request.getSession();
        System.out.println("sessionId:" + session.getId() + session.isNew());
        session.setAttribute("currentTime",System.currentTimeMillis());
        Enumeration<String> attrs = session.getAttributeNames();
        while(attrs.hasMoreElements()){
            // 获取session键值
            String name = attrs.nextElement().toString();
            // 根据键值取session中的值
            Object vakue = session.getAttribute(name);
            System.out.println(name + ":[session]" + vakue);
        }
        //HttpServletRequest request2 = ServletActionContext.getRequest();com.opensymphony.webwork.ServletActionContext
        //HttpSession session2 = request.getSession();

        testRpcInterfaceImpl.queryXXX();
        return "aopTest方法结束";
    }

    @GetMapping("/queryYYY")
    public String queryYYY()  {
        ServletRequestAttributes  servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpSession session = request.getSession();
        //　　创建：sessionid第一次产生是在直到某server端程序调用 HttpServletRequest.getSession(true)这样的语句时才被创建。
        Enumeration<String> attrs = session.getAttributeNames();
        while(attrs.hasMoreElements()){
            // 获取session键值
            String name = attrs.nextElement().toString();
            // 根据键值取session中的值
            Object vakue = session.getAttribute(name);
            System.out.println(name + ":[session]" + vakue);
        }

        return "aopTest方法结束";
    }

    @GetMapping("/testMy")
    public void testMy() throws Exception{
        if (atomicInteger.incrementAndGet() == 1){
            //第一次进来，需要
            Advice adviceImpl = new AdviceImpl();
            Pointcut pointcut = new Pointcut("com\\.cloud\\.ceres\\.rnp\\.service\\.aspect\\.TestRpcInterfaceImpl",".*");//.正则特殊字符，需要转义
            Aspect aspect = new Aspect(adviceImpl,pointcut);
            //存入准备好的切面到工厂容器 类比：Spring是通过xml或者注解的形式）ioc是aop的基石
            deafultApplicationContext.setAspect(aspect);
            deafultApplicationContext.registBeanDefinition("TestRpcInterfaceImpl", TestRpcInterfaceImpl.class);
        }
        //通过之前存入切面作为条件，判断注册到工厂类的bean是否处于Pointcut中，处于则返回代理类，不处于则直接返回工厂对象
        TestRpcInterface testRpcInterface = (TestRpcInterface) deafultApplicationContext.getBean("TestRpcInterfaceImpl");//返回代理对象
        testRpcInterface.queryXXX();
        testRpcInterface.queryYYY();
    }
}
