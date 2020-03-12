package com.wnmap.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String userName;
    private String passWord;
    private String role;

    protected User() {}

    public User(String userName, String passWord, String role) {
        this.userName = userName;
        this.passWord = passWord;
        this.role=role;
    }
    
    public Long getId() {
    	return this.id;
    }

    public String getUsername() {
    	return this.userName;
    }
    
    public String getPassword() {
    	return this.passWord;
    }
    
    public String getRole() {
    	return this.role;
    }

}
