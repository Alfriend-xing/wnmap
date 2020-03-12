package com.wnmap.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wnmap.bean.Msg;

@RestController
public class LoginController {
    @RequestMapping("/api/login_info")
    public Msg login(HttpServletRequest requests) {
		Msg msg = new Msg(true, "已登录");
        return msg;
    }

}
