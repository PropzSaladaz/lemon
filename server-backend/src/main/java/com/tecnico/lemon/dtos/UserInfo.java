package com.tecnico.lemon.dtos;

import javax.crypto.SecretKey;

public class UserInfo {

    public String _email;
    public String _publicKey;
    public String _type;
    public SecretKey _secretKey;
    public String _token;

    public UserInfo(String email) {
        _email = email;
        _publicKey = null;
        _secretKey = null;
        _token = null;
        _type = "Customer";
    }

    public String get_email() {
        return _email;
    }

    public String get_publicKey() {
        return _publicKey;
    }

    public String get_type() {
        return _type;
    }

    public SecretKey get_secretKey() {
        return _secretKey;
    }

    public String get_token() {
        return _token;
    }

    public void set_secretKey(SecretKey secretKey) {
        _secretKey = secretKey;
    }

    public void set_publicKey(String publicKey) {
        _publicKey = publicKey;
    }

    public void set_token(String token) {
        _token = token;
    }
}

