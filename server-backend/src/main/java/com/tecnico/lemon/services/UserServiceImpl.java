package com.tecnico.lemon.services;

import com.tecnico.lemon.ContractsAndKeys.KeyGenerate;
import com.tecnico.lemon.TokenGenerator;
import com.tecnico.lemon.controllers.JavaMailUtil;
import com.tecnico.lemon.database.DataBase;
import com.tecnico.lemon.dtos.UserInfo;
import com.tecnico.lemon.mobile.MobileFrontend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    DataBase dBase;
    @Autowired
    SignUpWaitingQueue signUpWaitingQueue;
    @Autowired
    MobileFrontend mobileFrontend = new MobileFrontend();

    @Override
    public boolean saveUser(UserInfo userinfo) {
        return dBase.saveUser(userinfo);
    }

    @Override
    public boolean lookupUser(String email) {
        return dBase.lookupUser(email);
    }

    @Override
    public boolean signupUser(String email) throws Exception {
        UserInfo user = new UserInfo(email);
        String token = TokenGenerator.generateRandom(8);
        user.setSecretKey(KeyGenerate.generateKey(token));
        user.setToken(token);

        signUpWaitingQueue.addUser(email,user);
        JavaMailUtil.sendEmail(token,email);
        mobileFrontend.signup();
        if (waitOnUserKey(email)) {
            saveUser(signUpWaitingQueue.getInfo(email));
            signUpWaitingQueue.removeToken(email);
            return true;
        }
        else {
            signUpWaitingQueue.removeToken(email);
            return false;
        }
    }

    @Override
    public void loginUser(String email) throws Exception {

    }

    private boolean waitOnUserKey(String email) {
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0;
        long millis = 600000; // 10 minutes in milliseconds
        while (elapsedTime < millis) {
            elapsedTime = System.currentTimeMillis() - startTime;
            if (signUpWaitingQueue.userHasKey(email)) {
                return true;
            }
            System.out.println("Waiting 1 minute...");
            try {
                Thread.sleep(60000); // Sleep for 1 minute (60 seconds)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}