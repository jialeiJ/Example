package com.vienna.websocket.service;

import com.vienna.websocket.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

//注册成组件
@Component
//定义websocket服务器端，它的功能主要是将目前的类定义成一个websocket服务器端。注解的值将被用于监听用户连接的终端访问URL地址
@ServerEndpoint("/websocket/{sid}")
//如果不想每次都写private  final Logger logger = LoggerFactory.getLogger(当前类名.class); 可以用注解@Slf4j;可以直接调用log.info
@Slf4j
public class WebSocketServer {

    //实例一个session，这个session是websocket的session
    private Session session;
    //接收sid
    private String sid = "";

    /**
     * 以用户的姓名为key,websocket为对象保存起来
     */
    private static ConcurrentHashMap<String, WebSocketServer> clients = new ConcurrentHashMap<>();


    private static RedisUtil redisUtil;
    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        WebSocketServer.redisUtil = redisUtil;
    }

    //前端请求时一个websocket时
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        this.session = session;
        this.sid = sid;
        clients.put(sid, this);
        log.info("【websocket消息】有新的连接, 总数:{}", clients.size());

        Set<String> keys = redisUtil.getKeys(sid.concat(":"));

        List<String> collect = keys.stream().sorted().collect(Collectors.toList());
        collect.stream().forEach(key -> {
            sendMessageForSingleOfOffline(redisUtil.get(key), sid);
            redisUtil.del(key);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    //前端关闭时一个websocket时
    @OnClose
    public void onClose() throws IOException {
        clients.get(this.sid).session.close();
//        clients.remove(this.sid);
        log.info("【websocket消息】连接断开, 总数:{}", clients.size());
    }

    //前端向后端发送消息
    @OnMessage
    public void onMessage(String message) {
        log.info("【websocket消息】收到客户端发来的消息:{}", message);
    }

    //新增一个方法用于主动向客户端发送消息
    public void sendMessage(String message) {
        for (Map.Entry<String, WebSocketServer> entry : clients.entrySet()) {
            log.info("【websocket消息】广播消息, message={}", message);
            try {
                if (entry.getValue().session.isOpen()) {
                    entry.getValue().session.getBasicRemote().sendText(message);
                } else {
                    redisUtil.set(entry.getValue().sid, message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //新增一个方法用于主动向客户端发送消息
    public void sendMessageForSingle(String message, String sid) {
        WebSocketServer session = clients.get(sid);
        if (session != null) {
            if (session.session.isOpen()) {
//                session.session.getAsyncRemote().sendText("收到 " + sid + " 的消息:" + message);

                // 如果redis中存在数据，将数据保存到redis，依次推送
                Set<String> keys = redisUtil.getKeys(sid.concat(":"));
                if (keys.size() > 0) {
                    redisUtil.set(sid, message);
                } else {
                    session.session.getAsyncRemote().sendText(message);
                }
            } else {
                log.info("用户 {} session关闭 redis保存数据 {}", sid, message);
                redisUtil.set(sid, message);
            }
        } else {
            log.info("用户 {} session不存在 redis保存数据 {}", sid, message);
            redisUtil.set(sid, message);
        }
    }


    //新增一个方法用于主动向客户端发送离线消息
    public void sendMessageForSingleOfOffline(String message, String sid) {
        WebSocketServer session = clients.get(sid);
        if (session != null) {
            if (session.session.isOpen()) {
                session.session.getAsyncRemote().sendText(message);
            }
        }
    }


    //新增一个方法用于主动向客户端群发消息
    public void sendMessageForGroup(String message, HashSet<String> toSids) {
        log.info("推送消息到客户端 " + toSids + "，推送内容:" + message);

        // null则全部推送，否则只推送给传入的sid，
        if (toSids == null || toSids.size() <= 0) {
            sendMessage(message);
        } else {
            toSids.stream().forEach(sid -> {
                sendMessageForSingle(message, sid);
            });
        }
    }
}
