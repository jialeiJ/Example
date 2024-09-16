package com.vienna.jaray.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 聊天消息信息
 */
@Document(collection = "chatMessage")
public class ChatMessage {
    @Id
    private String roomId;

    /**
     * 聊天类型 1：单聊，2：群聊
     */
    private String type;

    private String from;
    private String to;
    private String msg;
    @Indexed
    private LocalDateTime sendTime;

    // 这个字段将用于确定文档的过期时间
    @Indexed(expireAfterSeconds = (60 * 60 * 24 * 30)) // 例如，30天后过期
    private Date expireTime;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}
