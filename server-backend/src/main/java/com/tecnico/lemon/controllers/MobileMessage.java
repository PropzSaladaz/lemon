package com.tecnico.lemon.controllers;


public class MobileMessage {
    private String publicKey;

    public MobileMessage(String Token, String PublicKey) {
        publicKey = PublicKey;
    }

    public String get_publicKey() {
        return publicKey;
    }

}