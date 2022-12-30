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

    public static MobileMessage decryptAES(String message,SecretKey key){
        try{
            byte[] decodedBytes = Base64.getDecoder().decode(message);
            final String CIPHER_ALGO = "AES/ECB/PKCS5Padding";
            Cipher cipher = Cipher.getInstance(CIPHER_ALGO);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] cipherBytes = cipher.doFinal(decodedBytes);
            return convertToMessage(cipherBytes);
        }catch(Exception e){
            throw new RuntimeException("ERROR WHILE DECRYPTING");
        }
    }

    public static MobileMessage convertToMessage(byte[] cyperBytes) throws IOException, ClassNotFoundException {
        // Create the array of bytes to be decoded

        // Create a ByteArrayInputStream from the array of bytes
        ByteArrayInputStream bais = new ByteArrayInputStream(cyperBytes);

        // Create an ObjectInputStream from the ByteArrayInputStream
        ObjectInputStream ois = new ObjectInputStream(bais);

        // Read the object from the ObjectInputStream and cast it to the desired class
        MobileMessage obj = (MobileMessage) ois.readObject();

        // Close the streams
        ois.close();
        bais.close();
        return obj;
    }
}
