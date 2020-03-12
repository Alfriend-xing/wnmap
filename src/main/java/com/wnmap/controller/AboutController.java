package com.wnmap.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wnmap.bean.Info;
import com.wnmap.bean.Msg;
import com.wnmap.service.InfoService;

@RestController
public class AboutController {
	@Autowired
	InfoService infoService;

	@GetMapping("/api/about/info")
	public List<Info> info() {
		return infoService.getInfo();
	}
	
	@GetMapping("/api/about/realinfo")
	public Info realInfo() {
		return infoService.getRealInfo();
	}
	
	@GetMapping("/api/about/serverinfo")
	public Msg serverInfo() {
		return infoService.getServerInfo();
	}
	
}
