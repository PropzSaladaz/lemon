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

    public void putMap(String token, UserInfo userinfo){
        map.put(token,userinfo);
    }
    public void changePublicKey(String token, String publicKey){
        map.get(token).set_publicKey(publicKey);
    }

    public UserInfo getInfo(String token) {
        return map.get(token);
    }

    public boolean containsToken(String token){
        return map.containsKey(token);
    }

    public void removeToken(String token) { map.remove(token);}

}