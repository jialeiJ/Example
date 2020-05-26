package com.vienna.jaray.webflux;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.vienna.jaray.model.ExcelPropertyModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ZeroCopyHttpOutputMessage;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 路由器函数配置
 */
@Slf4j
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
    public RouterFunction<ServerResponse> exportExcel() {
        return RouterFunctions.route(RequestPredicates.GET("/excel/export"),
            request -> {
                String fileName = "withHeadwhitEntity.xlsx";
                File f = new File(fileName);
                OutputStream out = null;
                try{
                    out = new FileOutputStream(f);
                    List<ExcelPropertyModel> data = new ArrayList<>();
                    for (int i = 0; i < 100; i++) {
                        ExcelPropertyModel item = new ExcelPropertyModel("name_" + i, "age_" + i, "email_" + i,
                                "address_" + i, "sax_" + i,"height_" + i,"last_" + i);
                        data.add(item);
                    }
                    EasyExcel.write(out)
                            .excelType(ExcelTypeEnum.XLSX)
                            .sheet("员工信息").head(ExcelPropertyModel.class).doWrite(data);
                }catch (IOException e) {
                    log.error("异常", e.getMessage());
                }
                return ServerResponse.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename="+fileName)
                        .contentType(MediaType.parseMediaType("multipart/form-data"))
                        .body((p, a) -> {
                            ZeroCopyHttpOutputMessage resp = (ZeroCopyHttpOutputMessage) p;
                            return resp.writeWith(f, 0, f.length());
                        }).doFinally(a -> {f.deleteOnExit();});
            });
    }

    @Bean
    public RouterFunction<ServerResponse> inportExcel() {
        return RouterFunctions.route(RequestPredicates.GET("/excel/import"),
                request -> {
                    //获取文件参数 并进行存储
                    return request.multipartData().flatMap(map -> {
                        System.out.println(map);
                        map.forEach((k, v) -> {
                            v.forEach(i -> {
                                FilePart f = (FilePart) i;
                                System.out.println(f.filename());
                                System.out.println(request.formData().toString());
                                f.transferTo(new File("/tmp/" + f.filename()));
                            });
                        });

                        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                                .body(BodyInserters.fromValue(map.size()));
                    });
                });
    }


}
