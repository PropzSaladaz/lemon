package com.tecnico.lemon;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

public class Crypto {

    public static String encryptAES(String message, SecretKey key){
        try{
            byte[] plainBytes = message.getBytes();
            final String CIPHER_ALGO = "AES/ECB/PKCS5Padding";
            Cipher cipher = Cipher.getInstance(CIPHER_ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] cipherBytes = cipher.doFinal(plainBytes);
            return Base64.getEncoder().encodeToString(cipherBytes);
        }catch(Exception e){
            throw new RuntimeException("ERROR WHILE ENCRYPTING");
        }

    }

    public static String decryptAES(String message, SecretKey key){
        try{
            byte[] decodedBytes = Base64.getDecoder().decode(message);
            final String CIPHER_ALGO = "AES/ECB/PKCS5Padding";
            Cipher cipher = Cipher.getInstance(CIPHER_ALGO);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] cipherBytes = cipher.doFinal(decodedBytes);
            return new String(cipherBytes, StandardCharsets.UTF_8);
        }catch(Exception e){
            throw new RuntimeException("ERROR WHILE DECRYPTING");
        }
    }


    public static byte[] encryptRSA(byte[] plainBytes, Key key){
        RSACipherByteArray cipher = new RSACipherByteArray(Cipher.ENCRYPT_MODE, key);
        return cipher.cipher(plainBytes);
    }

    public static byte[] RSADecryptBase64(byte[] plainBytes, Key key) {
        RSACipherByteArray cipher = new RSACipherByteArray(Cipher.DECRYPT_MODE, key);
        return cipher.cipher(plainBytes);
    }

}