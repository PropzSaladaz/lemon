package com.tecnico.lemon.models.user;

import com.tecnico.lemon.auth.Authenticatable;

import javax.crypto.SecretKey;

public class UserSignup extends User implements Authenticatable {

    private String token;
    private SecretKey symmetricKey;
    private boolean authenticated = false;

    public SecretKey getSymmetricKey() {
        return symmetricKey;
    }

    public void setSymmetricKey(SecretKey symmetricKey) {
        this.symmetricKey = symmetricKey;
    }

    public UserSignup(String email, SecretKey symmetricKey, String token) {
        super(email, "", "");
        this.symmetricKey = symmetricKey;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void authenticate() {
        authenticated = true;
    }

    @Override
    public void cancelAuthentication() {
        authenticated = false;
    }
}

