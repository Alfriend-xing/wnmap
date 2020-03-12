package com.wnmap.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//资产清单表

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
//@Table(indexes = {
//        @Index(name = "ipindex", columnList = "ip", unique = true),
//})
public class Host {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
//    IP地址
    @Column(unique = true)
    private String ip;
    
//    发现时间
    private Long foundtime;

//  发现时间
    private String foundformat;
  
//    开放端口
    private String openports;
    
//  开放端口数量
    private Integer portsum;
    
//    操作系统类型
    private String ostype;
    
//    ip地理位置
    private String location;
    
//    启动时间
    private Long uptime;
    
//    关闭时间
    private Long downtime;
    
//    在线状态
    private Boolean state;
    
//  累计在线时长
    private Long totaltime;
//  mac地址
    private String macaddress;
//  mac厂商
    private String macvendor;
    
    public Host() {
		
	}
    
    public Host(String ip, Long foundtime, String foundformat, String openports, Integer portsum, String ostype,
    		 String location, Long uptime, Long downtime, Boolean state, Long totaltime) {
		this.ip = ip;
		this.foundtime = foundtime;
		this.foundformat = foundformat;
		this.openports = openports;
		this.portsum = portsum;
		this.ostype = ostype;
		this.location = location;
		this.uptime = uptime;
		this.downtime = downtime;
		this.state = state;
		this.totaltime = totaltime;
	}
    
    public String getIp() {
		return ip;
	}
    
    public void setIp(String ip) {
		this.ip = ip;
	}
    
    public Long getFoundtime() {
		return foundtime;
	}
    
    public void setFoundtime(Long foundtime) {
		this.foundtime = foundtime;
	}
    
    public String getFoundformat() {
		return foundformat;
	}
    
    public void setFoundformat(String foundformat) {
		this.foundformat = foundformat;
	}
    
    public String getOpenports() {
		return openports;
	}
    
    public void setOpenports(String openports) {
		this.openports = openports;
	}
    
    public Integer getPortsum() {
		return portsum;
	}
    
    public void setPortsum(Integer portsum) {
		this.portsum = portsum;
	}
    
    public String getOstype() {
		return ostype;
	}
    
    public void setOstype(String ostype) {
		this.ostype = ostype;
	}
    
    public String getLocation() {
		return location;
	}
    
    public void setLocation(String location) {
		this.location = location;
	}
    
    public Long getUptime() {
		return uptime;
	}
    
    public void setUptime(Long uptime) {
		this.uptime = uptime;
	}
    
    public Long getDowntime() {
		return downtime;
	}
    
    public void setDowntime(Long downtime) {
		this.downtime = downtime;
	}
    
    public Long getTotaltime() {
		return totaltime;
	}
    
    public void setTotaltime(Long totaltime) {
		this.totaltime = totaltime;
	}
    
    public Boolean getState() {
		return state;
	}
    
    public void setState(Boolean state) {
		this.state = state;
	}
    
    public String getMacaddress() {
		return macaddress;
	}
    
    public void setMacaddress(String macaddress) {
		this.macaddress = macaddress;
	}
    
    public String getMacvendor() {
		return macvendor;
	}
    
    public void setMacvendor(String macvendor) {
		this.macvendor = macvendor;
	}

}
