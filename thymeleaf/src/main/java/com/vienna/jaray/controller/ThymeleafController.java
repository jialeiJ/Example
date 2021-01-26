package com.vienna.jaray.controller;

import com.vienna.jaray.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class ThymeleafController {
    @GetMapping("/thymeleaf")
    public ModelAndView index(ModelAndView modelAndView, HttpServletRequest request) {
        modelAndView.setViewName("thymeleaf");
        modelAndView.addObject("name", "Hello Jaray!");


        modelAndView.addObject("intValue", 100);
        modelAndView.addObject("longValue", 10000L);
        modelAndView.addObject("stringValue", "我是字符串");
        modelAndView.addObject("doubleValue", 3.45D);
        modelAndView.addObject("booleanValue", true);
        modelAndView.addObject("dateValue", new Date());
        modelAndView.addObject("nullValue", null);


        User user = new User();
        user.setName("Jaray");
        user.setAge(25);
        user.setBrief("<font color='red'>我想早点下班。对不起，你是程序员！</font>");
        modelAndView.addObject("user", user);

        modelAndView.addObject("date", new Date());

        List<String> list = new ArrayList<>();
        list.add("JiaLei");
        list.add("Jaray");
        list.add("Java");
        modelAndView.addObject("list", list);

        Set<String> names = new HashSet<String>() ;
        List<Integer> ids = new ArrayList<Integer>() ;
        for (int i = 0 ; i < 5 ; i ++) {
            names.add("boot-" + i);
            ids.add(i);
        }
        modelAndView.addObject("names", names) ;
        modelAndView.addObject("ids", ids) ;
        modelAndView.addObject("mydate", new java.util.Date()) ;


        Map<String, String> map = new HashMap<>();
        map.put("JiaLei", "Hello JiaLei");
        map.put("Jaray", "Hello Jaray");
        map.put("Java", "Hello Java");
        modelAndView.addObject("map", map);

        request.setAttribute("requestMessage", "springboot-request");
        request.getSession().setAttribute("sessionMessage", "springboot-session");
        request.getServletContext().setAttribute("applicationMessage",
                "springboot-application");
        modelAndView.addObject("url", "www.baidu.cn");
        request.setAttribute("url2",
                "<span style='color:red'>www.baidu.cn</span>");

        return modelAndView;
    }
}
