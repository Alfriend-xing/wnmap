package com.wnmap.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Port {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

//  端口号
    @Column(unique = true)
    private String number;

//  开放数量
    private Integer sum;
    
    public Port() {
		
	}
    public Port(String number, Integer sum) {
		this.number = number;
		this.sum = sum;
	}
    
    public void setNumber(String number) {
		this.number = number;
	}
    
    public String getNumber() {
		return number;
	}
    
    public void setSum(Integer sum) {
		this.sum = sum;
	}
    
    public Integer getSum() {
		return sum;
	}

}
