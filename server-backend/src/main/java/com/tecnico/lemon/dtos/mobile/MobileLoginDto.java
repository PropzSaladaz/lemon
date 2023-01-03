package com.tecnico.lemon.dtos.mobile;

public class MobileLoginDto {
    public String email;
    public String publicKey;

    public MobileLoginDto() {
        // Default constructor
    }

    public MobileLoginDto(String email, String publicKey) {
        this.email = email;
        this.publicKey = publicKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getEmail() {
        return email;
    }
}


