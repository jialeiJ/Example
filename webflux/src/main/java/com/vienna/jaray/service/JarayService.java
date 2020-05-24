package com.vienna.jaray.service;

import com.vienna.jaray.pojo.User;
import org.springframework.boot.Banner;

import java.util.List;

public interface JarayService {

    public List<User> getList(Integer page, Integer size);


    public User update(User user);

    public List<User> deletes(String[] ids);

    public User add(User user);
}
