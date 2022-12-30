package com.tecnico.lemon.ContractsAndKeys;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class TokenGenerate {

    public static String generateToken(int length){
            SecureRandom random = new SecureRandom();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < length; i++) {
                sb.append((char)('a' + random.nextInt(26)));
            }
            return sb.toString();
        }
}
