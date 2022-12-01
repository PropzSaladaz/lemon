package com.tecnico.lemon.services;

import com.tecnico.lemon.contract.UserTableServiceGrpc;
import com.tecnico.lemon.database.DatabaseManager;
import com.tecnico.lemon.database.Queries;


public class UserTableServiceImpl extends UserTableServiceGrpc.UserTableServiceImplBase {
    DatabaseManager _db;

    public UserTableServiceImpl(DatabaseManager db) {
        _db = db;
    }

    @Override
    public void createUser(CreateUserReq request, StreamObserver<CreateUserResp> responseObserver) {
        _db.executeQuery(Queries.insertUser(request.getId(), request.getEmail(), request.getPassword(), request.getType()));
    }
}
