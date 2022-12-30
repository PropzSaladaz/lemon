package com.tecnico.lemon.database;
import com.tecnico.lemon.contract.*;
import com.tecnico.lemon.database.DataBase;
import com.tecnico.lemon.dtos.UserInfoDto;
import com.tecnico.lemon.dtos.UserInfo;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyChannelBuilder;
import io.netty.handler.ssl.SslContext;
import org.springframework.stereotype.Service;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.*;
import io.grpc.StatusRuntimeException;

import com.tecnico.lemon.models.user.UserForm;

import javax.net.ssl.SSLException;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import static com.tecnico.lemon.contract.UserTableServiceOuterClass.*;


public class UserTableServiceFrontend {

    private final String target = "localhost:8082";
    private final ManagedChannel channel;
    private final UserTableServiceGrpc.UserTableServiceBlockingStub stub;

    public SslContext loadTLSCredentials() throws SSLException {
        File serverCACertFile = new File("src/main/credentials/ca-cert.pem");
        File clientCertFile = new File("src/main/credentials/client-cert.pem");
        File clientKeyFile = new File("src/main/credentials/client-key.pem");

        return GrpcSslContexts.forClient()
                .keyManager(clientCertFile, clientKeyFile)
                .trustManager(serverCACertFile)
                .build();
    }

    public UserTableServiceFrontend() {
        SslContext context = null;
        try {
            context = loadTLSCredentials();
        } catch (Exception e) {
            System.err.println("Error Loading Credentials");
        }
        channel = NettyChannelBuilder.forTarget(target).sslContext(context).build();
        stub = UserTableServiceGrpc.newBlockingStub(channel);
    }

    public boolean signUpUser(UserInfo userinfo) {
        if(!lookupUser(userinfo.get_email())){
            CreateUserReq request = CreateUserReq.newBuilder()
                    .setEmail(userinfo.get_email())
                    .setKey(userinfo.get_publicKey())
                    .setType(userinfo.get_type())
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
