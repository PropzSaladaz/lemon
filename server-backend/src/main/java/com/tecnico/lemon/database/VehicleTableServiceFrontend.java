package com.tecnico.lemon.database;
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

import static com.tecnico.lemon.contract.VehicleTableServiceOuterClass.*;

public class VehicleTableServiceFrontend {

    private final String target = "localhost:";
    private final ManagedChannel channel;
    private final VehicleTableServiceGrpc.VehicleTableServiceBlockingStub stub;

    public VehicleTableServiceFrontend() {
        channel = ManagedChannelBuilder.forTarget(target).usePlaintext().build();
        stub =  VehicleTableServiceGrpc.newBlockingStub(channel);
    }

    public List<VehicleDto> getVehicles() {

        VehiclesReq request = VehiclesReq.newBuilder().build();
        VehiclesResp resp = stub.getVehicles(request);
        List<VehicleDto> vehicles = resp.getVehiclesList().stream()
                .map(s -> new VehicleDto(s.getVehicle()))
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
