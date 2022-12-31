package com.tecnico.lemon.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.tecnico.lemon.dtos.UserInfo;


import com.tecnico.lemon.models.user.UserForm;
import com.tecnico.lemon.dtos.UserInfoDto;

@Service
public interface UserService {
    boolean saveUser(UserInfo userinfo);
    boolean lookupUser(String email);

    boolean signupUser(String email) throws  Exception;
}