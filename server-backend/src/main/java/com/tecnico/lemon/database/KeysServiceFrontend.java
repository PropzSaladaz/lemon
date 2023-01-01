package com.tecnico.lemon.database;

import com.tecnico.lemon.contract.*;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyChannelBuilder;
import io.netty.handler.ssl.SslContext;
import org.springframework.stereotype.Service;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.*;
import io.grpc.StatusRuntimeException;

import javax.net.ssl.SSLException;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import static com.tecnico.lemon.contract.KeysServiceOuterClass.*;


public class KeysServiceFrontend {

    private final String target = "localhost:8082";
    private final ManagedChannel channel;
    private final KeysServiceGrpc.KeysServiceBlockingStub stub;

    public SslContext loadTLSCredentials() throws SSLException {
        File serverCACertFile = new File("src/main/credentials/ca-cert.pem");
        File clientCertFile = new File("src/main/credentials/client-cert.pem");
        File clientKeyFile = new File("src/main/credentials/client-key.pem");

        return GrpcSslContexts.forClient()
                .keyManager(clientCertFile, clientKeyFile)
                .trustManager(serverCACertFile)
                .build();
    }

    public KeysServiceFrontend() {
        SslContext context = null;
        try {
            context = loadTLSCredentials();
        } catch (Exception e) {
            System.err.println("Error Loading Credentials");
        }
        channel = NettyChannelBuilder.forTarget(target).sslContext(context).build();
        stub = KeysServiceGrpc.newBlockingStub(channel);
    }

    public String requestSharedKey() {
        return stub.requestSharedKey(RequestSharedKeyReq.getDefaultInstance()).getSharedKey();
    }
}
