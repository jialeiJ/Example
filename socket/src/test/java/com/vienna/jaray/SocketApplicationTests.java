package com.vienna.jaray;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

@Slf4j
//@SpringBootTest //去掉后就不启动程序，只会运行该测试（否则重启socket服务报错端口被占用）
class SocketApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void testSocker() throws IOException {
        String host = "192.168.1.1";
        int port = 8068;

        String uuid = UUID.randomUUID().toString();
        log.info("uuid: {}", uuid);

        // 与服务端建立连接
        Socket socket = new Socket(host, port);
        socket.setOOBInline(true);

        // 建立连接后获取输出流
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

        // 向服务端发送消息
        outputStream.write(uuid.getBytes());

        // 建立连接后获取输入流，读取服务器端的响应信息
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        String content = "";
        while (true){
            byte[] buff = new byte[1024];
            inputStream.read(buff);
            String buffer = new String(buff, "utf-8");
            content += buffer;
            log.info("服务器端返回数据为：{}", buffer);
            File file = new File("json.json");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.flush();
        }
    }

}
