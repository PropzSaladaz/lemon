package com.tecnico.lemon.services;

import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import com.tecnico.lemon.dtos.UserInfo;

import java.util.HashMap;
import java.util.Map;

@Service
public class SignUpRepository {
    private Map<String, UserInfo> map;

    public SignUpRepository() {
        map = new HashMap<>();
    }

    public Map<String, UserInfo> getMap() {
        return map;
    }

    public void putMap(String email, UserInfo userinfo){
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

}