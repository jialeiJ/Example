package com.vienna.jaray;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SocketApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(SocketApplication.class, args);
        //在spring容器启动后，取到已经初始化的SocketServer，启动Socket服务
        //applicationContext.getBean(SocketServer.class).start();
    }

}
