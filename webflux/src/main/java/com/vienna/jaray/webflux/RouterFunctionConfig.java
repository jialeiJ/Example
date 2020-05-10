package com.vienna.jaray.webflux;

import com.vienna.jaray.pojo.User;
import com.vienna.jaray.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

import java.util.Collection;

/**
 * 路由器函数配置
 */
@Configuration
public class RouterFunctionConfig {
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
    @Autowired
    public RouterFunction<ServerResponse> personFindAll(UserRepository userRepository) {
        return RouterFunctions.route(RequestPredicates.GET("/person/find/all"),
            request -> {
                // 返回所有用户对象
                Collection<User> users = userRepository.findAll();
                Flux<User> userFlux = Flux.fromIterable(users);
                return ServerResponse.ok().body(userFlux, User.class);
            });
    }
}
