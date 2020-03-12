package com.wnmap.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//服务器信息，系统启动时清空记录

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Info {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

//  数据记录时间
    private Long time;
    
//  数据格式化时间
    private String format;
    
//    cpu使用率
    private Integer cpu;
    
//    内存使用率
    private Integer mem;
    
    public Info() {}
    
    public Info(Long time, String format, Integer cpu, Integer mem) {
    	this.time = time;
    	this.format = format;
    	this.cpu = cpu;
    	this.mem = mem;
    }
    
    public void setTime(Long time) {
		this.time = time;
	}
    
    public Long getTime() {
		return time;
	}
    
    public void setFormat(String format) {
		this.format = format;
	}
    
    public String getFormat() {
		return format;
	}
    
    public void setCpu(Integer cpu) {
		this.cpu = cpu;
	}
    
    public Integer getCpu() {
		return cpu;
	}
    
    public void setMem(Integer mem) {
		this.mem = mem;
	}
    
    public Integer getMem() {
		return mem;
	}
    

}
