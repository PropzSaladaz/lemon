package com.tecnico.lemon.controllers;

import com.tecnico.lemon.models.user.UserForm;
import com.tecnico.lemon.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/user")
public class UserController {
    
    @Autowired
    UserService userService;

    @PostMapping (value="/signup/_signup")
    public void signupUser(@RequestBody UserForm userForm) {
        userService.signupUser(userForm);
    }
}