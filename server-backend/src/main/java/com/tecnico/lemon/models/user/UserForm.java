package com.tecnico.lemon.models.user;

public class UserForm {

    private String email;
    private String password;
    private String type;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }   

    public UserForm(String email, String password, String type){
        this.email = email;
        // Encrypt Password here
        this.password = password;
        this.type = type;
    }
}