package com.tecnico.lemon.services;

public class MobileMessage {
    public String publicKey;
    public String token;

    public MobileMessage() {
        // Default constructor
    }

    public MobileMessage(String publicKey, String token) {
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
