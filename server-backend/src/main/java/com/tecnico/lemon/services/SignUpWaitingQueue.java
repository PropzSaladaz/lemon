package com.tecnico.lemon.services;

import org.springframework.stereotype.Service;
import com.tecnico.lemon.dtos.UserInfo;

import java.util.HashMap;
import java.util.Map;

@Service
public class SignUpWaitingQueue {
    private Map<String, UserInfo> map;

    public SignUpWaitingQueue() {
        map = new HashMap<>();
    }

    public Map<String, UserInfo> getMap() {
        return map;
    }

    public void addUser(String email, UserInfo userinfo){
        map.put(email,userinfo);
    }
    public void setUserPublicKey(String email, String publicKey){
        map.get(email).setPublicKey(publicKey);
    }

    public UserInfo getInfo(String email) {
        return map.get(email);
    }

    public String getToken(String email) { return map.get(email).getToken();}

    public boolean userHasToken(String email,String token){
        return map.get(email).getToken().equals(token);
    }

    public void removeToken(String email) { map.remove(email);}

    public boolean userHasKey(String email) {
        return getInfo(email).getPublicKey() != null;
    }

}