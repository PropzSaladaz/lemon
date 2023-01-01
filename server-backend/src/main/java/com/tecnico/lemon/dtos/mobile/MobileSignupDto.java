package com.tecnico.lemon.dtos.mobile;

public class MobileSignupDto {
    public String publicKey;
    public String token;
    public String email;

    public MobileSignupDto() {
        // Default constructor
    }

    public MobileSignupDto(String email, String publicKey, String token) {
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
