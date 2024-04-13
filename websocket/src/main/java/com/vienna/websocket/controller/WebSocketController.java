package com.vienna.websocket.controller;

import com.vienna.websocket.service.WebSocketServer;
import com.vienna.websocket.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashSet;

@CrossOrigin
@RestController
@RequestMapping("/websocket")
public class WebSocketController {

    @Resource
    private WebSocketServer webSocketService;

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/send/{sid}")
    public void send(@PathVariable("sid") String sid) throws InterruptedException {
        String msg = "";

        HashSet<String> sids = new HashSet<>();
        for(int i=0; i<=100; i++) {
            if (i % 10 == 0) {
                msg = String.valueOf(i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                webSocketService.onMessage(msg);

                if (!StringUtils.equalsIgnoreCase("null", sid)) {
                    sids.add(sid);
                }

                webSocketService.sendMessageForGroup(msg, sids);
            }
        }


        if (StringUtils.equalsIgnoreCase("null", sid)) {
            webSocketService.sendMessageForGroup("88", sids);
        } else {
            sids.add(sid);
            webSocketService.sendMessageForGroup(msg, sids);
        }
    }
}
