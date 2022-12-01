package com.tecnico.lemon;

import com.tecnico.lemon.database.DatabaseManager;
import io.grpc.BindableService;
import io.grpc.Server;

public class LemonServer {
    Server server;
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        DatabaseManager db = new DatabaseManager();

        final BindableService databaseServiceImpl;
//        server = new ServerBuilder.forPort(port)
//                .addService(databaseServiceImpl)
//                .build();
    }
}
