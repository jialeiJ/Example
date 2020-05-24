package com.vienna.jaray.service.impl;

import com.vienna.jaray.pojo.User;
import com.vienna.jaray.service.JarayService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class JarayServiceImpl implements JarayService {
    /**
     * 采用内存型的存储方式 -> Map
     */
    private final ConcurrentMap<Integer, User> userMap = new ConcurrentHashMap<>();
    /**
     * id自增生成器
     */
    private final static AtomicInteger idGenerator = new AtomicInteger();

    @Override
    public List<User> getList(Integer page, Integer size) {
        List<User> userList = new ArrayList<>();
        for(Map.Entry<Integer, User> entry : userMap.entrySet()){
            userList.add(entry.getValue());
        }
        return userList;
    }

    @Override
    public User update(User user) {
        userMap.put(user.getId(),user);
        return user;
    }

    @Override
    public List<User> deletes(String[] ids) {
        for(String id : ids){
            userMap.remove(Integer.valueOf(id));
        }

        List<User> userList = new ArrayList<>();
        for(Map.Entry<Integer, User> entry : userMap.entrySet()){
            userList.add(entry.getValue());
        }
        return userList;
    }

    @Override
    public User add(User user) {
        // id从1开始
        Integer id = idGenerator.incrementAndGet();
        user.setId(id);
        userMap.put(id, user);
        return user;
    }
}
