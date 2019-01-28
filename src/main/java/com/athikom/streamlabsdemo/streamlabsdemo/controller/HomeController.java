package com.athikom.streamlabsdemo.streamlabsdemo.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
@Scope("session")
public class HomeController {

    @RequestMapping("/")
    public String home(HttpSession session){
        String token = (String)session.getAttribute("token");
        return "home";
    }
}
