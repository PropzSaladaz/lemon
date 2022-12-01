package com.tecnico.lemon.dtos;

public class UserFormDto {
    
    public String email;
    public String password;

    public UserFormDto(UserForm userForm) {
        email = userForm.get_email();
        password = userForm.get_password();
    }
}
