package com.athikom.streamlabsdemo.streamlabsdemo.service;

import com.athikom.streamlabsdemo.streamlabsdemo.models.AccessToken;
import com.athikom.streamlabsdemo.streamlabsdemo.models.User;

import java.util.List;

public interface TwitchApi {

    AccessToken getAccessToken(String scope, String code) throws TwitchApiException;

    User getLoggedInUser(String accessToken) throws TwitchApiException;

    List<User> getUsers(String loginName) throws TwitchApiException;

    List<String> getVideoIds(String streamerName, Integer limit) throws TwitchApiException;

    void subscribeUserFollowsWebhooks(String streamerName) throws TwitchApiException;

    void subscribeStreamChangedWebhooks(String streamerName) throws TwitchApiException;

    void subscribeUserUserChangedWebhooks(String streamerName) throws TwitchApiException;
}
