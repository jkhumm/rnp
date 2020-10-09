package com.cloud.ceres.rnp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class RnpApplication {


    public static void main(String[] args) throws UnknownHostException {
        System.out.println("启动服务");
        ConfigurableApplicationContext application =  SpringApplication.run(RnpApplication.class,args);
        System.out.println("服务启动完成");
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        System.out.println("\n----------------------------------------------------------\n\t"+
                path +
                "Application is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port  + "/\n\t" +
                "External: \thttp://" + ip + ":" + port  + "/\n\t" +
                "Swagger-UI: http://" + ip + ":" + port  + "/doc.html\n" +
                "----------------------------------------------------------");
    }




}
