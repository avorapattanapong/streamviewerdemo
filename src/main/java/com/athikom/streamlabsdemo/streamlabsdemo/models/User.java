package com.athikom.streamlabsdemo.streamlabsdemo.models;

import java.io.Serializable;

public class User implements Serializable {

    private String email;

    private String name;

    private Integer id;

    private String login;

    public User(String email, String name, Integer id, String login) {
        this.email = email;
        this.name = name;
        this.id = id;
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", login='" + login + '\'' +
                '}';
    }
}
