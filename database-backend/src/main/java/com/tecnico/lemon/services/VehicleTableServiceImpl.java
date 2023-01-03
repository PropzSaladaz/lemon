package com.tecnico.lemon.services;

import com.tecnico.lemon.contract.VehicleTableServiceGrpc;
import com.tecnico.lemon.database.DatabaseManager;
import com.tecnico.lemon.database.Queries;
import com.tecnico.lemon.database.Tables;
import io.grpc.stub.StreamObserver;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.tecnico.lemon.contract.VehicleTableServiceOuterClass.*;

public class VehicleTableServiceImpl extends VehicleTableServiceGrpc.VehicleTableServiceImplBase {
    DatabaseManager _db;

    public VehicleTableServiceImpl(DatabaseManager dbInterface) {
        _db = dbInterface;
    }

    @Override
    public void getVehicles(VehiclesReq request, StreamObserver<VehiclesResp> responseObserver) {
        VehiclesResp.Builder resp = VehiclesResp.newBuilder();
        try{
            ResultSet res = _db.executeQuery(Queries.SELECT_ALL_FROM_VEHICLES);
            while(res.next()) {
                    Vehicle v = Vehicle.newBuilder()
                            .setVehicleId(res.getInt(Tables.Vehicle.VEHICLE_ID))
                            .setPrice(res.getInt(Tables.Vehicle.PRICE))
                            .setLocation(res.getString(Tables.Vehicle.LOCALIZATION))
                            .setReserved(res.getBoolean(Tables.Vehicle.LOCKED))
                            .setDescription(res.getString(Tables.Vehicle.DESCRIPTION))
                            .build();
                    resp.addVehicles(v);

            }
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
        responseObserver.onNext(resp.build());
        responseObserver.onCompleted();
    }

    @Override
    public void reserveVehicle(ReserveVehicleReq request, StreamObserver<ReserveVehicleResp> responseObserver) {

        ReserveVehicleResp.Builder resp = ReserveVehicleResp.newBuilder();
        _db.newVehicleReservation(request.getVehicleId());
        responseObserver.onNext(resp.build());
        responseObserver.onCompleted();
    }

    @Override
    public void unlockVehicle(UnlockVehicleReq request, StreamObserver<UnlockVehicleResp> responseObserver) {

        UnlockVehicleResp.Builder resp = UnlockVehicleResp.newBuilder();
        _db.executeQuery("update " + Tables.Vehicle.TABLE_NAME + " set locked = " + false + " where id= " + request.getVehicleId());
        responseObserver.onNext(resp.build());
        responseObserver.onCompleted();
    }
}
