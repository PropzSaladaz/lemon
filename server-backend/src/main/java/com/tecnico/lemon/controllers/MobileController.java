package com.tecnico.lemon.controllers;

import com.tecnico.lemon.Crypto;
import com.tecnico.lemon.dtos.mobile.MobileLoginDto;
import com.tecnico.lemon.dtos.mobile.MobileSignupDto;
import com.tecnico.lemon.models.user.User;
import com.tecnico.lemon.models.user.UserSignup;
import com.tecnico.lemon.auth.SignupLoginQueue;
import com.tecnico.lemon.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;

@RestController
@RequestMapping(value="/mobile")
public class MobileController {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    SignupLoginQueue queue;

    @PostMapping(value="/signup")
    public ResponseEntity<String> signup(@RequestBody MobileSignupDto mobileSignup) { // TODO should be done in a mobile service
        String userEmail = mobileSignup.getEmail();

        UserSignup signupUser = queue.getSignupQueue().get(userEmail);
        SecretKey userKey = signupUser.getSymmetricKey();
        String publicKey = Crypto.decryptAES(mobileSignup.getPublicKey(), userKey);
        String receivedToken = Crypto.decryptAES(mobileSignup.getToken(), userKey);

        if (signupUser.getToken().equals(receivedToken)) {
            signupUser.setPublicKey(publicKey);
            queue.getSignupQueue().add(userEmail, signupUser); // update user
            queue.getSignupQueue().authenticateUser(userEmail);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
        return new ResponseEntity<>("Wrong token!", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value="/login")
    public ResponseEntity<String> login(@RequestBody MobileLoginDto mobileLogin) { // TODO should be done in a mobile service
        String userEmail = mobileLogin.getEmail();
        SecretKey symmetricKey = queue.getLoginQueue().get(userEmail).getSymmetricKey();
        String userPublicKey = Crypto.decryptAES(mobileLogin.getPublicKey(), symmetricKey);
        User user = userService.lookupUser(userEmail);

        if (user.getPublicKey().equals(userPublicKey)) {
            queue.getLoginQueue().authenticateUser(userEmail);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
        return new ResponseEntity<>("Wrong key!", HttpStatus.BAD_REQUEST);
    }
}