package com.tecnico.lemon.services;

import com.tecnico.lemon.contract.VehicleTableServiceGrpc;
import com.tecnico.lemon.database.DatabaseManager;
import com.tecnico.lemon.database.Queries;
import com.tecnico.lemon.database.Tables;
import io.grpc.stub.StreamObserver;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

import static com.tecnico.lemon.contract.VehicleTableServiceOuterClass.*;

public class VehicleTableServiceImpl extends VehicleTableServiceGrpc.VehicleTableServiceImplBase {
    DatabaseManager _db;

    public VehicleTableServiceImpl(DatabaseManager dbInterface) {
        _db = dbInterface;
    }

    public Vehicle buildVehicle(ResultSet res) {
        try {
            return Vehicle.newBuilder()
                .setVehicleId(res.getInt(Tables.Vehicle.VEHICLE_ID))
                .setPrice(res.getInt(Tables.Vehicle.PRICE))
                .setLocation(res.getString(Tables.Vehicle.LOCALIZATION))
                .setReserved(!res.getString(Tables.Vehicle.RESERVATION_ID).equals("NULL"))
                .setDescription(res.getString(Tables.Vehicle.DESCRIPTION))
                .build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Vehicle.newBuilder().build();
        }
    }

    @Override
    public void getVehicles(VehiclesReq request, StreamObserver<VehiclesResp> responseObserver) {
        VehiclesResp.Builder respBuilder = VehiclesResp.newBuilder();
        try{
            ResultSet res = _db.executeQuery(Queries.SELECT_ALL_FROM_VEHICLES);
            while(res.next()) {
                respBuilder.addVehicles(buildVehicle(res));
            }
        }catch(SQLException ex) {
            ex.printStackTrace();
        }
        responseObserver.onNext(respBuilder.build());
        responseObserver.onCompleted();
    }

    @Override 
    public void getUserReservedVehicles(UserReservedVehiclesReq request, StreamObserver<UserReservedVehiclesResp> responseObserver) {
        UserReservedVehiclesResp.Builder respBuilder = UserReservedVehiclesResp.newBuilder(); 
        try {
            List<Integer> userReservedVehicleIds = _db.getUserReservedVehicles(request.getUserId());
            for (Integer vehicle_id: userReservedVehicleIds) {
                ResultSet res = _db.executeQuery(Queries.lookupVehicleById(vehicle_id));
                res.next();
                respBuilder.addVehicles(buildVehicle(res));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        responseObserver.onNext(respBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void reserveVehicle(ReserveVehicleReq request, StreamObserver<ReserveVehicleResp> responseObserver) {

        ReserveVehicleResp.Builder resp = ReserveVehicleResp.newBuilder();
        _db.newVehicleReservation(request.getVehicleId(), request.getUserId());
        responseObserver.onNext(resp.build());
        responseObserver.onCompleted();
    }

    @Override
    public void unlockVehicle(UnlockVehicleReq request, StreamObserver<UnlockVehicleResp> responseObserver) {

        UnlockVehicleResp.Builder resp = UnlockVehicleResp.newBuilder();
        _db.deleteVehicleReservation(request.getVehicleId());
        responseObserver.onNext(resp.build());
        responseObserver.onCompleted();
    }
}
