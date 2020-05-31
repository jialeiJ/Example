package com.vienna.jaray.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.vienna.jaray.pojo.User;

@Controller
public class FreemarkerController {
	
	@RequestMapping("/freemarker")
	public ModelAndView index(ModelAndView modelAndView) {
		modelAndView.setViewName("freemarker");
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
		user.setBrief("<font color='red'>我想早点下班。对不起，你是程序员！</font><br>");
		modelAndView.addObject("user", user);
		
		
		List<String> list = new ArrayList<>();
		list.add("JiaLei");
		list.add("Jaray");
		list.add("Java");
		modelAndView.addObject("list", list);
		
		
		Map<String, String> map = new HashMap<>();
		map.put("JiaLei", "Hello JiaLei");
		map.put("Jaray", "Hello Jaray");
		map.put("Java", "Hello Java");
		modelAndView.addObject("map", map);
		
		
		modelAndView.addObject("sort_int", new SortIntMethod());
		
		return modelAndView;
	}

}
