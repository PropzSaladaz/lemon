package com.tecnico.lemon;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import com.tecnico.lemon.database.DatabaseManager;
import com.tecnico.lemon.database.DatabaseManagerImpl;
import com.tecnico.lemon.services.UserTableServiceImpl;
import com.tecnico.lemon.services.VehicleTableServiceImpl;
import io.grpc.BindableService;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyServerBuilder;
import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import java.io.File;
import java.io.IOException;

import javax.net.ssl.SSLException;
import java.io.File;
import java.io.IOException;

public class LemonDatabaseServer {

    private static final int port = 8082;
    public static SslContext loadTLSCredentials() throws SSLException {
        File serverCertificateFile = new File("src/main/credentials/server-cert.pem");
        File privateKeyFile = new File("src/main/credentials/server-key.pem");
        File clientCACertificateFile = new File("src/main/credentials/ca-cert.pem");

        SslContextBuilder contextBuilder = SslContextBuilder.forServer(serverCertificateFile, privateKeyFile)
                .clientAuth(ClientAuth.REQUIRE)
                .trustManager(clientCACertificateFile);

        return GrpcSslContexts.configure(contextBuilder).build();
    }
    public static void main( String[] args ) throws InterruptedException, IOException {
        System.out.println( "Starting Lemon Database server..." );
        Server server;
        DatabaseManager db = new DatabaseManagerImpl();
        db.buildSchema();
        final BindableService vehicleService = new VehicleTableServiceImpl(db);
        final BindableService userService = new UserTableServiceImpl(db);
        SslContext sslContext = loadTLSCredentials();

        server = NettyServerBuilder.forPort(port)
            .sslContext(sslContext)
            .addService(vehicleService)
            //.addService(userService)
            .build();

        server.start();

        Thread closeState = new Thread(db::close);
        Runtime.getRuntime().addShutdownHook(closeState);
        server.awaitTermination();
    }


}
