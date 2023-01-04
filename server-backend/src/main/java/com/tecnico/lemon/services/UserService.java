package com.tecnico.lemon.services;

import com.tecnico.lemon.models.user.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    boolean saveUser(User user);
    User lookupUser(String email);

    String signupUser(String email) throws  Exception;

    String loginUser(String email) throws  Exception;
}