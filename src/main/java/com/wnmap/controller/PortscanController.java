package com.wnmap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wnmap.bean.Host;
import com.wnmap.bean.Msg;
import com.wnmap.bean.OsTask;
import com.wnmap.bean.Port;
import com.wnmap.bean.PortTask;
import com.wnmap.service.HostService;
import com.wnmap.service.PortService;
import com.wnmap.service.PortTaskService;

@RestController
public class PortscanController {
	@Autowired
	HostService hostService;
	@Autowired	
	PortService portService;
	@Autowired	
	PortTaskService portTaskService;

	@GetMapping("/api/portscan/port")
	@ResponseBody
	public Page<Host> getPageByPort(
			@RequestParam(name="page", required=true, defaultValue="0") Integer page,
			@RequestParam(name="size", required=true, defaultValue="10") Integer size) {
	    Page<Host> pageHostPage = hostService.getHostByPage(page, size, Sort.by("portsum").descending());
	    return pageHostPage;
	}

	@GetMapping("/api/portscan/portlist")
	@ResponseBody
	public Page<Port> getPortList(
			@RequestParam(name="page", required=true, defaultValue="0") Integer page,
			@RequestParam(name="size", required=true, defaultValue="10") Integer size) {
	    Page<Port> portPage = portService.getPortByPage(page, size, Sort.by("sum").descending());
	    return portPage;
	}	
	@GetMapping("/api/portscan/getporttaskbypage")
	@ResponseBody
	public Page<PortTask> getPortTaskByPage(
			@RequestParam(name="page", required=true, defaultValue="0") Integer page,
			@RequestParam(name="size", required=true, defaultValue="5") Integer size) {
	    Page<PortTask> pageHostPage = portTaskService.getPortTaskByPage(page, size);
	    return pageHostPage;
	}
	@PostMapping("/api/portscan/deleteById")
	@ResponseBody
	public Msg deleteById(@RequestParam(name="id", required=true) Long id) {
		try {
			portTaskService.deleteById(id);
		    return new Msg(true, "删除成功");
		}catch(Exception e) {
		    return new Msg(false, "删除失败"+e.toString());
		}
	}
	@PostMapping("/api/portscan/addTask")
	@ResponseBody
	public Msg addTask(
			@RequestParam(name="ip", required=true) String ip,
			@RequestParam(name="frequence", required=true) String frequence) {
		try {
			PortTask portTask = new PortTask(ip,"addtime",frequence,"stopped",0L);
			portTaskService.addTask(portTask);
		    return new Msg(true, "任务添加成功");
		}catch(Exception e) {
		    return new Msg(false, "任务添加失败"+e.toString());
		}
	}
	@PostMapping("/api/portscan/updateTask")
	@ResponseBody
	public Msg updateTask(
			@RequestParam(name="id", required=true) Long id,
			@RequestParam(name="ip", required=true) String ip,
			@RequestParam(name="frequence", required=true) String frequence) {
		try {
			portTaskService.updateTask(id, ip, frequence);
		    return new Msg(true, "更新成功，任务ID:"+id.toString());
		}catch(Exception e) {
		    return new Msg(false, "更新失败，任务ID:"+id.toString()+e.toString());
		}
	}
}
