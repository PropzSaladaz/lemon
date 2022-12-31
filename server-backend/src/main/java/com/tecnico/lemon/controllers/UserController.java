package com.tecnico.lemon.controllers;

import com.tecnico.lemon.ContractsAndKeys.KeyGenerate;
import com.tecnico.lemon.TokenGenerator;
import com.tecnico.lemon.dtos.UserInfo;
import com.tecnico.lemon.mobile.MobileFrontend;
import com.tecnico.lemon.services.SignUpRepository;
import com.tecnico.lemon.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping(value="login")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value="/{email}")
    public ResponseEntity<String> signupUser(@PathVariable("email") String email) throws Exception {
        if(userService.signupUser(email)) return new ResponseEntity<>("Signed Completed With Success", HttpStatus.OK);
        else return ResponseEntity.badRequest().body("Already Signed Up");
    }
}
