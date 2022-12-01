package com.tecnico.lemon.services;

import org.springframework.stereotype.Service;

import com.tecnico.lemon.models.UserForm;

@Service
public interface UserService {
    void signupUser(UserForm userForm);
}