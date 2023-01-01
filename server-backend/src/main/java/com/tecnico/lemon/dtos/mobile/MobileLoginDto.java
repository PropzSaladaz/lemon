package com.tecnico.lemon.dtos.mobile;

public class MobileLoginDto {
    public String publicKey;
    public String email;

    public MobileLoginDto() {
        // Default constructor
    }

    public MobileLoginDto(String publicKey, String Email) {
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


