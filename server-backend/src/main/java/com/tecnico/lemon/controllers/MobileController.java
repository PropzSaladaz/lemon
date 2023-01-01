package com.tecnico.lemon.controllers;

import com.tecnico.lemon.Crypto;
import com.tecnico.lemon.dtos.mobile.MobileLoginDto;
import com.tecnico.lemon.dtos.mobile.MobileSignupDto;
import com.tecnico.lemon.services.SignUpWaitingQueue;
import com.tecnico.lemon.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;

@RestController
@RequestMapping(value="/mobile")
public class MobileController {

    @Autowired
    UserService userService;
    @Autowired
    SignUpWaitingQueue signupRepository;

    @PostMapping(value="/signup")
    public ResponseEntity<String> signup(@RequestBody MobileSignupDto mobileSignup) {
        String userEmail = mobileSignup.getEmail();
        SecretKey userKey = signupRepository.getInfo(userEmail).getSecretKey();
        String publicKey = Crypto.decryptAES(mobileSignup.getPublicKey(), userKey);
        String token = Crypto.decryptAES(mobileSignup.getToken(), userKey);

        if (signupRepository.userHasToken(userEmail, token)){
            signupRepository.setUserPublicKey(userEmail, publicKey);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Wrong token!", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value="/login")
    public ResponseEntity<String> login(@RequestBody MobileLoginDto mobileLogin) {
        String sessionKey = "lololo"; // TODO generate a sessionKey for each user trying to log in
        String userEmail = mobileLogin.getEmail();
        SecretKey userKey = signupRepository.getInfo(userEmail).getSecretKey();
        String publicKey = Crypto.decryptAES(mobileLogin.getPublicKey(), userKey);
        // TODO login condition
        return null;
    }


}