package com.tecnico.lemon.database;

import com.tecnico.lemon.contract.*;
import com.tecnico.lemon.dtos.UserInfoDto;
import com.tecnico.lemon.contract.UserTableServiceGrpc;
import com.tecnico.lemon.dtos.UserInfo;
import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import io.netty.handler.ssl.SslContext;

import static com.tecnico.lemon.contract.UserTableServiceOuterClass.*;


public class UserTableServiceFrontend {

    private final String target = "localhost:8082";
    private final ManagedChannel channel;
    private final UserTableServiceGrpc.UserTableServiceBlockingStub stub;

    public UserTableServiceFrontend() {
        SslContext context = null;
        try {
            String serverCACertFile = "src/main/credentials/ca-cert.pem";
            String clientCertFile = "src/main/credentials/client-cert.pem";
            String clientKeyFile = "src/main/credentials/client-key.pem";
            context = SSLContext.forClient(clientCertFile, clientKeyFile, serverCACertFile);
        } catch (Exception e) {
            System.err.println("Error Loading Credentials");
        }
        channel = NettyChannelBuilder.forTarget(target).sslContext(context).build();
        stub = UserTableServiceGrpc.newBlockingStub(channel);
    }

    public boolean signUpUser(UserInfo userinfo) {
        if (!lookupUser(userinfo.get_email())) {
            CreateUserReq request = CreateUserReq.newBuilder()
                    .setEmail(userinfo.getEmail())
                    .setKey(userinfo.getPublicKey())
                    .setType(userinfo.getType())
                    .build();
            stub.createUser(request);
            return true;
        }
        return false;
    }

    public boolean lookupUser(String email) {
        System.out.println(email);
        LookupUserResp resp = stub.lookupUser(LookupUserReq.newBuilder().setEmail(email).build());
     return resp.getExist();
    }
}
