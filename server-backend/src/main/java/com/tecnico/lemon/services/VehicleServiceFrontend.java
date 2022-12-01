package com.tecnico.lemon.services;

import com.tecnico.lemon.contract.VehicleServiceGrpc;
import com.tecnico.lemon.dtos.VehicleDto;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.List;
import java.util.stream.Collectors;

import static com.tecnico.lemon.contract.VehicleServiceOuterClass.*;


public class VehicleServiceFrontend {

    private final String target = "localhost:8080";
    private final ManagedChannel channel;
    private final VehicleServiceGrpc.VehicleServiceBlockingStub stub;

    public VehicleServiceFrontend() {

        channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
        stub =  VehicleServiceGrpc.newBlockingStub(channel);

    }

    public List<VehicleDto> getVehicles() {

        VehiclesReq request = VehiclesReq.newBuilder().build();
        VehiclesResp resp = stub.getVehicles(request);
        return resp.getVehiclesList().stream()
                .map(VehicleDto::new)
                .collect(Collectors.toList());

    }

    public void lockVehicle(int id) {

        LockVehicleReq request = LockVehicleReq.newBuilder().setId(id).build();
        LockVehicleResp resp =  stub.lockVehicle(request);
    }


    public void unlockVehicle(int id) {

        UnlockVehicleReq request = UnlockVehicleReq.newBuilder().setId(id).build();
        UnlockVehicleResp resp =  stub.unlockVehicle(request);

    }





}
