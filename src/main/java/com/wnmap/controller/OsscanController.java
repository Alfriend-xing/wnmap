package com.wnmap.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wnmap.bean.HostTask;
import com.wnmap.bean.Msg;
import com.wnmap.bean.OsTask;
import com.wnmap.service.HostService;
import com.wnmap.service.OsTaskService;

@RestController
public class OsscanController {
	@Autowired
	HostService hostService;
	@Autowired
	OsTaskService osTaskService;

	@GetMapping("/api/osscan/ostype")
	@ResponseBody
	public Map<String, Long> getOstype() {
		Map<String, Long> countByOstype = hostService.countByOstype();
	    return countByOstype;
	}	
	@GetMapping("/api/osscan/getostaskbypage")
	@ResponseBody
	public Page<OsTask> getOsTaskByPage(
			@RequestParam(name="page", required=true, defaultValue="0") Integer page,
			@RequestParam(name="size", required=true, defaultValue="5") Integer size) {
	    Page<OsTask> pageHostPage = osTaskService.getOsTaskByPage(page, size);
	    return pageHostPage;
	}
	@PostMapping("/api/osscan/deleteById")
	@ResponseBody
	public Msg deleteById(@RequestParam(name="id", required=true) Long id) {
		try {
			osTaskService.deleteById(id);
		    return new Msg(true, "删除成功");
		}catch(Exception e) {
		    return new Msg(false, "删除失败"+e.toString());
		}
	}
	@PostMapping("/api/osscan/addTask")
	@ResponseBody
	public Msg addTask(
			@RequestParam(name="ip", required=true) String ip,
			@RequestParam(name="frequence", required=true) String frequence) {
		try {
			OsTask osTask = new OsTask(ip,"addtime",frequence,"stopped",0L);
			osTaskService.addTask(osTask);
		    return new Msg(true, "任务添加成功");
		}catch(Exception e) {
		    return new Msg(false, "任务添加失败"+e.toString());
		}
	}
	@PostMapping("/api/osscan/updateTask")
	@ResponseBody
	public Msg updateTask(
			@RequestParam(name="id", required=true) Long id,
			@RequestParam(name="ip", required=true) String ip,
			@RequestParam(name="frequence", required=true) String frequence) {
		try {
			osTaskService.updateTask(id, ip, frequence);
		    return new Msg(true, "更新成功，任务ID:"+id.toString());
		}catch(Exception e) {
		    return new Msg(false, "更新失败，任务ID:"+id.toString()+e.toString());
		}
	}
}
