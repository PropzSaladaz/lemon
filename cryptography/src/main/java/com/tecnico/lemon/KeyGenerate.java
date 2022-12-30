package com.tecnico.lemon;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class KeyGenerate {

    public static SecretKey generateKey(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        char[] password = token.toCharArray();
        byte[] salt = { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08 };
        int iterationCount = 1000;
        int keyLength = 128;
        PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterationCount, keyLength);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("AES");
        SecretKey key = keyFactory.generateSecret(keySpec);
        return key;
    }
}