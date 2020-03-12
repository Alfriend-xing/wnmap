package com.wnmap.bean;

public class Msg {
    public Boolean status;
    public String msg;
    public Object object;
    
    public Msg(Boolean status, String msg) {
        this.status = status;
        this.msg = msg;
    }
    public Msg(Boolean status, String msg,Object object) {
        this.status = status;
        this.msg = msg;
        this.object = object;
    }
	
}
