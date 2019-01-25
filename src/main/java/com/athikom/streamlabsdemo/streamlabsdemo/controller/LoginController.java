package com.athikom.streamlabsdemo.streamlabsdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/login/auth")
    public String auth() {
        return "redirect:https://id.twitch.tv/oauth2/authorize";
    }

    @RequestMapping("/users/me")
    public Principal user(Principal principal) {
        return principal;
    }
}
