package com.tecnico.lemon.database;
import com.tecnico.lemon.contract.*;
import com.tecnico.lemon.database.DataBase;
import com.tecnico.lemon.dtos.VehicleDto;
import com.tecnico.lemon.models.vehicle.Vehicle;
import com.tecnico.lemon.contract.VehicleTableServiceGrpc;
import com.tecnico.lemon.dtos.VehicleDto;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.stub.StreamObserver;
import io.netty.handler.ssl.SslContext;

import javax.net.ssl.SSLException;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import static com.tecnico.lemon.contract.VehicleTableServiceOuterClass.*;

public class VehicleTableServiceFrontend {

    private final String target = "localhost:8082";
    private final ManagedChannel channel;
    private final VehicleTableServiceGrpc.VehicleTableServiceBlockingStub stub;

    public SslContext loadTLSCredentials() throws SSLException {
        File serverCACertFile = new File("src/main/credentials/ca-cert.pem");
        File clientCertFile = new File("src/main/credentials/client-cert.pem");
        File clientKeyFile = new File("src/main/credentials/client-key.pem");

        return GrpcSslContexts.forClient()
                .keyManager(clientCertFile, clientKeyFile)
                .trustManager(serverCACertFile)
                .build();
    }

    public VehicleTableServiceFrontend() {
        SslContext context = null;
        try {
            //context = loadTLSCredentials();
        } catch (Exception e) {
            System.err.println("Error Loading Credentials");
        }
        channel = NettyChannelBuilder.forTarget(target).sslContext(context).build();
        stub = VehicleTableServiceGrpc.newBlockingStub(channel);
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
