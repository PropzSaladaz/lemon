package com.tecnico.lemon.services;

import com.tecnico.lemon.contract.UserTableServiceGrpc;
import com.tecnico.lemon.database.DatabaseManager;
import com.tecnico.lemon.database.Queries;
import com.tecnico.lemon.database.Tables;
import io.grpc.stub.StreamObserver;

import java.sql.ResultSet;
import java.sql.SQLException;


import static com.tecnico.lemon.contract.UserTableServiceOuterClass.*;


public class UserTableServiceImpl extends UserTableServiceGrpc.UserTableServiceImplBase {
    DatabaseManager _db;

    public UserTableServiceImpl(DatabaseManager db) {
        _db = db;
    }

    @Override
    public void createUser(CreateUserReq request, StreamObserver<CreateUserResp> responseObserver) {
        _db.executeQuery(Queries.insertUser(request.getId(), request.getEmail(), request.getPassword(), request.getType()));
        responseObserver.onCompleted();
    }

    @Override 
    public void lookupUser(LookupUserReq request, StreamObserver<LookupUserResp> responseObserver) {
        LookupUserResp.Builder resp = LookupUserResp.newBuilder();
        try {
            ResultSet res = _db.executeQuery(Queries.lookupUserByEmail(request.getEmail()));
            if (res != null) {
                resp.setId(res.getInt(Tables.User.ID));
                resp.setEmail(res.getString(Tables.User.EMAIL));
                resp.setPassword(res.getString(Tables.User.PASSWORD));
                resp.setType(res.getString(Tables.User.TYPE));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        responseObserver.onNext(resp.build());
        responseObserver.onCompleted();
    }
}
