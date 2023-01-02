package com.tecnico.lemon.models.user;

import com.tecnico.lemon.database.UserTypes;

public class User {
    private String email;
    private String publicKey;
    private String type;

    public User(String email, String publicKey, String type) {
        this.email = email;
        this.publicKey = publicKey;
        this.type = type;
    }

    public User(UserSignup user) {
        this.email = user.getEmail();
        this.publicKey = user.getPublicKey();
        this.type = UserTypes.CUSTOMER;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
