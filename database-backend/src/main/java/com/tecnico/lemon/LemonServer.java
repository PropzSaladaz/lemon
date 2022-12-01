package com.tecnico.lemon;

import com.tecnico.lemon.database.DatabaseManager;
import com.tecnico.lemon.database.DatabaseManagerImpl;
import com.tecnico.lemon.services.UserServiceImpl;
import com.tecnico.lemon.services.VehicleServiceImpl;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class LemonServer {
    private static final int port = 8080;
    public static void main( String[] args ) throws InterruptedException {
        System.out.println( "Starting Lemon Database server..." );
        Server server;
        DatabaseManager db = new DatabaseManagerImpl();
        db.buildSchema();

        final BindableService vehicleService = new VehicleServiceImpl(db);
        final BindableService userService = new UserServiceImpl(db);

        server = ServerBuilder.forPort(port)
                .addService(vehicleService)
                .build();

        Thread closeState = new Thread(db::close);
        Runtime.getRuntime().addShutdownHook(closeState);
        server.awaitTermination();
    }
}
