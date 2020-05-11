package com.vienna.jaray.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class DockerController {
	
	@GetMapping("/docker")
	public String docker() {
		log.info("test docker......");
		return "test docker success";
	}

}
