package com.tecnico.lemon;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class KeyConverter {
    public static String publicKeyToString(PublicKey pub) {
        return Base64.getEncoder().encodeToString(pub.getEncoded());
    }

    public static PublicKey stringToPublicKey(String pub) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(pub);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }
}
