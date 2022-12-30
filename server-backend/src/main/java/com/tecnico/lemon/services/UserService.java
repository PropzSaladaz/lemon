package com.tecnico.lemon.services;

import org.springframework.stereotype.Service;
import com.tecnico.lemon.dtos.UserInfo;
import com.tecnico.lemon.dtos.UserInfoDto;

@Service
public interface UserService {
    boolean signupUser(UserInfo email);
    boolean lookupUser(String email);
}