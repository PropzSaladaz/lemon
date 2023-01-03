package com.tecnico.lemon.auth;

import com.tecnico.lemon.models.user.UserLogin;
import com.tecnico.lemon.models.user.UserSignup;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("singleton")
@Component
public class SignupLoginQueue {
    private final AuthMap<String, UserSignup> signup = new AuthMap<>();
    private final AuthMap<String , UserLogin> login = new AuthMap<>();

    public synchronized AuthMap<String, UserSignup> getSignupQueue(){
        return signup;
    }
    public synchronized AuthMap<String, UserLogin> getLoginQueue(){
        return login;
    }
}
