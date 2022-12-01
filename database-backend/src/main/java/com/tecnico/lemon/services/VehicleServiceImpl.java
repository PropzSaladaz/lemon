package com.tecnico.lemon.services;
import com.tecnico.lemon.contract.*;
import com.tecnico.lemon.database.DatabaseManager;
import com.tecnico.lemon.database.Queries;
import com.tecnico.lemon.database.Tables;
import io.grpc.stub.StreamObserver;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.tecnico.lemon.contract.VehicleServiceOuterClass.*;

public class VehicleServiceImpl extends VehicleServiceGrpc.VehicleServiceImplBase{
    DatabaseManager _db;

    public VehicleServiceImpl(DatabaseManager db) {
        _db = db;
    }
    @Override
    public void getAvailableVehicles(AvailableVehiclesReq request, StreamObserver<AvailableVehiclesResp> responseObserver) {
        AvailableVehiclesResp.Builder resp = AvailableVehiclesResp.newBuilder();
        try{
            ResultSet res = _db.executeQuery(Queries.SELECT_ALL_FROM_USERS);
            while(res.next()) {
                if (!res.getBoolean(Tables.Vehicle.LOCKED)) {
                    Vehicle v = Vehicle.newBuilder()
                            .setDescription(res.getString(Tables.Vehicle.DESCRIPTION))
                            .setPrice(res.getInt(Tables.Vehicle.PRICE))
                            .setId(res.getInt(Tables.Vehicle.ID))
                            .build();
                    resp.addVehicles(v);
                }
            }
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
        responseObserver.onNext(resp.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getLockedVehicles(LockedVehiclesReq request, StreamObserver<LockedVehiclesResp> responseObserver) {
        super.getLockedVehicles(request, responseObserver);
    }
}
