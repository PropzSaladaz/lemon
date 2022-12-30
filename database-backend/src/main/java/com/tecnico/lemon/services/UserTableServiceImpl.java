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
        _db.executeQuery(Queries.insertUser(request.getEmail(), request.getKey(), request.getType()));
        responseObserver.onCompleted();
    }

    @Override 
    public void lookupUser(LookupUserReq request, StreamObserver<LookupUserResp> responseObserver){
        System.out.println("OLAAAAAAAAAAAAAAAA");
        LookupUserResp.Builder resp = LookupUserResp.newBuilder();
        ResultSet res = _db.executeQuery(Queries.lookupUserByEmail(request.getEmail()));
        try{
            while(res.next()) {
                if (res != null) {
                    resp.setEmail(res.getString(Tables.User.EMAIL));
                    resp.setKey(res.getString(Tables.User.PUBLIC_KEY));
                    resp.setType(res.getString(Tables.User.TYPE));
                    resp.setExist(true);
                }else{
                    resp.setExist(false);
                }
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }

        responseObserver.onNext(resp.build());
        responseObserver.onCompleted();
    }
}
