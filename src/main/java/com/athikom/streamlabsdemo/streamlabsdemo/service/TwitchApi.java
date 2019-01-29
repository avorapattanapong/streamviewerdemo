package com.athikom.streamlabsdemo.streamlabsdemo.service;

import com.athikom.streamlabsdemo.streamlabsdemo.models.AccessToken;
import com.athikom.streamlabsdemo.streamlabsdemo.models.User;

import java.util.List;

public interface TwitchApi {

    AccessToken getAccessToken(String scope, String code) throws Exception;

    List<User> getUser(String code, String loginName) throws Exception;

    void getEvents(String streamerName) throws Exception;

    String getStreamId(String streamerName) throws Exception;

    List<String> getVideoIds(String streamerName, String code, Integer limit) throws Exception;
}
