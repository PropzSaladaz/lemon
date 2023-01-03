package com.tecnico.lemon.services;

import com.google.protobuf.ByteString;
import com.tecnico.lemon.*;
import com.tecnico.lemon.controllers.JavaMailUtil;
import com.tecnico.lemon.database.DataBase;
import com.tecnico.lemon.database.UserTableServiceFrontend;
import com.tecnico.lemon.models.user.User;
import com.tecnico.lemon.models.user.UserLogin;
import com.tecnico.lemon.models.user.UserSignup;
import com.tecnico.lemon.mobile.MobileFrontend;
import com.tecnico.lemon.auth.SignupLoginQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.PrivateKey;
import java.security.PublicKey;

import static com.tecnico.lemon.contract.MobileServiceOuterClass.*;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    DataBase dBase;
    @Autowired
    UserTableServiceFrontend userRepo;
    @Autowired
    SignupLoginQueue queue;
    @Autowired
    MobileFrontend mobileFrontend;

    @Override
    public boolean saveUser(User user) {
        return userRepo.saveUser(user);
    }

    @Override
    public User lookupUser(String email) {
        return userRepo.lookupUser(email);
    }

    @Override
    public boolean signupUser(String email) throws Exception {
        String token = TokenGenerator.generateRandom(8);
        // Create signup user
        UserSignup signupUser = new UserSignup(email, KeyGenerate.generateKey(token), token);
        // send email & password request
        queue.getSignupQueue().add(email, signupUser);
        JavaMailUtil.sendEmail(token,email);
        mobileFrontend.signup();
        // wait for response
        if (queue.getSignupQueue().waitForAuthentication(email, 120)) {
            User user = new User(queue.getSignupQueue().get(email));
            saveUser(user);
            queue.getSignupQueue().remove(email);
            return true;
        }
        queue.getSignupQueue().remove(email);
        return false;
    }

    @Override
    public boolean loginUser(String email) throws Exception {
        User user = lookupUser(email);
        SecretKey sessionKey = KeyGenerate.generateKey(TokenGenerator.generateRandom(8));
        UserLogin userLogin = new UserLogin(sessionKey);

        queue.getLoginQueue().add(email, userLogin);
        LoginReq req = buildEncryptedLoginReq(user, sessionKey);
        mobileFrontend.login(req);

        if (queue.getLoginQueue().waitForAuthentication(email, 60)) {
            queue.getLoginQueue().remove(email);
            return true;
        }
        queue.getLoginQueue().remove(email);
        return false;
    }

    private LoginReq buildEncryptedLoginReq(User user, SecretKey sessionKey) throws Exception {
        PublicKey userKey = KeyConverter.base64ToPublicKey(user.getPublicKey());
        PrivateKey serverKey = RSAKeyReader.readPrivate("src/main/credentials/backend-private.der");

        byte[] sessionKeyBytes = sessionKey.getEncoded();
        byte[] userEncryptedSessionKey = Crypto.encryptRSAPublic(sessionKeyBytes, userKey);
        byte[] digest = Crypto.digest(userEncryptedSessionKey);
        byte[] encryptedDigest = Crypto.encryptRSAPrivate(digest, serverKey);

        return LoginReq.newBuilder()
                .setEncryptedKey(ByteString.copyFrom(userEncryptedSessionKey))
                .setDigest(ByteString.copyFrom(encryptedDigest))
                .build();
    }
}