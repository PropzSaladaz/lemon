package com.tecnico.lemon.services;
import com.tecnico.lemon.contract.*;
import org.springframework.stereotype.Service;

import com.tecnico.lemon.database.DataBase;
import com.tecnico.lemon.models.user.UserForm;

@Service
public class UserServiceImpl implements UserService {


    private DataBase dBase = new DataBase();

    @Override
    public void signupUser(UserForm userForm) {
        dBase.createUser(userForm);
    }
}