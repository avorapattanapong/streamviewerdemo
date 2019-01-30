package com.athikom.streamlabsdemo.streamlabsdemo.service;

import com.athikom.streamlabsdemo.streamlabsdemo.models.AccessToken;
import com.athikom.streamlabsdemo.streamlabsdemo.models.User;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TwitchApiImpl implements TwitchApi {

    @Value("${twitch.api.client-id:}")
    private String clientId;

    @Value("${twitch.api.secret:}")
    private String secretId;

    @Value("${twitch.api.redirect-url:}")
    private String redirectUrl;

    @Value("${twitch.api.v5.url:}")
    private String apiV5Url;

    @Value("${twitch.api.new.url:}")
    private String apiNewUrl;

    @Value("${webhookHandlerBaseUrl:}")
    private String webhookHandlerBaseUrl;

    HttpClient client;

    private void getClient(){
        this.client = HttpClients.createDefault();
    }

    private String post(String url, Map<String, String> params, Map<String, String> headers, Map<String, Object> jsonBodyParams) throws Exception {
        URIBuilder builder = new URIBuilder(url);
        if(params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.setParameter(entry.getKey(), entry.getValue());
            }
        }

        HttpPost post = new HttpPost(builder.build());

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            post.setHeader(entry.getKey(), entry.getValue());
        }

        if(jsonBodyParams != null) {
            post.setHeader("Content-Type", "application/json");
            JSONObject body = new JSONObject(jsonBodyParams);
            StringEntity entity = new StringEntity(body.toString());
            post.setEntity(entity);
        }

        HttpResponse response = this.client.execute(post);

        StatusLine statusLine = response.getStatusLine();

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        if(statusLine.getStatusCode() >= 400) {
            JSONObject object = new JSONObject(result.toString());
            switch(statusLine.getStatusCode()) {
                case 400:
                    throw new TwitchApiBadRequestException(object.getString("message"));
                case 404:
                    throw new TwitchApiNotFoundException(object.getString("message"));
                default:
                    throw new TwitchApiInternalServerErrorException(object.getString("message"));
            }
        }
        return result.toString();
    }

    private String get(String url, Map<String, String> params, Map<String, String> headers) throws Exception {
        URIBuilder builder = new URIBuilder(url);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.setParameter(entry.getKey(), entry.getValue());
        }
        HttpGet get = new HttpGet(builder.build());

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            get.setHeader(entry.getKey(), entry.getValue());
        }

        HttpResponse response = client.execute(get);

        StatusLine statusLine = response.getStatusLine();

        if(statusLine.getStatusCode() >= 400) {
            switch(statusLine.getStatusCode()) {
                case 400:
                    throw new TwitchApiBadRequestException(statusLine.getReasonPhrase());
                case 404:
                    throw new TwitchApiNotFoundException(statusLine.getReasonPhrase());
                default:
                    throw new TwitchApiInternalServerErrorException(statusLine.getReasonPhrase());
            }
        }
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        return result.toString();
    }

    @Override
    public AccessToken getAccessToken(String scope, String code) throws TwitchApiException {
        String url = "https://id.twitch.tv/oauth2/token";

        if(this.client == null) {
            getClient();
        }

        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");

        Map<String, String> params = new HashMap<>();
        params.put("client_id", clientId);
        params.put("client_secret", secretId);
        params.put("code", code);
        params.put("grant_type", "authorization_code");
        params.put("redirect_uri", redirectUrl);
        params.put("scope", scope);

        try{
            String result = post(url, params, headers, null);

            JSONObject jsonObj = new JSONObject(result);
            JSONArray scopeArray = jsonObj.getJSONArray("scope");
            List<String> scopeString = new ArrayList<>();
            for(int i = 0; i < scopeArray.length(); i++){
                scopeString.add(scopeArray.getString(i));
            }
            return new AccessToken(
                    jsonObj.getString("access_token"),
                    scopeString,
                    jsonObj.getString("refresh_token"),
                    jsonObj.getInt("expires_in"),
                    jsonObj.getString("token_type"));
        } catch (TwitchApiException e) {
            throw e;
        } catch (Exception e) {
            throw  new TwitchApiInternalServerErrorException(e.getMessage());
        }

    }

    @Override
    public User getLoggedInUser(String accessToken) throws TwitchApiException {
        if(this.client == null) {
            getClient();
        }

        try {
            return getUsers(accessToken, null).get(0);
        } catch (TwitchApiException e) {
            throw e;
        } catch (Exception e) {
            throw  new TwitchApiInternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public List<User> getUsers(String loginName) throws TwitchApiException {
        if(this.client == null) {
            getClient();
        }

        try {
            return getUsers(null, loginName);
        } catch (TwitchApiException e) {
            throw e;
        } catch (Exception e) {
            throw  new TwitchApiInternalServerErrorException(e.getMessage());
        }
    }

    private List<User> getUsers(String accessToken, String loginName) throws Exception {
        String url = "https://api.twitch.tv/helix/users";
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");

        if(accessToken != null) {
            headers.put(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        } else {
            headers.put("Client-ID",clientId);
        }

        Map<String, String> params = new HashMap<>();
        if(loginName != null && !loginName.equals("")) {
            params.put("login", loginName);
        }

        String result = get(url, params, headers);
        JSONObject jsonObj = new JSONObject(result);
        JSONArray data = jsonObj.getJSONArray("data");

        List<User> users = new ArrayList<>();
        for(int i = 0; i < data.length(); i ++) {
            JSONObject userObj = data.getJSONObject(i);
            String email = "";
            userObj.has("email");
            if(userObj.has("email")) {
                email = userObj.getString("email");
            }
            User user = new User(
                    email,
                    userObj.getString("display_name"),
                    userObj.getInt("id"),
                    userObj.getString("login"));
            users.add(user);
        }

        return users;
    }

    @Override
    public List<String> getVideoIds(String streamerName, Integer limit) throws TwitchApiException {
        List<User> users = getUsers(streamerName);
        if(users.size() == 0) {
            throw new TwitchApiNotFoundException("Error user not found");
        }

        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Client-ID",clientId);

        Map<String, String> params = new HashMap<>();
        params.put("user_id", users.get(0).getId().toString());
        params.put("first", limit.toString());

        try {
            String result = get(apiNewUrl + "/videos", params, headers);
            JSONObject jsonObj = new JSONObject(result);

            List<String> videoIds = new ArrayList<>();
            JSONArray data = jsonObj.getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
                videoIds.add(data.getJSONObject(i).getString("id"));
            }
            return videoIds;
        } catch (TwitchApiException e) {
            throw e;
        } catch (Exception e) {
            throw  new TwitchApiInternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public void subscribeUserFollowsWebhooks(String streamerName) throws TwitchApiException {
        User user = getUsers(streamerName).get(0);

        try{
            URIBuilder builder = new URIBuilder(apiNewUrl + "/users/follows");
            builder.addParameter("first", "1");
            builder.addParameter("to_id", user.getId().toString());
            subscribeWebhooks(true, builder.toString(), webhookHandlerBaseUrl + "/userFollows");
        } catch (TwitchApiException e) {
            throw e;
        } catch (Exception e) {
            throw new TwitchApiInternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public void subscribeStreamChangedWebhooks(String streamerName) throws TwitchApiException {
        User user = getUsers(streamerName).get(0);
        try{
            URIBuilder builder = new URIBuilder(apiNewUrl + "/streams");
            builder.addParameter("user_id", user.getId().toString());
            subscribeWebhooks(true, builder.toString(), webhookHandlerBaseUrl + "/streamChanged");
        } catch (TwitchApiException e) {
            throw e;
        } catch (Exception e) {
            throw new TwitchApiInternalServerErrorException(e.getMessage());
        }
    }

    @Override
    public void subscribeUserUserChangedWebhooks(String streamerName) throws TwitchApiException {
        User user = getUsers(streamerName).get(0);
        try{
            URIBuilder builder = new URIBuilder(apiNewUrl + "/users");
            builder.addParameter("id", user.getId().toString());
            subscribeWebhooks(true, builder.toString(), webhookHandlerBaseUrl + "/userChanged");
        } catch (TwitchApiException e) {
            throw e;
        } catch (Exception e) {
            throw new TwitchApiInternalServerErrorException(e.getMessage());
        }
    }

    private void subscribeWebhooks(Boolean subscribe, String eventUrl, String callbackUrl) throws Exception {
        String url = apiNewUrl + "/webhooks/hub";

        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("Client-ID",clientId);

        Map<String, Object> jsonBody = new HashMap<>();
        jsonBody.put("hub.callback", callbackUrl);
        jsonBody.put("hub.mode", subscribe? "subscribe":"unsubscribe");
        jsonBody.put("hub.topic", eventUrl);
        jsonBody.put("hub.lease_seconds", 30);

        //No response body
        post(url, null, headers, jsonBody);
    }
}
