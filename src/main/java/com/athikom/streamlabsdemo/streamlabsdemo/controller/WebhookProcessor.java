package com.athikom.streamlabsdemo.streamlabsdemo.controller;

import com.athikom.streamlabsdemo.streamlabsdemo.StreamlabsdemoApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/webhook/process")
public class WebhookProcessor {

    protected static final Logger logger = LoggerFactory.getLogger(StreamlabsdemoApplication.class);

    @RequestMapping("/userFollows")
    public ResponseEntity<Object> handleUserFollows(@RequestParam(required = false) Map<String,String> allParams) {
        logger.info("userFollows");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/streamChanged")
    public ResponseEntity<Object> handleStreamChanged(@RequestParam(required = false) Map<String,String> allParams) {
        logger.info("streamChanged");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping("/userChanged")
    public ResponseEntity<Object> handleUserChanged(@RequestParam(required = false) Map<String,String> allParams) {
        logger.info("userChanged");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
