package com.tecnico.lemon;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import com.tecnico.lemon.database.DatabaseManager;
import com.tecnico.lemon.database.DatabaseManagerImpl;
import com.tecnico.lemon.services.UserTableServiceImpl;
import com.tecnico.lemon.services.VehicleTableServiceImpl;
import com.tecnico.lemon.services.KeysServiceImpl;
import io.grpc.BindableService;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyServerBuilder;
import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import javax.net.ssl.SSLException;
import java.io.File;
import java.io.IOException;

import java.util.Scanner;

public class LemonDatabaseServer {

    private static final int port = 8082;

    public static void main( String[] args ) throws InterruptedException, IOException {
        System.out.println( "Starting Lemon Database server..." );
        Server server;
        DatabaseManager db = new DatabaseManagerImpl();
        final BindableService vehicleService = new VehicleTableServiceImpl(db);
        final BindableService userService = new UserTableServiceImpl(db);
        final BindableService keysService = new KeysServiceImpl(db);

        String serverCert = "src/main/credentials/server-cert.pem";
        String privateKeyFile = "src/main/credentials/server-key.pem";
        String caCert = "src/main/credentials/ca-cert.pem";

        SslContext sslContext = SSLContext.forServer(serverCert, privateKeyFile, caCert);
        server = NettyServerBuilder.forPort(port)
            .sslContext(sslContext)
            .addService(vehicleService)
            .addService(userService)
            .addService(keysService)
            .build();


        server.start();

        Thread closeState = new Thread(db::close);
        Runtime.getRuntime().addShutdownHook(closeState);

        Scanner scanner = new Scanner(System.in);
        Boolean exit = false;
        while (!exit) {
            // Wait for the user to enter a command
            System.out.println("Waiting for new command: ");
            String command = scanner.nextLine();

            switch (command) {
                case "init database":
                    db.initDatabase();
                    break;
                case "exit":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid command");
                    break;
            }
        }

        System.out.println("Shutting down Lemon Database Server");
        server.shutdown();
    }


}
