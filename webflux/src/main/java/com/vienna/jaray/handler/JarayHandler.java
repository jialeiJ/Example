package com.vienna.jaray.handler;

import com.vienna.jaray.pojo.User;
import com.vienna.jaray.service.JarayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ZeroCopyHttpOutputMessage;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.io.*;
import java.util.*;

@Slf4j
@Component
public class JarayHandler {
    private static final String projectName = "webflux";

    @Autowired
    private JarayService jarayService;

    public Mono<ServerResponse> getList(ServerRequest request) {
        //获取请求参数
        String page = request.queryParam("page").orElse("1");
        String size = request.queryParam("rows").orElse("5");
        List<User> result = jarayService.getList(Integer.valueOf(page), Integer.valueOf(size));
        return ServerResponse.ok().body(BodyInserters.fromValue(result));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        //参数序列化为实体类进行更新
        return request.bodyToMono(User.class).flatMap(user -> {
            System.out.println("---" + user);
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(jarayService.update(user)));
        });
    }

    public Mono<ServerResponse> add(ServerRequest request) {
        return request.bodyToMono(User.class).flatMap(user -> {
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(jarayService.add(user)));
        });
    }

    public Mono<ServerResponse> deletes(ServerRequest request) {
        //获取路径中的参数作用等同于 @PathVariable
        String ids = request.pathVariable("ids");
        Assert.hasText(ids, "ids不能为空");
        return ServerResponse.ok().body(BodyInserters.fromValue(jarayService.deletes(ids.split(","))));
    }

    public Mono<ServerResponse> init(ServerRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Jaray");
        map.put("time", new Date());
        List<String> list = new ArrayList<String>();
        list.add("Jaray_1");
        list.add("Jaray_2");
        list.add("Jaray_3");
        map.put("list", list);
        //返回thymeleaf模版页面
        return ServerResponse.ok().render("user", map);
    }

    public Mono<ServerResponse> upload(ServerRequest request) {
        //获取文件参数 并进行存储
        String path = System.getProperty("user.dir") + File.separator + projectName;
        return request.multipartData().flatMap(map -> {
            map.forEach((k, v) -> {
                v.forEach(i -> {
                    FilePart f = (FilePart) i;
                    f.transferTo(new File(path + File.separator + "tmp/" + f.filename()));
                });
            });
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(map.size()));
        });
    }

    public Mono<ServerResponse> download(ServerRequest request) {
        // 方式一
        Mono<ServerResponse> result = downloadByZeroCopyHttpOutputMessage(request);
        // 方式二
        //result = downloadByDataBuffer(request);
        return result;
    }

    private Mono<ServerResponse> downloadByZeroCopyHttpOutputMessage(ServerRequest request){
        String path = System.getProperty("user.dir") + File.separator + projectName;
        String fileName = path + File.separator + "tmp" + File.separator + "猫猫猫.jpg";
        File f = new File(fileName);
        InputStream is = null;
        try {
            is = new FileInputStream(f);
        } catch (IOException e) {
            log.error("异常", e.getMessage());
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                log.error("流关闭异常", e.getMessage());
            }
        }
        try {
            fileName = new String(f.getName().getBytes("UTF-8"),"ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            log.error("编码格式异常", e.getMessage());
        }
        return ServerResponse.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
                .contentType(MediaType.parseMediaType("multipart/form-data"))
                .body((p, a) -> {
                    ZeroCopyHttpOutputMessage resp = (ZeroCopyHttpOutputMessage) p;
                    return resp.writeWith(f, 0, f.length());
                }).doFinally(a -> {
                    //f.deleteOnExit();
                });
    }

    private Mono<ServerResponse> downloadByDataBuffer(ServerRequest request){
        String path = System.getProperty("user.dir") + File.separator + projectName;
        String fileName = path + File.separator + "tmp" + File.separator + "猫猫猫.jpg";
        File f = new File(fileName);
        InputStream is = null;
        ByteArrayOutputStream bos = null;
        try {
            is = new FileInputStream(f);
            bos = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024 * 1024];
            int len =0;
            while((len=is.read(buffer))!= -1){
                bos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            log.error("异常", e.getMessage());
        }finally {
            try {
                is.close();
                bos.close();
            } catch (IOException e) {
                log.error("流关闭异常", e.getMessage());
            }
        }
        try {
            fileName = new String(f.getName().getBytes("UTF-8"),"ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            log.error("编码格式异常", e.getMessage());
        }
        //读取文件并包装为DataBuffer返回，spring-webflux会自动写入response
        byte[] finalResult = bos.toByteArray();;
        return ServerResponse.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.IMAGE_JPEG).contentLength(f.length())
                .body(BodyInserters.fromDataBuffers(Mono.create(r -> {
                    DataBuffer buf = new DefaultDataBufferFactory().wrap(finalResult);
                    r.success(buf);
                    return;
                })));
    }
}