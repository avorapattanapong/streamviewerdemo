package com.athikom.streamlabsdemo.streamlabsdemo.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class AccessToken implements Serializable {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("scope")
    private List<String> scope;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("expires_in")
    private Integer ttlSeconds;

    @JsonProperty("token_type")
    private String tokenType;

    public AccessToken(String accessToken, List<String> scope, String refreshToken, Integer ttlSeconds, String tokenType) {
        this.accessToken = accessToken;
        this.scope = scope;
        this.refreshToken = refreshToken;
        this.ttlSeconds = ttlSeconds;
        this.tokenType = tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public List<String> getScope() {
        return scope;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Integer getTtlSeconds() {
        return ttlSeconds;
    }

    public String getTokenType() {
        return tokenType;
    }

    @Override
    public String toString() {
        return "AccessToken{" +
                "accessToken='" + accessToken + '\'' +
                ", scope=" + scope +
                ", refreshToken='" + refreshToken + '\'' +
                ", ttlSeconds=" + ttlSeconds +
                ", tokenType='" + tokenType + '\'' +
                '}';
    }
}
