package com.vienna.jaray.controller;

import com.vienna.jaray.client.NettyClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RestController
public class NettyClientController {
    @Autowired
    private NettyClient nettyClient;
    private final static AtomicInteger idGenerator = new AtomicInteger();

    @PostMapping("/send")
    public Object send(@RequestParam String name) throws InterruptedException{
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8765);
        Object result = nettyClient.startAndWrite(address, name+idGenerator.incrementAndGet());
        return result;
    }

}
