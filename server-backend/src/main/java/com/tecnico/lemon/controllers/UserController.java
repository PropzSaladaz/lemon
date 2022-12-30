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
    public ResponseEntity<String> signupUser(@PathVariable("email") String email) throws InvalidKeySpecException, NoSuchAlgorithmException {
        System.out.println(email);
        if(!userService.lookupUser(email)){
            UserInfo info = new UserInfo(email);
            String token = TokenGenerate.generateToken(8);
            info.set_secretKey(KeyGenerate.generateKey(token));
            repository.putMap(token,info);
            JavaMailUtil.sendEmail(token,email);
            long startTime = System.currentTimeMillis();
            long elapsedTime = 0;
            long millis = 600000; // 10 minutes in milliseconds
            while (elapsedTime < millis) {
                elapsedTime = System.currentTimeMillis() - startTime;
                if (repository.getInfo(token).get_publicKey() != null) {
                    return new ResponseEntity<>("Signed Completed With Success", HttpStatus.OK);
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
        }else{
            return ResponseEntity.badRequest().body("Already Signed Up");
        }
        return ResponseEntity.badRequest().body("Already Signed Up");
    }
}
