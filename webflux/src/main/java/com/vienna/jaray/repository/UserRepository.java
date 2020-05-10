package com.vienna.jaray.repository;

import com.vienna.jaray.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class UserRepository {
    /**
     * 采用内存型的存储方式 -> Map
     */
    private final ConcurrentMap<Integer, User> repository = new ConcurrentHashMap<>();

    /**
     * id自增生成器
     */
    private final static AtomicInteger idGenerator = new AtomicInteger();

    public boolean save(User user){
        // id从1开始
        Integer id = idGenerator.incrementAndGet();
        user.setId(id);
        return repository.put(id, user) == null;
    }

    public Collection<User> findAll(){
        return repository.values();
    }
}
