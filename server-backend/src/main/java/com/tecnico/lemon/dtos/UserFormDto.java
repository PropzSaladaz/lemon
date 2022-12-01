package com.tecnico.lemon.dtos;

import com.tecnico.lemon.models.user.UserForm;

public class UserFormDto {
    
    public String email;
    public String password;
    public String type;

    public UserFormDto(UserForm userForm) {
        email = userForm.get_email();
        password = userForm.get_password();
        type = userForm.get_type();
    }
}
