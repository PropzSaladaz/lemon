package com.tecnico.lemon.services;

import org.springframework.stereotype.Service;
import com.tecnico.lemon.dtos.UserInfo;


import com.tecnico.lemon.models.user.UserForm;
import com.tecnico.lemon.dtos.UserInfoDto;

@Service
public interface UserService {
    boolean signupUser(UserInfo userinfo);
    boolean lookupUser(String email);
}