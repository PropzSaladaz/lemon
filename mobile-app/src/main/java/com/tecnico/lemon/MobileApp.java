package com.tecnico.lemon;

import com.tecnico.lemon.services.MobileServiceImpl;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.netty.NettyServerBuilder;
import io.netty.handler.ssl.SslContext;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.Scanner;

public class MobileApp {
    private final static String EMPLOYER = "E";
    private final static String CUSTOMER = "C";
    private static String password;
    private static String pubKeyPath;
    private static String privKeyPath;
    private static String serverHostname;
    private static int clientPort;
    public static void main(String[] args) throws Exception {

        Server server;
        System.out.println( "Starting Mobile App..." );
        Scanner scanner = new Scanner(System.in);
        System.out.println( "Enter user type (E (employer) or C (customer):" );
        String type = scanner.nextLine();
        // Populate static attributes with values from properties file
        loadProperties(type);

        // Create Grpc Server
        final BindableService mobileService = new MobileServiceImpl(password, privKeyPath, pubKeyPath, serverHostname);
        String serverCertFile = "src/main/credentials/mobile-server-cert.pem";
        String privateKeyFile = "src/main/credentials/mobile-server-key.pem";
        String caCert         = "src/main/credentials/ca-cert.pem";
        SslContext context = SSLContext.forServer(serverCertFile, privateKeyFile, caCert);
        server = NettyServerBuilder.forPort(clientPort)
                .sslContext(context)
                .addService(mobileService)
                .build();

        server.start();
        System.out.println("grpc open and waiting for connections");
        server.awaitTermination();
    }

    private static void loadProperties(String type) {
        Properties config = new Properties();
        try {
            config.load(new FileInputStream("src/main/resources/config.properties"));
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        String pathPrefix = "";
        if (type.equals(EMPLOYER)) pathPrefix = "emp-";
        password = config.getProperty("client-password");
        pubKeyPath = config.getProperty(pathPrefix + "pub-key-path");
        privKeyPath = config.getProperty(pathPrefix + "priv-key-path");
        serverHostname = config.getProperty("server-hostname") + ":" + config.getProperty("server-port");
        clientPort = Integer.parseInt(config.getProperty("client-port"));
    }
}