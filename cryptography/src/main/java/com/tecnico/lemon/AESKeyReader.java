package com.tecnico.lemon;

import javax.crypto.spec.SecretKeySpec;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import java.io.FileInputStream;

public class AESKeyReader {

    public static SecretKey readSharedKey(String keyPath) throws Exception {
        FileInputStream keyIn = new FileInputStream(keyPath);
        byte[] keyBytes = new byte[keyIn.available()];
        keyIn.read(keyBytes);
        keyIn.close();

        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("AES");
        return new SecretKeySpec(keyFactory.generateSecret(keySpec).getEncoded(), "AES");
    }
}
