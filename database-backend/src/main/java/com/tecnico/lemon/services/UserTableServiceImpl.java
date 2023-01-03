package com.tecnico.lemon.services;
import com.tecnico.lemon.contract.UserTableServiceGrpc;
import com.tecnico.lemon.contract.VehicleTableServiceOuterClass;
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
        _db.executeQuery(Queries.insertUser(request.getKey(), _db.encrypt(request.getEmail()), request.getType()));
        responseObserver.onNext(CreateUserResp.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override 
    public void lookupUser(LookupUserReq request, StreamObserver<LookupUserResp> responseObserver){
        LookupUserResp.Builder resp = LookupUserResp.newBuilder();
        ResultSet res = _db.executeQuery(Queries.lookupUserByEmail(_db.encrypt(request.getEmail())));
        try {
            while(res.next()) {
                resp.setEmail(res.getString(Tables.User.EMAIL));
                resp.setKey(res.getString(Tables.User.PUBLIC_KEY));
                resp.setType(res.getString(Tables.User.TYPE));
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        responseObserver.onNext(resp.build());
        responseObserver.onCompleted();
    }
}
