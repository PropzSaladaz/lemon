package com.tecnico.lemon.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecnico.lemon.ContractsAndKeys.KeyGenerate;
import com.tecnico.lemon.Crypto;
import com.tecnico.lemon.contract.MobileServiceGrpc;
import com.tecnico.lemon.contract.MobileServiceOuterClass;
import com.tecnico.lemon.contract.MobileServiceOuterClass.PasswordRequest;
import com.tecnico.lemon.contract.MobileServiceOuterClass.PasswordResp;
import com.tecnico.lemon.KeyConverter;
import com.tecnico.lemon.KeyReader;
import io.grpc.stub.StreamObserver;
import com.tecnico.lemon.contract.MobileServiceOuterClass.LoginResp;
import com.tecnico.lemon.contract.MobileServiceOuterClass.LoginRequest;

import net.minidev.json.JSONObject;

import javax.crypto.SecretKey;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyPair;
import java.util.Scanner;

public class MobileServiceImpl extends MobileServiceGrpc.MobileServiceImplBase {
    private final String password;
    private final String serverHostname;
    private final KeyPair keys;
    public MobileServiceImpl(String password, String privateKeyPath, String publicKeyPath,
                             String serverHostname) throws Exception {
        this.password = password;
        this.serverHostname = serverHostname;
        this.keys = KeyReader.read(publicKeyPath, privateKeyPath);
    }

    @Override
    public void signup(PasswordRequest request, StreamObserver<PasswordResp> responseObserver) {
        // For test purposes we request password for every action needed
        try {
            requestPassword();
            sendToken();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        responseObserver.onNext(PasswordResp.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void login(LoginRequest request, StreamObserver<LoginResp> responseObserver) {
        // For test purposes we request password for every action needed
        try {
            requestPassword();
            sendPublicKey(request.getMessage());
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

    private void sendToken() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the Token received ");
        String token = scanner.nextLine();
        System.out.println("Token: "  + token);
        System.out.println("Please enter your email ");
        String email = scanner.nextLine();
        System.out.println("Email: "  + email);
        SecretKey secretKey = KeyGenerate.generateKey(token);
        String publicKey = KeyConverter.publicKeyToString(this.keys.getPublic());
        System.out.println("KEY: " + secretKey);
        String encryptedPubKey = Crypto.encryptAES(publicKey,secretKey);
        String encryptedToken = Crypto.encryptAES(token,secretKey);
        System.out.println("TokenPRE: " +encryptedToken);
        HttpResponse<String> resp = sendHTTP("/signup/" + email,createSignupJSON(encryptedToken,encryptedPubKey));

        while (resp.statusCode() != 200) { // TODO change condition based on returned message
            System.out.println("Wrong token or Wrong Email, please enter again.");
            token = scanner.nextLine();
            email = scanner.nextLine();
            System.out.println("Email: "  + email);
            encryptedToken = Crypto.encryptAES(token,secretKey);
            resp = sendHTTP("/signup/" + email,createSignupJSON(encryptedToken,encryptedPubKey));
        }
        System.out.println("Signed up successfully");
    }

    private void sendPublicKey(String loginMessage) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your email ");
        String email = scanner.nextLine();
        System.out.println("Email: "  + email);
        String publicKey = KeyConverter.publicKeyToString(this.keys.getPublic());
        SecretKey sessionKey = Crypto.decryptRSA(loginMessage);
        String encryptedPubKey = Crypto.encryptAES(publicKey,sessionKey);
        String encryptedEmail = Crypto.encryptAES(email,sessionKey);
        HttpResponse<String> resp = sendHTTP("/login",createLoginJSON(encryptedEmail,encryptedPubKey));
        while (resp.statusCode() != 200) { // TODO change condition based on returned message
            System.out.println("Wrong Email, please enter again.");
            email = scanner.nextLine();
            System.out.println("Email: "  + email);
            encryptedEmail = Crypto.encryptAES(email,sessionKey);
            resp = sendHTTP("/login",createLoginJSON(encryptedEmail,encryptedPubKey));
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

    private String createSignupJSON(String token, String publicKey) throws JsonProcessingException { // TODO currently not used
        System.out.println(token);
        MobileMessage mobileMessage = new MobileMessage(publicKey,token);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(mobileMessage);
    }

    private String createLoginJSON(String email, String publicKey) throws JsonProcessingException { // TODO currently not used
        MobileMessageLogin mobileMessage = new MobileMessageLogin(publicKey,email);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(mobileMessage);
    }
}
