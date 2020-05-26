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

    /**
     * Servlet
     *   请求接口：ServletRequest 或者 HttpServletRequest
     *   响应接口：ServletResponse 或者 HttpServletResponse
     * Spring 5.0重新定义了服务请求和响应接口
     *   请求接口：ServerRequest
     *   响应接口：ServerResponse
     *   即支持Servlet规范，也可以支持自定义，比如Netty（Web Server）
     *
     *   定义GET请求，并且返回所有用户对象 URI：/person/find/all
     *   Flux 是0-N个对象集合
     *   Mono 是0-1个对象集合
     *   Reactive中的Flux或者Mono它是异步处理（非阻塞）
     *   集合对象基本上是同步处理（阻塞）
     */
    @Bean
    public RouterFunction<ServerResponse> webFluxRoutesRegister(){
        return RouterFunctions
            .route(RequestPredicates.GET("/user/init").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                    jarayHandler::init)//页面渲染 html响应
            .andRoute(RequestPredicates.POST("/user/add").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                    jarayHandler::add)//添加 json响应
            .andRoute(RequestPredicates.GET("/user/find/all").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                    jarayHandler::findAll)//查询 json响应
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
