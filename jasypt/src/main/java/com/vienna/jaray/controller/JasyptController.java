package com.vienna.jaray.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vienna.jaray.config.PropertiesConfig;

@RestController
public class JasyptController {
	
	@Autowired
	private PropertiesConfig propertiesConfig;
	
	@GetMapping("/testJasypt")
	public PropertiesConfig testJasypt() {
		return propertiesConfig;
	}
	
}
