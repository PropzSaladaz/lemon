package com.tecnico.lemon.controllers;

import com.tecnico.lemon.database.UserTypes;
import com.tecnico.lemon.models.user.User;
import com.tecnico.lemon.mobile.MobileFrontend;
import com.tecnico.lemon.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping(value="/login")
public class UserController {

    public class SigninResponse {
        private String _publicKey;
        private String _customerType;

        private boolean _signup;

        public SigninResponse(String publicKey, String customerType, boolean signup) {
            _publicKey = publicKey;
            _customerType = customerType;
            _signup = signup;
        }

        public String getPublicKey() {
            return _publicKey;
        }
        
        public String getCustomerType() {
            return _customerType;
        }

        public boolean getSignup() {
            return _signup;
        }
    }

    @Autowired
    UserService userService;

    @PostMapping(value="/{email}")
    public ResponseEntity<SigninResponse> loginSignup(@PathVariable("email") String email) throws Exception {
        User user = userService.lookupUser(email);
        if (user == null) {
            System.out.println("user doesnt exist");
            String userPublicKey = userService.signupUser(email);
            if (!userPublicKey.equals("ERROR")){
                return new ResponseEntity<SigninResponse>(new SigninResponse(userPublicKey, UserTypes.CUSTOMER, true), HttpStatus.OK);
            }
            return new ResponseEntity<SigninResponse>(new SigninResponse("", "", true), HttpStatus.BAD_REQUEST);
        }
        else {
            System.out.println("user exists");
            String userPublicKey = userService.loginUser(email);
            if (!userPublicKey.equals("ERROR")) {
                return new ResponseEntity<SigninResponse>(new SigninResponse(userPublicKey, user.getType(), false), HttpStatus.OK);
            }
            return new ResponseEntity<SigninResponse>(new SigninResponse("", "", false), HttpStatus.BAD_REQUEST);
        }
    }
}
