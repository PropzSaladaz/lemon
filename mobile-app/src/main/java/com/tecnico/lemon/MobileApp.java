package com.tecnico.lemon;

import com.tecnico.lemon.services.MobileServiceImpl;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.netty.NettyServerBuilder;
import io.netty.handler.ssl.SslContext;

import java.io.FileInputStream;
import java.util.Properties;

public class MobileApp {
    private static String password;
    private static String pubKeyPath;
    private static String privKeyPath;
    private static String serverHostname;
    private static int clientPort;
    public static void main(String[] args) throws Exception {
        Server server;
        System.out.println( "Starting Mobile App..." );

        // Populate static attributes with values from properties file
        loadProperties();

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

    private static void loadProperties() {
        Properties config = new Properties();
        try {
            config.load(new FileInputStream("src/main/resources/config.properties"));
        }catch(Exception ex) {
            ex.printStackTrace();
        }

        password = config.getProperty("client-password");
        pubKeyPath = config.getProperty("pub-key-path");
        privKeyPath = config.getProperty("priv-key-path");
        serverHostname = config.getProperty("server-hostname") + ":" + config.getProperty("server-port");
        clientPort = Integer.parseInt(config.getProperty("client-port"));
    }
}