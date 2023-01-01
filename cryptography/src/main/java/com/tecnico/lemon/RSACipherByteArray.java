package com.tecnico.lemon;

import javax.crypto.Cipher;
import java.security.Key;

public class RSACipherByteArray {

    private Key key;
    private int opmode;

    public RSACipherByteArray(int opmode, Key key) {
        this.opmode = opmode;
        this.key = key;
    }
    public byte[] cipher(byte[] byteArray) {

        try {
            Cipher cipher =   Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(this.opmode, key);
            // string initialize 2 buffers.
            // scrambled will hold intermediate results
            byte[] scrambled = new byte[0];

            // toReturn will hold the total result
            byte[] toReturn = new byte[0];
            // if we encrypt we use 100 byte long blocks. Decryption requires 128 byte long blocks (because of RSA)
            int length = (opmode == Cipher.ENCRYPT_MODE)? 100 : 128;
            System.out.println(length);
            System.out.println(byteArray.length);

            // another buffer. this one will hold the bytes that have to be modified in this step
            byte[] buffer = new byte[length];

            for (int i=0; i< byteArray.length; i++){

                // if we filled our buffer array we have our block ready for de- or encryption
                if ((i > 0) && (i % length == 0)){
                    //execute the operation
                    scrambled = cipher.doFinal(buffer);
                    // add the result to our total result.
                    toReturn = append(toReturn,scrambled);
                    System.out.println("toReturn lenght:" + toReturn.length);
                    // here we calculate the length of the next buffer required
                    int newlength = length;

                    // if newlength would be longer than remaining bytes in the bytes array we shorten it.
                    if (i + length > byteArray.length) {
                        newlength = byteArray.length - i;
                    }
                    // clean the buffer array
                    buffer = new byte[newlength];
                }
                // copy byte into our buffer.
                buffer[i%length] = byteArray[i];
            }

            // this step is needed if we had a trailing buffer. should only happen when encrypting.
            // example: we encrypt 110 bytes. 100 bytes per run means we "forgot" the last 10 bytes. they are in the buffer array
            scrambled = cipher.doFinal(buffer);

            // final step before we can return the modified data.
            toReturn = append(toReturn,scrambled);

            return toReturn;

        } catch (Exception e) {
            // Pokemon exception handling!
            e.printStackTrace();
        }

        return null;

    }

    private byte[] append(byte[] prefix, byte[] suffix){
        byte[] toReturn = new byte[prefix.length + suffix.length];
        for (int i=0; i< prefix.length; i++){
            toReturn[i] = prefix[i];
        }
        for (int i=0; i< suffix.length; i++){
            toReturn[i+prefix.length] = suffix[i];
        }
        return toReturn;
    }
}
