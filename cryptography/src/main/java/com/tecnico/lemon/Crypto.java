package com.tecnico.lemon;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.util.Base64;

public class Crypto {

    public static String encryptAES(String message, SecretKey key){
        try{
            System.out.println("OLA");
            System.out.println("OLAZAZA");
            byte[] plainBytes = message.getBytes();
            final String CIPHER_ALGO = "AES/ECB/PKCS5Padding";
            Cipher cipher = Cipher.getInstance(CIPHER_ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            System.out.println("OLA2");
            byte[] cipherBytes = cipher.doFinal(plainBytes);
            System.out.println("OLA1");
            return Base64.getEncoder().encodeToString(cipherBytes);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }

    }

    public static String decryptAES(String message,SecretKey key){
        try{
            byte[] decodedBytes = Base64.getDecoder().decode(message);
            final String CIPHER_ALGO = "AES/ECB/PKCS5Padding";
            Cipher cipher = Cipher.getInstance(CIPHER_ALGO);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] cipherBytes = cipher.doFinal(decodedBytes);
            return String.valueOf(cipherBytes);
        }catch(Exception e){
            throw new RuntimeException("ERROR WHILE DECRYPTING");
        }
    }

}