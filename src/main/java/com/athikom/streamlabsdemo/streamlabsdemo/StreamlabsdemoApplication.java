package com.athikom.streamlabsdemo.streamlabsdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class StreamlabsdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(StreamlabsdemoApplication.class, args);
    }

}

