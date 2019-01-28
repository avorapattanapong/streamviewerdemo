package com.athikom.streamlabsdemo.streamlabsdemo.service;

import com.athikom.streamlabsdemo.streamlabsdemo.models.AccessToken;
import com.athikom.streamlabsdemo.streamlabsdemo.models.User;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
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

    HttpClient client;

    private void getClient(){
        this.client = HttpClients.createDefault();
    }

    private String post(String url, Map<String, String> params, Map<String, String> headers) throws Exception {
        HttpPost post = new HttpPost(url);

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            post.setHeader(entry.getKey(), entry.getValue());
        }

        List<NameValuePair> urlParameters = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            urlParameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = this.client.execute(post);
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
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
    public AccessToken getAccessToken(String scope, String code) throws Exception {
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

        String result = post(url, params, headers);

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
    }

    @Override
    public List<User> getUser(String code) throws Exception {
        if(this.client == null) {
            getClient();
        }
        String url = "https://api.twitch.tv/helix/users";
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put(HttpHeaders.AUTHORIZATION, "Bearer " + code);
        Map<String, String> params = new HashMap<>();

        String result = get(url, params, headers);
        JSONObject jsonObj = new JSONObject(result);
        JSONArray data = jsonObj.getJSONArray("data");

        List<User> users = new ArrayList<>();
        for(int i = 0; i < data.length(); i ++) {
            JSONObject userObj = data.getJSONObject(i);
            User user = new User(
                    userObj.getString("email"),
                    userObj.getString("display_name"),
                    userObj.getInt("id"),
                    userObj.getString("login"));
            users.add(user);
        }

        return users;
    }
}
