package com.vienna.jaray.model;

import java.io.Serializable;
import java.util.List;

/**
 * 房间信息
 */
public class ChatRoom implements Serializable {

    /**
     * id
     */
    private String id;

    /**
     * 用户id
     */
    private List<String> userIdList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }
}
