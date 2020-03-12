package com.wnmap.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//主机发现任务表

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class HostTask {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

//  IP地址
    @Column(unique = true)
    private String ip;
    
//    添加时间
    private String addtime;
    
//    执行频率
    private String frequence;
    
//    执行状态
    private String state;
    
//    执行时间
    private Long runtime;
    
    public HostTask() {};

    public HostTask(String ip, String addtime, String frequence, String state,
    		Long runtime) {
    	this.ip = ip;
    	this.addtime = addtime;
    	this.frequence = frequence;
    	this.state = state;
    	this.runtime = runtime;
    };
    
    public Long getId() {
		return id;
	}
    
    public void setId(Long id) {
		this.id = id;
	}
    
    public String getIp() {
		return ip;
	}
    
    public void setIp(String ip) {
		this.ip = ip;
	}
    
    public String getAddtime() {
		return addtime;
	}
    
    public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
    
    public String getFrequence() {
		return frequence;
	}
    
    public void setFrequence(String frequence) {
		this.frequence = frequence;
	}
    
    public String getState() {
		return state;
	}
    
    public void setState(String state) {
		this.state = state;
	}
    
    public Long getRuntime() {
		return runtime;
	}
    
    public void setRuntime(Long runtime) {
		this.runtime = runtime;
	}
    
    
}
