package com.tecnico.lemon.controllers;
import com.tecnico.lemon.Crypto;
import com.tecnico.lemon.MobileMessage;
import com.tecnico.lemon.services.SignUpRepository;
import com.tecnico.lemon.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping(value="/signup")
public class MobileController {

    @Autowired
    UserService userService;
    @Autowired
    SignUpRepository repository;

    @PostMapping(value="/{email}")
    public ResponseEntity<String> tokenUpdate(@PathVariable("email") String email,@RequestBody MobileMessage mobileMessage) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        String publicKey = Crypto.decryptAES(mobileMessage.getPublicKey(),repository.getInfo(email).get_secretKey());
        String token = Crypto.decryptAES(mobileMessage.getToken(),repository.getInfo(email).get_secretKey());
        if (repository.containsToken(email,token)){
            repository.changePublicKey(email,publicKey);
            userService.signupUser(repository.getInfo(email));
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }else{
            return ResponseEntity.badRequest().body("Token Does Not Exist");
        }
    }


}