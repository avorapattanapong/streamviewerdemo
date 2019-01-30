package com.athikom.streamlabsdemo.streamlabsdemo.controller;

import com.athikom.streamlabsdemo.streamlabsdemo.StreamlabsdemoApplication;
import com.athikom.streamlabsdemo.streamlabsdemo.models.AccessToken;
import com.athikom.streamlabsdemo.streamlabsdemo.models.User;
import com.athikom.streamlabsdemo.streamlabsdemo.service.TwitchApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private TwitchApi twitchApi;

    protected static final Logger logger = LoggerFactory.getLogger(StreamlabsdemoApplication.class);

    @RequestMapping("/")
    public String home(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)auth.getPrincipal();
        model.addAttribute("user", user);
        return "home";
    }

    @RequestMapping("/getVideoIds")
    public ResponseEntity<List<String>> getVideoIds(@RequestParam("streamerName")String streamerName) {
        try {
            List<String> videoIds = twitchApi.getVideoIds(streamerName, 10);
            return new ResponseEntity<>(videoIds, HttpStatus.OK);
        } catch(Exception e) {
            logger.error("Error getting events", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping("/subscribe")
    public ResponseEntity<Object> subscribeWebhooks(@RequestParam("streamerName")String streamerName) {
        try {
            twitchApi.subscribeUserFollowsWebhooks(streamerName);
            twitchApi.subscribeStreamChangedWebhooks(streamerName);
            twitchApi.subscribeUserUserChangedWebhooks(streamerName);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(Exception e) {
            logger.error("Error getting events", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
