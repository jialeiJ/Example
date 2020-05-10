package com.vienna.jaray;

import com.vienna.jaray.client.NettyClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.InetSocketAddress;

@Slf4j
@SpringBootTest
class NettyApplicationTests {
    @Autowired
    private NettyClient nettyClient;

    @Test
    void contextLoads() {
    }

}
