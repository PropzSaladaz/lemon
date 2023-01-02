package com.tecnico.lemon.controllers;

import com.tecnico.lemon.database.UserTypes;
import com.tecnico.lemon.models.user.User;
import com.tecnico.lemon.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/login")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value="/{email}")
    public ResponseEntity<String> loginSignup(@PathVariable("email") String email) throws Exception {
        User user = userService.lookupUser(email);
        if (user == null) {
            System.out.println("user doesnt exist");
            if (userService.signupUser(email)){
                return new ResponseEntity<>(UserTypes.CUSTOMER, HttpStatus.OK);
            }
            else return new ResponseEntity<>("Token expired, try again!", HttpStatus.BAD_REQUEST);
        } else{
            System.out.println("user exists");
            userService.loginUser(email);
            return new ResponseEntity<>(user.getType(), HttpStatus.OK);
        }
    }
}
