package com.tecnico.lemon.services;

import com.tecnico.lemon.contract.UserServiceGrpc;
import com.tecnico.lemon.database.DatabaseManager;
import com.tecnico.lemon.database.Queries;


public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {
    DatabaseManager _db;

    public UserServiceImpl(DatabaseManager db) {
        _db = db;
    }

    @Override
    public void createUser(CreateUserReq request, StreamObserver<CreateUserResp> responseObserver) {
        _db.executeQuery(Queries.insertUser(request.getId(), request.getEmail(), request.getPassword()));
    }
}
