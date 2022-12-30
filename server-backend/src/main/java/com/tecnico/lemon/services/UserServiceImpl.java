package com.tecnico.lemon.services;
import com.tecnico.lemon.contract.*;
import org.springframework.stereotype.Service;

import com.tecnico.lemon.dtos.UserInfo;

import com.tecnico.lemon.database.DataBase;
import com.tecnico.lemon.models.user.UserForm;

@Service
public class UserServiceImpl implements UserService {


    private DataBase dBase = new DataBase();

    @Override
    public boolean signupUser(UserInfo userinfo) {
        return dBase.signUpUser(userinfo);
    }

    public boolean lookupUser(String email) {
        return dBase.lookupUser(email);
    }



}