package com.bfwg.model;


public class UserTokenState {
    private String fullName;
    private String access_token;
    private Long expires_in;

    public UserTokenState() {
        this.access_token = null;
        this.expires_in = null;
    }

    public UserTokenState(String access_token, long expires_in,String fullName) {
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.fullName = fullName;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Long expires_in) {
        this.expires_in = expires_in;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}