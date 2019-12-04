package com.metrica.formacion.oauthserver.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class UserCredentials {

    private Integer id;
    private String username;
    private String password;
    private List<roles> roles;

    public UserCredentials() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<com.metrica.formacion.oauthserver.entity.roles> getRoles() {
        return roles;
    }

    public void setRoles(List<com.metrica.formacion.oauthserver.entity.roles> roles) {
        this.roles = roles;
    }
}
