package com.wnmap.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//在线情况表

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Online {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

//  数据记录时间
    private Long time;

//  数据格式化时间
    private String format;
    
//  在线主机数
    private Integer sum;

    public void setSum(Integer sum) {
		this.sum = sum;
	}
    
    public Integer getSum() {
		return sum;
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
    
    public Online() {
		
	}
    
    public Online(Long time, String format, Integer sum ) {
		this.time = time;
		this.format = format;
		this.sum = sum;
	}
}
