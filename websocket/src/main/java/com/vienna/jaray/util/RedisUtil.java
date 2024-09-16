package com.vienna.jaray.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
public class RedisUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public Set<String> getKeys(String keyPattern){
        Set<String> keys = redisTemplate.keys(keyPattern);
        if (keys.size() == 0) {
            keys = new HashSet<>();
        }
        return keys;
    }

    public String get(String key){
        String val = String.valueOf(redisTemplate.opsForValue().get(key));
        return val;
    }

    public void set(String key, String val){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        redisTemplate.opsForValue().set(key.concat(":").concat(dateFormat.format(new Date())), val);
    }

    public void setForFixedKey(String key, String val){
        redisTemplate.opsForValue().set(key, val);
    }





    public void del(String key){
        redisTemplate.delete(key);
    }
}
