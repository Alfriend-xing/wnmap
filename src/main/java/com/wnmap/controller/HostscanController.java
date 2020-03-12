package com.wnmap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wnmap.bean.Host;
import com.wnmap.bean.HostTask;
import com.wnmap.bean.Msg;
import com.wnmap.service.HostService;
import com.wnmap.service.HostTaskService;

@RestController
public class HostscanController {
	@Autowired
	HostService hostService;
	@Autowired
	HostTaskService hostTaskService;
	
	@GetMapping("/api/hostlist/totaltime")
	@ResponseBody
	public Page<Host> gettotaltime(
			@RequestParam(name="page", required=true, defaultValue="0") Integer page,
			@RequestParam(name="size", required=true, defaultValue="10") Integer size) {
	    Page<Host> pageHostPage = hostService.getHostByPage(page, size, Sort.by("totaltime").descending());
	    return pageHostPage;
	}
	@GetMapping("/api/hostlist/uptime")
	@ResponseBody
	public Page<Host> getuptime(
			@RequestParam(name="page", required=true, defaultValue="0") Integer page,
			@RequestParam(name="size", required=true, defaultValue="10") Integer size) {
	    Page<Host> pageHostPage = hostService.getHostByPage(page, size, Sort.by("uptime").descending());
	    return pageHostPage;
	}
	@GetMapping("/api/hostlist/downtime")
	@ResponseBody
	public Page<Host> getdowntime(
			@RequestParam(name="page", required=true, defaultValue="0") Integer page,
			@RequestParam(name="size", required=true, defaultValue="10") Integer size) {
	    Page<Host> pageHostPage = hostService.getHostByPage(page, size, Sort.by("downtime").descending());
	    return pageHostPage;
	}	
	@GetMapping("/api/hostscan/gethosttaskbypage")
	@ResponseBody
	public Page<HostTask> getHostTaskByPage(
			@RequestParam(name="page", required=true, defaultValue="0") Integer page,
			@RequestParam(name="size", required=true, defaultValue="5") Integer size) {
	    Page<HostTask> pageHostPage = hostTaskService.getHostTaskByPage(page, size);
	    return pageHostPage;
	}
	@PostMapping("/api/hostscan/deleteById")
	@ResponseBody
	public Msg deleteById(@RequestParam(name="id", required=true) Long id) {
		try {
			hostTaskService.deleteById(id);
		    return new Msg(true, "删除成功");
		}catch(Exception e) {
		    return new Msg(false, "删除失败"+e.toString());
		}
	}
	@PostMapping("/api/hostscan/addTask")
	@ResponseBody
	public Msg addTask(
			@RequestParam(name="ip", required=true) String ip,
			@RequestParam(name="frequence", required=true) String frequence) {
		try {
			HostTask hostTask = new HostTask(ip,"addtime",frequence,"stopped",0L);
			hostTaskService.addTask(hostTask);
		    return new Msg(true, "任务添加成功");
		}
		catch(AccessDeniedException accessDeniedException) {
		    return new Msg(false, "任务添加失败，权限不足"+accessDeniedException.toString());
		}
		catch(Exception e) {
		    return new Msg(false, "任务添加失败"+e.toString());
		}
	}
	@PostMapping("/api/hostscan/updateTask")
	@ResponseBody
	public Msg updateTask(
			@RequestParam(name="id", required=true) Long id,
			@RequestParam(name="ip", required=true) String ip,
			@RequestParam(name="frequence", required=true) String frequence) {
		try {
			hostTaskService.updateTask(id, ip, frequence);
		    return new Msg(true, "更新成功，任务ID:"+id.toString());
		}catch(Exception e) {
		    return new Msg(false, "更新失败，任务ID:"+id.toString()+e.toString());
		}
	}

}
