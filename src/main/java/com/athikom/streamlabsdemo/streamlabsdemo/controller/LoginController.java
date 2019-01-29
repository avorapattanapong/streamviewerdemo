package com.athikom.streamlabsdemo.streamlabsdemo.controller;

import com.athikom.streamlabsdemo.streamlabsdemo.StreamlabsdemoApplication;
import com.athikom.streamlabsdemo.streamlabsdemo.models.AccessToken;
import com.athikom.streamlabsdemo.streamlabsdemo.models.User;
import com.athikom.streamlabsdemo.streamlabsdemo.service.TwitchApi;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
public class LoginController {

    @Value("${twitch.api.client-id:}")
    private String clientId;

    @Value("${twitch.api.secret:}")
    private String secretId;

    private String redirectUrl = "http://localhost:8123/handleoauth";

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @Autowired
    private TwitchApi twitchApi;

    protected static final Logger logger = LoggerFactory.getLogger(StreamlabsdemoApplication.class);

    @RequestMapping("/auth")
    public String authorization(HttpSession session) {
        String state = generateState();
        session.setAttribute("state", state);
        return "redirect:https://id.twitch.tv/oauth2/authorize?" +
                "force_verify=true&" +
                "response_type=code&" +
                "scope=user:read:email&" +
                "redirect_uri=" + redirectUrl + "&" +
                "client_id="+clientId + "&" +
                "state="+state;
    }


    @RequestMapping("/handleoauth")
    public String oauthHandler(HttpSession session, @RequestParam("code") String code, @RequestParam("scope") String scope, @RequestParam("state") String state) {
        String sessionState = (String)session.getAttribute("state");
        if (!sessionState.equals(state)) {
            return "error";
        }

        try {
            AccessToken token = twitchApi.getAccessToken(scope, code);
            List<User> user = twitchApi.getUser(token.getAccessToken(),null);

            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.get(0), token, authorities);
            SecurityContext context = new SecurityContextImpl(authentication);
            SecurityContextHolder.setContext(context);
        } catch(Exception e) {
            logger.error("error", e);
        }

        return "redirect:/";
    }

    @RequestMapping("/users/me")
    public Principal user(Principal principal) {
        return principal;
    }

    public String generateState() {
        return RandomStringUtils.randomAlphanumeric(10);
    }
}
