package com.tecnico.lemon;

public class MobileLogin {
    public String publicKey;
    public String email;

    public MobileLogin() {
        // Default constructor
    }

    public MobileLogin(String publicKey, String Email) {
        this.publicKey = publicKey;
        this.email = Email;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getEmail() {
        return email;
    }
}


