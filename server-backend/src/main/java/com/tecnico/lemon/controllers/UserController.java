package com.tecnico.lemon.controllers;

import com.tecnico.lemon.models.user.UserForm;
import com.tecnico.lemon.services.UserService;

@RestController
@RequestMapping(value="/user")
public class UserController {
    
    @Autowired
    UserService userService;

    @PostMapping (value="/signup/_signup")
    public void signupUser(@RequestBody) {
        UserForm userForm = new UserForm(email, password)
        userService.signupUser(userForm);
    }

}