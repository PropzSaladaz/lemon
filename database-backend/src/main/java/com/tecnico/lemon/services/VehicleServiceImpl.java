package com.tecnico.lemon.services;
import com.tecnico.lemon.contract.*;
import com.tecnico.lemon.database.DatabaseManager;
import io.grpc.stub.StreamObserver;

import static com.tecnico.lemon.contract.VehicleServiceOuterClass.*;

public class VehicleServiceImpl extends VehicleServiceGrpc.VehicleServiceImplBase{
    DatabaseManager _db;

    public VehicleServiceImpl(DatabaseManager db) {
        _db = db;
    }
    @Override
    public void getAvailableVehicles(AvailableVehiclesReq request, StreamObserver<AvailableVehiclesResp> responseObserver) {
        String query = "SELECT id, description, price FROM ";
//        responseObserver.onNext();
    }

    @Override
    public void getLockedVehicles(LockedVehiclesReq request, StreamObserver<LockedVehiclesResp> responseObserver) {
        super.getLockedVehicles(request, responseObserver);
    }
}
