package com.tecnico.lemon;

import io.grpc.netty.GrpcSslContexts;
import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;

import javax.net.ssl.SSLException;
import java.io.File;

public class SSLContext {
    public static SslContext forServer(String serverCertPath, String serverPrivKeyPath, String CACertPath) throws SSLException {
        File serverCertificateFile = new File(serverCertPath);
        File privateKeyFile = new File(serverPrivKeyPath);
        File clientCACertificateFile = new File(CACertPath);

        SslContextBuilder contextBuilder = SslContextBuilder.forServer(serverCertificateFile, privateKeyFile)
                .clientAuth(ClientAuth.REQUIRE)
                .trustManager(clientCACertificateFile);

        return GrpcSslContexts.configure(contextBuilder).build();
    }

    public static SslContext forClient(String clientCertPAth, String clientPrivKeyPath, String CACertPath) throws SSLException {
        File clientCertFile = new File(clientCertPAth);
        File clientKeyFile = new File(clientPrivKeyPath);
        File serverCACertFile = new File(CACertPath);

        return GrpcSslContexts.forClient()
                .keyManager(clientCertFile, clientKeyFile)
                .trustManager(serverCACertFile)
                .build();
    }
}
