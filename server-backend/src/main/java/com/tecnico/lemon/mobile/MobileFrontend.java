package com.tecnico.lemon.mobile;

import com.tecnico.lemon.SSLContext;
import com.tecnico.lemon.contract.MobileServiceGrpc;
import com.tecnico.lemon.contract.MobileServiceOuterClass;
import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import io.netty.handler.ssl.SslContext;
import org.springframework.stereotype.Component;

import static com.tecnico.lemon.contract.MobileServiceOuterClass.*;

@Component
public class MobileFrontend {
    private final String target = "192.168.2.3:8080";
    private final ManagedChannel channel;
    private final MobileServiceGrpc.MobileServiceBlockingStub stub;

    public MobileFrontend() {
        SslContext context = null;
        try {
            String clientCertFile = "src/main/credentials/mobile-client-cert.pem";
            String privateKeyFile = "src/main/credentials/mobile-client-key.pem";
            String caCert         = "src/main/credentials/ca-cert.pem";
            context = SSLContext.forClient(clientCertFile, privateKeyFile, caCert);
        } catch (Exception e) {
            System.err.println("Error Loading Credentials");
        }
        channel = NettyChannelBuilder.forTarget(target).sslContext(context).build();
        stub = MobileServiceGrpc.newBlockingStub(channel);
    }

    public void signup() {
        PasswordResp resp = stub.signup(PasswordRequest.newBuilder().build());
    }

    public void login(LoginReq request) {
        LoginResp  resp = stub.login(request);
    }
}
