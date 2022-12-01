package com.tecnico.lemon.database;

import com.tecnico.lemon.contract.*;
import com.tecnico.lemon.database.DataBase;
import com.tecnico.lemon.dtos.UserInfoDto;

import org.springframework.stereotype.Service;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.*;
import io.grpc.StatusRuntimeException;

import com.tecnico.lemon.models.user.UserForm;

import java.util.List;
import java.util.stream.Collectors;

import static com.tecnico.lemon.contract.UserTableServiceOuterClass.*;


public class UserTableServiceFrontend {

    private final String target = "localhost:";
    private final ManagedChannel channel;
    private final UserTableServiceGrpc.UserTableServiceBlockingStub stub;

    public UserTableServiceFrontend() {

        channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
        stub = UserTableServiceGrpc.newBlockingStub(channel);
    }

    public void createUser(UserForm userForm, int id) {

        CreateUserReq request = CreateUserReq.newBuilder()
            .setId(id)
            .setEmail(userForm.get_email())
            .setPassword(userForm.get_password())
            .setType(userForm.get_type())
            .build();
        CreateUserResp resp = stub.createUser(request);
    }

    public UserInfoDto lookupUser(String email) {
        LookupUserResp resp = stub.lookupUser(LookupUserReq.newBuilder().setEmail(email).build());
        return new UserInfoDto(resp.getId(), resp.getEmail(), resp.getPassword(), resp.getType());
    }
}
