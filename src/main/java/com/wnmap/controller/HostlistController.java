package com.wnmap.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wnmap.bean.Host;
import com.wnmap.bean.Online;
import com.wnmap.service.HostService;
import com.wnmap.service.OnlineService;

@RestController()
public class HostlistController {
	@Autowired
	HostService hostService;
	@Autowired
	OnlineService onlineService;

	@GetMapping("/api/hostlist/hostsum")
	public long hostSum() {
		return hostService.getHostSum();
	}
	
	@GetMapping("/api/hostlist/onlinesum")
	public long onlineSum() {
		return hostService.getOnlineSum();
	}
	
	@GetMapping("/api/hostlist/portsum")
	public long portSum() {
		return hostService.getPortSum();
	}
	
	@GetMapping("/api/hostlist/hoursum")
	public long hourSum() {
		return hostService.getHourSum();
	}

	@GetMapping("/api/hostlist/online")
	public List<Online> online() {
		return onlineService.getOnline();
	}
	
	@GetMapping("/api/hostlist/gethostbypage")
	@ResponseBody
	public Page<Host> getHostByPage(
			@RequestParam(name="page", required=true, defaultValue="0") Integer page,
			@RequestParam(name="size", required=true, defaultValue="10") Integer size) {
	    Page<Host> pageHostPage = hostService.getHostByPage(page, size);
	    return pageHostPage;
	}
	
	@GetMapping("/api/hostlist/gethostbyiplike")
	@ResponseBody
	public Page<Host> getHostByIpLike(
			@RequestParam(name="page", required=true, defaultValue="0") Integer page,
			@RequestParam(name="size", required=true, defaultValue="10") Integer size,
			@RequestParam(name="ip", required=true) String ip) {
	    Page<Host> pageHostPage = hostService.getHostByIpLike(ip, page, size);
	    return pageHostPage;
	}
	
	
}
