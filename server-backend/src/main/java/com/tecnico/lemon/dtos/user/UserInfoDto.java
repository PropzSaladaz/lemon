package com.tecnico.lemon.dtos.user;

import com.tecnico.lemon.models.user.UserForm;

public class UserInfoDto {
    
    public int id;
    public String email;
    public String password;
    public String type;

    public UserInfoDto(int _id, String _email, String _password, String _type) {
        id = _id;
        email = _email;
        password = _password;
        type = _type;
    }
}
