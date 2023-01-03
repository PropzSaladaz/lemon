package com.tecnico.lemon.database;

import com.tecnico.lemon.SSLContext;
import com.tecnico.lemon.contract.VehicleTableServiceGrpc;
import com.tecnico.lemon.dtos.vehicle.VehicleDto;
import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import io.netty.handler.ssl.SslContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.tecnico.lemon.contract.VehicleTableServiceOuterClass.*;

@Component
public class VehicleTableServiceFrontend {

    private final String target = "localhost:8082";
    private final ManagedChannel channel;
    private final VehicleTableServiceGrpc.VehicleTableServiceBlockingStub stub;

    public VehicleTableServiceFrontend() {
        SslContext context = null;
        try {
            String clientCertFile = "src/main/credentials/client-cert.pem";
            String privateKeyFile = "src/main/credentials/client-key.pem";
            String caCert         = "src/main/credentials/ca-cert.pem";
            context = SSLContext.forClient(clientCertFile, privateKeyFile, caCert);
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

    public void reserveVehicle(int id) {
        ReserveVehicleReq request = ReserveVehicleReq.newBuilder().setVehicleId(id).build();
        ReserveVehicleResp resp = stub.reserveVehicle(request);
    }


    public void unlockVehicle(int id) {
        UnlockVehicleReq request = UnlockVehicleReq.newBuilder().setVehicleId(id).build();
        UnlockVehicleResp resp =  stub.unlockVehicle(request);
    }
}
