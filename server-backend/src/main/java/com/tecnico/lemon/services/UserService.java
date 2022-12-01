package com.tecnico.lemon.services;

import org.springframework.stereotype.Service;

import com.tecnico.lemon.models.user.UserForm;
import com.tecnico.lemon.dtos.UserInfoDto;

@Service
public interface UserService {
    void signupUser(UserForm userForm);
    UserInfoDto lookupUser(String email);
}