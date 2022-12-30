package com.tecnico.lemon.services;
import com.tecnico.lemon.contract.*;
import org.springframework.stereotype.Service;

import com.tecnico.lemon.dtos.UserInfo;
import com.tecnico.lemon.database.UserTableServiceFrontend;

@Service
public class UserServiceImpl implements UserService {

    UserTableServiceFrontend userServiceFrontend = new UserTableServiceFrontend();

    @Override
    public boolean signupUser(UserInfo userInfo) {
        return userServiceFrontend.signUpUser(userInfo);
    }

    public boolean lookupUser(String email) {
        return userServiceFrontend.lookupUser(email);
    }



}