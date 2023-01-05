package com.tecnico.lemon.services;

import com.tecnico.lemon.Crypto;
import com.tecnico.lemon.KeyConverter;
import com.tecnico.lemon.KeyGenerate;
import com.tecnico.lemon.RSAKeyReader;
import com.tecnico.lemon.contract.MobileServiceGrpc;
import com.tecnico.lemon.contract.MobileServiceOuterClass;
import com.tecnico.lemon.contract.MobileServiceOuterClass.LoginResp;
import com.tecnico.lemon.contract.MobileServiceOuterClass.PasswordRequest;
import com.tecnico.lemon.contract.MobileServiceOuterClass.PasswordResp;
import io.grpc.stub.StreamObserver;
import net.minidev.json.JSONObject;
import org.apache.commons.codec.digest.Crypt;

import javax.crypto.SecretKey;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Scanner;

import static com.tecnico.lemon.contract.MobileServiceOuterClass.*;

public class MobileServiceImpl extends MobileServiceGrpc.MobileServiceImplBase {
    private final String password;
    private String email = "admin@gmail.com";
    private final String serverHostname;
    private final KeyPair keys;
    public MobileServiceImpl(String password, String privateKeyPath, String publicKeyPath,
                             String serverHostname) throws Exception {
        this.password = password;
        this.serverHostname = serverHostname;
        this.keys = RSAKeyReader.read(publicKeyPath, privateKeyPath);
    }

    @Override
    public void signup(PasswordRequest request, StreamObserver<PasswordResp> responseObserver) {
        // For test purposes we request password for every action needed
        try {
            requestPassword();
            signup();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        responseObserver.onNext(PasswordResp.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void login(LoginReq request, StreamObserver<LoginResp> responseObserver) {
        // For test purposes we request password for every action needed
        try {
            requestPassword();
            SecretKey key = getSymmetricKey(request);
            login(key);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        responseObserver.onNext(LoginResp.newBuilder().build());
        responseObserver.onCompleted();
    }

    private void requestPassword() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        int counter = 0;

        System.out.println("Enter your password: ");
        String password = scanner.nextLine();
        while(!password.equals(this.password)) {
            counter++;
            if (counter > 2) {
                System.out.println("Too many tries. Try again later (5s)");
                Thread.sleep(5000);
                counter = 0;
            }
            else {
                System.out.println("Wrong password, please try again:");
            }
            password = scanner.nextLine();
        }
        System.out.println("Correct password!");
    }

    private void signup() throws Exception {
        Scanner scanner = new Scanner(System.in);

        String token = requestToken(scanner);
        email = requestEmail(scanner);
        SecretKey secret = KeyGenerate.generateKey(token);
        String publicKey = KeyConverter.keyToString(this.keys.getPublic());
        String signupReq = createEncryptedSignupJSON(email, token, publicKey, secret);

        HttpResponse<String> resp = sendHTTP("/mobile/signup", signupReq);

        while (resp.statusCode() != 200) { // TODO change condition based on returned message
            System.out.println("Wrong token or Wrong Email, please enter again.");
            token = requestToken(scanner);
            email = requestEmail(scanner);
            signupReq = createEncryptedSignupJSON(email, token, publicKey, secret);
            resp = sendHTTP("/mobile/signup", signupReq);
        }
        System.out.println("Signed up successfully");
    }

    private void login(SecretKey secret) throws Exception {
        Scanner scanner = new Scanner(System.in);

        String publicKey = KeyConverter.keyToString(this.keys.getPublic());
        String loginReq = createEncryptedLoginJSON(email, publicKey, secret);

        HttpResponse<String> resp = sendHTTP("/mobile/login", loginReq);

        while (resp.statusCode() != 200) { // TODO change condition based on returned message
            System.out.println("Wrong Email.");
            email = requestEmail(scanner);
            loginReq = createEncryptedLoginJSON(email, publicKey, secret);
            resp = sendHTTP("/mobile/login", loginReq);
        }
        System.out.println("Logged in successfully");
    }

    private HttpResponse<String> sendHTTP(String path,String object) throws Exception {
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder()
                .uri(new URI("https://" + serverHostname + path))
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(object))
                .build();
        HttpClient client = HttpClient.newHttpClient();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private String createEncryptedSignupJSON(String email, String token, String publicKey, SecretKey secret) {
        JSONObject signupRequest = new JSONObject();
        String encryptedPubKey = Crypto.encryptAES(publicKey, secret);
        String encryptedToken = Crypto.encryptAES(token, secret);
        signupRequest.put("email", email);
        signupRequest.put("publicKey", encryptedPubKey);
        signupRequest.put("token", encryptedToken);
        return signupRequest.toJSONString();
    }

    private String createEncryptedLoginJSON(String email, String publicKey, SecretKey secret) {
        JSONObject signupRequest = new JSONObject();
        String encryptedPubKey = Crypto.encryptAES(publicKey, secret);
        signupRequest.put("email", email);
        signupRequest.put("publicKey", encryptedPubKey);
        return signupRequest.toJSONString();
    }

    private String requestToken(Scanner sc) {
        System.out.println("Please enter the Token received: ");
        return sc.nextLine();
    }

    private String requestEmail(Scanner sc) {
        System.out.println("Please enter your e-mail: ");
        return sc.nextLine();
    }

    private SecretKey getSymmetricKey(LoginReq request) throws Exception {
        // Get message fields
        byte[] encryptedKey = request.getEncryptedKey().toByteArray();
        byte[] receivedDigest = request.getDigest().toByteArray();
        // decrypt message
        PrivateKey key = keys.getPrivate();
        PublicKey serverKey = RSAKeyReader.readPublic("src/main/credentials/backend-public.der");
        byte[] decryptedKey = Crypto.decryptRSAPrivate(encryptedKey, key);
        byte[] decryptedDigest = Crypto.decryptRSAPublic(receivedDigest, serverKey);
        // check for integrity
        byte[] digest = Crypto.digest(encryptedKey);
        if (Arrays.equals(digest, decryptedDigest)) {
            return KeyConverter.bytesToSecretKey(decryptedKey);
        } else {
            throw new Exception("Login message was tampered or corrupted.");
        }

    }
}
