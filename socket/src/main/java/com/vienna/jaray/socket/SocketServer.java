package com.vienna.jaray.socket;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Data
@Component
@PropertySource("classpath:socket.properties")
@NoArgsConstructor
public class SocketServer implements CommandLineRunner {
    @Value("${socket.port}")
    private Integer port;
    private boolean started;
    private ServerSocket serverSocket;
    private ExecutorService executorService = Executors.newCachedThreadPool();

    public void start(Integer port){
        log.info("port: {}, {}", this.port, port);
        try {
            serverSocket =  new ServerSocket(port == null ? this.port : port);
            started = true;
            log.info("Socket服务已启动，占用端口： {}", serverSocket.getLocalPort());
        } catch (IOException e) {
            log.error("端口冲突,异常信息：{}", e);
            System.exit(0);
        }

        while (started){
            try {
                Socket socket = serverSocket.accept();
                socket.setKeepAlive(true);
                ClientSocket register = SocketHandler.register(socket);
                log.info("客户端已连接，其Key值为：{}", register.getKey());
                List<String> list = new ArrayList<>();
                list.add("111");
                list.add("222");
                list.add("3334");
                SocketHandler.sendMessage(register, JSON.toJSONString(list));
                if (register != null){
                    executorService.submit(register);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 启动
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        start(8068);
    }
}
