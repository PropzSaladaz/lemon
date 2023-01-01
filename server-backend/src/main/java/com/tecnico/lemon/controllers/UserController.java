package com.tecnico.lemon.controllers;

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
        if (!userService.lookupUser(email)) {
            System.out.println("when user doesnt exist");
            if (userService.signupUser(email)){
                System.out.println("signed");
                return new ResponseEntity<>("Customer", HttpStatus.OK);
            }
            else return new ResponseEntity<>("Token expired, try again!", HttpStatus.BAD_REQUEST);
        } else{
            userService.loginUser(email);
            return new ResponseEntity<>("Logged in Successfully", HttpStatus.OK);
        }
    }
}
