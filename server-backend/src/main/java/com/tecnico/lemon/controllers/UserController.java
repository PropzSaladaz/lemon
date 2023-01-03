package com.tecnico.lemon.controllers;

import com.tecnico.lemon.dtos.UserInfo;
import com.tecnico.lemon.mobile.MobileFrontend;
import com.tecnico.lemon.services.SignUpRepository;
import com.tecnico.lemon.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import com.tecnico.lemon.KeyReader;
import com.tecnico.lemon.KeyGenerate;
import java.util.Base64;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping(value="/login")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value="/{email}")
    public ResponseEntity<String> loginUser(@PathVariable("email") String email) throws InvalidKeySpecException, NoSuchAlgorithmException {
        System.out.println("Logging in with " + email);
        String token = "NULL";
        try {
            SecretKey key = KeyReader.readSharedKey("src/main/credentials/shared-key.bin");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            String encrypted_email = Base64.getEncoder().encodeToString(cipher.doFinal(email.getBytes(StandardCharsets.UTF_8)));
            System.out.println("Encrypted email: " + encrypted_email);

            // Email does not exist -> Signup
            if (!userService.lookupUser(encrypted_email)) {
                UserInfo info = new UserInfo(encrypted_email);
                token = TokenGenerate.generateToken(8);
                System.out.println(token);
                info.set_secretKey(KeyGenerate.generateKey(token));
                repository.putMap(token,info);
                JavaMailUtil.sendEmail(token, email);
                mobileFrontend.signup();
            }
            // Email already exists -> Login
            else {
                // Prompt the password for the user with this email
                // Send a session key encrypted with his public key (?)
            }
            long startTime = System.currentTimeMillis();
            long elapsedTime = 0;
            long millis = 600000; // 10 minutes in milliseconds
            while (elapsedTime < millis) {
                elapsedTime = System.currentTimeMillis() - startTime;
                if (token != "NULL" && repository.getInfo(token).get_publicKey() != null) {
                    return new ResponseEntity<>("Signed up Successfuly", HttpStatus.OK);
                }
                else if (true /* Condition for havin logged in */) {
                    // Code for login
                    return new ResponseEntity<>("Logged in Successfuly", HttpStatus.OK);
                }
                System.out.println("Waiting 10 minutes...");
                try {
                    Thread.sleep(60000); // Sleep for 1 minute (60 seconds)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (elapsedTime >= millis) {
                repository.removeToken(token);
            }
            return ResponseEntity.badRequest().body("Already Signed Up");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Exception occured");
        }
    }
}
