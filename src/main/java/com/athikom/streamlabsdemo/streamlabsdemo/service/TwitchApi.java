package com.athikom.streamlabsdemo.streamlabsdemo.service;

import com.athikom.streamlabsdemo.streamlabsdemo.models.AccessToken;
import com.athikom.streamlabsdemo.streamlabsdemo.models.User;

import java.util.List;

public interface TwitchApi {

    public AccessToken getAccessToken(String scope, String code) throws Exception;

    public List<User> getUser(String code) throws Exception;
}
