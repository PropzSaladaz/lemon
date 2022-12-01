package com.tecnico.lemon.models.user;

public class UserForm {

    private String _email;
    private String _password;
    private String _type;

    public String get_email() {
        return _email;
    }

    public String get_password() {
        return _password;
    }

    public String get_type() {
        return _type;
    }   

    public UserForm(String email, String password, String type){
        _email = email;
        // Encrypt Password here
        _password = password;
        _type = type;
    }
}