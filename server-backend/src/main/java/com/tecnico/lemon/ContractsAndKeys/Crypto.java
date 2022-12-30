package com.tecnico.lemon.ContractsAndKeys;
import com.tecnico.lemon.controllers.MobileMessage;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Base64;

public class Crypto {

    public static String encryptAES(String message,SecretKey key){
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
