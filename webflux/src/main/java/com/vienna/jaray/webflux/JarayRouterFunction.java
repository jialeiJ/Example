package com.vienna.jaray.webflux;

import com.sun.org.apache.bcel.internal.classfile.Visitor;
import com.vienna.jaray.handler.JarayHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class JarayRouterFunction {
    @Autowired
    private JarayHandler jarayHandler;

    @Bean
    public RouterFunction<ServerResponse> webFluxRoutesRegister(){
        return RouterFunctions
            .route(RequestPredicates.GET("/user/init").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                    jarayHandler::init)//页面渲染 html响应
            .andRoute(RequestPredicates.POST("/user/add").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                    jarayHandler::add)//查询 json响应
            .andRoute(RequestPredicates.GET("/user/getList").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                    jarayHandler::getList)//查询 json响应
            .andRoute(RequestPredicates.PUT("/user/update").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                    jarayHandler::update)//更新 json响应
            .andRoute(RequestPredicates.DELETE("/user/delete/{ids}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                    jarayHandler::deletes)//删除 json响应
            .andRoute(RequestPredicates.POST("/user/upload").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                    jarayHandler::upload)//文件上传 json响应
            .andRoute(RequestPredicates.GET("/user/download").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                    jarayHandler::download);//文件下载 stream IO
    }
}
