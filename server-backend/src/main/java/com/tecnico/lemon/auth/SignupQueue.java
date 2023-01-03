package com.tecnico.lemon.auth;

import org.springframework.stereotype.Service;
import com.tecnico.lemon.models.user.UserSignup;

@Service
public class SignupQueue extends AuthMap<String, UserSignup> {
    public SignupQueue() {
        super();
    }
}