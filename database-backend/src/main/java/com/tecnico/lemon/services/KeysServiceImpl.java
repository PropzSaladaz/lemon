package com.tecnico.lemon.services;

import com.tecnico.lemon.database.DatabaseManager;

import io.grpc.stub.StreamObserver;

import com.tecnico.lemon.contract.KeysServiceGrpc;
import static com.tecnico.lemon.contract.KeysServiceOuterClass.*;


public class KeysServiceImpl extends KeysServiceGrpc.KeysServiceImplBase {
    DatabaseManager _db;

    public KeysServiceImpl(DatabaseManager db) {
        _db = db;
    }

    @Override
    public void requestSharedKey(RequestSharedKeyReq request, StreamObserver<RequestSharedKeyResp> responseObserver) {
        RequestSharedKeyResp resp = RequestSharedKeyResp.newBuilder().setSharedKey(_db.getB64EncodedSharedKey()).build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }
}