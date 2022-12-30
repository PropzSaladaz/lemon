package com.tecnico.lemon.controllers;


public class MobileMessage {
    private String publicKey;

    public MobileMessage(String PublicKey) {
        publicKey = PublicKey;
    }

    public String get_publicKey() {
        return publicKey;
    }

}