package com.tecnico.lemon.services;

import org.springframework.stereotype.Service;
import com.tecnico.lemon.dtos.UserInfo;

@Service
public interface UserService {
    boolean saveUser(UserInfo userinfo);
    boolean lookupUser(String email);

    boolean signupUser(String email) throws  Exception;

    void loginUser(String email) throws  Exception;
}