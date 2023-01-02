package com.tecnico.lemon.database;

import com.tecnico.lemon.SSLContext;
import com.tecnico.lemon.contract.UserTableServiceGrpc;
import com.tecnico.lemon.models.user.User;
import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import io.netty.handler.ssl.SslContext;
import org.springframework.stereotype.Component;

import static com.tecnico.lemon.contract.UserTableServiceOuterClass.*;

@Component
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

    public boolean saveUser(User user) {
        System.out.println("UserFrontend: save user");
        User lookupUser = lookupUser(user.getEmail());
        if(lookupUser == null){
            CreateUserReq request = CreateUserReq.newBuilder()
                    .setEmail(user.getEmail())
                    .setKey(user.getPublicKey())
                    .setType(user.getType())
                    .build();
            stub.createUser(request);
            System.out.println("UserFrontend: req sent!");
            return true;
        }
        return false;
    }

    public User lookupUser(String email) {
        LookupUserResp resp = stub.lookupUser(LookupUserReq.newBuilder().setEmail(email).build());
        if (!resp.getEmail().equals(""))
            return new User(resp.getEmail(), resp.getKey(), resp.getType());
        return null;
    }
}
