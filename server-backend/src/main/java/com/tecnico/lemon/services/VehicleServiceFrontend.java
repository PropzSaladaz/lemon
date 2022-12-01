package com.tecnico.lemon.services;
import com.tecnico.lemon.contract.*;
import com.tecnico.lemon.database.DataBase;
import com.tecnico.lemon.dtos.VehicleDto;
import com.tecnico.lemon.models.vehicle.Vehicle;

import org.springframework.stereotype.Service;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.*;
import io.grpc.StatusRuntimeException;

import java.util.List;
import java.util.stream.Collectors;


public class VehicleServiceFrontend {

    private final String target = "localhost:";
    private final ManagedChannel channel;
    private final VehicleServiceGrpc.VehicleServiceBlockingStub stub;

    public VehicleServiceFrontend() {

        channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
        stub =  VehicleServiceGrpc.newBlockingStub(channel);

    }

    public List<VehicleDto> getVehicles() {

        VehiclesReq request = VehiclesReq.newBuilder().build();
        VehiclesResp resp = stub.getVehicles(request).getVehilces();
        List<VehicleDto> vehicles = resp.getVehiclesList().stream()
                .map(s -> new VehicleDto(s.getVehicle))
                .collect(Collectors.toList());
        return vehicles;

    }

    public void lockVehicle(int id) {

        lockVehicleReq request = lockVehicleReq.newBuilder().setId(id).build();
        stub.lockVehicle(request);
    }


    public void unlockVehicle(int id) {

        unlockVehicleReq request = unlockVehicleReq.newBuilder().setId(id).build();
        stub.unlockVehicle(request);

    }





}
