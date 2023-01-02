package com.tecnico.lemon.services;

import com.tecnico.lemon.models.user.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    boolean saveUser(User user);
    User lookupUser(String email);

    boolean signupUser(String email) throws  Exception;

    boolean loginUser(String email) throws  Exception;
}