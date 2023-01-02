package com.tecnico.lemon.auth;

import com.tecnico.lemon.models.user.UserLogin;
import org.springframework.stereotype.Component;

@Component
public class LoginQueue extends AuthMap<String, UserLogin> {

    public LoginQueue() {
       super();
    }

}
