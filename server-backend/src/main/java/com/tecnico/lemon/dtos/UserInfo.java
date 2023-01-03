package com.tecnico.lemon.dtos;

import javax.crypto.SecretKey;

public class UserInfo {

    public String email;
    public String publicKey;
    public String type;
    public SecretKey secretKey;
    public String token;

    public UserInfo(String email) {
        this.email = email;
        publicKey = null;
        secretKey = null;
        token = null;
        type = "Customer";
    }

    public String getEmail() {
        return email;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getType() {
        return type;
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }

    public String getToken() {
        return token;
    }

    public void setSecretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

