package com.tecnico.lemon.services;

public class MobileSignup {
    public String publicKey;
    public String token;

    public MobileSignup() {
        // Default constructor
    }

    public MobileSignup(String publicKey, String token) {
        this.publicKey = publicKey;
        this.token = token;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getToken() {
        return token;
    }
}
