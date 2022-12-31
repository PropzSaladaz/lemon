package com.tecnico.lemon;

public class MobileMessageLogin {
    public String publicKey;
    public String email;

    public MobileMessageLogin() {
        // Default constructor
    }

    public MobileMessageLogin(String publicKey, String Email) {
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

