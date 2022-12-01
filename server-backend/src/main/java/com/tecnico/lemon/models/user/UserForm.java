package com.tecnico.lemon.models.user;

public class UserForm {

    private final static String _email;
    private final static String _password;

    public String get_email() {
        return _email;
    }

    public String get_password() {
        return _password;
    }

    public UserForm(String email, String password){
        _email = email;
        // Encrypt Password here
        _password = password;
    }
}