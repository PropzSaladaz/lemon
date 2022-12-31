package com.tecnico.lemon;

public class MobileSignup {
    public String publicKey;
    public String token;
    public String email;

    public MobileSignup() {
        // Default constructor
    }

    public MobileSignup(String email, String publicKey, String token) {
        this.email = email;
        this.publicKey = publicKey;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getToken() {
        return token;
    }
}
