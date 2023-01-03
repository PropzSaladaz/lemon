package com.tecnico.lemon.models.user;

import com.tecnico.lemon.auth.Authenticatable;

import javax.crypto.SecretKey;

public class UserLogin implements Authenticatable {

    private SecretKey symmetricKey;
    private boolean authenticated = false;

    public UserLogin(SecretKey symmetricKey) {
        this.symmetricKey = symmetricKey;
    }

    public SecretKey getSymmetricKey() {
        return symmetricKey;
    }

    public void setSymmetricKey(SecretKey secret) {
        this.symmetricKey = secret;
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
