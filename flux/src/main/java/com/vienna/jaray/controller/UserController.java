package com.vienna.jaray.controller;

import com.vienna.jaray.pojo.User;
import com.vienna.jaray.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {
    private final UserRepository userRepository;

    /**
     * 构造器注入
     * @param userRepository
     */
    @Autowired
    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @PostMapping("/person/save")
    public User save(@RequestParam String name){
        User user = new User();
        user.setName(name);
        if(userRepository.save(user)){
           log.info("用户对象： {} 保存成功！", user);
        }
        return user;
    }
}
