package com.tecnico.lemon.controllers;

import com.tecnico.lemon.ContractsAndKeys.Crypto;
import com.tecnico.lemon.services.SignUpRepository;
import com.tecnico.lemon.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/signup")
public class MobileController {

    @Autowired
    UserService userService;
    @Autowired
    SignUpRepository repository;

    @PostMapping(value="/{message}/{token}")
    public ResponseEntity<String> tokenUpdate(@PathVariable("message") String message,@PathVariable("token") String token){

        String publicKey = Crypto.decryptAES(message,repository.getInfo(token).get_secretKey());
        if (repository.containsToken(token)){
            repository.changePublicKey(token,publicKey);
            userService.signupUser(repository.getInfo(token));
            repository.removeToken(token);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }else{
            return ResponseEntity.badRequest().body("Token Does Not Exist");
        }
    }


}