package com.tecnico.lemon.ContractsAndKeys;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class KeyGenerate {

    public static SecretKey generateKey(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        char[] password = token.toCharArray();
        byte[] salt = { 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08 };
        int iterationCount = 1000;
        int keyLength = 128;
        PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterationCount, keyLength);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        SecretKey key = keyFactory.generateSecret(keySpec);
        return key;
    }
}
