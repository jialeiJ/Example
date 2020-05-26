package com.vienna.jaray.controller;

import com.vienna.jaray.pojo.User;
import com.vienna.jaray.service.JarayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {
    private final JarayService jarayRepository;
    /**
     * 构造器注入
     * @param jarayRepository
     */
    @Autowired
    public UserController(JarayService jarayRepository){
        this.jarayRepository = jarayRepository;
    }

    @PostMapping("/person/save")
    public User save(@RequestParam String name){
        User user = new User();
        user.setName(name);
        user = jarayRepository.add(user);
        log.info("用户对象： {} 保存成功！", user);
        return user;
    }
}
