package com.tecnico.lemon.controllers;

import com.tecnico.lemon.dtos.UserInfo;
import com.tecnico.lemon.services.SignUpRepository;
import com.tecnico.lemon.services.UserService;
import com.tecnico.lemon.ContractsAndKeys.TokenGenerate;
import com.tecnico.lemon.ContractsAndKeys.KeyGenerate;
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
    @Autowired
    SignUpRepository repository;


    @PostMapping(value="/{email}")
    public ResponseEntity<String> loginUser(@PathVariable("email") String email) throws InvalidKeySpecException, NoSuchAlgorithmException {
        System.out.println(email);
        String token = "NULL";

        // Email does not exist -> Signup
        if (!userService.lookupUser(email)){
            UserInfo info = new UserInfo(email);
            token = TokenGenerate.generateToken(8);
            info.set_secretKey(KeyGenerate.generateKey(token));
            repository.putMap(token,info);
            JavaMailUtil.sendEmail(token,email);
        }
        // Email already exists -> Login
        else {
            // Prompt the password for the user with this email
            // Send a session key encrypted with his public key (?)
        }
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0;
        long millis = 600000; // 10 minutes in milliseconds
        while (elapsedTime < millis) {
            elapsedTime = System.currentTimeMillis() - startTime;
            if (token != "NULL" && repository.getInfo(token).get_publicKey() != null) {
                return new ResponseEntity<>("Signed up Successfuly", HttpStatus.OK);
            }
            else if (true /* Condition for havin logged in */) {
                // Code for login
                return new ResponseEntity<>("Logged in Successfuly", HttpStatus.OK);
            }
            System.out.println("Waiting 10 minutes...");
            try {
                Thread.sleep(60000); // Sleep for 1 minute (60 seconds)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (elapsedTime >= millis) {
            repository.removeToken(token);
        }
        return ResponseEntity.badRequest().body("Already Signed Up");
    }
}
