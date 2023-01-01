package com.tecnico.lemon;

import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;

public class KeyReader {
    public static KeyPair read(String publicKeyPath, String privateKeyPath) throws Exception {
        System.out.println("Reading public key from file " + publicKeyPath + " ...");
        FileInputStream pubFis = new FileInputStream(publicKeyPath);
        byte[] pubEncoded = new byte[pubFis.available()];
        pubFis.read(pubEncoded);
        pubFis.close();

        X509EncodedKeySpec pubSpec = new X509EncodedKeySpec(pubEncoded);
        KeyFactory keyFacPub = KeyFactory.getInstance("RSA");
        PublicKey pub = keyFacPub.generatePublic(pubSpec);

        System.out.println("Reading private key from file " + privateKeyPath + " ...");
        FileInputStream privFis = new FileInputStream(privateKeyPath);
        byte[] privEncoded = new byte[privFis.available()];
        privFis.read(privEncoded);
        privFis.close();

        PKCS8EncodedKeySpec privSpec = new PKCS8EncodedKeySpec(privEncoded);
        KeyFactory keyFacPriv = KeyFactory.getInstance("RSA");
        PrivateKey priv = keyFacPriv.generatePrivate(privSpec);

        KeyPair keys = new KeyPair(pub, priv);
        return keys;
    }

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
