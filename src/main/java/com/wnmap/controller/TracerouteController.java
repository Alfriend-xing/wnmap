package com.wnmap.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wnmap.TracerouteComponent;
import com.wnmap.bean.Msg;

@RestController
public class TracerouteController {
	
	@Autowired
	TracerouteComponent tracerouteComponent;
	
	@PostMapping("/api/traceroute/newtask")
	@ResponseBody
	public Msg addtask(@RequestParam(name="ip", required=true) String ip) {
		if (tracerouteComponent.getIsRunning()==false) {
			ArrayList<String[]> trace = tracerouteComponent.traceroute(ip);
			return new Msg(true,"success", trace);
		}
		return new Msg(false, "isRunning");
	}
}
